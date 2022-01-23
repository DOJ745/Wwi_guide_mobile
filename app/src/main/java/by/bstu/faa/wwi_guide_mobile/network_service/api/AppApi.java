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

    //@GET("/posts/{id}")
    //Call<Post> getPostWithID(@Path("id") int id);

    @POST("/auth/login")
    Call<Token> tokenData(@Body LoginData data);

    @GET("/api/years")
    Call<List<YearDto>> getYears(@Header("Authorization") String token);
}
