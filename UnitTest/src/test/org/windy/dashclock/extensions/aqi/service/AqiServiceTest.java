package org.windy.dashclock.extensions.aqi.service;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.windy.dashclock.extensions.aqi.DashClockAQITestRunner;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(DashClockAQITestRunner.class)
public class AqiServiceTest {

    private AqiService service;

    @Before
    public void setUp() throws Exception {
        service = AqiService_.getInstance_(Robolectric.application);
    }

    @Test
    public void should_load() {
        ResponseEntity<AqiInfoList> beijing = service.aqi("beijing");

        assertThat(beijing.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(beijing.getBody()).isNotEmpty();
    }

    @Test
    public void should_deserialze() {
        Gson gson = new Gson();

        String json = "[{\"aqi\":221,\"area\":\"北京\",\"pm2_5\":171,\"pm2_5_24h\":123,\"position_name\":\"万寿西宫\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":\"1001A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":144,\"area\":\"北京\",\"pm2_5\":110,\"pm2_5_24h\":90,\"position_name\":\"定陵\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"轻度污染\",\"station_code\":\"1002A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":229,\"area\":\"北京\",\"pm2_5\":179,\"pm2_5_24h\":124,\"position_name\":\"东四\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":\"1003A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":192,\"area\":\"北京\",\"pm2_5\":144,\"pm2_5_24h\":108,\"position_name\":\"天坛\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"中度污染\",\"station_code\":\"1004A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":277,\"area\":\"北京\",\"pm2_5\":227,\"pm2_5_24h\":137,\"position_name\":\"农展馆\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":\"1005A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":220,\"area\":\"北京\",\"pm2_5\":170,\"pm2_5_24h\":113,\"position_name\":\"官园\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":\"1006A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":259,\"area\":\"北京\",\"pm2_5\":209,\"pm2_5_24h\":151,\"position_name\":\"海淀区万柳\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":\"1007A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":221,\"area\":\"北京\",\"pm2_5\":171,\"pm2_5_24h\":101,\"position_name\":\"顺义新城\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":\"1008A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":183,\"area\":\"北京\",\"pm2_5\":138,\"pm2_5_24h\":91,\"position_name\":\"怀柔镇\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"中度污染\",\"station_code\":\"1009A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":176,\"area\":\"北京\",\"pm2_5\":133,\"pm2_5_24h\":111,\"position_name\":\"昌平镇\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"中度污染\",\"station_code\":\"1010A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":247,\"area\":\"北京\",\"pm2_5\":197,\"pm2_5_24h\":132,\"position_name\":\"奥体中心\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":\"1011A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":210,\"area\":\"北京\",\"pm2_5\":160,\"pm2_5_24h\":99,\"position_name\":\"古城\",\"primary_pollutant\":\"细颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":\"1012A\",\"time_point\":\"2014-01-02T23:00:00Z\"},{\"aqi\":218,\"area\":\"北京\",\"pm2_5\":167,\"pm2_5_24h\":115,\"position_name\":null,\"primary_pollutant\":\"颗粒物(PM2.5)\",\"quality\":\"重度污染\",\"station_code\":null,\"time_point\":\"2014-01-02T23:00:00Z\"}]";

        AqiInfoList aqiInfos = gson.fromJson(json, AqiInfoList.class);

        assertThat(aqiInfos).isNotEmpty();
    }
}
