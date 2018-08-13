package com.example.android.stocks.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {


    final static String SearchQuery = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&interval=1min&apikey=GJX95WU3CGMSBH7Y";

    final static String ParamStock = "symbol";

    public static URL buildUrl(String stockName) {

        Uri builtUri = Uri.parse(SearchQuery).buildUpon()
                .appendQueryParameter(ParamStock,stockName)
                .build();

        URL url = null;

        try {
            url= new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner sc = new Scanner(in);
            sc.useDelimiter("\\A");

            if (sc.hasNext()) {
                return sc.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
