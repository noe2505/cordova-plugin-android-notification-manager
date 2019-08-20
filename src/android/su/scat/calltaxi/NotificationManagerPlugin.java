package su.scat.calltaxi;

import android.annotation.TargetApi;

import android.content.Context;
import android.content.Intent;

import android.os.Build;

import android.provider.Settings;

import android.app.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;


public class NotificationManagerPlugin extends CordovaPlugin {

    @TargetApi(26)
    private void openAppNotificationSettings() {
        // only call on Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final Activity activity = this.cordova.getActivity();

            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);

            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName());

            activity.startActivity(intent);
        }
    }

    /**
     * After you create a notification channel, you cannot change the notification channel's visual
     * and auditory behaviors programmatically—only the user can change the channel behaviors from
     * the system settings. To provide your users easy access to these notification settings, you
     * should add an item in your app's settings UI that opens these system settings.
     *
     * See: https://developer.android.com/training/notify-user/channels#UpdateChannel
     **/

    @TargetApi(26)
    private void openNotificationChannelSettings(String channelId) {
        // only call on Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final Activity activity = this.cordova.getActivity();

            Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);

            intent.putExtra(Settings.EXTRA_APP_PACKAGE, activity.getPackageName());
            intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);

            activity.startActivity(intent);
        }
    }

    /**
     * Users can modify the settings for notification channels, including behaviors such as
     * vibration and alert sound. You might like to know the settings a user has applied to your
     * notification channels.
     *
     * See: https://developer.android.com/training/notify-user/channels#ReadChannel
     **/

    @TargetApi(26)
    private JSONObject getNotificationChannel(String channelId) throws JSONException {
        JSONObject channelJSON = new JSONObject();

        // only call on Android O and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            final Activity activity = this.cordova.getActivity();
            final NotificationManager manager = (NotificationManager) activity
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            final NotificationChannel channel = manager.getNotificationChannel(channelId);

            channelJSON.put("id", channel.getId());
            channelJSON.put("group", channel.getGroup());
            channelJSON.put("description", channel.getDescription());
            channelJSON.put("importance", channel.getImportance());
            channelJSON.put("lightColor", channel.getLightColor());
            channelJSON.put("lockscreenVisibility", channel.getLockscreenVisibility());
        }

        return channelJSON;
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {

            if ("getNotificationChannel".equals(action)) {
                callbackContext.success(getNotificationChannel(args.getString(0)));
                return true;
            }

            if ("openNotificationChannelSettings".equals(action)) {
                openNotificationChannelSettings(args.getString(0));
                callbackContext.success();
                return true;
            }

            if ("openAppNotificationSettings".equals(action)) {
                openAppNotificationSettings();
                callbackContext.success();
                return true;
            }

        } catch (JSONException e) {
            callbackContext.error(e.getMessage());
        }

        return false;
    }
}
