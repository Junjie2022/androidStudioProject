package info.hccis.grading.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import info.hccis.grading.entity.GradingAssessmentTechnical;

public class FirebaseFirestoreRepository {
    public static final String TAG = "JJ FirebaseFirestoreRepository";
    private FirebaseFirestore db;
    private static FirebaseFirestoreRepository fbfsr;

    public static FirebaseFirestoreRepository getInstance() {
        if (fbfsr == null) {
            fbfsr = new FirebaseFirestoreRepository();
        }
        return fbfsr;
    }

    public FirebaseFirestoreRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void add(ArrayList<GradingAssessmentTechnical> skillsAssessmentSquashTechnicalList) {
        HashMap<String, GradingAssessmentTechnical> map = new HashMap();
        for (GradingAssessmentTechnical current : skillsAssessmentSquashTechnicalList) {
            map.put("" + current.getId(), current);
        }

        // Add a new document with a generated ID
        db.collection("assessments")
                .add(map)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "DocumentSnapshot successfully written!");

                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }

}
