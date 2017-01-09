package com.wills.help.release.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.SublimePickerFragment;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.db.bean.OrderTypeInfo;
import com.wills.help.db.bean.PointInfo;
import com.wills.help.db.manager.OrderTypeInfoHelper;
import com.wills.help.db.manager.PointInfoHelper;
import com.wills.help.pay.ui.PayActivity;
import com.wills.help.utils.IntentUtils;
import com.wills.help.utils.ScreenUtils;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * com.wills.help.release.ui
 * Created by lizhaoyong
 * 2016/11/14.
 */

public class ReleaseFragment extends BaseFragment implements View.OnClickListener{

    private TextView tv_release_state,tv_release_from,tv_release_time_start,tv_release_time_end,tv_release_send;
    private EditText et_release_from_address,et_release_money,et_release_bargain,et_release_send_address;
    private Button btn_submit;
    private int timeType = 0;
    String[] state ;
    String[] stateId;
    String[] address;
    String[] addressId;
    public static ReleaseFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ReleaseFragment fragment = new ReleaseFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    public View initView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.fragment_release,null);
        tv_release_state = (TextView) view.findViewById(R.id.tv_release_state);
        tv_release_from = (TextView) view.findViewById(R.id.tv_release_from);
        tv_release_time_start = (TextView) view.findViewById(R.id.tv_release_time_start);
        tv_release_time_end = (TextView) view.findViewById(R.id.tv_release_time_end);
        tv_release_send = (TextView) view.findViewById(R.id.tv_release_send);
        et_release_from_address = (EditText) view.findViewById(R.id.et_release_from_address);
        et_release_money = (EditText) view.findViewById(R.id.et_release_money);
        et_release_bargain = (EditText) view.findViewById(R.id.et_release_bargain);
        et_release_send_address = (EditText) view.findViewById(R.id.et_release_send_address);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        initListener();
        OrderTypeInfoHelper.getInstance().queryAll()
                .doOnNext(new Action1<List<OrderTypeInfo>>() {
                    @Override
                    public void call(List<OrderTypeInfo> orderTypeInfos) {
                        state = new String[orderTypeInfos.size()];
                        stateId = new String[orderTypeInfos.size()];
                        for (int i=0;i<orderTypeInfos.size();i++){
                            state[i] = orderTypeInfos.get(i).getOrdertype();
                            stateId[i] = orderTypeInfos.get(i).getTypeid();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<OrderTypeInfo>>() {
                    @Override
                    public void onCompleted() {
                        tv_release_state.setText(state[0]);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<OrderTypeInfo> orderTypeInfos) {

                    }
                });
        PointInfoHelper.getInstance().queryAll()
                .doOnNext(new Action1<List<PointInfo>>() {
                    @Override
                    public void call(List<PointInfo> pointInfos) {
                        address = new String[pointInfos.size()];
                        addressId = new String[pointInfos.size()];
                        for (int i=0;i<pointInfos.size();i++){
                            address[i] = pointInfos.get(i).getPosname();
                            addressId[i] = pointInfos.get(i).getPosname();
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<PointInfo>>() {
                    @Override
                    public void onCompleted() {
                        tv_release_from.setText(address[0]);
                        tv_release_send.setText(address[0]);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<PointInfo> pointInfos) {

                    }
                });
    }

    private void initListener(){
        tv_release_state.setOnClickListener(this);
        tv_release_from.setOnClickListener(this);
        tv_release_time_start.setOnClickListener(this);
        tv_release_time_end.setOnClickListener(this);
        tv_release_send.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_release_state:
                showAlert(state,tv_release_state);
                break;
            case R.id.tv_release_from:
                showAlert(address,tv_release_from);
                break;
            case R.id.tv_release_time_start:
                timeType = 1;
                SublimePickerFragment start = SublimePickerFragment.newInstance(SublimePickerFragment.TIME_TIME);
                start.setCallback(callback);
                start.show(getAppCompatActivity().getSupportFragmentManager(), "RELEASE_START");
                break;
            case R.id.tv_release_time_end:
                timeType = 2;
                SublimePickerFragment end = SublimePickerFragment.newInstance(SublimePickerFragment.TIME_TIME);
                end.setCallback(callback);
                end.show(getAppCompatActivity().getSupportFragmentManager(), "RELEASE_START");
                break;
            case R.id.tv_release_send:
                showAlert(address,tv_release_send);
                break;
            case R.id.btn_submit:
                IntentUtils.startActivity(getAppCompatActivity(), PayActivity.class);
                break;
        }
    }

    SublimePickerFragment.Callback callback = new SublimePickerFragment.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate, String hourOfDay, String minute, SublimeRecurrencePicker.RecurrenceOption recurrenceOption, String recurrenceRule) {
            if (timeType == 1){
                tv_release_time_start.setText(hourOfDay+":"+minute);
            }else if (timeType == 2){
                tv_release_time_end.setText(hourOfDay+":"+minute);
            }
        }
    };

    private void showAlert(final String[] strings , final TextView textView){
        AlertDialog.Builder builder = new AlertDialog.Builder(getAppCompatActivity());
        builder.setItems(strings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                textView.setText(strings[i]);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        if (strings.length>10){
            WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
            params.height = 3*ScreenUtils.getScreenHeight(getAppCompatActivity())/4;
            dialog.getWindow().setAttributes(params);
        }
    }
}
