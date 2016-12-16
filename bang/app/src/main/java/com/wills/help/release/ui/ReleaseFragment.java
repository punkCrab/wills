package com.wills.help.release.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.appeaser.sublimepickerlibrary.SublimePickerFragment;
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.wills.help.R;
import com.wills.help.base.BaseFragment;
import com.wills.help.pay.ui.PayActivity;
import com.wills.help.utils.IntentUtils;

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
    String[] state = new String[]{"取快递","买零食"};
    String[] str = new String[]{"申通","圆通","中通","顺风","韵达"};
    String[] address = new String[]{"北京交通大学第七教学楼","北京交通大学第八教学楼","北京交通大学第九教学楼"};
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
                showAlert(str,tv_release_from);
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
        }).show();
    }

}
