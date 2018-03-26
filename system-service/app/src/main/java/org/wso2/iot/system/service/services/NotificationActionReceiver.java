package org.wso2.iot.system.service.services;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.wso2.iot.system.service.R;
import org.wso2.iot.system.service.api.OTAServerManager;
import org.wso2.iot.system.service.utils.CommonUtils;
import org.wso2.iot.system.service.utils.Constants;
import org.wso2.iot.system.service.utils.Preference;

import java.net.MalformedURLException;

public class NotificationActionReceiver extends BroadcastReceiver {

    private static final String TAG = NotificationActionReceiver.class.getName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "onReceiver");
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(Constants.DEFAULT_NOTIFICATION_CODE);
        String action = intent.getAction();

        try {
            OTAServerManager otaServerManager = new OTAServerManager(context);
            if (Constants.FIRMWARE_INSTALL_CONFIRM_ACTION.equals(action)) {
                String message = "Installing firmware upon user's confirmation.";
                Log.d(TAG, message);
                CommonUtils.callAgentApp(context, Constants.Operation.FIRMWARE_UPGRADE_COMPLETE, Preference.getInt(
                        context, context.getResources().getString(R.string.operation_id)), message);
                otaServerManager.startInstallUpgradePackage();
            } else if (Constants.FIRMWARE_INSTALL_CANCEL_ACTION.equals(action)) {
                String message = "Firmware upgrade has been canceled by the user.";
                CommonUtils.sendBroadcast(context, Constants.Operation.FIRMWARE_INSTALLATION_CANCELED, Constants.Code.SUCCESS,
                        Constants.Status.USER_CANCELED, message);
                CommonUtils.callAgentApp(context, Constants.Operation.FIRMWARE_INSTALLATION_CANCELED, Preference.getInt(
                        context, context.getResources().getString(R.string.operation_id)), message);
                Log.d(TAG, message);
            }
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error in getting OTA Server Manager. ", e);
        }
    }

}
