package org.windy.dashclock.extensions.aqi.service;

import com.googlecode.androidannotations.annotations.EBean;
import com.googlecode.androidannotations.annotations.sharedpreferences.Pref;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.windy.dashclock.extensions.aqi.settings.Settings_;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.lang.String.format;

@EBean
public class TokenAppender implements ClientHttpRequestInterceptor {

    @Pref
    protected Settings_ settings;

    private String getToken() {
        return settings.apiKey().get();
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] bytes, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        HttpRequest alteredRequest = new TokenedRequest(getToken(), httpRequest);
        return clientHttpRequestExecution.execute(alteredRequest, bytes);
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
}
