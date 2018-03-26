/*
 * Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.iot.agent.activities;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.util.Log;

import org.wso2.iot.agent.services.AgentDeviceAdminReceiver;
import org.wso2.iot.agent.utils.Constants;

/**
 * This activity is started after the provisioning is complete in {@link AgentDeviceAdminReceiver}.
 */
public class EnableProfileActivity extends Activity {
    private static final String TAG = EnableProfileActivity.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.DEBUG_MODE_ENABLED){
            Log.d(TAG, "onCreate");
        }
        if (savedInstanceState == null) {
            enableProfile();
            startEnrollment();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void enableProfile() {
        if (Constants.DEBUG_MODE_ENABLED){
            Log.d(TAG, "enableProfile");
        }
        DevicePolicyManager manager =
                (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName componentName = AgentDeviceAdminReceiver.getComponentName(this);
        // This is the name for the newly created managed profile.
        manager.setProfileName(componentName, Constants.TAG);
        // Enable the profile.
        manager.setProfileEnabled(componentName);
    }

    private void startEnrollment(){
        if (Constants.DEBUG_MODE_ENABLED){
            Log.d(TAG, "startEnrollment");
        }
        Intent intent = new Intent(this, ServerConfigsActivity.class);
        startActivity(intent);
    }

}