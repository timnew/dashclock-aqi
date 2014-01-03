package org.windy.dashclock.extensions.aqi.service;

import android.content.Context;

import com.googlecode.androidannotations.annotations.AfterInject;
import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.RootContext;
import com.googlecode.androidannotations.annotations.rest.RestService;

import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.web.client.RestTemplate;
import org.windy.dashclock.extensions.aqi.R;
import org.windy.dashclock.extensions.aqi.models.AqiInfoList;
import org.windy.dashclock.extensions.aqi.models.CityList;
import org.windy.dashclock.extensions.aqi.models.CityStationInfo;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@EBean
public class AqiService implements AqiRestInterface {

    public static final String TOKEN_KEY = "token";

    @RootContext
    protected Context context;

    @RestService
    protected AqiRestInterface restInterface;

    private String token;

    @AfterInject
    protected void setupService() {
        loadConfig();

        RestTemplate restTemplate = restInterface.getRestTemplate();

        restTemplate.setInterceptors(asList((ClientHttpRequestInterceptor) new TokenAppender(token)));

    }

    private void loadConfig() {
        Properties properties = new Properties();

        try {
            properties.loadFromXML(context.getResources().openRawResource(R.raw.extension_config));
            token = properties.getProperty(TOKEN_KEY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RestTemplate getRestTemplate() {
        throw new RuntimeException("Not Supported");
    }

    @Override
    public ResponseEntity<AqiInfoList> aqi(String city) {
        return restInterface.aqi(city);
    }

    @Override
    public ResponseEntity<CityStationInfo> queryStationInfo(String city) {
        return restInterface.queryStationInfo(city);
    }

    @Override
    public ResponseEntity<CityList> aqiCities() {
        return restInterface.aqiCities();
    }

    public static class TokenedRequest extends HttpRequestWrapper {

        private URI uriWithToken;

        public TokenedRequest(String token, HttpRequest request) {
            super(request);

            URI uri = request.getURI();

            try {
                uriWithToken = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(), uri.getPath(), addToken(uri.getQuery(), token), uri.getFragment());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }

        private String addToken(String query, String token) {
            if (query == null || query.equals(""))
                return format("token=%s", token);
            else
                return format("%s&token=%s", query, token);

        }

        @Override
        public URI getURI() {
            return uriWithToken;
        }
    }

    public static class TokenAppender implements ClientHttpRequestInterceptor {

        private String token;

        public TokenAppender(String token) {
            this.token = token;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
            HttpRequest alteredRequest = new TokenedRequest(token, httpRequest);
            return clientHttpRequestExecution.execute(alteredRequest, bytes);
        }
    }
}
