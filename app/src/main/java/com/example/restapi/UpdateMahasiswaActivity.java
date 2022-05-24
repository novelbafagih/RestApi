package com.example.restapi;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.restapi.api.ApiConfig;
import com.example.restapi.model.AddMahasiswaResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateMahasiswaActivity extends AppCompatActivity {
    private EditText edtNama;
    private EditText edtEmail;
    private EditText edtJurusan;
    private ProgressBar progressBar;
    private Button btnAdd;
    private String id;
    private String nrp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mahasiswa);
        edtNama = findViewById(R.id.edtNama);
        edtEmail = findViewById(R.id.edtEmail);
        edtJurusan = findViewById(R.id.edtJurusan);
        progressBar = findViewById(R.id.progressBar);
        btnAdd = findViewById(R.id.btnAdd);
        Intent intent = getIntent();

        id = intent.getStringExtra("id");
        nrp = intent.getStringExtra("nrp");
        btnAdd.setOnClickListener(view -> {
            updateMahasiswa();
        });
    }

    private void updateMahasiswa() {
        showLoading(true);
        String nama = edtNama.getText().toString();
        String email = edtEmail.getText().toString();
        String jurusan = edtJurusan.getText().toString();
        if (nama.isEmpty() ||
                email.isEmpty() || jurusan.isEmpty()) {
            Toast.makeText(UpdateMahasiswaActivity.this, "Silahkan lengkapi form terlebih dahulu",
                    Toast.LENGTH_SHORT).show();
            showLoading(false);
        } else {
            Call<AddMahasiswaResponse> client =
                    ApiConfig.getApiService().updateMahasiswa(id,nrp, nama, email,
                            jurusan);
            client.enqueue(new
                                   Callback<AddMahasiswaResponse>() {
                                       @Override
                                       public void
                                       onResponse(Call<AddMahasiswaResponse> call,
                                                  Response<AddMahasiswaResponse> response) {
                                           showLoading(false);

                                           if (response.isSuccessful()) {
                                               if (response.body() != null) {
                                                   Toast.makeText(UpdateMahasiswaActivity.this, "Berhasil menambahakan silahakan cek data pada halaman list !",
                                                           Toast.LENGTH_SHORT).show();
                                               }
                                           } else {
                                               if (response.body() != null) {
                                                   Log.e("", "onFailure: " +
                                                           response.body().getMessage());
                                               }
                                           }
                                       }

                                       @Override
                                       public void
                                       onFailure(Call<AddMahasiswaResponse> call, Throwable t) {
                                           showLoading(false);

                                           Log.e("Error Retrofit", "onFailure: "

                                                   + t.getMessage());
                                       }
                                   });
        }
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}