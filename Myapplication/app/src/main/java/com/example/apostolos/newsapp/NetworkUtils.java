package com.example.apostolos.newsapp;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;





public class NetworkUtils {
    public static final String TAG ="NetworkUtils";

    public static final String BASE_URL="https://newsapi.org/v1/articles?";
    public static final String PARAM_QUERY="source";
    public static final String PARAM_SORT="sortBy";
    public static final String apiKey="apiKey";


    public static URL makeURL(String searchQuery, String sortBy){
        Uri uri= Uri.parse(BASE_URL).buildUpon().appendQueryParameter(PARAM_QUERY,"the-next-web")
                .appendQueryParameter(PARAM_SORT,"latest")
                .appendQueryParameter(apiKey,"1e13a1ec462543d8a1b1100a0445c62f").build();

        URL url =null;
        try{
            String urlString= uri.toString();
            Log.d(TAG,"URL" +urlString);
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    public static String getResponseFromHttpUrl(URL url) throws IOException{
        HttpURLConnection urlConnection =(HttpURLConnection) url.openConnection();
        try{
            InputStream in =urlConnection.getInputStream();
            Scanner input = new Scanner(in);

            input.useDelimiter("\\A");
            return(input.hasNext()) ? input.next():null;
        }finally{
            urlConnection.disconnect();
        }
    }
}
