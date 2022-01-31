package by.bstu.faa.wwi_guide_mobile.network_service.api;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AppApi {

    @POST("/auth/login")
    Call<Token> tokenData(@Body LoginData data);

    @GET("/api/years")
    Call<List<YearDto>> getYears(@Header("Authorization") String token);

    @GET("/api/events")
    Call<List<YearDto>> getEvents(@Header("Authorization") String token);

    @GET("/api/achievements")
    Call<List<YearDto>> getAchievements(@Header("Authorization") String token);
}
