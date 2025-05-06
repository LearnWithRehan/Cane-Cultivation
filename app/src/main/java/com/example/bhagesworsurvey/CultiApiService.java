package com.example.bhagesworsurvey;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CultiApiService {
    @Headers("Content-Type: application/json")
    @POST("save_culti.php")
    Call<ApiResponse> insertCulti(@Body CultiData cultiData);

    Call<ApiResponse> insertCultiData(CultivationDetails details);
}
