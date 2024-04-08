package info.hccis.grading.net;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import info.hccis.grading.bo.GradingWidgetUtil;

import java.util.ArrayList;
import java.util.List;
import info.hccis.grading.R;
import info.hccis.grading.broadcast.receiver.BroadcastSender;
import info.hccis.grading.entity.GradingAssessmentContent;
import info.hccis.grading.entity.GradingAssessmentTechnical;
import info.hccis.grading.firebase.FirebaseFirestoreRepository;
import info.hccis.grading.ui.gradinglist.GradingListFragment;
import info.hccis.grading.ui.gradinglist.GradingListViewModel;

/**
 * ApiWatcher class will be used as a background thread which will monitor the api. It will notify
 * the ui activity if the number of rows changes.
 *
 * @author JJ modified by Mariana Alkabalan 20210402 remodified by BJM 20220202
 * @since 20240329
 */

public class ApiWatcher extends Thread {

    private int lengthLastCall = -1;  //Number of rows returned
    public static final int CONNECTED_SLEEP_TIME = 10000;
    public static final int NOT_CONNECTED_SLEEP_TIME = 30000;
    private static int sleepTime = CONNECTED_SLEEP_TIME;

    public static void setSleepTime(int milliSeconds) {
        sleepTime = milliSeconds;
    }

    private static boolean currentlyConnected = true;
    private FragmentActivity activity = null;
    private RequestQueue requestQueue;

    public ApiWatcher(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public void run() {
        super.run();

        /* Create RequestQueue: and pass it context */
        requestQueue = Volley.newRequestQueue(activity);

        /* Create RestHandler: pass it RequestQueue and SkillsAssessmentSquashTechnical */
        RestHandler restHandler = new RestHandler(requestQueue, new GradingAssessmentTechnical());

        try {
            do {

                //************************************************************************
                // It will access the api and if if notes that the number of orders has changed, will
                // notify the view order fragment that the data is changed.
                //************************************************************************

                //Create a list of ticket orders.
                // Call<List<TicketOrder>> call = TicketOrderRepository.getInstance().getTicketOrderService().getTicketOrders();

                GradingListViewModel gradingListViewModel = new ViewModelProvider(activity).get(GradingListViewModel.class);

                restHandler.getJsonArrayRequest(new ResponseCallBack() {
                    @Override
                    public void onSuccess(Object object) {
                        Log.d("jj api access", "onSuccess triggered");
                        /* Both the RestHandler's server response and MainActivity are available for code execution */
                        ArrayList<GradingAssessmentTechnical> responseList = (ArrayList<GradingAssessmentTechnical>) object;
                        Log.d("jj got list?", "List of objects returned=" + responseList.size());

                        List<GradingAssessmentTechnical> newList = responseList;
                        List<GradingAssessmentTechnical> oldList = gradingListViewModel.getGradingArrayList();

                        //******************************************************************
                        //Note here.  The recycler view is using the ArrayList from this
                        //ticketOrderViewModel class.  We will take the ticket orders obtained
                        //from the api and add refresh the list .  Will then notify the
                        //recyclerview that things have changed.
                        //******************************************************************

                        if (!newList.equals(oldList)) {
                          ////Set the high grade in shared preferences
                            GradingWidgetUtil.setHighGrade(activity.getApplicationContext(),newList);

                            FirebaseFirestoreRepository.getInstance().add(new ArrayList<>(newList));

                            Log.d("jj data changed","old size:"+oldList.size()+" new size"+newList.size());
                            BroadcastSender.sendCustomBroadcast(activity.getApplicationContext());

                            //Note:  The following line will clear/addAll rather than change the list object.
                            gradingListViewModel.setGradingArrayList(newList);

                            //******************************************************************
                            //Save latest orders in the database.
                            //******************************************************************
                            GradingAssessmentContent.reloadGradingAssessmentsInRoom(newList);

                            //**********************************************************************
                            // This method will allow a call to the runOnUiThread which will be allowed
                            // to interact with the ui components of the app.
                            //**********************************************************************


                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("JJ api", "trying to notify adapter that things have changed");
                                    GradingListFragment.notifyDataChanged("Found more rows");
                                }
                            });
                        } else {
                            Log.d("JJ checked api", "List from api is not changed.");
                        }

                    }

                    @Override
                    public void onError() {
                        Log.d("BJM ApiWatcher", "ApiWatcher run onError calling api triggered");

                        //set connected to false
                        setConnectedToNetwork(false);

                        //Was not able to obtain data from the api.  In the future will
                        //load from Room database if desired.
                        //******************************************************************************************
                        // Using the default shared preferences.  Using the application context - may want to access the
                        // shared prefs from other activities.
                        //******************************************************************************************

                        loadFromRoomIfPreferred(gradingListViewModel);
                        lengthLastCall = -1; //Indicate couldn't load from api will trigger reload next time
                    }
                });


//FUTURE
//                            //******************************************************************************************
//                            // Using the default shared preferences.  Using the application context - may want to access the
//                            // shared prefs from other activities.
//                            //******************************************************************************************
//
//                            loadFromRoomIfPreferred(ticketOrderViewModel);
//                            lengthLastCall = -1; //Indicate couldn't load from api will trigger reload next time
//

//FUTURE
//                                //******************************************************************
//                                //Save latest orders in the database.
//                                //******************************************************************
//                                TicketOrderContent.reloadTicketOrdersInRoom(ticketOrdersTemp);
//
//

                //***********************************************************************************
                // Sleep so not checking all the time
                //***********************************************************************************
                //sleepTime = 10000;
                Log.d("BJM Sleep", "Sleeping for " + (sleepTime / 1000) + " seconds");
                Thread.sleep(sleepTime); //Check api every 10 seconds

            } while (true);
        } catch (Exception e) {
            Log.d("jj api", "Thread interrupted.  Stopping in the thread.");
        }

    }


