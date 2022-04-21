package by.bstu.faa.wwi_guide_mobile.ui.adapters;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.data_objects.dto.AchievementDto;

public interface AdapterSetItem<T> {
    public void setItems(List<T> items);
}
