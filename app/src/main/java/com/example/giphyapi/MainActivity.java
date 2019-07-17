package com.example.giphyapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.giphyapi.Retrofit.GiphyService;
import com.example.giphyapi.Retrofit.RetrofitClientInstance;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements GiphyAdapter.OnGiphyClicked {

    private static final String TAG = "MainActivitys";
    private RecyclerView recyclerView;
    private GiphyAdapter giphyAdapter;
    Boolean brandonsBoolean = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
//        new GiphyTask().execute("100");
//        volleyRequest(100);
        retrofitRequest(100);

        findViewById(R.id.btnView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (brandonsBoolean == false) {
                    brandonsBoolean = true;
                    recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
                } else {
                    brandonsBoolean = false;
                    recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
                }
            }
        });

    }

    @Override
    public void giphyClicked(String url) {
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

//    class GiphyTask extends AsyncTask<String, Void, List<String>> {
//        @Override
//        protected List<String> doInBackground(String... strings) {
//
//            HttpURLConnection httpURLConnection = null;
//
//            //
//            String baseUrl = "http://shibe.online/api/shibes";
//            String query = "?count=" + strings[0];
//
//            // This will hold all the JSON
//            StringBuilder result = new StringBuilder();
//
//            // Create a URL object, passing the url string into the constructor
//            try {
//                URL url = new URL(baseUrl + query);
//
//                // Use the URL object instance to create an internet connection
//                httpURLConnection = (HttpURLConnection) url.openConnection();
//
//                //Create an Input Stream Instance and init it with a BufferedInputStream
//                //Then pass the stream from the httpURLConnection instance
//                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
//
//                // Declare InputStreamReader and init it with inputstream
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//
//                //Declare BufferReader Object and init it with inputstream
//                BufferedReader reader = new BufferedReader(inputStreamReader);
//
//                String line;
//
//                // Read each line from the bufferedreader object and append it to our result(StringBuilder)
//                while ((line = reader.readLine()) != null) {
//                    //if line is not null append to result
//                    result.append(line);
//                }
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (httpURLConnection != null) {
//                    httpURLConnection.disconnect();
//                }
//            }
//
//            Log.d(TAG, "doInBackground: " + result);
//
//            //Convert String(JSON) into List<String>
//
//            // First remove the brackets
//            String removeBrackets = result.substring(1, result.length() - 1);
//
//            //Second replace quotes with nothing
//            String removeQuotes = removeBrackets.replace("\"", "");
//
//            //Third split using comma(,) which creates a new line after ever comma(,)
//            String[] urls = removeQuotes.split(",");
//
//            List<String> url = Arrays.asList(urls);
//
//
//            return url;
//        }

//        @Override
//        protected void onPostExecute(List<String> strings) {
//            loadRecyclerView(strings);
//            super.onPostExecute(strings);
//        }
//
//    }

    public void retrofitRequest(int count) {
        // 1. Declare GiphyService and Init using RetrofitClientInstance
        GiphyService giphyService = RetrofitClientInstance.getRetrofit().create(GiphyService.class);

        // Declare GiphyService Return type and Init using the GiphyService from step 1
        Call<List<String>> giphycall = giphyService.loadGiphy(count);

        // 3. Use the giphyCall from step 2 and call the .enqueue method
        giphycall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, retrofit2.Response<List<String>> response) {

                if (response.isSuccessful()){
                Log.d(TAG, "onResponse: Success");
                loadRecyclerView(response.body());

                } else {
                    Log.d(TAG, "onResponse: Fail");
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
            }
        });

    }

//    public void volleyRequest(int count) {
//        // 1. Setup url
//        String baseUrl = "http://shibe.online/api/shibes";
//        String query = "?count=" + count;
//
//        // combine to url
//        String url = baseUrl + query;
//
//        // 2. Declare RequestQueue Object Instance and Init it with Volley.newreqestQueue()
//        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
//
//        // 3. Declare JsonArrayRequest or JsonObjectRequest (depends on your .json file structure)
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
//                url,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        List<String> urls = new ArrayList<>();
//
//                        for (int i = 0; i < response.length(); i++) {
//
//                            try {
//                                urls.add(response.get(i).toString());
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        loadRecyclerView(urls);
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Log.d(TAG, "onErrorResponse: " + error.getLocalizedMessage());
//                    }
//                }
//        );
//
//        // 4. Pass the request object from step 3 into the requestQueue object from step 2
//        requestQueue.add(jsonArrayRequest);
//
//    }

    private void loadRecyclerView(List<String> strings) {
        GiphyAdapter giphyAdapter = new GiphyAdapter(strings, MainActivity.this);
        recyclerView.setAdapter(giphyAdapter);
    }
}
