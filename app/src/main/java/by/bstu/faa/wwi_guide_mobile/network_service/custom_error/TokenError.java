package by.bstu.faa.wwi_guide_mobile.network_service.custom_error;

public class TokenError {
    private int code;
    private String message;

    public TokenError(int _code, String _message) {
        this.code = _code;
        this.message = _message;
    }
}
