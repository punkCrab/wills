package com.wills.help.assist.ui;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
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
        marker();
    }

    /**
     * 定位
     */
    private void location(){
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
    }

    private void marker(){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(40.03, 116.30));
        markerOptions.title("wills").snippet("wills：110");
        markerOptions.draggable(false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.map_bubble)));
        markerOptions.setFlat(true);
        aMap.addMarker(markerOptions);
    }
}
