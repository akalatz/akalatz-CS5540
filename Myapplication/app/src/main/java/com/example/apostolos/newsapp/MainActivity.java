package com.example.apostolos.newsapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;



public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;


    private TextView mErrorMessageDisplay;


    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_newsapp_search_results_json);


        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);


        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }


    private void makenewsapiSearchQuery() {
        String newsapiQuery = mSearchBoxEditText.getText().toString();
        URL newsapiSearchUrl = NetworkUtils.buildUrl(newsapiQuery);
        mUrlDisplayTextView.setText(newsapiSearchUrl.toString());
        new newsapiQueryTask().execute(newsapiSearchUrl);
    }


    private void showJsonDataView() {
        // First, make sure the error is invisible
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        // Then, make sure the JSON data is visible
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {

        mSearchResultsTextView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class newsapiQueryTask extends AsyncTask<URL, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String newsapiSearchResults = null;
            try {
                newsapiSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return newsapiSearchResults;
        }

        @Override
        protected void onPostExecute(String newsapiSearchResults) {
            // COMPLETED (27) As soon as the loading is complete, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (newsapiSearchResults != null && !newsapiSearchResults.equals("")) {

                showJsonDataView();
                mSearchResultsTextView.setText(newsapiSearchResults);
            } else {

                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makenewsapiSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
