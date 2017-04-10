package com.wills.help.assist.ui;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.hyphenate.chat.EMClient;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.wills.help.R;
import com.wills.help.assist.model.OrderNum;
import com.wills.help.assist.presenter.OrderNumPresenterImpl;
import com.wills.help.assist.view.OrderNumView;
import com.wills.help.base.BaseFragment;
import com.wills.help.message.ui.MessageActivity;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ToastUtils;

import java.util.List;

import rx.functions.Action1;

/**
 * com.wills.help.assist.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class AssistFragment extends BaseFragment implements OrderNumView{

    private Toolbar toolbar;
    private MapView mapView;
    private FrameLayout frameLayout;
    private TextView tv_amount;
    private AMap aMap;
    private MyLocationStyle myLocationStyle;
    private OrderNumPresenterImpl orderNumPresenter;
    private boolean isFirst = true;//第一次进来
    private ImageView imageView;
    RxPermissions rxPermissions;

    public static AssistFragment newInstance() {

        Bundle args = new Bundle();

        AssistFragment fragment = new AssistFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_assist, null);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        frameLayout = (FrameLayout) view.findViewById(R.id.fl_map);
        tv_amount = (TextView) view.findViewById(R.id.tv_amount);
        imageView = (ImageView) view.findViewById(R.id.image);
        setHasOptionsMenu(true);
        toolbar.setLogo(R.drawable.title);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        rxPermissions = new RxPermissions(getAppCompatActivity());
        toolbar.inflateMenu(R.menu.menu_base);
        toolbar.getMenu().getItem(0).setIcon(R.drawable.msg);
        toolbar.getMenu().getItem(0).setTitle(R.string.tab_msg);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_right:
                        IntentUtils.startActivity(getAppCompatActivity(), MessageActivity.class);
                        break;
                }
                return true;
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        .subscribe(new Action1<Boolean>() {
                            @Override
                            public void call(Boolean aBoolean) {
                                if (aBoolean){
                                    location();
                                }else {
                                    ToastUtils.toast("permission is not granted");
                                }
                            }
                        });
            }
        });
        LatLng centerBJPoint= new LatLng(39.952272,116.342779);
        AMapOptions mapOptions = new AMapOptions();
        mapOptions.camera(new CameraPosition(centerBJPoint, 17f, 0, 0));
        mapView = new MapView(getAppCompatActivity(),mapOptions);
        mapView.onCreate(savedInstanceState);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mapView.setLayoutParams(layoutParams);
        frameLayout.addView(mapView);
        aMap = mapView.getMap();
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = new Bundle();
                bundle.putString("srcid",((OrderNum.PosNum)marker.getObject()).getSrcid());
                bundle.putString("blockid",((OrderNum.PosNum)marker.getObject()).getBlockid());
                IntentUtils.startActivity(getAppCompatActivity(),AssistListActivity.class,bundle);
                return false;
            }
        });
//        LatLng southwestLatLng = new LatLng(39.26, 115.25);
//        LatLng northeastLatLng = new LatLng(41.03, 117.30);
//        LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng, northeastLatLng);
//        aMap.setMapStatusLimits(latLngBounds);
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(17f));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            changeMsgIcon();
            if (aMap !=null){
                aMap.clear(true);
            }
            if (orderNumPresenter == null){
                orderNumPresenter = new OrderNumPresenterImpl(this);
            }
            orderNumPresenter.getOrderNum();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        if (isVisible()){
            changeMsgIcon();
            if (aMap !=null&&!isFirst){
                aMap.clear(true);
            }else {
                isFirst = false;
            }
            if (orderNumPresenter == null){
                orderNumPresenter = new OrderNumPresenterImpl(this);
            }
            orderNumPresenter.getOrderNum();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void changeMsgIcon(){
        int count = EMClient.getInstance().chatManager().getUnreadMsgsCount();
        if (count > 0) {
            if (!toolbar.getMenu().getItem(0).getIcon().equals(getResources().getDrawable(R.drawable.msg_new))){
                toolbar.getMenu().getItem(0).setIcon(R.drawable.msg_new);
                toolbar.invalidate();
            }
        } else {
            if (!toolbar.getMenu().getItem(0).getIcon().equals(getResources().getDrawable(R.drawable.msg))){
                toolbar.getMenu().getItem(0).setIcon(R.drawable.msg);
                toolbar.invalidate();
            }
        }
    }

    @Override
    public void setOrderNum(OrderNum orderNum) {
        tv_amount.setText(String.format(getString(R.string.accept_count),String.valueOf(orderNum.getCount())));
        marker(orderNum.getData());
    }

    /**
     * 定位
     */
    private void location(){
        if (myLocationStyle == null){
            myLocationStyle = new MyLocationStyle();
            myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
//            myLocationStyle.interval(2000);
            myLocationStyle.strokeColor(getResources().getColor(R.color.map_stroke));
            myLocationStyle.radiusFillColor(getResources().getColor(R.color.map_radiusFill));
            myLocationStyle.strokeWidth(1f);
        }
        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
    }

    private void marker(List<OrderNum.PosNum> list){
        for (OrderNum.PosNum posNum : list){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Double.parseDouble(posNum.getLat()), Double.parseDouble(posNum.getLng())));
            markerOptions.draggable(false);
            Bitmap bitmap = getBitmap(posNum.getCount());
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            markerOptions.anchor(0.3f,0.5f);
            aMap.addMarker(markerOptions).setObject(posNum);
        }
    }

    private Bitmap getBitmap(String string){
        Bitmap bitmap = BitmapDescriptorFactory.fromResource(R.drawable.map_bubble).getBitmap();
        bitmap = Bitmap.createBitmap(bitmap, 0 ,0, bitmap.getWidth(),bitmap.getHeight());
        Canvas canvas = new Canvas(bitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(36f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(getResources().getColor(R.color.white));
        canvas.drawText(string, bitmap.getWidth()/4, bitmap.getHeight()/4 + (textPaint.descent()-textPaint.ascent()) / 2 ,textPaint);
        return bitmap;
    }
}
