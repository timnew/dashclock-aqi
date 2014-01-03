package org.windy.dashclock.extensions.aqi;

import android.content.Context;

import com.google.android.apps.dashclock.api.ExtensionData;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;

import org.springframework.http.ResponseEntity;
import org.windy.dashclock.extensions.aqi.models.AqiInfo;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;

import java.util.List;

import static com.google.common.collect.Iterables.getLast;
import static java.lang.String.format;

@EBean
public class AqiInfoRender {

    private List<AqiInfo> aqiInfos;

    @RootContext
    protected Context context;

    public ExtensionData render(ResponseEntity<AqiInfoList> aqi) {
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
