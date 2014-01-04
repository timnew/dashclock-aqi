package org.windy.dashclock.extensions.aqi.settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Background;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EFragment;
import com.googlecode.androidannotations.annotations.UiThread;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import org.springframework.http.ResponseEntity;
import org.windy.dashclock.extensions.aqi.R;
import org.windy.dashclock.extensions.aqi.models.CityList;
import org.windy.dashclock.extensions.aqi.models.StringArrayList;
import org.windy.dashclock.extensions.aqi.service.AqiService;

@EFragment
public class SettingsFragment extends PreferenceFragment {

    private ListPreference cityNamePreference;

    @Pref
    protected Settings_ settings;

    @Bean
    protected AqiService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);

        initPreferences(getPreferenceManager());
    }

    private void initPreferences(PreferenceManager preferenceManager) {
        cityNamePreference = (ListPreference) preferenceManager.findPreference("cityName");
    }

    @AfterViews
    protected void loadData() {
        loadCityList();
    }

    @Background
    protected void loadCityList() {
        CharSequence[] contents;

        try {
            ResponseEntity<CityList> response = service.aqiCities();

            StringArrayList citiesCities = response.getBody().getCities();
            contents = new CharSequence[citiesCities.size()];
            citiesCities.toArray(contents);

        } catch (Exception ex) {
            ex.printStackTrace();

            contents = getActivity().getResources().getStringArray(R.array.default_cities);
        }

        cityNamePreference.setEntries(contents);
        cityNamePreference.setEntryValues(contents);

        togglePreference(cityNamePreference, true);
    }

    @UiThread
    protected void togglePreference(Preference preference, boolean enabled) {
        preference.setEnabled(enabled);
    }
}

