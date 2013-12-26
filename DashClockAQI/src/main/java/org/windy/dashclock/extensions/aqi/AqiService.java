package org.windy.dashclock.extensions.aqi;

import com.googlecode.androidannotations.annotations.rest.Get;
import com.googlecode.androidannotations.annotations.rest.Rest;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;
import org.windy.dashclock.extensions.aqi.models.CityList;
import org.windy.dashclock.extensions.aqi.models.CityStationInfo;

@Rest(rootUrl = "http://www.pm25.in/api", converters = {GsonHttpMessageConverter.class})
public interface AqiService {
    RestTemplate getRestTemplate();

    void setRestTemplate(RestTemplate restTemplate);

    @Get("/querys/pm2_5.json?city={city}")
    public AqiInfoList pm25(String city);

    @Get("/querys/station_names.json?city={city}")
    public CityStationInfo queryStationInfo(String city);

    @Get("/querys.json")
    public CityList aqiCities();
}




