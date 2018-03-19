/*
 * Copyright (c) 2018, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 * 
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.iot.agent.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.wso2.iot.agent.AndroidAgentException;
import org.wso2.iot.agent.R;
import org.wso2.iot.agent.utils.CommonUtils;
import org.wso2.iot.agent.utils.Constants;
import org.wso2.iot.agent.utils.Preference;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * IntentService responsible for handling FCM messages.
 */
public class FCMMessagingService extends FirebaseMessagingService {

    private static final String TAG = FCMMessagingService.class.getName();
    private static volatile boolean hasPending = false;

    @Override
    public void onCreate() {
        super.onCreate();
        foreground();
    }

    protected void foreground() {
        // launch service in foreground
        int id = 11140;
        Log.i(TAG, "launch service in foreground");
        NotificationCompat.Builder mBuilder = null;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel notificationChannel = new NotificationChannel( "" + id, TAG, importance);
                mNotificationManager.createNotificationChannel(notificationChannel);
                mBuilder = new NotificationCompat.Builder(this.getApplicationContext(), notificationChannel.getId());
            } else {
                mBuilder = new NotificationCompat.Builder(this.getApplicationContext());
            }
            mBuilder.setContentText(TAG).setAutoCancel(true);
            startForeground(id,  mBuilder.build());
        } catch (NullPointerException npe) {
            Log.e(TAG,"failed to start on foreground ", npe);
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Log.i(TAG, "New FCM notification. Message id: " + message.getMessageId());
        syncMessages();
        if (Constants.SYSTEM_APP_ENABLED && Preference.getBoolean(this,
                getResources().getString(R.string.firmware_upgrade_retry_pending))) {
            Preference.putBoolean(this,
                    getResources().getString(R.string.firmware_upgrade_retry_pending), false);
            CommonUtils.callSystemApp(this, Constants.Operation.UPGRADE_FIRMWARE, null, null);
        }
    }

    private void syncMessages() {
        try {
            if (Preference.getBoolean(this, Constants.PreferenceFlag.REGISTERED)) {
                long currentTimeStamp = Calendar.getInstance().getTimeInMillis();
                if (currentTimeStamp - MessageProcessor.getInvokedTimeStamp() > Constants.DEFAULT_START_INTERVAL) {
                    MessageProcessor messageProcessor = new MessageProcessor(this);
                    messageProcessor.getMessages();
                } else if (!hasPending) {
                    hasPending = true;
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (Constants.DEBUG_MODE_ENABLED) {
                                Log.d(TAG, "Triggering delayed operation polling.");
                            }
                            hasPending = false;
                            syncMessages();
                        }
                    }, Constants.DEFAULT_START_INTERVAL);
                    if (Constants.DEBUG_MODE_ENABLED) {
                        Log.d(TAG, "Scheduled for delayed operation syncing.");
                    }
                } else {
                    Log.i(TAG, "Ignoring message since there are ongoing and pending polling.");
                }
            }
        } catch (AndroidAgentException e) {
            Log.e(TAG, "Failed to perform operation", e);
        }
    }

}
