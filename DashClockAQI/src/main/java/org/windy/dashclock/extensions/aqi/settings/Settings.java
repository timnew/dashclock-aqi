package org.windy.dashclock.extensions.aqi.settings;

import com.googlecode.androidannotations.annotations.sharedpreferences.DefaultString;
import com.googlecode.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref(SharedPref.Scope.UNIQUE)
public interface Settings {
    @DefaultString("beijing")
    String cityName();

    @DefaultString("5j1znBVAsnSf5xQyNQyq")
    String apiKey();
}


