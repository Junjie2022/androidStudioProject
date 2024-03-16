//package info.hccis.grading.entity;
//
//import android.content.Context;
//import android.util.Log;
//
//import androidx.room.Room;
//
//import java.util.List;
//
//import info.hccis.grading.dao.MyAppDatabase;
//
//
///**
// * This class will be used to access the business data stored on the local device database.
// * @since 20230209
// * @author BJM
// */
//public class GradingAssessmentContent {
//
//    //Room Database attribute
//    private static MyAppDatabase myAppDatabase;
//
//    /**
//     * Provide a getter for the database to be used throughout the app.
//     * @since 20230209
//     * @author BJM
//     */
//
//    public static MyAppDatabase getMyAppDatabase() {
//        return myAppDatabase;
//    }
//
//    /**
//     * This method will setup the database attribute to be used in this ap
//     * @since 20230209
//     * @author BJM
//     * @param context
//     */
//    public static void setMyAppDatabase(Context context){
//        //****************************************************************************************
//        //Set the database attribute (Room database)
//        //****************************************************************************************
//        myAppDatabase = Room.databaseBuilder(context, MyAppDatabase.class, "gradingassessmentdb").allowMainThreadQueries().build();
//
//    }
//
//    /**
//     * This method will take the list passed in and reload the room database
//     * based on the items in the list.
//     * @param gradingAssessments
//     * @since 20220210
//     * @author BJM
//     */
//    public static void reloadGradingAssessmentsInRoom(List<GradingAssessmentTechnical> gradingAssessments)
//    {
//        //Delete all entries
//        getMyAppDatabase().gradingAssessmentDAO().deleteAll();
//
//        //Loop through and insert each entry.
//        for(GradingAssessmentTechnical current : gradingAssessments)
//        {
//            getMyAppDatabase().gradingAssessmentDAO().insert(current);
//        }
//
//        Log.d("BJM Room","loading skills assessments into Room ("+gradingAssessments.size()+" loaded");
//
//    }
//
//
//    /**
//     * This method will obtain all the entities out of the Room database.
//     * @return list of entities
//     * @since 20240208
//     * @author BJM
//     */
//    public static List<GradingAssessmentTechnical> getGradingAssessmentsFromRoom()
//    {
//        Log.d("JJ Room","Loading ticket orders from Room");
//
//        List<GradingAssessmentTechnical> entitiesBack = getMyAppDatabase().gradingAssessmentDAO().selectAll();
//        Log.d("JJ Room","Number of entities loaded from Room: " + entitiesBack.size());
//        for(GradingAssessmentTechnical current : entitiesBack)
//        {
//            Log.d("JJ Room",current.toString());
//        }
//        return entitiesBack;
//    }
//
//}

package info.hccis.grading.entity;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.util.List;

import info.hccis.grading.dao.MyAppDatabase;
import info.hccis.grading.entity.GradingAssessmentTechnical;

/**
 * 这个类用于访问存储在本地设备数据库中的业务数据。
 * @since 20230209
 * @author BJM
 */
public class GradingAssessmentContent {

    //Room Database 属性
    private static MyAppDatabase myAppDatabase;

    /**
     * Provide a getter for the database to be used throughout the app.
     * @since 20230209
     * @author JJ
     */
    public static MyAppDatabase getMyAppDatabase() {
        return myAppDatabase;
    }

    /**
     *his method will setup the database attribute to be used in this ap
     * @since 20230209
     * @author JJ
     * @param context
     */
    public static void setMyAppDatabase(Context context){
        //****************************************************************************************
        //设置数据库属性（Room数据库）
        //****************************************************************************************

            myAppDatabase = Room.databaseBuilder(context, MyAppDatabase.class, "gradingassessmentdb").allowMainThreadQueries().build();

    }

    /**
     * 此方法将根据列表中的项目重新加载房间数据库。
     * @param gradingAssessments
     * @since 20220210
     * @author BJM
     */
    public static void reloadGradingAssessmentsInRoom(List<GradingAssessmentTechnical> gradingAssessments)
    {
        //删除所有条目
        getMyAppDatabase().gradingAssessmentDAO().deleteAll();

        //循环并插入每个条目。
        for(GradingAssessmentTechnical current : gradingAssessments)
        {
            getMyAppDatabase().gradingAssessmentDAO().insert(current);
        }

        Log.d("JJ Room","loading skills assessments into Room ("+gradingAssessments.size()+" loaded");
    }

    /**
     * /**
     *      * This method will take the list passed in and reload the room database
     *      * based on the items in the list.
     *      * @param gradingAssessments
     *      * @since 20220210
     *      * @author JJ
     */
    public static List<GradingAssessmentTechnical> getGradingAssessmentsFromRoom()
    {
        Log.d("JJ Room","Loading ticket orders from Room");

        List<GradingAssessmentTechnical> entitiesBack = myAppDatabase.gradingAssessmentDAO().selectAll();
        Log.d("JJ Room","Number of entities loaded from Room: " + entitiesBack.size());
        for(GradingAssessmentTechnical current : entitiesBack)
        {
            Log.d("JJ Room",current.toString());
        }
        return entitiesBack;
    }
}
