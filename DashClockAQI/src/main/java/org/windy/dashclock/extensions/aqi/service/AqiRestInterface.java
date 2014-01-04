package org.windy.dashclock.extensions.aqi.service;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;
import org.windy.dashclock.extensions.aqi.models.CityList;
import org.windy.dashclock.extensions.aqi.models.CityStationInfo;

@Rest(rootUrl = "http://www.pm25.in/api", converters = {GsonHttpMessageConverter.class})
public interface AqiRestInterface {
    RestTemplate getRestTemplate();

    @Get("/querys/aqi_details.json?city={city}&stations={stations}&avg={avg}")
    public ResponseEntity<AqiInfoList> aqi(String city, String stations, String avg);

    @Get("/querys/station_names.json?city={city}")
    public ResponseEntity<CityStationInfo> stations(String city);

    @Get("/querys.json")
    public ResponseEntity<CityList> aqiCities();
}

