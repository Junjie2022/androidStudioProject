package info.hccis.grading.ui.grading;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import info.hccis.grading.databinding.FragmentGradingBinding;
import info.hccis.grading.util.CisUtility;
import info.hccis.grading.bo.GradingAssessmentBO;
import info.hccis.grading.entity.GradingAssessmentTechnical;

public class GradingFragment extends Fragment {
    private FragmentGradingBinding binding;
    private GradingViewModel gradingViewModel;

    public static GradingFragment newInstance() {
        return new GradingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Log.d("BJM GradingFrag", "onCreateView is running");
        binding = FragmentGradingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        gradingViewModel = new ViewModelProvider(this.getActivity()).get(GradingViewModel.class);
        GradingAssessmentTechnical sast = gradingViewModel.getSast();
        setTestValues(sast);

        binding.buttonSubmitGrading.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("JJ TEST", "Student name entered = " + binding.editTextStudentName.getText().toString());

                GradingAssessmentTechnical sast = gradingViewModel.getSast();

                sast.setStudentName(binding.editTextStudentName.getText().toString());
                sast.setAcademicYear(Integer.valueOf(binding.editTextAcademicYear.getText().toString()));
                sast.setCourseName(binding.editTextCourseName.getText().toString());
                sast.setInstructorName(binding.editTextInstructorName.getText().toString());
                sast.setCourseRoom(binding.editTextCourseRoom.getText().toString());
                sast.setNumericGrade(Double.valueOf(binding.editTextNumericGrade.getText().toString()));

                String letterGrade = GradingAssessmentBO.calculateLetterGrade(sast);

                // Update the textViewGradingSummary with the letter grade
                binding.textViewGradingSummary.setText("LetterGrade is: "+letterGrade);

                CisUtility.writeToFile(getActivity(), "Assessments.txt", sast.toString() + System.lineSeparator());
                String allAssessments = CisUtility.readFromFile(getActivity(), "Assessments.txt");
                Log.d("BJM read from file", allAssessments);
            }
        });

        return view;
    }

    public void setTestValues(GradingAssessmentTechnical sast) {
        binding.editTextStudentName.setText(sast.getStudentName());
        binding.editTextAcademicYear.setText(String.valueOf(sast.getAcademicYear()));
        binding.editTextInstructorName.setText(sast.getInstructorName());
        binding.editTextCourseRoom.setText(sast.getCourseRoom());
        binding.editTextNumericGrade.setText(String.valueOf(sast.getNumericGrade()));
    }
}
