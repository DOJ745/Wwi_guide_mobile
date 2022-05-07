package by.bstu.faa.wwi_guide_mobile.database;

import by.bstu.faa.wwi_guide_mobile.database.dao.AchievementDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.ArmamentDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.CountryDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.EventDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.RankDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyAnswerDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.SurveyQuestionDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestAnswerDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.TestQuestionDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.UserDao;
import by.bstu.faa.wwi_guide_mobile.database.dao.YearDao;
import by.bstu.faa.wwi_guide_mobile.database.entities.AchievementEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.CountryEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.EventEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.LogEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.RankEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.SurveyQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestAnswerEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.TestQuestionEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.UserEntity;
import by.bstu.faa.wwi_guide_mobile.database.entities.YearEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {
        UserEntity.class,
        EventEntity.class,
        YearEntity.class,
        AchievementEntity.class,
        RankEntity.class,
        CountryEntity.class,
        SurveyAnswerEntity.class,
        SurveyQuestionEntity.class,
        TestAnswerEntity.class,
        TestQuestionEntity.class,
        LogEntity.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract EventDao eventDao();
    public abstract RankDao rankDao();
    public abstract YearDao yearDao();
    public abstract AchievementDao achievementDao();
    public abstract ArmamentDao armamentDao();
    public abstract TestAnswerDao testAnswerDao();
    public abstract TestQuestionDao testQuestionDao();
    public abstract SurveyAnswerDao surveyAnswerDao();
    public abstract SurveyQuestionDao surveyQuestionDao();
    public abstract CountryDao countryDao();
}
