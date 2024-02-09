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
     * 提供用于在整个应用程序中使用的数据库的getter。
     * @since 20230209
     * @author BJM
     */
    public static MyAppDatabase getMyAppDatabase() {
        return myAppDatabase;
    }

    /**
     * 这个方法将设置用于在此应用程序中使用的数据库属性
     * @since 20230209
     * @author BJM
     * @param context
     */
    public static void setMyAppDatabase(Context context){
        //****************************************************************************************
        //设置数据库属性（Room数据库）
        //****************************************************************************************
        if (myAppDatabase == null) {
            myAppDatabase = Room.databaseBuilder(context, MyAppDatabase.class, "gradingassessmentdb").allowMainThreadQueries().build();
        }
    }

    /**
     * 此方法将根据列表中的项目重新加载房间数据库。
     * @param gradingAssessments
     * @since 20220210
     * @author BJM
     */
    public static void reloadGradingAssessmentsInRoom(List<GradingAssessmentTechnical> gradingAssessments)
    {
        //检查myAppDatabase对象是否为空
        if (myAppDatabase == null) {
            Log.e("GradingAssessmentContent", "MyAppDatabase 对象为空，无法重新加载评估。");
            return;
        }

        //删除所有条目
        myAppDatabase.gradingAssessmentDAO().deleteAll();

        //循环并插入每个条目。
        for(GradingAssessmentTechnical current : gradingAssessments)
        {
            myAppDatabase.gradingAssessmentDAO().insert(current);
        }

        Log.d("BJM Room","加载技能评估到Room中（加载了" + gradingAssessments.size() + "个）");
    }

    /**
     * 从Room数据库中获取所有实体。
     * @return 实体列表
     * @since 20240208
     * @author BJM
     */
    public static List<GradingAssessmentTechnical> getGradingAssessmentsFromRoom()
    {
        Log.d("JJ Room","从Room中加载评估订单");

        List<GradingAssessmentTechnical> entitiesBack = myAppDatabase.gradingAssessmentDAO().selectAll();
        Log.d("JJ Room","从Room中加载的实体数量: " + entitiesBack.size());
        for(GradingAssessmentTechnical current : entitiesBack)
        {
            Log.d("JJ Room",current.toString());
        }
        return entitiesBack;
    }
}
