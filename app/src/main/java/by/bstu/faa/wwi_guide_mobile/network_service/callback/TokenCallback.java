package by.bstu.faa.wwi_guide_mobile.network_service.callback;

import by.bstu.faa.wwi_guide_mobile.data_objects.TokenData;
import by.bstu.faa.wwi_guide_mobile.network_service.custom_error.TokenError;

public interface TokenCallback {
    void onSuccess(TokenData token);
    void onFailed(TokenError error);
    void onLoading(boolean loadingStatus);
}
