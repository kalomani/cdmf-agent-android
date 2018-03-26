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
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import org.wso2.iot.agent.utils.Constants;
import org.wso2.iot.agent.utils.Preference;

public class FCMInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = FCMInstanceIdService.class.getName();

    @Override
    public void onCreate() {
        super.onCreate();
        foreground();
    }

    protected void foreground() {
        if (Constants.DEBUG_MODE_ENABLED){
            Log.d(TAG, "foreground");
        }
        // launch service in foreground
        int id = 11150;
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
    public void onTokenRefresh() {
        if (Constants.DEBUG_MODE_ENABLED){
            Log.d(TAG, "onTokenRefresh");
        }
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        if (Constants.DEBUG_MODE_ENABLED){
            Log.d(TAG, "FCM Token: " + token);
        }
        Preference.putString(this, Constants.FCM_REG_ID, token);
        Intent intent = new Intent();
        intent.setAction(Constants.FCM_TOKEN_REFRESHED_BROADCAST_ACTION);
        sendBroadcast(intent);
    }
}
