package org.windy.dashclock.extensions.aqi;

import android.content.Context;

import com.google.android.apps.dashclock.api.ExtensionData;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.windy.dashclock.extensions.aqi.models.AqiInfo;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;
import org.windy.dashclock.extensions.aqi.settings.Settings_;

import java.util.List;

import static com.google.common.collect.Iterables.getLast;
import static java.lang.String.format;

@EBean
public class AqiInfoRender {

    private List<AqiInfo> aqiInfos;

    @Pref
    protected Settings_ settings;

    @RootContext
    protected Context context;

    public ExtensionData render(ResponseEntity<AqiInfoList> aqi) {
        if (aqi.getStatusCode() != HttpStatus.OK) {
            return new ExtensionData()
                    .visible(true)
                    .icon(R.drawable.ic_leaf)
                    .expandedTitle(settings.cityName().get())
                    .expandedBody(context.getString(R.string.data_not_available))
                    .status(context.getString(R.string.data_not_available));

        }

        aqiInfos = aqi.getBody();

        return new ExtensionData()
                .visible(true)
                .icon(R.drawable.ic_leaf)
                .expandedTitle(renderTitle())
                .expandedBody(renderDescription())
                .status(renderStatus());
    }

    private AqiInfo getCityInfo() {
        return getLast(aqiInfos, null);
    }

    private String renderDescription() {
        StringBuilder result = new StringBuilder();

        AqiInfo cityInfo = getCityInfo();

        result.append(context.getString(R.string.item_aqi, cityInfo.getAqi()));
        result.append(context.getString(R.string.item_pm25, cityInfo.getPM25()));
        result.append(context.getString(R.string.item_primary_pollutant, cityInfo.getPrimaryPollutant()));
        result.append(context.getString(R.string.item_quality, cityInfo.getQuality()));

        return result.toString();
    }

    private String renderTitle() {
        return format("AQI %s", getCityInfo().getArea());
    }

    private String renderStatus() {
        return format("%s PM2.5: %d", getCityInfo().getArea(), getCityInfo().getPM25());
    }
}
