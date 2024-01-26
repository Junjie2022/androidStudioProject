package info.hccis.grading.ui.grading;

import androidx.lifecycle.ViewModel;

import info.hccis.grading.ui.grading.entity.GradingAssessmentTechnical;

public class GradingViewModel extends ViewModel {


    private GradingAssessmentTechnical sast = new GradingAssessmentTechnical();

    public GradingAssessmentTechnical getSast() {
        return sast;
    }

    public void setSast(GradingAssessmentTechnical sast) {
        this.sast = sast;
    }
}