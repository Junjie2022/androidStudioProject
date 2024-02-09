package info.hccis.grading;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import info.hccis.grading.databinding.ActivityMainBinding;
import info.hccis.grading.entity.GradingAssessmentTechnical;
import info.hccis.grading.ui.grading.GradingViewModel;
import info.hccis.grading.ui.gradinglist.GradingListViewModel;
import androidx.core.splashscreen.SplashScreen;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import info.hccis.grading.databinding.ActivityMainBinding;
import info.hccis.grading.entity.GradingAssessmentTechnical;
import info.hccis.grading.net.ApiWatcher;
import info.hccis.grading.net.ResponseCallBack;
import info.hccis.grading.net.RestHandler;
import info.hccis.grading.ui.grading.GradingViewModel;
import info.hccis.grading.ui.gradinglist.GradingListViewModel;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private GradingViewModel gradingViewModel;
    private GradingListViewModel gradingListViewModel;
    private RequestQueue requestQueue;
    private boolean keep = true;
    private final int DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        //Keep returning false to Should Keep On Screen until ready to begin.
        splashScreen.setKeepOnScreenCondition(new SplashScreen.KeepOnScreenCondition() {
            @Override
            public boolean shouldKeepOnScreen() {
                return keep;
            }
        });

        Handler handler = new Handler();

        handler.postDelayed(runner, DELAY);

        Log.d("BJM Lifecycle", "onCreate of activity running");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_gradingadd, R.id.nav_gradinglist)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        final Activity activity = this;

        gradingListViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(GradingListViewModel.class);
        gradingListViewModel.setGradingArrayList(new ArrayList<GradingAssessmentTechnical>());

        //This is needed to set new assessment object when clicking fab to add skills assessment.
        gradingViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(GradingViewModel.class);

        ApiWatcher apiWatcher = new ApiWatcher((FragmentActivity) activity);
        apiWatcher.start();

        /* Create RequestQueue: and pass it context */
        requestQueue = Volley.newRequestQueue(activity);

        /* Create RestHandler: pass it RequestQueue and SkillsAssessmentSquashTechnical */
        RestHandler restHandler = new RestHandler(requestQueue, new GradingAssessmentTechnical());

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("BJM FAB clicked-", "Send user to add squash skills");
                gradingViewModel.setGat(new GradingAssessmentTechnical());
                navController.navigate(R.id.nav_grading);



                /* ___________________________________________________________________
                The followingcode will allow a post.
                ___________________________________________________________________ */

                /* Place functionality in callback interface to prevent execution until server response is available */
//                restHandler.postJsonRequest(new ResponseCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        SkillsAssessmentSquashTechnical response = restHandler.getSast();
//                        sast.setAssessmentDate(response.getAssessmentDate());
//                        sast.setAthleteName(response.getAthleteName());
//                        sast.setAssessorName(response.getAssessorName());
//                        sast.setForehandDrives(response.getForehandDrives());
//                        sast.setBackhandDrives(response.getBackhandDrives());
//                        sast.setBackhandVolleyMax(response.getBackhandVolleyMax());
//                        sast.setBackhandVolleySum(response.getBackhandVolleySum());
//                        sast.setForehandVolleySum(response.getForehandVolleySum());
//                        sast.setForehandVolleyMax(response.getForehandVolleyMax());
//                        Log.d("BJM onCreate","onCreate of MainActivity end of onSuccess");
//                        navController.navigate(R.id.nav_squashskills);
//                    }
//                });
                Log.d("BJM FAB clicked", "finished fab onclick method");
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Log.d("MainActivity BJM", "Option selected About");
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_about);
            //**************************************************************************************
            //todo 20230210 Review that the navController as wetup above will send the user to a
            //non top level destination.  Note that this syntax does not work to send the user to
            //a top level destination and causes issues if used for top level / non top level navigation.
            //***************************************************************************************
            return true;
        }
//        } else if (id == R.id.action_about) {
//            Log.d("MainActivity BJM", "Option selected About");
//            Intent intent = new Intent(this, AboutActivity.class);
//            startActivity(intent);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("JJW Lifecycle", "onStart of activity running");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("JJW Lifecycle", "onDestroy of activity running");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("JJW Lifecycle", "onStop of activity running");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("JJW Lifecycle", "onResume of activity running");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("JJW Lifecycle", "onPause of activity running");
    }
    private final Runnable runner = new Runnable() {
        @Override
        public void run() {
            keep = false;
        }
    };
}