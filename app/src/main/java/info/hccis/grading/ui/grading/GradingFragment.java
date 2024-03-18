package info.hccis.grading.ui.grading;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;


import info.hccis.grading.bo.GradingAssessmentBO;
import info.hccis.grading.databinding.FragmentGradingBinding;
import info.hccis.grading.net.ApiWatcher;
import info.hccis.grading.net.ResponseCallBack;
import info.hccis.grading.net.RestHandler;
import info.hccis.grading.service.BackgroundMusicService;
import info.hccis.grading.util.CisUtility;
import info.hccis.grading.entity.GradingAssessmentTechnical;
import info.hccis.grading.util.ContentProviderUtil;

public class GradingFragment extends Fragment {
    private FragmentGradingBinding binding;
    private GradingViewModel gradingViewModel;
    private RequestQueue requestQueue;

    public static GradingFragment newInstance() {
        return new GradingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        /* Create RequestQueue: and pass it context */
        requestQueue = Volley.newRequestQueue(getActivity());

        /* Create RestHandler: pass it RequestQueue and SkillsAssessmentSquashTechnical */
        RestHandler restHandler = new RestHandler(requestQueue, new GradingAssessmentTechnical());


        Log.d("JJ GradingFrag", "onCreateView is running");

        binding = FragmentGradingBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        gradingViewModel = new ViewModelProvider(this.getActivity()).get(GradingViewModel.class);
        GradingAssessmentTechnical gat = gradingViewModel.getGat();
        setTestValues(gat);

        binding.buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("JJ GradingFragment", "GradingFragment - share button clicked");

                if(ApiWatcher.getConnectedToNetwork() == false){
                    Toast.makeText(getActivity().getApplicationContext(), "Connectivity issues, try again later.", Toast.LENGTH_SHORT).show();
                }else{
                    //share this gat via social media
                    GradingAssessmentBO.sendNotification(gat.toString(), getParentFragment(), v);
                }

            }

        });
        binding.buttonWear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GradingAssessmentTechnical gat = gradingViewModel.getGat();

                gat.setStudentName(binding.editTextStudentName.getText().toString());
                gat.setAcademicYear(Integer.valueOf(binding.editTextAcademicYear.getText().toString()));
                gat.setCourseName(binding.editTextCourseName.getText().toString());
                gat.setInstructorName(binding.editTextInstructorName.getText().toString());
                gat.setCourseRoom(binding.editTextCourseRoom.getText().toString());
                gat.setNumericGrade(Double.valueOf(binding.editTextNumericGrade.getText().toString()));

                String letterGrade = GradingAssessmentBO.calculateLetterGrade(gat);
                sendScoreMessageToWearable(letterGrade);

                // Update the textViewGradingSummary with the letter grade
                //binding.textViewGradingSummary.setText("LetterGrade is: "+letterGrade);

            }
        });

        binding.buttonSubmitGrading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BJM onClick", "athlete name entered = " + binding.editTextStudentName.getText().toString());

                GradingAssessmentTechnical gat = gradingViewModel.getGat();

        gat.setAssessmentDate(binding.editTextStudentName.getText().toString());

        gat.setAcademicYear(Integer.valueOf(binding.editTextAcademicYear.getText().toString()));
        gat.getInstructorName();
        gat.getCourseRoom();
        gat.getCourseName();
        gat.getNumericGrade();


                restHandler.postJsonRequest(gat, new ResponseCallBack() {
                    @Override
                    public void onSuccess(Object object) {
                        Log.d("JJ GradingFragment", "GradingFragment - postJsonRequest - onSuccess triggered");
                        GradingAssessmentTechnical gat = (GradingAssessmentTechnical) object;
                        Toast.makeText(getActivity().getApplicationContext(), "Successfully saved", Toast.LENGTH_SHORT).show();
                        ContentProviderUtil.createEvent(getActivity(), gat.toString(), "Grading Assessed");
//                        ContentProviderUtil.createCalendarEventIntent(getActivity(), sast, "Skills Assessed");
                    }
                    @Override
                    public void onError() {
                        Log.d("JJ GradingFragment", "GradingFragment - postJsonRequest - onError triggered");
                        Toast.makeText(getActivity().getApplicationContext(), "Error saving", Toast.LENGTH_SHORT).show();
                    }
                });

        CisUtility.writeToFile(getActivity(), "Assessments.txt", gat.toString() + System.lineSeparator());
            String allAssessments = CisUtility.readFromFile(getActivity(), "Assessments.txt");
                Log.d("JJ GradingFragment", "GradingFragment - postJsonRequest - onSuccess triggered");
                Log.d("JJ GradingFragment", "GradingFragment - postJsonRequest - Finished with the add/update");
        }
    });
    // Add Listener in calendar
        binding.calendarViewAssessmentDate.setOnDateChangeListener(
                new CalendarView
                .OnDateChangeListener() {
        @Override
        // https://www.geeksforgeeks.org/android-creating-a-calendar-view-app/
        // In this Listener have one method
        // and in this method we will
        // get the value of DAYS, MONTH, YEARS
        public void onSelectedDayChange(
                @NonNull CalendarView view,
        int year,
        int month,
        int dayOfMonth) {

            // Store the value of date with
            // format in String type Variable
            // Add 1 in month because month
            // index is start with 0
            String Date
                    = year + "-"
                    + (month + 1) + "-" + dayOfMonth;

            // set this date in TextView for Display
            binding.editTextAssessmentDate.setText(Date);
        }
    });


        return view;
}

    /**
     * Setting som test values to save typing while testing app.
     *
     * @param gat The assessment test object
     * @author BJM
     * @since 20240119
     */
    public void setTestValues(GradingAssessmentTechnical gat) {

        binding.editTextAssessmentDate.setText(gat.getAssessmentDate());
        binding.editTextStudentName.setText(gat.getStudentName());
        binding.editTextAcademicYear.setText(""+ gat.getAcademicYear());
        binding.editTextCourseRoom.setText(gat.getCourseRoom());
        binding.editTextInstructorName.setText(gat.getInstructorName());
        binding.editTextCourseName.setText(gat.getCourseName());
        binding.editTextNumericGrade.setText("" + gat.getNumericGrade());

    }

    /**
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        //check to see if connected.  If not hide the button.
        Log.d("JJ GradingFragment", "GradingFragment - onResume - enabling button TO:" + ApiWatcher.getConnectedToNetwork());

        if (ApiWatcher.getConnectedToNetwork()) {
            Log.d("JJ GradingFragment","onResume-showing buttons");
            binding.buttonShare.setVisibility(View.VISIBLE);
            binding.buttonSubmitGrading.setVisibility(View.VISIBLE);
        } else {
            Log.d("JJ GradingFragment","onResume-hiding buttons");
            binding.buttonShare.setVisibility(View.INVISIBLE);
            binding.buttonSubmitGrading.setVisibility(View.INVISIBLE);
        }

        // Start the background music service
        getActivity().startService(new Intent(getActivity(), BackgroundMusicService.class));
    }

    @Override
    public void onPause() {
        super.onPause();
        // Stops the background music service when the activity is destroyed
        getActivity().stopService(new Intent(getActivity(), BackgroundMusicService.class));
    }

    private void sendScoreMessageToWearable(String letterGrade) {
        String message = String.valueOf(letterGrade);
        Log.d("SendMessage", "LetterGrade: " + message);

        Activity activity = getActivity();
        Task<List<Node>> getConnectedNodesTask = Wearable.getNodeClient(activity).getConnectedNodes();
        getConnectedNodesTask.addOnSuccessListener(nodes -> {
//            for (Node node : nodes) {
//                Task<String> sendMessageTask = Wearable.getMessageClient(activity).sendMessage(
//                        node.getId(),                   // nodeId of the wearable device
//                        "/LetterGrade",                  // path for the wearable app to identify the message
//                        message.getBytes());           // data to be sent
//                sendMessageTask.addOnSuccessListener(result -> {
//                    Log.d("Message", "Message sent: " + result);
//                }).addOnFailureListener(e -> {
//                    Log.e("Message", "Failed to send message", e);
//                });
//            }
        }).addOnFailureListener(e -> {
            Log.e("Message", "Failed to get connected nodes", e);
        });
    }

}