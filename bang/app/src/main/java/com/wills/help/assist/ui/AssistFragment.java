package com.wills.help.assist.ui;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.hyphenate.chat.EMClient;
import com.wills.help.R;
import com.wills.help.assist.model.OrderNum;
import com.wills.help.assist.presenter.OrderNumPresenterImpl;
import com.wills.help.assist.view.OrderNumView;
import com.wills.help.base.BaseFragment;
import com.wills.help.message.ui.MessageActivity;
import com.wills.help.utils.IntentUtils;

import java.util.List;

/**
 * com.wills.help.assist.ui
 * Created by lizhaoyong
 * 2016/11/8.
 */

public class AssistFragment extends BaseFragment implements OrderNumView{

    private Toolbar toolbar;
    private MapView mapView;
    private AMap aMap;
    private OrderNumPresenterImpl orderNumPresenter;

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
        setHasOptionsMenu(true);
        toolbar.setLogo(R.drawable.title);
        mapView = (MapView) view.findViewById(R.id.map);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
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
        mapView.onCreate(savedInstanceState);
        if (aMap == null){
            aMap = mapView.getMap();
        }
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = new Bundle();
                bundle.putString("srcid",((OrderNum.PosNum)marker.getObject()).getSrcid());
                IntentUtils.startActivity(getAppCompatActivity(),AssistListActivity.class,bundle);
                return false;
            }
        });
        LatLng southwestLatLng = new LatLng(39.26, 115.25);
        LatLng northeastLatLng = new LatLng(41.03, 117.30);
        LatLngBounds latLngBounds = new LatLngBounds(southwestLatLng, northeastLatLng);
        aMap.setMapStatusLimits(latLngBounds);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17f));
        location();
        orderNumPresenter = new OrderNumPresenterImpl(this);
        orderNumPresenter.getOrderNum();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            changeMsgIcon();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
        changeMsgIcon();
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
        marker(orderNum.getData());
    }

    /**
     * 定位
     */
    private void location(){
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000);
        myLocationStyle.strokeColor(getResources().getColor(R.color.map_stroke));
        myLocationStyle.radiusFillColor(getResources().getColor(R.color.map_radiusFill));
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
    }

    private void marker(List<OrderNum.PosNum> list){
        for (OrderNum.PosNum posNum : list){
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(Double.parseDouble(posNum.getLat()), Double.parseDouble(posNum.getLng())));
            markerOptions.draggable(false);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(getBitmap(posNum.getCount())));
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
