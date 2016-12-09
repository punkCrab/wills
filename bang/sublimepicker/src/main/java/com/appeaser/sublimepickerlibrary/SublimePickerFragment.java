/*
 * Copyright 2015 Vikram Kakkar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appeaser.sublimepickerlibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeListenerAdapter;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;

import java.text.DateFormat;
import java.util.Locale;
import java.util.TimeZone;

public class SublimePickerFragment extends DialogFragment {
    DateFormat mDateFormatter, mTimeFormatter;
    SublimePicker mSublimePicker;
    Callback mCallback;
    public static final int TIME_ALL = 1;
    public static final int TIME_DATE = 2;
    public static final int TIME_TIME = 3;
    private int state = 0;

    SublimeListenerAdapter mListener = new SublimeListenerAdapter() {
        @Override
        public void onCancelled() {
            if (mCallback!= null) {
                mCallback.onCancelled();
            }
            dismiss();
        }

        @Override
        public void onDateTimeRecurrenceSet(SublimePicker sublimeMaterialPicker,
                                            SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {
            if (mCallback != null) {
                mCallback.onDateTimeRecurrenceSet(selectedDate,
                        String.format("%02d",hourOfDay), String.format("%02d",minute), recurrenceOption, recurrenceRule);
            }
            dismiss();
        }
    };

    public SublimePickerFragment() {
        mDateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
        mTimeFormatter = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.getDefault());
        mTimeFormatter.setTimeZone(TimeZone.getTimeZone("GMT+0"));
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mSublimePicker = (SublimePicker) getActivity().getLayoutInflater().inflate(R.layout.sublime_picker, container);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        state = getArguments().getInt("state",0);
        SublimeOptions options = getOptions(state);
        mSublimePicker.initializePicker(options, mListener);
        return mSublimePicker;
    }

    private SublimeOptions getOptions(int state){
        SublimeOptions options = new SublimeOptions();
        int displayOptions = 0;
        switch (state){
            case TIME_ALL:
                displayOptions |= SublimeOptions.ACTIVATE_DATE_PICKER;
                displayOptions |= SublimeOptions.ACTIVATE_TIME_PICKER;
                options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
                break;
            case TIME_DATE:
                displayOptions = SublimeOptions.ACTIVATE_DATE_PICKER;
                options.setPickerToShow(SublimeOptions.Picker.DATE_PICKER);
                break;
            case TIME_TIME:
                displayOptions = SublimeOptions.ACTIVATE_TIME_PICKER;
                options.setPickerToShow(SublimeOptions.Picker.TIME_PICKER);
                break;
            default:
                break;
        }
        options.setDisplayOptions(displayOptions);
        return options;
    }

    public static SublimePickerFragment newInstance(int state) {
        
        Bundle args = new Bundle();
        args.putInt("state",state);
        SublimePickerFragment fragment = new SublimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    public interface Callback {
        void onCancelled();
        void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                     String hourOfDay, String minute,
                                     SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                     String recurrenceRule);
    }
}
