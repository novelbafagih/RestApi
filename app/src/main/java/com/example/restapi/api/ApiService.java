package com.example.restapi.api;

import com.example.restapi.model.AddMahasiswaResponse;
import com.example.restapi.model.MahasiswaResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("mahasiswa")
    Call<MahasiswaResponse> getMahasiswa(@Query("nrp") String nrp);
    @POST("mahasiswa")
    @FormUrlEncoded
    Call<AddMahasiswaResponse> addMahasiswa(
            @Field("nrp") String nrp,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("jurusan") String jurusan
    );
}
