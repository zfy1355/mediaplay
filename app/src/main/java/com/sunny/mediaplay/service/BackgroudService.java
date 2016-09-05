package com.sunny.mediaplay.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by home on 2016/8/25.
 */
public class BackgroudService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: 2016/8/25 add play song background

        return null;
    }
}
