package info.hccis.grading.bo

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import info.hccis.grading.entity.GradingAssessmentTechnical

object GradingAssessmentBO {
    /**
     * calculate and set the technical score in the passed GradingAssessmentTechnical object
     *
     * @param gat GradingAssessmentTechnical object
     * @return technical grading
     * @author Junjie
     * @since 20231026
     */
    @JvmStatic
    fun calculateLetterGrade(gat: GradingAssessmentTechnical): String {
        val numericGrade = gat.getNumericGrade()
        val letter = when {
            numericGrade >= 90 -> "A"
            numericGrade >= 80 -> "B"
            numericGrade >= 70 -> "C"
            numericGrade >= 60 -> "D"
            else -> "F"
        }
        gat.letterGrade = letter
        return letter
    }


    /**
     * This method will return an ArrayList of assessments for testing purposes until
     * data can be retrieved from the API.
     *
     * @return list of GradingAssessmentTechnical objects
     * @throws JSONException
     * @author Junjie
     * @since 20240118
     */


    val testList: ArrayList<GradingAssessmentTechnical?>
        get(){
        val jsonForAssessments = "[{\"id\":1,\"assessmentDate\":\"2022-08-22\",\"studentName\":\"Junjie\",\"instructorName\":\"BJ\",\"courseName\":\"CIS1122\" }]"
            var jsonArray: JSONArray? = null
            jsonArray = try {
                JSONArray(jsonForAssessments)
            } catch (e: JSONException) {
                throw RuntimeException(e)
            }

            println("Here are the rows")
            val gson = Gson()
            val theList = ArrayList<GradingAssessmentTechnical?>()
            if (jsonArray != null) {
                for (currentIndex in 0 until jsonArray.length()) {
                    var current: GradingAssessmentTechnical? = null
                    if (jsonArray != null) {
                        current = try {
                            gson.fromJson(
                                jsonArray.getJSONObject(currentIndex).toString(),
                                GradingAssessmentTechnical::class.java
                            )
                        } catch (e: JSONException) {
                            throw RuntimeException(e)
                        }
                    }
                    theList.add(current)
                    Log.d("JJ test list", current.toString())
                }
            }
            return theList
        }

    /**
     * Share the message.
     * @param message
     * @param fragment
     * @param view
     * @since 20240212
     * @author JJ
     */
    @JvmStatic
    fun sendNotification(message: String?, fragment: Fragment, view: View?) {

        //https://www.digitalocean.com/community/tutorials/android-snackbar-example-tutorial
        val snackbar = Snackbar
                .make(view!!, "Sdk not implemented but using intent chooser", Snackbar.LENGTH_LONG)
        snackbar.show()

        // string variable for text
        // the intent "ACTION_SEND" will be used to send the information from one activity to another
        val intent = Intent(Intent.ACTION_SEND)
        // set the type of input you will be sending to the application
        intent.setType("text/plain")

        //startActivity(intent);
        intent.putExtra(Intent.EXTRA_TEXT, message)
        //                This creates the dialogue menu pop up of the apps that the message can be shared using
        fragment.startActivity(Intent.createChooser(intent, "Share"))
    }
}
