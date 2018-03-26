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

package org.wso2.iot.system.service;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.wso2.iot.system.service.utils.CommonUtils;
import org.wso2.iot.system.service.utils.Constants;

public class MainActivity extends Activity {
    private static final int ACTIVATION_REQUEST = 47;
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "onCreate");
        }
        setContentView(R.layout.activity_main);
        ComponentName receiver = new ComponentName(this, ServiceDeviceAdminReceiver.class);
        // CommonUtils.registerForNetworkConnectivityStatusReceiver(this);
        // SystemService.enqueueWork(this, getIntent());
        startDeviceAdminPrompt(receiver);
    }

    // todo This function is a test only.
    @Override
    protected void onStart() {
        super.onStart();
        CommonUtils.registerForNetworkConnectivityStatusReceiver(this);
        //SystemService.enqueueWork(this, getIntent());
    }

    /**
     * Start device admin activation request.
     *
     * @param cdmDeviceAdmin - Device admin component.
     */
    private void startDeviceAdminPrompt(ComponentName cdmDeviceAdmin) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "startDeviceAdminPrompt");
        }
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        if (!devicePolicyManager.isAdminActive(cdmDeviceAdmin)) {
            Log.d(TAG, "not admin active");
            Intent deviceAdminIntent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            deviceAdminIntent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cdmDeviceAdmin);
            deviceAdminIntent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                                       getResources().getString(R.string.device_admin_enable_alert));
            startActivityForResult(deviceAdminIntent, ACTIVATION_REQUEST);
        } else {
            Log.d(TAG, "not admin active");
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "onActivityResult with requestCode: " + requestCode + " and resultCode: " + requestCode);
        }
        if (requestCode == ACTIVATION_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                finish();
                Log.i("onActivityResult", "Administration enabled!");
            } else {
                Log.i("onActivityResult", "Administration enable FAILED!");
            }
        }
    }

}
