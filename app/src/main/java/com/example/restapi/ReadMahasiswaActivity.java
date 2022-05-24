package com.example.restapi;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.restapi.api.ApiConfig;
import com.example.restapi.model.Mahasiswa;
import com.example.restapi.model.MahasiswaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class ReadMahasiswaActivity extends AppCompatActivity {
    private RecyclerView mahasiswaRecycler;
    private MahasiswaAdapter mahasiswaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_mahasiswa);
        mahasiswaRecycler = findViewById(R.id.recyclerView);
        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        mahasiswaAdapter = new MahasiswaAdapter();

        mahasiswaRecycler.setAdapter(mahasiswaAdapter);
        mahasiswaRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getData(){
        Call<MahasiswaResponse> client =
                ApiConfig.getApiService().getMahasiswa();
        client.enqueue(new
                               Callback<MahasiswaResponse>() {
                                   @Override
                                   public void
                                   onResponse(Call<MahasiswaResponse> call,
                                              Response<MahasiswaResponse> response) {
                                       if (response.isSuccessful()){
                                           if (response.body() != null){
                                               List<Mahasiswa> mahasiswaList = response.body().getData();
                                               mahasiswaAdapter.setItems(new ArrayList<>(mahasiswaList));
                                           }
                                       } else {
                                           if (response.body() != null)
                                           {
                                               Log.e("", "onFailure: " +
                                                       response.message());
                                           }
                                       }
                                   }
                                   @Override
                                   public void

                                   onFailure(Call<MahasiswaResponse> call, Throwable t) {
                                       Log.e("Error Retrofit",

                                               "onFailure: " + t.getMessage());
                                   }
                               });
    }
}