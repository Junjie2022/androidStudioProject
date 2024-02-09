package info.hccis.grading.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import info.hccis.grading.entity.GradingAssessmentTechnical;


@Database(entities = {GradingAssessmentTechnical.class},version = 1)
public abstract class MyAppDatabase extends RoomDatabase {

    public abstract GradingAssessmentDAO gradingAssessmentDAO();
}

