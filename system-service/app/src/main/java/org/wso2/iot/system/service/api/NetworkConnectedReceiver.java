/*
 * Copyright (c) 2014, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.iot.system.service.api;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import org.wso2.iot.system.service.utils.Constants;

/**
 * This class handles all the functionality required for monitoring device network connectivity.
 * This can be used to invoke the agent application when the user connects the device to the network for
 * the first time.
 */
public class NetworkConnectedReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkConnectedReceiver.class.getName();
    private static final String PREFERENCES = "system_service_pref";
    private static final String AGENT_HAS_BEEN_CALLED = "agent_has_been_called";

    /**
     * Returns the network connectivity status the device.
     *
     * @return - Connection status.
     */
    private static boolean isConnected(Context context) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "isConnected");
        }
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "onReceive");
        }
        Log.d(TAG, "Entered OnReceive in NetworkConnectedReceiver");

        //Skipping non admin users
        if (ActivityManager.getCurrentUser() != 0) {
            Log.i(TAG, "The user serial number is " + ActivityManager.getCurrentUser());
        } else {
            Log.i(TAG, "The user is admin");
            SharedPreferences prefs = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
            Boolean isAgentCalled = prefs.getBoolean(AGENT_HAS_BEEN_CALLED, false);

            if (!isAgentCalled) {
                //Check network connected
                if (isConnected(context)) {
                    Intent agentLauncherIntent = new Intent();
                    agentLauncherIntent.setComponent(new ComponentName(Constants.AGENT_APP_PACKAGE_NAME,
                                                                       Constants.AGENT_APP_PACKAGE_NAME + Constants.AGENT_APP_LAUNCH_ACTIVITY));
                    agentLauncherIntent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    agentLauncherIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(agentLauncherIntent);

                    Editor editor = prefs.edit();
                    editor.putBoolean(AGENT_HAS_BEEN_CALLED, true);
                    editor.apply();
                    Log.i(TAG, "IoT agent has been called");
                }
            }
        }
    }
} 