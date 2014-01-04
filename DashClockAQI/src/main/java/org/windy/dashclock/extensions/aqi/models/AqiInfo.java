/*
 * Copyright (c) 2014. TimNew (http://github.com/timnew)
 */

package org.windy.dashclock.extensions.aqi.models;

import com.google.gson.annotations.SerializedName;

public class AqiInfo {
    private int aqi;
    private String area;
    private float co;
    @SerializedName("co_24h")
    private float co24h;
    private int no2;
    @SerializedName("no2_24h")
    private int no224h;
    private int o3;
    @SerializedName("o3_24h")
    private int o324h;
    @SerializedName("o3_8h")
    private int o38h;
    @SerializedName("o3_8h_24h")
    private int o38h24h;
    private int pm10;
    @SerializedName("pm10_24h")
    private int pm1024h;
    @SerializedName("pm2_5")
    private int pm25;
    @SerializedName("pm2_5_24h")
    private int pm2524h;
    private int so2;
    @SerializedName("so2_24h")
    private int so224h;
    private String positionName;
    @SerializedName("primary_pollutant")
    private String primaryPollutant;
    private String quality;
    @SerializedName("station_code")
    private String stationCode;
    @SerializedName("time_point")
    private String timePoint;


    public int getAqi() {
        return aqi;
    }

    public String getArea() {
        return area;
    }

    public float getCO() {
        return co;
    }

    public float getCOAverage() {
        return co24h;
    }

    public int getNO2() {
        return no2;
    }

    public int getNO2Average() {
        return no224h;
    }

    public int getO3() {
        return o3;
    }

    public int getO3Average() {
        return o324h;
    }

    public int getO38Hour() {
        return o38h;
    }

    public int getO38HourAverage() {
        return o38h24h;
    }

    public int getPM10() {
        return pm10;
    }

    public int getPM10Average() {
        return pm1024h;
    }

    public int getPM25() {
        return pm25;
    }

    public int getPM25Average() {
        return pm2524h;
    }

    public int getSO2() {
        return so2;
    }

    public int getSO2Average() {
        return so224h;
    }

    public String getPositionName() {
        return positionName;
    }

    public String getPrimaryPollutant() {
        return primaryPollutant;
    }

    public String getQuality() {
        return quality;
    }

    public String getStationCode() {
        return stationCode;
    }

    public String getTimePoint() {
        return timePoint;
    }
}
