package com.wills.help.message.controller;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.util.EasyUtils;
import com.wills.help.R;
import com.wills.help.base.App;
import com.wills.help.db.bean.Contact;
import com.wills.help.db.manager.ContactHelper;
import com.wills.help.db.manager.UserInfoHelper;
import com.wills.help.listener.MessageConnectionListener;
import com.wills.help.message.ContactsView;
import com.wills.help.message.domain.EaseEmojicon;
import com.wills.help.message.domain.EaseUser;
import com.wills.help.message.model.EaseAtMessageHelper;
import com.wills.help.message.model.EaseNotifier;
import com.wills.help.message.presenter.ContactsPresenterImpl;
import com.wills.help.net.HttpMap;
import com.wills.help.utils.AppConfig;
import com.wills.help.utils.AppManager;
import com.wills.help.utils.SharedPreferencesUtils;
import com.wills.help.utils.StringUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class EaseUI implements ContactsView {
    private static final String TAG = EaseUI.class.getSimpleName();

    /**
     * the global EaseUI instance
     */
    private static EaseUI instance = null;
    
    /**
     * user profile provider
     */
    private EaseUserProfileProvider userProvider;
    
    private EaseSettingsProvider settingsProvider;
    
    /**
     * application context
     */
    private Context appContext = null;
    
    /**
     * init flag: test if the sdk has been inited before, we don't need to init again
     */
    private boolean sdkInited = false;
    
    /**
     * the notifier
     */
    private EaseNotifier notifier = null;


    private ContactsPresenterImpl contactsPresenter;

    private EaseUI(){}
    
    /**
     * get instance of EaseUI
     * @return
     */
    public synchronized static EaseUI getInstance(){
        if(instance == null){
            instance = new EaseUI();
        }
        return instance;
    }
    
    /**
     *this function will initialize the SDK and easeUI kit
     * 
     * @return boolean true if caller can continue to call SDK related APIs after calling onInit, otherwise false.
     * 
     * @param context
     * @param options use default if options is null
     * @return
     */
    public synchronized boolean init(Context context, EMOptions options){
        if(sdkInited){
            return true;
        }
        appContext = context;
        
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        
        Log.d(TAG, "process app name : " + processAppName);

        // if there is application has remote service, application:onCreate() maybe called twice
        // this check is to make sure SDK will initialized only once
        // return if process name is not application's name since the package name is the default process name
        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e(TAG, "enter the service process!");
            return false;
        }
        if(options == null){
            EMClient.getInstance().init(context, initChatOptions());
        }else{
            EMClient.getInstance().init(context, options);
        }
        EMClient.getInstance().setDebugMode(true);
        EMClient.getInstance().addConnectionListener(new MessageConnectionListener());
        initNotifier();
        registerMessageListener();
        
        if(settingsProvider == null){
            settingsProvider = new DefaultSettingsProvider();
        }
        
        sdkInited = true;
        return true;
    }
    

    protected EMOptions initChatOptions(){
        Log.d(TAG, "init HuanXin Options");

        EMOptions options = new EMOptions();
        // change to need confirm contact invitation
        options.setAcceptInvitationAlways(false);
        // set if need read ack
        options.setRequireAck(true);
        // set if need delivery ack
        options.setRequireDeliveryAck(false);
        //huawei push
        options.setHuaweiPushAppId(AppConfig.HW_APP_ID);
        //mi push
        options.setMipushConfig(AppConfig.MI_APP_ID,AppConfig.MI_APP_KEY);
        return options;
    }
    
    void initNotifier(){
        notifier = createNotifier();
        notifier.init(appContext);
    }

    private Map<String,String> getMap(Map<String, EMConversation> conversations){
        String userNames = "";
        HttpMap map = new HttpMap();
        for (Map.Entry<String,EMConversation> entry:conversations.entrySet()){
            if (!entry.getKey().equals(AppConfig.ADMIN)){
                userNames+=entry.getKey()+",";
            }
        }
        if (!StringUtils.isNullOrEmpty(userNames)){
            map.put("usernames",userNames.substring(0,userNames.length()-1));
        }
        return map.getMap();
    }

    private void registerMessageListener() {
        contactsPresenter = new ContactsPresenterImpl(this);
        if (App.getApp().getIsLogin()){
            Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
            if (conversations.size()>0){
                if (!(conversations.size() == 1&&conversations.containsKey(AppConfig.ADMIN))){
                    contactsPresenter.getContacts(getMap(conversations));
                }
            }
        }

        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListener() {
            
            @Override
            public void onMessageReceived(List<EMMessage> messages) {
                EaseAtMessageHelper.get().parseMessages(messages);
                if (!EasyUtils.isAppRunningForeground(appContext)){
                    EaseUI.getInstance().getNotifier().onNewMesg(messages);
                }else {
                    String currentActivity = AppManager.getAppManager().currentActivity().getClass().getSimpleName();
                    if (!currentActivity.equals("MessageActivity")&&!currentActivity.equals("ChatActivity")){
                        EaseUI.getInstance().getNotifier().onNewMesg(messages);
                    }
                }
            }
            @Override
            public void onMessageReadAckReceived(List<EMMessage> messages) {
                
            }
            @Override
            public void onMessageDeliveryAckReceived(List<EMMessage> messages) {
            }
            @Override
            public void onMessageChanged(EMMessage message, Object change) {
                
            }
            @Override
            public void onCmdMessageReceived(List<EMMessage> messages) {
                for (EMMessage message : messages){
                    EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
                    String action = cmdMsgBody.action();
                    if (action.equals("userInfo")){
                        HttpMap map = new HttpMap();
                        map.put("usernames",message.getUserName());
                        contactsPresenter.getContacts(map.getMap());
                    }else if (action.contains("idcheck")){
                        App.getApp().getUser().setUsertype("1");
                        App.getApp().getUser().setTypename(appContext.getString(R.string.approved));
                        App.getApp().getUser().setSchool_num(action.substring(7));
                        UserInfoHelper.getInstance().updateData(App.getApp().getUser()).subscribe();
                    }
                }
            }
        });
    }
    
    protected EaseNotifier createNotifier(){
        return new EaseNotifier();
    }
    
    public EaseNotifier getNotifier(){
        return notifier;
    }
    

    /**
     * set user profile provider
     */
    public void setUserProfileProvider(EaseUserProfileProvider userProvider){
        this.userProvider = userProvider;
    }
    
    /**
     * get user profile provider
     * @return
     */
    public EaseUserProfileProvider getUserProfileProvider(){
        return userProvider;
    }
    
    public void setSettingsProvider(EaseSettingsProvider settingsProvider){
        this.settingsProvider = settingsProvider;
    }
    
    public EaseSettingsProvider getSettingsProvider(){
        return settingsProvider;
    }
    
    
    /**
     * check the application process name if process name is not qualified, then we think it is a service process and we will not init SDK
     * @param pID
     * @return
     */
    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
                    // Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
                    // info.processName +"  Label: "+c.toString());
                    // processName = c.toString();
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    @Override
    public void setContacts(List<Contact> contactList) {
        ContactHelper.getInstance().insertData(contactList).subscribe();
    }

    /**
     * User profile provider
     * @author wei
     *
     */
    public interface EaseUserProfileProvider {
        /**
         * return EaseUser for input username
         * @param username
         * @return
         */
        EaseUser getUser(String username);
    }
    
    /**
     * Emojicon provider
     *
     */
    public interface EaseEmojiconInfoProvider {
        /**
         * return EaseEmojicon for input emojiconIdentityCode
         * @param emojiconIdentityCode
         * @return
         */
        EaseEmojicon getEmojiconInfo(String emojiconIdentityCode);
        
        /**
         * get Emojicon map, key is the text of emoji, value is the resource id or local path of emoji icon(can't be URL on internet)
         * @return
         */
        Map<String, Object> getTextEmojiconMapping();
    }
    
    private EaseEmojiconInfoProvider emojiconInfoProvider;
    
    /**
     * Emojicon provider
     * @return
     */
    public EaseEmojiconInfoProvider getEmojiconInfoProvider(){
        return emojiconInfoProvider;
    }
    
    /**
     * set Emojicon provider
     * @param emojiconInfoProvider
     */
    public void setEmojiconInfoProvider(EaseEmojiconInfoProvider emojiconInfoProvider){
        this.emojiconInfoProvider = emojiconInfoProvider;
    }
    
    /**
     * new message options provider
     *
     */
    public interface EaseSettingsProvider {
        boolean isMsgNotifyAllowed(EMMessage message);
        boolean isMsgSoundAllowed(EMMessage message);
        boolean isMsgVibrateAllowed(EMMessage message);
        boolean isSpeakerOpened();
    }
    
    /**
     * default settings provider
     *
     */
    protected class DefaultSettingsProvider implements EaseSettingsProvider{

        @Override
        public boolean isMsgNotifyAllowed(EMMessage message) {
            return (boolean)SharedPreferencesUtils.getInstance().get(AppConfig.IM_NOTICE,true);
        }

        @Override
        public boolean isMsgSoundAllowed(EMMessage message) {
            return (boolean)SharedPreferencesUtils.getInstance().get(AppConfig.IM_VOICE,true);
        }

        @Override
        public boolean isMsgVibrateAllowed(EMMessage message) {
            return (boolean)SharedPreferencesUtils.getInstance().get(AppConfig.IM_SHAKE,true);
        }

        @Override
        public boolean isSpeakerOpened() {
            return true;
        } 
    }
    
    public Context getContext(){
        return appContext;
    }
}
