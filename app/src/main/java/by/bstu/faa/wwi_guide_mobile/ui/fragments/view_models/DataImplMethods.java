package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import io.reactivex.Single;

public interface DataImplMethods<T> extends GetUserMethod, UpdateUser {
    Single<T> getEntityDataById(String entityId);
}
