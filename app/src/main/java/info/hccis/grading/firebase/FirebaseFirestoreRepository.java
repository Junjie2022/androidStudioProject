package info.hccis.squash.firebase;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

import info.hccis.squash.entity.SkillsAssessmentSquashTechnical;

public class FirebaseFirestoreRepository {
    public static final String TAG = "BJM FirebaseFirestoreRepository";
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

    public void add(ArrayList<SkillsAssessmentSquashTechnical> skillsAssessmentSquashTechnicalList) {
        HashMap<String, SkillsAssessmentSquashTechnical> map = new HashMap();
        for (SkillsAssessmentSquashTechnical current : skillsAssessmentSquashTechnicalList) {
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