//FUTURE USE

    /**
     * Check the shared preferences and load from the db if the setting is set to do such a thing.
     *
     * @param gradingListViewModel
     * @author BJM
     * @since 20220211
     */
    public void loadFromRoomIfPreferred(GradingListViewModel gradingListViewModel) {
        Log.d("JJ room", "Check to see if should load from room");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());

        boolean loadFromRoom = sharedPref.getBoolean(activity.getString(R.string.preference_load_from_room), true);

        Log.d("JJ room", "Load from Room=" + loadFromRoom);
        if (loadFromRoom) {
            ArrayList oldList = new ArrayList();
            oldList.addAll(gradingListViewModel.getGradingArrayList());

            List<GradingAssessmentTechnical> listFromRoom = GradingAssessmentContent.getGradingAssessmentsFromRoom();
            Log.d("jj room", "Obtained ticket orders from the db: " + listFromRoom.size());
            if (!oldList.equals(listFromRoom)) {
                gradingListViewModel.setGradingArrayList(listFromRoom); //Will add all ticket orders
//            //**********************************************************************
//            // This method will allow a call to the runOnUiThread which will be allowed
//            // to interact with the ui components of the app.
//            //**********************************************************************
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("JJ api", "trying to notify adapter that things have changed");
                        GradingListFragment.notifyDataChanged("Found more rows");
                    }

                });

            }
        }


    }


    /**
     * Set flag indicating connectivity.
     *
     * @param connected
     * @return connectivity changed boolean
     * @author BJM
     * @since 20240213
     */
    public static boolean setConnectedToNetwork(boolean connected) {

        if (currentlyConnected == connected) {
            return false;
        } else {
            //connectivity changed.
            Log.d("BJM ApiWatcher","Connectivity to the network has changed to "+connected+".");
            currentlyConnected = connected;
            if (connected) {
                setSleepTime(CONNECTED_SLEEP_TIME);
            } else {
                setSleepTime(NOT_CONNECTED_SLEEP_TIME);
            }
            return true;
        }
    }

    public static boolean getConnectedToNetwork() {
        return currentlyConnected;
    }


}

