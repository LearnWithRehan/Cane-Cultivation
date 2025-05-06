package com.example.bhagesworsurvey;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import retrofit2.http.Query;

public interface ApiService {
    @POST("login.php") // Replace "your-endpoint" with the actual endpoint from your API
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("get_village_name.php")
    Call<ApiResponse> getVillageName(@Query("V_code") String vCode);

    @GET("get_grower_name.php") // replace with your actual PHP file name
    Call<GrowerResponse> getGrowerName(
            @Query("G_no") String gNo,
            @Query("G_vill") String gVill
    );

    @GET("survey_fetch.php")
    Call<SurveyResponse> getSurveyData(
            @Query("V_code") String vCode,
            @Query("G_code") String gCode
    );


    @POST("save_culti.php")
    Call<ApiResponseculti> saveCultivationData(@Body CultivationData cultivationData);

}
