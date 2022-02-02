# Credits

Simple plugin to manage Android Notificacion Channels.

Forked from https://git.scat.su/external/cordova-plugin-android-notification-manager, original credits go to Andrey Sharapov (@sharupoff).

Original plugin lacked the funcionality to add customized Notification Channels, this fork enables your Cordova App to do so.


# Usage

## Create a notification channel
```js
NotificationManager.setNotificationChannel("channel_id","channel_name","channel_description","channel_importance","mp3_file_name_same_as_channel_id_without_extension")
    .then(console.info)
    .catch(console.error);
```

### Customize alert sounds
Place your mp3 files in /res/raw/, the file name must match the channel_id.


## Get a notification channel
```js
NotificationManager.getNotificationChannel('channel_id')
    .then(console.info)
    .catch(console.error);

// NotificationChannel {id: "channel_id", importance: 5, lightColor: 0, lockscreenVisibility: -1000, openSettings: ƒ, …}
```

## Open a notification channel settings

![Notification Channel Settings](./docs/img/openNotificationChannelSettings.png)

### From the manager
```js
NotificationManager.openNotificationChannelSettings('channel_id')
    .then(console.info)
    .catch(console.error);

// OK
```


### From a channel
```js
NotificationManager.getNotificationChannel('channel_id')
    .then(function(channel) {
        channel.openSettings()
            .then(console.info)
            .catch(console.error);
    })
    .catch(console.error);

// OK
```

## Open an app notification settings

![Notification Channel Settings](./docs/img/openAppNotificationSettings.png)

```js
NotificationManager.openAppNotificationSettings()
    .then(console.info)
    .catch(console.error);

// OK
```
