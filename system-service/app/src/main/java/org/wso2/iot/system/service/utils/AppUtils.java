/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
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
package org.wso2.iot.system.service.utils;

import android.app.PackageInstallObserver;
import android.content.Context;
import android.content.pm.IPackageDeleteObserver;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.pm.PackageManager.*;

/**
 * Utility class to hold app operations.
 */
public class AppUtils {

    private static final String TAG = AppUtils.class.getName();
    private static final int DELETE_ALL_USERS = 0x00000002;
    private static final int INSTALL_ALL_USERS = 0x00000040;
    private static final int INSTALL_ALLOW_DOWNGRADE = 0x00000080;
    private static final int INSTALL_REPLACE_EXISTING = 0x00000002;
    public static final int INSTALL_SUCCEEDED = 1;
    private static final int DEFAULT_STATE_INFO_CODE = 0;
    private static final String INSTALL_FAILED_STATUS = "INSTALL_FAILED";
    private static final String INSTALL_SUCCESS_STATUS = "INSTALLED";
    private static final String PACKAGE_PREFIX = "package:";

    /**
     * Silently installs the app resides in the provided URI.
     * @param context - Application context.
     * @param  packageUri - App package URI.
     */
    public static void silentInstallApp(final Context context, Uri packageUri) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "silentInstallApp");
        }
        PackageManager pm = context.getPackageManager();
        PackageInstallObserver observer = new PackageInstallObserver() {
            @Override
            public void onPackageInstalled(String basePackageName, int returnCode, String msg, Bundle extras) {
                if (INSTALL_SUCCEEDED == returnCode) {
                    Log.d(TAG, "Installation succeeded!");
                    publishAppInstallStatus(context, INSTALL_SUCCESS_STATUS, null);
                } else {
                    if (msg == null || msg.isEmpty()) {
                        switch (returnCode){
                            case INSTALL_FAILED_ALREADY_EXISTS:
                                msg = "INSTALL_FAILED_ALREADY_EXISTS";
                                break;
                            case INSTALL_FAILED_INVALID_APK:
                                msg = "INSTALL_FAILED_INVALID_APK";
                                break;
                            case INSTALL_FAILED_INVALID_URI:
                                msg = "INSTALL_FAILED_INVALID_URI";
                                break;
                            case INSTALL_FAILED_INSUFFICIENT_STORAGE:
                                msg = "INSTALL_FAILED_INSUFFICIENT_STORAGE";
                                break;
                            case INSTALL_FAILED_DUPLICATE_PACKAGE:
                                msg = "INSTALL_FAILED_DUPLICATE_PACKAGE";
                                break;
                            case INSTALL_FAILED_NO_SHARED_USER:
                                msg = "INSTALL_FAILED_NO_SHARED_USER";
                                break;
                            case INSTALL_FAILED_UPDATE_INCOMPATIBLE:
                                msg = "INSTALL_FAILED_UPDATE_INCOMPATIBLE";
                                break;
                            case INSTALL_FAILED_SHARED_USER_INCOMPATIBLE:
                                msg = "INSTALL_FAILED_SHARED_USER_INCOMPATIBLE";
                                break;
                            case INSTALL_FAILED_MISSING_SHARED_LIBRARY:
                                msg = "INSTALL_FAILED_MISSING_SHARED_LIBRARY";
                                break;
                            case INSTALL_FAILED_REPLACE_COULDNT_DELETE:
                                msg = "INSTALL_FAILED_REPLACE_COULDNT_DELETE";
                                break;
                            case INSTALL_FAILED_DEXOPT:
                                msg = "INSTALL_FAILED_DEXOPT";
                                break;
                            case INSTALL_FAILED_OLDER_SDK:
                                msg = "INSTALL_FAILED_OLDER_SDK";
                                break;
                            case INSTALL_FAILED_CONFLICTING_PROVIDER:
                                msg = "INSTALL_FAILED_CONFLICTING_PROVIDER";
                                break;
                            case INSTALL_FAILED_NEWER_SDK:
                                msg = "INSTALL_FAILED_NEWER_SDK";
                                break;
                            case INSTALL_FAILED_TEST_ONLY:
                                msg = "INSTALL_FAILED_TEST_ONLY";
                                break;
                            case INSTALL_FAILED_CPU_ABI_INCOMPATIBLE:
                                msg = "INSTALL_FAILED_CPU_ABI_INCOMPATIBLE";
                                break;
                            case INSTALL_FAILED_MISSING_FEATURE:
                                msg = "INSTALL_FAILED_MISSING_FEATURE";
                                break;
                            case INSTALL_FAILED_CONTAINER_ERROR:
                                msg = "INSTALL_FAILED_CONTAINER_ERROR";
                                break;
                            case INSTALL_FAILED_INVALID_INSTALL_LOCATION:
                                msg = "INSTALL_FAILED_INVALID_INSTALL_LOCATION";
                                break;
                            case INSTALL_FAILED_MEDIA_UNAVAILABLE:
                                msg = "INSTALL_FAILED_MEDIA_UNAVAILABLE";
                                break;
                            case INSTALL_FAILED_VERIFICATION_TIMEOUT:
                                msg = "INSTALL_FAILED_VERIFICATION_TIMEOUT";
                                break;
                            case INSTALL_FAILED_VERIFICATION_FAILURE:
                                msg = "INSTALL_FAILED_VERIFICATION_FAILURE";
                                break;
                            case INSTALL_FAILED_PACKAGE_CHANGED:
                                msg = "INSTALL_FAILED_PACKAGE_CHANGED";
                                break;
                            case INSTALL_FAILED_UID_CHANGED:
                                msg = "INSTALL_FAILED_UID_CHANGED";
                                break;
                            case INSTALL_FAILED_VERSION_DOWNGRADE:
                                msg = "INSTALL_FAILED_VERSION_DOWNGRADE";
                                break;
                            case INSTALL_PARSE_FAILED_NOT_APK:
                                msg = "INSTALL_PARSE_FAILED_NOT_APK";
                                break;
                            case INSTALL_PARSE_FAILED_BAD_MANIFEST:
                                msg = "INSTALL_PARSE_FAILED_BAD_MANIFEST";
                                break;
                            case INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION:
                                msg = "INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION";
                                break;
                            case INSTALL_PARSE_FAILED_NO_CERTIFICATES:
                                msg = "INSTALL_PARSE_FAILED_NO_CERTIFICATES";
                                break;
                            case INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES:
                                msg = "INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES";
                                break;
                            case INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING:
                                msg = "INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING";
                                break;
                            case INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME:
                                msg = "INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME";
                                break;
                            case INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID:
                                msg = "INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID";
                                break;
                            case INSTALL_PARSE_FAILED_MANIFEST_MALFORMED:
                                msg = "INSTALL_PARSE_FAILED_MANIFEST_MALFORMED";
                                break;
                            case INSTALL_PARSE_FAILED_MANIFEST_EMPTY:
                                msg = "INSTALL_PARSE_FAILED_MANIFEST_EMPTY";
                                break;
                            case INSTALL_FAILED_INTERNAL_ERROR:
                                msg = "INSTALL_FAILED_INTERNAL_ERROR";
                                break;
                            case INSTALL_FAILED_USER_RESTRICTED:
                                msg = "INSTALL_FAILED_USER_RESTRICTED";
                                break;
                            case INSTALL_FAILED_DUPLICATE_PERMISSION:
                                msg = "INSTALL_FAILED_DUPLICATE_PERMISSION";
                                break;
                            case INSTALL_FAILED_NO_MATCHING_ABIS:
                                msg = "INSTALL_FAILED_NO_MATCHING_ABIS";
                                break;
                            case NO_NATIVE_LIBRARIES:
                                msg = "NO_NATIVE_LIBRARIES";
                                break;
                            case INSTALL_FAILED_ABORTED:
                                msg = "INSTALL_FAILED_ABORTED";
                                break;
                            default:
                                msg = "UNKNOWN";
                        }
                    }
                    String error = "Package installation failed due to an internal error with code: " + returnCode + " and message: " + msg;
                    Log.e(TAG, error);
                    publishAppInstallStatus(context, INSTALL_FAILED_STATUS, error);
                }
            }
        };
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                InputStream in = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/Download/update.apk");
                PackageInstaller pi = pm.getPackageInstaller();
                PackageInstaller.SessionParams params = new PackageInstaller.SessionParams(
                        PackageInstaller.SessionParams.MODE_FULL_INSTALL);
                params.setAppPackageName(packageUri.getPath());
                int sessionId = pi.createSession(params);
                PackageInstaller.Session session = pi.openSession(sessionId);
                OutputStream out = session.openWrite("COSU", 0, -1);
                byte[] buffer = new byte[65536];
                int c;
                while ((c = in.read(buffer)) != -1) {
                    out.write(buffer, 0, c);
                }
                session.fsync(out);
                in.close();
                out.close();
            } catch (FileNotFoundException fne) {
                Log.e(TAG, "install failed", fne);
            } catch (IOException ioe) {
                Log.e(TAG, "install failed", ioe);
            }
        } else {
            pm.installPackage(packageUri, observer, INSTALL_ALL_USERS | INSTALL_ALLOW_DOWNGRADE |
                    INSTALL_REPLACE_EXISTING, null);
        }
    }

    /**
     * Silently uninstalls the app resides in the provided URI.
     * @param context - Application context.
     * @param  packageName - App package name.
     */
    public static void silentUninstallApp(Context context, String packageName) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "silentUninstallApp");
        }
        if (packageName != null && packageName.contains(PACKAGE_PREFIX)) {
            packageName = packageName.replace(PACKAGE_PREFIX, "");
        }
        final String _packageName = packageName;
        PackageManager pm = context.getPackageManager();
        IPackageDeleteObserver observer = new IPackageDeleteObserver() {
            @Override
            public void packageDeleted(String s, int i) throws RemoteException {
                Log.d(TAG, _packageName + " deleted successfully.");
            }

            @Override
            public IBinder asBinder() {
                return null;
            }
        };
        pm.deletePackage(packageName, observer, DELETE_ALL_USERS);
    }

    private static void publishAppInstallStatus(Context context, String status, String error) {
        if (Constants.DEBUG_MODE_ENABLED) {
            Log.d(TAG, "publishAppInstallStatus");
        }
        JSONObject result = new JSONObject();

        try {
            result.put("appInstallStatus", status);
            if (error != null) {
                result.put("appInstallFailedMessage", error);
                CommonUtils.sendBroadcast(context, Constants.Operation.SILENT_INSTALL_APPLICATION, Constants.Code.FAILURE, Constants.Status.INTERNAL_ERROR,
                        result.toString());
            } else {
                CommonUtils.sendBroadcast(context, Constants.Operation.SILENT_INSTALL_APPLICATION, Constants.Code.SUCCESS, Constants.Status.SUCCESSFUL,
                        result.toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, "Failed to create JSON object when publishing App install status.");
            CommonUtils.sendBroadcast(context, Constants.Operation.SILENT_INSTALL_APPLICATION, Constants.Code.FAILURE, Constants.Status.INTERNAL_ERROR,
                          String.valueOf(DEFAULT_STATE_INFO_CODE));
        }
    }

}
