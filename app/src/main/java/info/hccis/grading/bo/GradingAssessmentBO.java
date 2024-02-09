package info.hccis.grading.bo;

import android.util.Log;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import info.hccis.grading.entity.GradingAssessmentTechnical;

public class GradingAssessmentBO {
    /**
     * calculate and set the technical score in the sast passed in
     *
     * @param sast
     * @return technical grading
     * @author Junjie
     * @since 20231026
     */
    public static String calculateLetterGrade(GradingAssessmentTechnical sast) {
        if (sast.getNumericGrade() >= 90) {
            return "A";
        } else if (sast.getNumericGrade() >= 80) {
            return "B";
        } else if (sast.getNumericGrade() >= 70) {
            return "C";
        } else if (sast.getNumericGrade() >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    /**
     * This method will give back an ArrayList of assessments for testing until
     * we can get this from our api.
     *
     * @return list
     * @author Junjie
     * @since 20240118
     */
    public static ArrayList<GradingAssessmentTechnical> getTestList() throws JSONException {
        String jsonForAssessments = "[{\"id\":1,\"assessmentDate\":\"2022-08-22\",\"studentName\":\"Junjie\",\"instructorName\":\"BJ\",\"courseName\":\"CIS1122\" }]";
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(jsonForAssessments);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        //**************************************************************
        //For each json object in the array, show the first and last names
        //**************************************************************
        System.out.println("Here are the rows");
        Gson gson = new Gson();
        ArrayList<GradingAssessmentTechnical> theList = new ArrayList<>();
        for (int currentIndex = 0; currentIndex < jsonArray.length(); currentIndex++) {
            GradingAssessmentTechnical current = null;
            try {
                current = gson.fromJson(jsonArray.getJSONObject(currentIndex).toString(), GradingAssessmentTechnical.class);
            }catch(JSONException e){
                throw new RuntimeException(e);
            }
            theList.add(current);
            Log.d("Junjie test list", current.toString());
        }
        return theList;
    }

}
