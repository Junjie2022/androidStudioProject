package info.hccis.grading.ui.grading.bo;

import info.hccis.grading.ui.grading.entity.GradingAssessmentTechnical;

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
}
