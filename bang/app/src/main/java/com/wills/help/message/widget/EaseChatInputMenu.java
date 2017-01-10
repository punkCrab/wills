package com.wills.help.message.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wills.help.R;
import com.wills.help.message.domain.EaseEmojicon;
import com.wills.help.message.domain.EaseEmojiconGroupEntity;
import com.wills.help.message.model.EaseDefaultEmojiconDatas;
import com.wills.help.message.utils.EaseSmileUtils;
import com.wills.help.message.widget.emojicon.EaseEmojiconMenu;
import com.wills.help.message.widget.emojicon.EaseEmojiconMenuBase;
import com.wills.help.utils.KeyBoardUtils;
import com.wills.help.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * input menu
 * 
 * including below component:
 *    EaseChatPrimaryMenu: main menu bar, text input, send button
 *    EaseChatExtendMenu: grid menu with image, file, location, etc
 *    EaseEmojiconMenu: emoji icons
 */
public class EaseChatInputMenu extends LinearLayout {
    FrameLayout primaryMenuContainer, emojiconMenuContainer;
    protected EaseChatPrimaryMenuBase chatPrimaryMenu;
    protected EaseEmojiconMenuBase emojiconMenu;
    protected EaseChatExtendMenu chatExtendMenu;
    protected FrameLayout chatExtendMenuContainer;
    protected LayoutInflater layoutInflater;

    private Handler handler = new Handler();
    private ChatInputMenuListener listener;
    private Context context;
    private boolean inited;
    private View root,contentView;
    private int KeyboardHeight = 0;

