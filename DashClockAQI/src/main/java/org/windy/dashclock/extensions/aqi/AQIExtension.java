package org.windy.dashclock.extensions.aqi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.google.android.apps.dashclock.api.DashClockExtension;
import com.google.android.apps.dashclock.api.ExtensionData;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EService;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;
import org.windy.dashclock.extensions.aqi.service.AqiService;
import org.windy.dashclock.extensions.aqi.settings.Settings_;

@EService
public class AQIExtension extends DashClockExtension {

    @Bean
    protected AqiService service;

    @Bean
    protected AqiInfoRender render;

    @Pref
    protected Settings_ settings;

    private LocalBroadcastManager broadcastManager;
    private ActionHandler actionHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        broadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());

        actionHandler = new ActionHandler(this);

        broadcastManager.registerReceiver(actionHandler, Actions.intentFilter());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        broadcastManager.unregisterReceiver(actionHandler);
    }

    @Override
    protected void onUpdateData(int reason) {
        ResponseEntity<AqiInfoList> aqi;

        try {
            aqi = service.aqi(settings.cityName().get(), false, true);
        } catch (ResourceAccessException ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.io_exception, Toast.LENGTH_SHORT).show();
            return;
        } catch (HttpMessageNotReadableException ex) {
            ex.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.api_rate_exceeded_exception, Toast.LENGTH_SHORT).show();
            return;
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();

            if (ex.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                Toast.makeText(getApplicationContext(), R.string.invalid_api_key_exception, Toast.LENGTH_SHORT).show();
                return;
            }

            throw ex;
        }

        ExtensionData data = render.render(aqi);

        publishUpdate(data);
    }

    public static class ActionHandler extends BroadcastReceiver {

        private final AQIExtension extension;

        public ActionHandler(AQIExtension extension) {
            this.extension = extension;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String actionName = intent.getAction();

            Actions action = Actions.valueOf(actionName);

            action.behave(extension, intent);
        }
    }

    public static enum Actions {
        UPDATE {
            @Override
            public void behave(AQIExtension extension, Intent intent) {
                extension.onUpdateData(DashClockExtension.UPDATE_REASON_MANUAL);
            }
        };

        public abstract void behave(AQIExtension extension, Intent intent);

        private static IntentFilter intentFilter() {
            IntentFilter filter = new IntentFilter();
            for (Actions action : values()) {
                filter.addAction(action.toString());
            }
            return filter;
        }
    }
}

