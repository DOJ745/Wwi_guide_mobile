package by.bstu.faa.wwi_guide_mobile.repo;

import by.bstu.faa.wwi_guide_mobile.constants.Constants;
import by.bstu.faa.wwi_guide_mobile.data_objects.LoginData;
import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.network_service.RetrofitService;
import by.bstu.faa.wwi_guide_mobile.network_service.api.UserApi;
import by.bstu.faa.wwi_guide_mobile.network_service.callback.TokenCallback;
import by.bstu.faa.wwi_guide_mobile.network_service.custom_error.TokenError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenRepo implements Callback<Token> {

    private TokenCallback callback;
    private TokenError tokenErrors = new TokenError(
            Constants.Values.CODE_SERVER_ERROR,
            "No response from server!");

    public TokenRepo(TokenCallback _callback) { this.callback = _callback; }

    void getToken(LoginData data){
        callback.onLoading(true);
        UserApi request = RetrofitService.getInstance().buildService(UserApi.class);
        Call call = request.tokenData(data);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Token> call, Response<Token> response) {

    }

    @Override
    public void onFailure(Call<Token> call, Throwable t) {

    }
}
