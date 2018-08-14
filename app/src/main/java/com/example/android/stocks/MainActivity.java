package com.example.android.stocks;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

// Create item.xml to store the listView which shows stock data


// Run an async task in background constantly to keep updating stock prices. We can make
// seperate thread to keep this async task running constantly (when stock app is open)

// As for now, we can retrieve latest data using a refresh button click.

// When json is retrieved, call parser to parse the json and return latest prices and volume traded.


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private parser jsonParser = new parser();;
    private Button refresh;
    private String[] stockNames;

    // Each element of stockData is of the format - (name, close)
    // Get a list of stock symbols of the US stock exchange, and store it in an arrayList or array.
    // Then for each symbol, make a separate http request to get json data.
    private String[][] stockData;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_watchlist:
                    mTextMessage.setText(R.string.title_watchlist);
                    return true;
                case R.id.navigation_settings:
                    mTextMessage.setText(R.string.title_settings);
                    return true;
            }
            return false;
        }
    };

    // Make the id for the refresh button as "refresh_button"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        refresh = (Button) findViewById(R.id.refresh_button);

        // Fill stockNames with data here ---

        stockData = new String[stockNames.length][2];

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    // Add code for refresh button click
    // When refresh button is clicked, iterate through each element of stockNames, and make
    // an http call for each stock. Load 20 stocks in one page (for simplicity).
    // Each time, when json is received for each stock, call populateStockData.

    public void RefreshButtonClicked(View v){



    }

    // stockIndex is nothing but the index of the stock in the array stockNames
    // This is called with each http request (once for each stock)

    public void populateStockData(String JSON, int stockIndex){
        tuple data = jsonParser.parse(JSON, 1);
        if(data == null){
            stockData[stockIndex][0] = "Undefined";
            stockData[stockIndex][1] = "Undefined";
            return;
        }
        stockData[stockIndex][0] = data.getName();
        stockData[stockIndex][1] = data.getClose();
    }

    // Make sure to make all necessary http requests before calling populateListView
    // This should be called in the end, when all json data is parsed, and stockData is filled
    public void PopulateListView(){

        String[] stocks = new String[stockData.length];
        for(int i = 0; i < stocks.length; i++){
            stocks[i] = stockData[i][0] + " -- " + stockData[i][1];
        }

        ArrayAdapter<String> stockAdapter =
                new ArrayAdapter<String>(this,
                        R.layout.item,
                        R.id.stock_data,
                        stocks
                );

        ListView stockList = new ListView(this);
        setContentView(stockList);
        stockList.setAdapter(stockAdapter);
    }

    public void makeStockSearch() {
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasSelected = item.getItemId();
        if (itemThatWasSelected == R.id.refreshing) {
            makeStockSearch();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
