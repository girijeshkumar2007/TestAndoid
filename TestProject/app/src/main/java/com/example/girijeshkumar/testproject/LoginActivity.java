package com.example.girijeshkumar.testproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

import Model.StackOverflowAPI;
import Model.StackOverflowQuestions;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, Callback<StackOverflowQuestions> {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        EditText txtViewEmail = (EditText) findViewById(R.id.EmailTxt);
        EditText txtViewPassword = (EditText) findViewById(R.id.PasswordTxt);

        Button btn = (Button) findViewById(R.id.ButtonSubmit);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        View focusViews = this.getCurrentFocus();
        if (focusViews != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusViews.getWindowToken(), 0);
        }
        switch (v.getId()) {
            case R.id.ButtonSubmit:
                Snackbar.make(v, "Button Clicked", Snackbar.LENGTH_LONG).show();
                callLoginAPI();
                break;
        }
    }

    public void callLoginAPI() {
        progressDialog = ProgressDialog.show(this, "", "Loading ...");

       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        },5000);*/
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

        //asynchronous call
        call.enqueue(this);
    }

    @Override
    public void onResponse(Response<StackOverflowQuestions> response, Retrofit retrofit) {
        progressDialog.dismiss();
        StackOverflowQuestions body = response.body();
        Gson gson = new Gson();
        String json = gson.toJson(body);
        Log.i("Response", json);
        Intent listViewA = new Intent(LoginActivity.this,ListViewActivity.class);
        listViewA.putExtra("list", body.getItems());
        startActivity(listViewA);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
