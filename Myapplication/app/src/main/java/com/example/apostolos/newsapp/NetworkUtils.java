package com.example.apostolos.newsapp;


import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;


public class NetworkUtils {

    final static String NEWSAPI_BASE_URL =
            "https://newsapi.org/v1/articles?source=the-next-web&sortBy=latest";

    final static String PARAM_KEY = "apiKey";
    final static String KEY = "1e13a1ec462543d8a1b1100a0445c62f";

    public static URL buildUrl(String newsapiQuery) {
        Uri builtUri = Uri.parse(NEWSAPI_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_KEY,KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}