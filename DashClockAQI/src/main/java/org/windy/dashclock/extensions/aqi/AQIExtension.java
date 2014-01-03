package org.windy.dashclock.extensions.aqi;

import android.widget.Toast;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EService;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.ResourceAccessException;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;
import org.windy.dashclock.extensions.aqi.service.AqiService;

@EService
public class AQIExtension extends DashClockExtension {

    @Bean
    protected AqiService service;

    @Bean
    protected AqiInfoRender render;

    @Override
    protected void onUpdateData(int reason) {
        ResponseEntity<AqiInfoList> aqi;

        try {
            aqi = service.aqi("beijing");
        } catch (ResourceAccessException ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.io_exception, Toast.LENGTH_SHORT).show();
            return;
        } catch (HttpMessageNotReadableException ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.api_rate_exceeded_exception, Toast.LENGTH_SHORT).show();
            return;
        }

        ExtensionData data = render.render(aqi);

        publishUpdate(data);
    }

}

