package by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class LogDto {
    @SerializedName("actionName")@Expose
    private String actionName;
    @SerializedName("actionResult")@Expose
    private String actionResult;
    @SerializedName("timestamp")@Expose
    private String timestamp;
}
