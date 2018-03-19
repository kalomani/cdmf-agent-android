/*
 * Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.iot.agent.services.kiosk;

// import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import org.wso2.iot.agent.activities.SplashActivity;

public class KioskUnlockService extends JobIntentService {
    // Unique job ID for this service.
    static final int JOB_ID = 101;
    private static final String TAG = "KioskUnlockService";

    public KioskUnlockService() {
        super(/*KioskUnlockService.class.getName()*/);
    }

    // Convenience method for enqueuing work in to this service.
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, KioskUnlockService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(Intent releaseIntent) {
        Log.d(TAG,"Device unlocked." );
        releaseIntent = new Intent(this, SplashActivity.class);
        releaseIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        releaseIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(releaseIntent);
    }
}
