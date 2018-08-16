package com.example.android.stocks;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Iterator;

// parser class --- only initialize at the beginning

public class parser {

    // 1 - financial stocks
    // 2 - bitcoin
    // Currently only functionality for financial stocks present
    public tuple parse(String json, int stockType){
        if(stockType == 1){
            return parseJSON(json);
        }

        return null;
    }

    // Method to return latest data as a tuple object

    public tuple parseJSON(String json){

        try{

            JSONObject obj = new JSONObject(json);

            if(obj.has("Error Message")){
                Log.d("error", "parseJSON: error ");
                return null;
            }

            JSONObject Meta = obj.getJSONObject("Meta Data");
            JSONObject Time = obj.getJSONObject("Time Series (Daily)");
            Iterator<String> it = Time.keys();

            String symbol = Meta.getString("2. Symbol");
            String date = it.next().toString();
            JSONObject DJSON = Time.getJSONObject(date);

            String open = DJSON.getString("1. open");
            String close = DJSON.getString("4. close");
            String high = DJSON.getString("2. high");
            String low = DJSON.getString("3. low");
            String volume = DJSON.getString("5. volume");

            Log.d("closing", "parseJSON: " + close);

            return new tuple(symbol, open, close, high, low, volume);

        }catch(JSONException e){
            e.printStackTrace();
            Log.d("exception", "parseJSON: erxception");
        };

        return null;

    }

}
