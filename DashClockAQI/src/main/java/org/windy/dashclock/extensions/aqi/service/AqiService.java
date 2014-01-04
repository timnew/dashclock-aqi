/*
 * Copyright (c) 2014. TimNew (http://github.com/timnew)
 */

package org.windy.dashclock.extensions.aqi.service;

import android.content.Context;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.Bean;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;
import org.windy.dashclock.extensions.aqi.models.CityList;
import org.windy.dashclock.extensions.aqi.models.CityStationInfo;
import org.windy.dashclock.extensions.aqi.settings.Settings_;

import static java.util.Arrays.asList;

@EBean
public class AqiService {

    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String TRUE = "true";
    public static final String FALSE = "false";

    @RootContext
    protected Context context;

    @RestService
    protected AqiRestInterface restInterface;

    @Pref
    protected Settings_ settings;

    @Bean
    protected TokenAppender tokenAppender;


    @AfterInject
    protected void setupService() {
        RestTemplate restTemplate = restInterface.getRestTemplate();
        restTemplate.setInterceptors(asList((ClientHttpRequestInterceptor) tokenAppender));
    }

    public ResponseEntity<AqiInfoList> aqi(String city, boolean stations, boolean avg) {
        return restInterface.aqi(city, formatStation(stations), formatAvg(avg));
    }

    private String formatStation(boolean stations) {
        return stations ? YES : NO;
    }

    private String formatAvg(boolean avg) {
        return avg ? TRUE : FALSE;
    }

    public ResponseEntity<CityStationInfo> stations(String city) {
        return restInterface.stations(city);
    }

    public ResponseEntity<CityList> aqiCities() {
        return restInterface.aqiCities();
    }

}
