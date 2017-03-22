package com.wills.help.message.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hyphenate.chat.EMChatService;
import com.hyphenate.util.EMLog;

/**
 * com.wills.help.message.receiver
 * Created by lizhaoyong
 * 2017/3/22.
 */

public class StartMessageReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)
                &&!intent.getAction().equals("android.intent.action.QUICKBOOT_POWERON")){
            return;
        }
        EMLog.d("boot", "start IM service on boot");
        Intent startServiceIntent=new Intent(context, EMChatService.class);
        startServiceIntent.putExtra("reason", "boot");
        context.startService(startServiceIntent);
    }
}
