package info.hccis.grading.ui.grading;

import androidx.lifecycle.ViewModel;

import info.hccis.grading.entity.GradingAssessmentTechnical;

public class GradingViewModel extends ViewModel {


    private GradingAssessmentTechnical gat = new GradingAssessmentTechnical();

    public GradingAssessmentTechnical getGat() {
        return gat;
    }

    public void setGat(GradingAssessmentTechnical gat) {
        this.gat = gat;
    }
}