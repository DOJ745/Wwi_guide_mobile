package by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import lombok.Data;

@Data
public class AppMsgResponseDto {
    @SerializedName("message")@Expose
    private String msgStatus;
    @SerializedName("error")@Expose@Nullable
    private String msgError;
    @SerializedName("token")@Expose@Nullable
    private String token;
}
