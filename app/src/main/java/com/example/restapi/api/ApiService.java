package com.example.restapi.api;

import com.example.restapi.model.AddMahasiswaResponse;
import com.example.restapi.model.DeleteMahasiswaResponse;
import com.example.restapi.model.MahasiswaResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("mahasiswa")
    Call<MahasiswaResponse> getMahasiswa(@Query("nrp") String nrp);

    @GET("mahasiswa")
    Call<MahasiswaResponse> getMahasiswa();

    @POST("mahasiswa")
    @FormUrlEncoded
    Call<AddMahasiswaResponse> addMahasiswa(
            @Field("nrp") String nrp,
            @Field("nama") String nama,
            @Field("email") String email,
            @Field("jurusan") String jurusan
    );

    @FormUrlEncoded
    @HTTP(method = "DELETE", path = "mahasiswa", hasBody = true)
    Call<DeleteMahasiswaResponse> deleteMahasiswa(@Field("id") String id);

    @FormUrlEncoded
    @HTTP(method = "PUT", path = "mahasiswa", hasBody = true)
    Call<AddMahasiswaResponse> updateMahasiswa(@Field("id") String id,
                                                  @Field("nrp") String nrp,
                                                  @Field("nama") String nama,
                                                  @Field("email") String email,
                                                  @Field("jurusan") String jurusan
    );
}
