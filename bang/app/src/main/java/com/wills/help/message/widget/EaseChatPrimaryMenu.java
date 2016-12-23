package com.wills.help.message.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wills.help.R;
import com.wills.help.message.ui.EaseChatFragment;
import com.wills.help.utils.KeyBoardUtils;
import com.wills.help.utils.ScreenUtils;


/**
 * primary menu
 *
 */
public class EaseChatPrimaryMenu extends EaseChatPrimaryMenuBase implements OnClickListener {
    private EditText editText;
    private View buttonSetModeKeyboard;
    private View buttonSetModeVoice;
    private View buttonSend;
    private TextView buttonPressToSpeak;
    private Button buttonMore;
    private Button btn_face;
    private Button btn_keyboard;
    private Context context;
    private LinearLayout ll_root;

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EaseChatPrimaryMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(final Context context, AttributeSet attrs) {
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.ease_widget_chat_primary_menu, this);
        ll_root = (LinearLayout) findViewById(R.id.ll_root);
        editText = (EditText) findViewById(R.id.et_sendmessage);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonSend = findViewById(R.id.btn_send);
        buttonPressToSpeak = (TextView) findViewById(R.id.tv_talk);
        buttonMore = (Button) findViewById(R.id.btn_more);
        btn_face = (Button) findViewById(R.id.btn_face);
        btn_keyboard = (Button) findViewById(R.id.btn_keyboard);
        editText.setBackgroundResource(R.drawable.ease_input_bar_bg_normal);

        btn_keyboard.setOnClickListener(this);
        buttonSend.setOnClickListener(this);
        buttonSetModeKeyboard.setOnClickListener(this);
        buttonSetModeVoice.setOnClickListener(this);
        buttonMore.setOnClickListener(this);
        btn_face.setOnClickListener(this);
        editText.setOnClickListener(this);
        editText.requestFocus();
        
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    editText.setBackgroundResource(R.drawable.ease_input_bar_bg_active);
                } else {
                    editText.setBackgroundResource(R.drawable.ease_input_bar_bg_normal);
                }

            }
        });
        // listen the text change
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    buttonMore.setVisibility(View.GONE);
                    buttonSend.setVisibility(View.VISIBLE);
                } else {
                    buttonMore.setVisibility(View.VISIBLE);
                    buttonSend.setVisibility(View.GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        
        
        buttonPressToSpeak.setOnTouchListener(new OnTouchListener() {
            
            @Override 
            public boolean onTouch(View v, MotionEvent event) {
                if(listener != null){
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });
    }
    
    /**
     * set recorder view when speak icon is touched
     * @param voiceRecorderView
     */
    public void setPressToSpeakRecorderView(EaseVoiceRecorderView voiceRecorderView){
        EaseVoiceRecorderView voiceRecorderView1 = voiceRecorderView;
    }

    /**
     * append emoji icon to editText
     * @param emojiContent
     */
    public void onEmojiconInputEvent(CharSequence emojiContent){
        editText.append(emojiContent);
    }
    
    /**
     * delete emojicon
     */
    public void onEmojiconDeleteEvent(){
        if (!TextUtils.isEmpty(editText.getText())) {
            KeyEvent event = new KeyEvent(0, 0, 0, KeyEvent.KEYCODE_DEL, 0, 0, 0, 0, KeyEvent.KEYCODE_ENDCALL);
            editText.dispatchKeyEvent(event);
        }
    }
    
    /**
     * on clicked event
     * @param view
     */
    @Override
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btn_send) {
            if(listener != null){
                String s = editText.getText().toString();
                editText.setText("");
                listener.onSendBtnClicked(s);
            }
        } else if (id == R.id.btn_set_mode_voice) {
            if (voiceListener!=null){
                voiceListener.requestVoice(new EaseChatFragment.PermissionListener() {
                    @Override
                    public void permissionSuccess() {
                        setModeVoice();
                        if(listener != null)
                            listener.onToggleVoiceBtnClicked();
                    }
                });
            }
        } else if (id == R.id.btn_set_mode_keyboard) {
            setModeKeyboard();
            if(listener != null)
                listener.onToggleVoiceBtnClicked();
        } else if (id == R.id.btn_more) {
            buttonSetModeVoice.setVisibility(View.VISIBLE);
            buttonSetModeKeyboard.setVisibility(View.GONE);
            editText.setVisibility(View.VISIBLE);
            buttonPressToSpeak.setVisibility(View.GONE);
            btn_face.setVisibility(View.VISIBLE);
            btn_keyboard.setVisibility(View.GONE);
            if(listener != null)
                listener.onToggleExtendClicked();
        } else if (id == R.id.et_sendmessage) {
            if(listener != null)
                listener.onEditTextClicked();
        } else if (id == R.id.btn_face) {
            showKeyboard();
            KeyBoardUtils.closeKeybord(context,editText);
            if(listener != null){
                listener.onToggleEmojiconClicked();
            }
        } else if (id == R.id.btn_keyboard){
            showFace();
            KeyBoardUtils.openKeybord(context,editText,100);
            if(listener != null){
                listener.onEditTextClicked();
            }
        }
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    break;
            }
        }
    };

    private void showFace(){
        btn_face.setVisibility(View.VISIBLE);
        btn_keyboard.setVisibility(View.GONE);
    }

    private void showKeyboard(){
        btn_face.setVisibility(View.GONE);
        btn_keyboard.setVisibility(View.VISIBLE);
    }


    /**
     * show voice icon when speak bar is touched
     * 
     */
    protected void setModeVoice() {
        hideKeyboard();
        editText.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.GONE);
        buttonSetModeKeyboard.setVisibility(View.VISIBLE);
        buttonSend.setVisibility(View.GONE);
        buttonMore.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.VISIBLE);
        btn_face.setVisibility(View.GONE);
        btn_keyboard.setVisibility(View.GONE);
    }

    /**
     * show keyboard
     */
    protected void setModeKeyboard() {
        editText.setVisibility(View.VISIBLE);
        buttonSetModeKeyboard.setVisibility(View.GONE);
        buttonSetModeVoice.setVisibility(View.VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        // buttonSend.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(View.GONE);
        btn_face.setVisibility(View.VISIBLE);
        btn_keyboard.setVisibility(View.GONE);
        if (TextUtils.isEmpty(editText.getText())) {
            buttonMore.setVisibility(View.VISIBLE);
            buttonSend.setVisibility(View.GONE);
        } else {
            buttonMore.setVisibility(View.GONE);
            buttonSend.setVisibility(View.VISIBLE);
        }
        editText.requestFocus();
        KeyBoardUtils.openKeybord(context,editText,100);
    }
    
    @Override
    public void onExtendMenuContainerHide() {
        if (buttonPressToSpeak.getVisibility() == View.GONE){
            showFace();
        }
    }

    @Override
    public void onTextInsert(CharSequence text) {
       int start = editText.getSelectionStart();
       Editable editable = editText.getEditableText();
       editable.insert(start, text);
       setModeKeyboard();
    }

    @Override
    public EditText getEditText() {
        return editText;
    }

    @Override
    public void canEdit(boolean isEdit) {
        if (isEdit){
//            editText.setBackgroundResource(R.drawable.ease_input_bar_bg_active);
        }else {
//            editText.setBackgroundResource(R.drawable.ease_input_bar_bg_normal);
        }
    }

}
