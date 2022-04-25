package by.bstu.faa.wwi_guide_mobile.database.dao;

import androidx.room.Dao;

import by.bstu.faa.wwi_guide_mobile.database.dao.base_dao.BaseDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.LogEntity;

@Dao
public interface LogDao extends BaseDao<LogEntity> {
}
