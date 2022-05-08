package by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto.base_dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public abstract class IdDto {
    @SerializedName("_id")@Expose
    protected String id;
}
