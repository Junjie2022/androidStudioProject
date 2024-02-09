package info.hccis.grading.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import info.hccis.grading.entity.GradingAssessmentTechnical;

/**
 * Interface which defines the methods available to interact with the database
 * @since 20240208
 * @author BJM
 */
@Dao
public interface GradingAssessmentDAO {

    @Insert
    void insert(GradingAssessmentTechnical gradingAssessmentTechnical);

    @Query("SELECT * FROM GradingAssessmentTechnical")
    List<GradingAssessmentTechnical> selectAll();

    @Query("delete from GradingAssessmentTechnical")
    public void deleteAll();

}
