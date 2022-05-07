package by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import by.bstu.faa.wwi_guide_mobile.network_service.data_objects.dto.base_dto.IdDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TestThemeDto extends IdDto {
    @SerializedName("name")@Expose
    private String name;
}
