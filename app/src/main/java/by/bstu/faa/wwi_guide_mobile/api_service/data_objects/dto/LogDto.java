package by.bstu.faa.wwi_guide_mobile.api_service.data_objects.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import lombok.Data;

@Data
public class LogDto {
    @SerializedName("actionName")@Expose
    private String actionName;
    @SerializedName("actionResult")@Expose
    private String actionResult;
    @SerializedName("timestamp")@Expose
    private String timestamp;

    public void formLog(String actionName, String actionResult){
        Calendar calendar = Calendar.getInstance();

        this.actionName = actionName;
        this.actionResult = actionResult;

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);

        this.timestamp = day + "." + month + "." + year
                + " --- " + hours + ":" + minutes;
    }
}
