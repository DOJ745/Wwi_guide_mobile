package by.bstu.faa.wwi_guide_mobile.api_service.api;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.RegData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.AchievementDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.AppMsgResponseDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.CountryDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.EventDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.RankDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.SurveyAnswerDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.SurveyQuestionDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.TestAnswerDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.TestQuestionDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.TestThemeDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.UserDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.ArmamentDto;
import by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.YearDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface AppApi {

    @POST("auth/login")
    Call<UserDto> loginUser(@Body LoginData data);

    @POST("auth/reg")
    Call<AppMsgResponseDto> registerUser(@Body RegData regData);

    @PUT("users")
    Call<AppMsgResponseDto> updateUserInfo(@Header("Authorization") String token);

    @POST("logs")
    Call<AppMsgResponseDto> sendLog(@Header("Authorization") String token);

    @GET("years")
    Call<List<YearDto>> getYears();

    @GET("ranks")
    Call<List<RankDto>> getRanks();

    @GET("countries")
    Call<List<CountryDto>> getCountries();

    @GET("armament")
    Call<List<ArmamentDto>> getArmament();

    @GET("tests-themes")
    Call<List<TestThemeDto>> getTestsThemes();

    @GET("tests-questions")
    Call<List<TestQuestionDto>> getTestsQuestions();

    @GET("tests-answers")
    Call<List<TestAnswerDto>> getTestsAnswers();

    @GET("surveys-questions")
    Call<List<SurveyQuestionDto>> getSurveysQuestions();

    @GET("surveys-answers")
    Call<List<SurveyAnswerDto>> getSurveysAnswers();

    @GET("events")
    Call<List<EventDto>> getEvents();

    @GET("achievements")
    Call<List<AchievementDto>> getAchievements();
}
