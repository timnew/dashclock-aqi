package org.windy.dashclock.extensions.aqi;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;
import com.googlecode.androidannotations.annotations.EService;

@EService
public class AQIExtension extends DashClockExtension {

    @Override
    protected void onInitialize(boolean isReconnect) {
        super.onInitialize(isReconnect);


    }

    @Override
    protected void onUpdateData(int reason) {
        publishUpdate(new ExtensionData()
                .visible(true)
                .status("AQI"));
    }
}
