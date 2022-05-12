package by.bstu.faa.wwi_guide_mobile.ui.fragments.view_models;

import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import io.reactivex.Maybe;

public interface GetUserMethods { Maybe<UserEntity> getUserFromDB();}
