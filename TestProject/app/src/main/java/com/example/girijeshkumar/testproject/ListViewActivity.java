package com.example.girijeshkumar.testproject;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.girijeshkumar.testproject.Adapter.MovieListViewAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import Model.Question;
import Model.StackOverflowAPI;
import Model.StackOverflowQuestions;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ListViewActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, Callback<StackOverflowQuestions> {

    public ArrayList<Question> arrOfQuestion;
    SwipeRefreshLayout layout;
    MovieListViewAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        arrOfQuestion = getIntent().getExtras().getParcelableArrayList("list");
        Log.i("list", arrOfQuestion.toString());

        layout = (SwipeRefreshLayout) findViewById(R.id.SwapRefresh);
        layout.setOnRefreshListener(this);
        ListView listView = (ListView) findViewById(R.id.listView);
        adapter = new MovieListViewAdapter(this, arrOfQuestion);
        listView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        arrOfQuestion.clear();
        adapter.notifyDataSetChanged();

        getDataFromAPI();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);

    }

    private void getDataFromAPI()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //setting up client
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(interceptor);
        client.setConnectTimeout(5, TimeUnit.MINUTES);
        client.setReadTimeout(5, TimeUnit.MINUTES);

        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.stackexchange.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        // prepare call in Retrofit 2.0
        StackOverflowAPI stackOverflowAPI = retrofit.create(StackOverflowAPI.class);
        Call<StackOverflowQuestions> call = stackOverflowAPI.loadQuestions("android");
//TODO test
        //asynchronous call
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<StackOverflowQuestions> response, Retrofit retrofit) {

        StackOverflowQuestions body = response.body();
        Gson gson = new Gson();
        String json = gson.toJson(body);
        Log.i("Response", json);
        arrOfQuestion.clear();
        arrOfQuestion.addAll(body.getItems());
        adapter.notifyDataSetChanged();
        layout.setRefreshing(false);

    }

    @Override
    public void onFailure(Throwable t) {

    }
}