    public EaseChatInputMenu(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public EaseChatInputMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EaseChatInputMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.ease_widget_chat_input_menu, this);
        primaryMenuContainer = (FrameLayout) findViewById(R.id.primary_menu_container);
        emojiconMenuContainer = (FrameLayout) findViewById(R.id.emojicon_menu_container);
        chatExtendMenuContainer = (FrameLayout) findViewById(R.id.extend_menu_container);
         // extend menu
        chatExtendMenu = (EaseChatExtendMenu) findViewById(R.id.extend_menu);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, KeyBoardUtils.getKeyBoardHeight());
        chatExtendMenu.setLayoutParams(params);


    }

    /**
     * init view 
     * 
     * This method should be called after registerExtendMenuItem(), setCustomEmojiconMenu() and setCustomPrimaryMenu().
     * @param emojiconGroupList --will use default if null
     */
    @SuppressLint("InflateParams")
    public void init(View rootView ,View contentView ,List<EaseEmojiconGroupEntity> emojiconGroupList) {
        root = rootView;
        this.contentView = contentView;
        if(inited){
            return;
        }
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                root.getWindowVisibleDisplayFrame(r);
                int screenHeight = ScreenUtils.getScreenHeight(context);
                KeyboardHeight = screenHeight-(r.bottom-r.top)-ScreenUtils.getStatusHeight(context);
            }
        });
        // primary menu, use default if no customized one
        if(chatPrimaryMenu == null){
            chatPrimaryMenu = (EaseChatPrimaryMenu) layoutInflater.inflate(R.layout.ease_layout_chat_primary_menu, null);
        }
        primaryMenuContainer.addView(chatPrimaryMenu);

        // emojicon menu, use default if no customized one
        if(emojiconMenu == null){
            emojiconMenu = (EaseEmojiconMenu) layoutInflater.inflate(R.layout.ease_layout_emojicon_menu, null);
            if(emojiconGroupList == null){
                emojiconGroupList = new ArrayList<EaseEmojiconGroupEntity>();
                emojiconGroupList.add(new EaseEmojiconGroupEntity(R.drawable.d_taikaixin,  Arrays.asList(EaseDefaultEmojiconDatas.getData())));
            }
            ((EaseEmojiconMenu)emojiconMenu).init(emojiconGroupList);
        }
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, KeyBoardUtils.getKeyBoardHeight());
        emojiconMenu.setLayoutParams(params);
        emojiconMenuContainer.addView(emojiconMenu);
        processChatMenu();
        chatExtendMenu.init();
        inited = true;
    }

    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        params.height = contentView.getHeight();
        params.weight = 0.0F;
    }

    private void unlockContentHeightDelayed() {
        chatPrimaryMenu.getEditText().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) contentView.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }

    private boolean isKeyboardOpen(){
        return KeyboardHeight==0?false:true;
    }

    /**
     * set custom emojicon menu
     * @param customEmojiconMenu
     */
    public void setCustomEmojiconMenu(EaseEmojiconMenuBase customEmojiconMenu){
        this.emojiconMenu = customEmojiconMenu;
    }
    
    /**
     * set custom primary menu
     * @param customPrimaryMenu
     */
    public void setCustomPrimaryMenu(EaseChatPrimaryMenuBase customPrimaryMenu){
        this.chatPrimaryMenu = customPrimaryMenu;
    }
    
    public EaseChatPrimaryMenuBase getPrimaryMenu(){
        return chatPrimaryMenu;
    }
    
    public EaseChatExtendMenu getExtendMenu(){
        return chatExtendMenu;
    }
    
    public EaseEmojiconMenuBase getEmojiconMenu(){
        return emojiconMenu;
    }
    

    /**
     * register menu item
     * 
     * @param name
     *            item name
     * @param drawableRes
     *            background of item
     * @param itemId
     *             id
     * @param listener
     *            on click event of item
     */
    public void registerExtendMenuItem(String name, int drawableRes, int itemId,
            EaseChatExtendMenu.EaseChatExtendMenuItemClickListener listener) {
        chatExtendMenu.registerMenuItem(name, drawableRes, itemId, listener);
    }

    /**
     * register menu item
     * 
     * @param nameRes
     *            resource id of item name
     * @param drawableRes
     *            background of item
     * @param itemId
     *             id
     * @param listener
     *            on click event of item
     */
    public void registerExtendMenuItem(int nameRes, int drawableRes, int itemId,
            EaseChatExtendMenu.EaseChatExtendMenuItemClickListener listener) {
        chatExtendMenu.registerMenuItem(nameRes, drawableRes, itemId, listener);
    }


    protected void processChatMenu() {
        // send message button
        chatPrimaryMenu.setChatPrimaryMenuListener(new EaseChatPrimaryMenuBase.EaseChatPrimaryMenuListener() {

            @Override
            public void onSendBtnClicked(String content) {
                if (listener != null)
                    listener.onSendMessage(content);
            }

            @Override
            public void onToggleVoiceBtnClicked() {
                voiceClick();
            }

            @Override
            public void onToggleExtendClicked() {
                toggleMore();
            }

            @Override
            public void onToggleEmojiconClicked() {
                toggleEmojicon();
            }

            @Override
            public void onEditTextClicked() {
                hideExtendMenuContainer();
            }


            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                if(listener != null){
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });

        // emojicon menu
        emojiconMenu.setEmojiconMenuListener(new EaseEmojiconMenuBase.EaseEmojiconMenuListener() {

            @Override
            public void onExpressionClicked(EaseEmojicon emojicon) {
                if(emojicon.getType() != EaseEmojicon.Type.BIG_EXPRESSION){
                    if(emojicon.getEmojiText() != null){
                        chatPrimaryMenu.onEmojiconInputEvent(EaseSmileUtils.getSmiledText(context,emojicon.getEmojiText()));
                    }
                }else{
                    if(listener != null){
                        listener.onBigExpressionClicked(emojicon);
                    }
                }
            }

            @Override
            public void onDeleteImageClicked() {
                chatPrimaryMenu.onEmojiconDeleteEvent();
            }
        });

    }
    
   
    /**
     * insert text
     * @param text
     */
    public void insertText(String text){
        getPrimaryMenu().onTextInsert(text);
    }

    /**
     * show or hide extend menu
     * 
     */
    protected void toggleMore() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            if (isKeyboardOpen()){
                lockContentHeight();
                chatExtendMenuContainer.setVisibility(View.VISIBLE);
                chatExtendMenu.setVisibility(View.VISIBLE);
                emojiconMenu.setVisibility(View.GONE);
                hideKeyboard();
                unlockContentHeightDelayed();
            }else {
                chatExtendMenuContainer.setVisibility(View.VISIBLE);
                chatExtendMenu.setVisibility(View.VISIBLE);
                emojiconMenu.setVisibility(View.GONE);
            }
        } else {
            if (chatExtendMenu.getVisibility() == View.VISIBLE) {
                lockContentHeight();
                chatExtendMenuContainer.setVisibility(View.GONE);
                chatExtendMenu.setVisibility(View.GONE);
                KeyBoardUtils.openKeybord(context, chatPrimaryMenu.getEditText());
                unlockContentHeightDelayed();
                lockContentHeight();
                chatExtendMenu.setVisibility(View.VISIBLE);
                KeyBoardUtils.openKeybord(context, chatPrimaryMenu.getEditText());
                unlockContentHeightDelayed();
            } else {
                chatExtendMenu.setVisibility(View.VISIBLE);
                emojiconMenu.setVisibility(View.GONE);
            }
        }
    }

    /**
     * show or hide emojicon
     */
    protected void toggleEmojicon() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            if (isKeyboardOpen()){
                lockContentHeight();
                chatExtendMenuContainer.setVisibility(View.VISIBLE);
                chatExtendMenu.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.VISIBLE);
                hideKeyboard();
                unlockContentHeightDelayed();
            }else {
                chatExtendMenuContainer.setVisibility(View.VISIBLE);
                chatExtendMenu.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.VISIBLE);
            }
        } else {
            if (emojiconMenu.getVisibility() == View.VISIBLE) {
                lockContentHeight();
                chatExtendMenuContainer.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.GONE);
                KeyBoardUtils.openKeybord(context,chatPrimaryMenu.getEditText());
                unlockContentHeightDelayed();
            } else {
                chatExtendMenu.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * hide keyboard
     */
    private void hideKeyboard() {
        chatPrimaryMenu.hideKeyboard();
    }

    /**
     * hide extend menu
     */
    public void hideExtendMenuContainer() {
        if (chatExtendMenuContainer.getVisibility() == View.VISIBLE){
            lockContentHeight();
            chatExtendMenu.setVisibility(View.GONE);
            emojiconMenu.setVisibility(View.GONE);
            chatExtendMenuContainer.setVisibility(View.GONE);
            chatPrimaryMenu.onExtendMenuContainerHide();
            KeyBoardUtils.openKeybord(context,chatPrimaryMenu.getEditText());
            unlockContentHeightDelayed();
        }
        KeyBoardUtils.openKeybord(context,chatPrimaryMenu.getEditText());
    }

    public void voiceClick(){
        if (chatExtendMenuContainer.getVisibility() == View.VISIBLE || isKeyboardOpen()){
            if (chatExtendMenuContainer.getVisibility() == View.VISIBLE){
                chatExtendMenu.setVisibility(View.GONE);
                emojiconMenu.setVisibility(View.GONE);
                chatExtendMenuContainer.setVisibility(View.GONE);
                chatPrimaryMenu.onExtendMenuContainerHide();
            }
            if (isKeyboardOpen()){
                hideKeyboard();
            }
        }else {
            KeyBoardUtils.openKeybord(context,chatPrimaryMenu.getEditText());
        }
    }

    public void hideInputMenu(){
        if (chatExtendMenuContainer.getVisibility() == View.VISIBLE){
            chatExtendMenu.setVisibility(View.GONE);
            emojiconMenu.setVisibility(View.GONE);
            chatExtendMenuContainer.setVisibility(View.GONE);
            chatPrimaryMenu.onExtendMenuContainerHide();
        }
        if (isKeyboardOpen()){
            hideKeyboard();
        }
    }

    /**
     * when back key pressed
     * 
     * @return false--extend menu is on, will hide it first
     *         true --extend menu is off 
     */
    public boolean onBackPressed() {
        if (chatExtendMenuContainer.getVisibility() == View.VISIBLE) {
            hideExtendMenuContainer();
            return false;
        } else if(isKeyboardOpen()){
            hideKeyboard();
            return false;
        }else {
            return true;
        }

    }
    

    public void setChatInputMenuListener(ChatInputMenuListener listener) {
        this.listener = listener;
    }

    public interface ChatInputMenuListener {
        /**
         * when send message button pressed
         * 
         * @param content
         *            message content
         */
        void onSendMessage(String content);
        
        /**
         * when big icon pressed
         * @param emojicon
         */
        void onBigExpressionClicked(EaseEmojicon emojicon);

        /**
         * when speak button is touched
         * @param v
         * @param event
         * @return
         */
        boolean onPressToSpeakBtnTouch(View v, MotionEvent event);
    }
    
}
