package info.hccis.grading.ui.gradinglist;



import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.hccis.grading.adapter.CustomerAdapterGrading;
import info.hccis.grading.databinding.FragmentGradingBinding;
import info.hccis.grading.entity.GradingAssessmentTechnical;
import info.hccis.grading.ui.grading.GradingViewModel;
import info.hccis.grading.R;
import info.hccis.grading.adapter.CustomerAdapterGrading;
import info.hccis.grading.databinding.FragmentGradingTrackListBinding;
import info.hccis.grading.ui.grading.GradingViewModel;
import info.hccis.grading.entity.GradingAssessmentTechnical;

public class GradingListFragment extends Fragment implements CustomerAdapterGrading.GradingAssessmentChosenListener{

    private GradingViewModel gradingViewModel;
    private GradingListViewModel gradingListViewModel;

    public static GradingListFragment newInstance() {
        return new GradingListFragment();
    }

    private List<GradingAssessmentTechnical> gradingArrayList;

    private @NonNull FragmentGradingTrackListBinding binding;
//    private FragmentGradingTrackListBinding binding;

    private static RecyclerView recyclerView;



    /**
     * This method will tell the recyclerview that the data has changed.  This will trigger the
     * data to be reloaed on the ui.
     * @param message
     * @since 20240126
     * @author BJM
     */
    public static void notifyDataChanged(String message) {
        Log.d("bjm", "Data changed:  " + message);
        //Send a notification that the data has changed.
        try {
            recyclerView.getAdapter().notifyDataSetChanged();
        } catch (Exception e) {
            Log.d("bjm api", "Exception when trying to notify that the data set as changed");
        }
    }

//    /**
//     * Provide notification tha the data has changed.  This method will notify the adapter that the
//     * rows have changed so it will know to refresh.  It will also send a notification to the user which
//     * will allow them to go directly back to the list from another activity of the app.
//     *
//     * @param message          Message to display
//     * @param activity         - originating activity
//     * @param destinationClass - class associated with the intent associated with the notification.
//     */
//    public static void notifyDataChanged(String message, Activity activity, Class destinationClass) {
//        Log.d("bjm", "Data changed:  " + message);
//        try {
//            notifyDataChanged(message);
//            NotificationApplication.setContext(context);
//            NotificationUtil.sendNotification("PHall Data Update", message, activity, MainActivity.class);
//        } catch (Exception e) {
//            Log.d("bjm notification", "Exception occured when notifying. " + e.getMessage());
//        }
//    }
//



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentGradingTrackListBinding .inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final GradingListFragment squashSkillsListFragment = GradingListFragment.this;

        //************************************************************************************
        // Set the view model.  This can be used to share data between fragments.
        // Corresponding to the add fragment, the view model is accessed to obtain a reference
        // to the list of entity objects.
        //************************************************************************************

        gradingViewModel = new ViewModelProvider(this.getActivity()).get(GradingViewModel.class);
        gradingListViewModel = new ViewModelProvider(this.getActivity()).get(GradingListViewModel.class);
        GradingAssessmentTechnical gat = gradingViewModel.getGat();
        gradingArrayList = gradingListViewModel.getGradingArrayList();


        if(gradingArrayList != null) Log.d("BJM list loaded","List loaded from view model size:"+gradingArrayList.size());

        //************************************************************************************
        // Set the context to be used when sending notifications
        //************************************************************************************

        //context = getView().getContext();

        //************************************************************************************
        // Setup the recycler view for displaying the items in the ticket order list.
        //************************************************************************************

        recyclerView = binding.recyclerView;

        setAdapter();

    }

    /**
     * Set the adapter for the recyclerview
     *
     * @author BJM
     * @since 20220129
     */
    private void setAdapter() {
        CustomerAdapterGrading adapter = new CustomerAdapterGrading(gradingArrayList, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onGradingAssessmentClick(GradingAssessmentTechnical gat) {
        Log.d("BJM row clicked","Clicked a row for student name = "+gat.getStudentName());
        Log.d("BJM row clicked","Clicked a row for numeric grade = "+gat.getNumericGrade());
        Log.d("BJM row clicked","Clicked a row for letter grade = "+gat.getLetterGrade());

        gradingViewModel.setGat(gat);
        NavHostFragment.findNavController(GradingListFragment.this)
                .navigate(R.id.nav_grading);


    }
}