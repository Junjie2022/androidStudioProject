package info.hccis.grading.adapter;



import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import info.hccis.grading.entity.GradingAssessmentTechnical;
import info.hccis.grading.R;
import info.hccis.grading.entity.GradingAssessmentTechnical;

public class CustomerAdapterGrading extends RecyclerView.Adapter<CustomerAdapterGrading.GradingViewHolder> {

    private List<GradingAssessmentTechnical> gradingAssessmentArrayList;
    private GradingAssessmentChosenListener gradingAssessmentListener;

    public CustomerAdapterGrading(List<GradingAssessmentTechnical> gradingAssessmentArrayList, GradingAssessmentChosenListener listener) {
        this.gradingAssessmentArrayList = gradingAssessmentArrayList;
        this.gradingAssessmentListener = listener;
    }

    @NonNull
    @Override
    public GradingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_grading_track, parent, false);
        return new GradingViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull GradingViewHolder holder, int position) {

        String StudentName = "" + gradingAssessmentArrayList.get(position).getStudentName();
        String NumericGrade = ""+gradingAssessmentArrayList.get(position).getNumericGrade();

        //Can access the overall row using holder.itemView
        //https://stackoverflow.com/questions/39146035/change-recycler-view-item-background-color-by-code
        if(gradingAssessmentArrayList.get(position).getNumericGrade() > 100) {
//            holder.itemView.setBackgroundColor(R.color.colorAccent);
            holder.itemView.setBackgroundResource(R.color.colorAccent);
        }

        holder.textViewStudentName.setText(StudentName);
        holder.textViewNumericGrade.setText(NumericGrade);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bjtest", "row was clicked");
                gradingAssessmentListener.onGradingAssessmentClick(gradingAssessmentArrayList.get(holder.getAdapterPosition()));

            }
        });
    }


    @Override
    public int getItemCount() {
        return gradingAssessmentArrayList.size();
    }

    public class GradingViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewStudentName;
        private TextView textViewAssessmentDate;
        private TextView textViewNumericGrade;

        public GradingViewHolder(final View view) {
            super(view);
            textViewStudentName = view.findViewById(R.id.textViewStudentName);
            textViewAssessmentDate = view.findViewById(R.id.textViewAssessmentDate);
            textViewNumericGrade = view.findViewById(R.id.textViewNumericGrade);


        }
    }

    /**
     * https://community.codenewbie.org/theplebdev/adding-onclicklistener-to-recyclerview-in-android-1bdl
     * This interface will be implemented by the Fragment class to allow navigation to the detail
     * fragment when the row is clicked.
     *
     * @author bjmaclean
     * @since 20220628
     */
    public interface GradingAssessmentChosenListener {
        void onGradingAssessmentClick(GradingAssessmentTechnical sast);
    }
}
