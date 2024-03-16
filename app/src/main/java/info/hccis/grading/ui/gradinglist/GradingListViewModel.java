package info.hccis.grading.ui.gradinglist;



import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import info.hccis.grading.entity.GradingAssessmentTechnical;

public class GradingListViewModel extends ViewModel {

    //********************************************************************************************
    // Adding the list to the view model class.
    //********************************************************************************************

    private List<GradingAssessmentTechnical> gradingArrayList = new ArrayList();

    public List<GradingAssessmentTechnical> getGradingArrayList() {
        return gradingArrayList;
    }

    public void setGradingArrayList(List<GradingAssessmentTechnical> gradingArrayList) {
        this.gradingArrayList.clear();
        this.gradingArrayList.addAll(gradingArrayList);
    }
}