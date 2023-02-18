package com.example.postdatatoapiretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    private TextView responseTV;
    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPost);
        responseTV = findViewById(R.id.idTVResponse);
        loadingPB = findViewById(R.id.idLoadingPB);

        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this,"Please enter both the feilds",Toast.LENGTH_SHORT).show();
                    return;
                }
                postData(nameEdt.getText().toString(),jobEdt.getText().toString());
            }
        });

    }
    private void postData(String name, String job) {
        loadingPB.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name",name);
        jsonObject.addProperty("job",job);


        DataModal modal = new DataModal(name,job);
        Call<DataModal> call = retrofitAPI.createPost(jsonObject);
        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                Toast.makeText(MainActivity.this,"Data added to API",Toast.LENGTH_SHORT).show();
                loadingPB.setVisibility(View.GONE);
                jobEdt.setText("");
                nameEdt.setText("");
                DataModal responseFromAPI = response.body();
                String responseString = "Response Code : " + response.code() + "/nName : " +responseFromAPI.getName() + "/nJob : " + responseFromAPI.getJob();
                responseTV.setText(responseString);
            }

            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {
                Log.e("tag",t.getMessage());
                responseTV.setText("Error found is : "+ t.getMessage());
            }
        });
    }
}