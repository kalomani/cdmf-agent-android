package org.wso2.iot.agent.services;

import android.content.Context;
import android.util.Log;

import org.wso2.iot.agent.events.publisher.DataPublisher;
import org.wso2.iot.agent.events.publisher.HttpDataPublisher;
import org.wso2.iot.agent.events.publisher.SplunkLogPublisher;
import org.wso2.iot.agent.utils.Constants;

/**
 * This class produce the matching Log Manager according to the preferred publisher.
 */
public class LogPublisherFactory {
    private Context context;
    private static SplunkLogPublisher splunkLogPublisher;
    private static String TAG = LogPublisherFactory.class.getName();

    public LogPublisherFactory(Context context) {
        if (Constants.DEBUG_MODE_ENABLED){
            Log.d(TAG, "creation");
        }
        this.context = context;
    }

    public DataPublisher getLogPublisher() {
        if (Constants.DEBUG_MODE_ENABLED){
            Log.d(TAG, "getLogPublisher");
        }
        if (Constants.LogPublisher.DAS_PUBLISHER.equals(Constants.LogPublisher.LOG_PUBLISHER_IN_USE)) {
            return new HttpDataPublisher(context);
        } else if (Constants.LogPublisher.SPLUNK_PUBLISHER.equals(Constants.LogPublisher.LOG_PUBLISHER_IN_USE)) {
            if (splunkLogPublisher == null) {
                splunkLogPublisher = new SplunkLogPublisher(context);
            }
            return splunkLogPublisher;
        } else {
            return null;
        }
    }
}
