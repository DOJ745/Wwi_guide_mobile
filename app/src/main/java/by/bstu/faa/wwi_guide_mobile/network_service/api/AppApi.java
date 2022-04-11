package by.bstu.faa.wwi_guide_mobile.network_service.api;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AppMsgResponseDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.EventDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.data_objects.dto.YearDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface AppApi {

    @POST("/auth/login")
    Call<UserDto> loginUser(@Body LoginData data);

    @POST("/auth/reg")
    Call<AppMsgResponseDto> registerUser(@Body RegData regData);

    @GET("/years")
    Call<List<YearDto>> getYears(@Header("Authorization") String token);

    @GET("/ranks")
    Call<List<RankDto>> getRanks(@Header("Authorization") String token);

    @GET("/countries")
    Call<List<CountryDto>> getCountries();

    @GET("/events")
    Call<List<EventDto>> getEvents(@Header("Authorization") String token);

    @GET("/achievements")
    Call<List<AchievementDto>> getAchievements(@Header("Authorization") String token);
}
