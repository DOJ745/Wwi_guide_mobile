package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

import by.bstu.faa.wwi_guide_mobile.constants.CONSTANTS;
import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.RankEntity;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface RankDao extends BaseDao<RankEntity> {
    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.RANKS_TABLE)
    Maybe<List<RankEntity>> getAll();

    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.RANKS_TABLE + " WHERE id = :rankId")
    Single<RankEntity> getRankById(String rankId);

    @Query("SELECT * FROM " + CONSTANTS.APP_DATABASE.RANKS_TABLE + " WHERE countryId = :countryId")
    Single<List<RankEntity>> getRanksByCountryId(String countryId);
}
