package by.bstu.faa.wwi_guide_mobile.data_objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public abstract class IdDto {
    @SerializedName("_id")@Expose
    protected String id;
}
