package org.windy.dashclock.extensions.aqi;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;

public class AQIExtension extends DashClockExtension {

    @Override
    protected void onUpdateData(int reason) {
        publishUpdate(new ExtensionData()
                .visible(true)
                .status("AQI"));
    }
}
