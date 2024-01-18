package info.hccis.grading;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import info.hccis.grading.databinding.ActivityMainBinding;
import info.hccis.grading.ui.grading.GradingViewModel;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private GradingViewModel gradingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Log.d("BJM Lifecycle", "onCreate of activity running");

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_grading)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.nav_grading);
            }
        });

        //squashSkillsViewModel = new ViewModelProvider(this).get(SquashSkillsViewModel.class);



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
}