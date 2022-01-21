package by.bstu.faa.wwi_guide_mobile.network_service.api;

import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApi {

    //@GET("/posts/{id}")
    //Call<Post> getPostWithID(@Path("id") int id);

    @POST("/auth/login")
    Call<Token> tokenData(@Body LoginData data);
}
