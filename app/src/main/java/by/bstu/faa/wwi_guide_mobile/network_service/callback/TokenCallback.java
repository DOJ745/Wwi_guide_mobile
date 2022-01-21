package by.bstu.faa.wwi_guide_mobile.network_service.callback;

import by.bstu.faa.wwi_guide_mobile.data_objects.Token;
import by.bstu.faa.wwi_guide_mobile.network_service.custom_error.TokenError;

public interface TokenCallback {
    void onSuccess(Token token);
    void onFailed(TokenError error);
    void onLoading(boolean loadingStatus);
}
