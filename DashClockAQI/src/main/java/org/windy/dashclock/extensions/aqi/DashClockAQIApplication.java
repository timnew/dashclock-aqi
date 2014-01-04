/*
 * Copyright (c) 2014. TimNew (http://github.com/timnew)
 */

package org.windy.dashclock.extensions.aqi;

import android.app.Application;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

@ReportsCrashes(formKey = "")
public class DashClockAQIApplication
        extends Application {
    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
    }
}
