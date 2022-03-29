package by.bstu.faa.wwi_guide_mobile.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.Nullable;

import lombok.Data;

@Data
public class RegDto {
    @SerializedName("message")@Expose
    private String regStatus;
    @SerializedName("error")@Expose@Nullable
    private String regError;
}
