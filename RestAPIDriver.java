package com.vlocity.qe.utilities;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class RestAPIDriver {

    /**
     * Function to get the response from request payload based operation CRUD
     *
     * @param requestHeaders Map of headers
     * @param requestUrl     Endpoint and resource
     * @param requestPaylod  JSON Payload into String format
     * @param requestType    POST/PUT/DELETE/GET
     * @return HttpResponse
     * @throws NoSuchAlgorithmException
     * @throws KeyStoreException
     * @throws KeyManagementException
     * @throws IOException
     */
    public static HttpResponse callRestService(HashMap<String, String> requestHeaders, String requestUrl, String requestPaylod, String requestType) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {

        StringEntity payload;
//        HttpClient requestClient = HttpClientBuilder.create().build();
        HttpClient requestClient = setClientForSelfSignedCertificate();
        HttpUriRequest request;
        switch (requestType.toUpperCase()) {
            case "POST":
                request = new HttpPost(requestUrl);
                if (requestPaylod != null) {
                    payload = new StringEntity(requestPaylod);
                    ((HttpPost) request).setEntity(payload);
                }
                break;
            case "PUT":
                request = new HttpPut(requestUrl);
                if (requestPaylod != null) {
                    payload = new StringEntity(requestPaylod);
                    ((HttpPut) request).setEntity(payload);
                }
                break;
            case "DELETE":
                request = new HttpDelete(requestUrl);
                break;
            case "GET":
            default:
                request = new HttpGet(requestUrl);
                break;
        }

        if (!requestHeaders.isEmpty()) {
            for (String key : requestHeaders.keySet()) {
                request.addHeader(key, requestHeaders.get(key));
            }
        }
        return requestClient.execute(request);
    }

    private static HttpClient setClientForSelfSignedCertificate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        return HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

//        SSLContextBuilder builder = new SSLContextBuilder();
//        builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
//        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(builder.build());
//        return HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }

}
