package org.wso2.iot.agent.services.kiosk;

// import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;

import org.wso2.iot.agent.KioskActivity;
/*
    IntentService used to update kiosk ui for alarm and message operations
 */
public class KioskMsgAlarmService extends JobIntentService {
    // Unique job ID for this service.
    static final int JOB_ID = 102;
    public static final String ACTIVITY_TYPE = "type";
    public static final String ACTIVITY_MSG = "msg";
    public static final String ACTIVITY_TITLE = "title";

    public KioskMsgAlarmService(String s) {
        super(/*"KioskMsgAlarmService"*/);
    }
    public KioskMsgAlarmService() {
        super(/*"KioskMsgAlarmService"*/);
    }

    // Convenience method for enqueuing work in to this service.
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, KioskMsgAlarmService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent intent) {
        String msg = intent.getStringExtra(ACTIVITY_MSG);
        String type = intent.getStringExtra(ACTIVITY_TYPE);
        String title = intent.getStringExtra(ACTIVITY_TITLE);
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(KioskActivity.ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(ACTIVITY_MSG, msg);
        broadcastIntent.putExtra(ACTIVITY_TYPE, type);
        broadcastIntent.putExtra(ACTIVITY_TITLE, title);
        sendBroadcast(broadcastIntent);
    }
}
