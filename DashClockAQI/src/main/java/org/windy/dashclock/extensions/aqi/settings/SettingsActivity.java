/*
 * Copyright (c) 2014. TimNew (http://github.com/timnew)
 */

package org.windy.dashclock.extensions.aqi.settings;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.OptionsItem;

import org.windy.dashclock.extensions.aqi.R;

@EActivity(R.layout.settings)
public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @OptionsItem(android.R.id.home)
    protected void homePressed() {
        finish();
    }
}