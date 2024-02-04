package info.hccis.grading.net;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import info.hccis.squash.entity.SkillsAssessmentSquashTechnical;

/**
 * Rest Api access implementation utilizing Android Volley Library
 *
 * @author David Deschene <ddeschene@hollandcollege.com>
 * @since Jan 22, 2024
 */
public class RestHandler {
    private String scheme = "http://";
    //private String ip = SECRET.IP;
    private String ip = "10.0.2.2"; //Anyone can access this for localhost access
    private String port = ":8082";
    //Note fake api doesn't have ending / and is port 80.
    private String path = "/api/SkillsAssessmentService/assessments/";
    private SkillsAssessmentSquashTechnical sast;
    private ArrayList<SkillsAssessmentSquashTechnical> sastList;

    public static final String TAG = "TAG_THIS";
    private RequestQueue queue;
    private RequestFuture<JSONObject> future;

    // Constructors
    public RestHandler(RequestQueue queue) {
        this.queue = queue;
    }

    public RestHandler(RequestQueue queue, SkillsAssessmentSquashTechnical sast) {
        this.sast = sast;
        this.queue = queue;
    }
    /* get all */

    /**
     * Contrived Example of a GET api call that populates UI with server response data
     *
     * @param callBack
     */
    public void getJsonArrayRequest(final ResponseCallBack callBack) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                buildUrl(),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            System.out.println("Here are the rows");
                            Gson gson = new Gson();
                            sastList = new ArrayList<>();
                            for (int currentIndex = 0; currentIndex < response.length(); currentIndex++) {
                                SkillsAssessmentSquashTechnical current = gson.fromJson(response.getJSONObject(currentIndex).toString(), SkillsAssessmentSquashTechnical.class);
                                System.out.println(current.toString());
                                sastList.add(current);
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("BJM JSONObject Response", "Response: " + response.toString());
                        callBack.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d("BJM api access Error", "Error: " + error.toString());
                Log.d("BJM api access errro","Setting sleep time to one minute");
                ApiWatcher.setSleepTime(60000);
                callBack.onError();
            }
        });

        Log.d("BJM test Request", "Request: " + jsonArrayRequest.toString());
        queue.add(jsonArrayRequest);
    }


    /**
     * An Example of a GET api call absent any functionality to provide a clear example of Android Volley's basic structure
     *
     * @param callBack
     */
    public void getRequest(final ResponseCallBack callBack) {
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET,
                buildUrl(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO: try something with getRequest() response
                        } catch (RuntimeException e) {
                            throw new RuntimeException(e);
                        }
                        Log.d("BJM getRequest Response", "Response: " + response.toString());
                        callBack.onSuccess();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle errors
                        Log.d("BJM test Error", "Error: " + error.toString());
                    }
                });
        Log.d("BJM test Request", "Request: " + stringRequest.toString());
        stringRequest.setTag(TAG);
        queue.add(stringRequest);
    }

    /**
     * Contrived Example of a GET api call that populates UI with server response data
     *
     * @param id
     * @param callBack
     */
    public void getJsonRequest(int id, final ResponseCallBack callBack) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                buildUrl().concat(String.valueOf(mockValidation(id))),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        sast = gson.fromJson(response.toString(), SkillsAssessmentSquashTechnical.class);
                        Log.d("BJM JSONObject Response", "Response: " + response.toString());
                        callBack.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d("BJM test Error", "Error: " + error.toString());
            }
        });
        Log.d("BJM test Request", "Request: " + jsonObjectRequest);
        jsonObjectRequest.setTag(TAG);
        queue.add(jsonObjectRequest);
    }

    /**
     * Contrived Example of a POST api call that creates a new DB record from a JSONObject and populates UI with server response data
     *
     * @param callBack
     */
    public void postJsonRequest(final ResponseCallBack callBack) {
        future = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                buildUrl(),
                mockJsonObject(),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        sast = gson.fromJson(response.toString(), SkillsAssessmentSquashTechnical.class);
                        Log.d("BJM JSONObject Response", "Response: " + response.toString());
                        callBack.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d("BJM test Error", "Error: " + error.toString());
            }
        });
        Log.d("BJM test Request", "Request: " + jsonObjectRequest);
        jsonObjectRequest.setTag(TAG);
        queue.add(jsonObjectRequest);
    }

    /**
     * Contrived Example of a POST api call that creates a new DB record from a JSONObject and populates UI with server response data
     *
     * @param jsonObjectIn
     * @param callBack
     */
    public void postJsonRequest(JSONObject jsonObjectIn, final ResponseCallBack callBack) {
        future = RequestFuture.newFuture();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                buildUrl(),
                jsonObjectIn,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        sast = gson.fromJson(response.toString(), SkillsAssessmentSquashTechnical.class);
                        Log.d("BJM postJsonRequest onResponse", "Response: " + response.toString());
                        callBack.onSuccess();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error
                Log.d("BJM postJsonRequest onErrorResponse", "Error: " + error.toString());
            }
        });
        Log.d("BJM postJsonRequest end", "Request: " + jsonObjectRequest);
        jsonObjectRequest.setTag(TAG);
        queue.add(jsonObjectRequest);
    }











    public void postJsonRequest(SkillsAssessmentSquashTechnical sast, final ResponseCallBack callBack) {

        Gson gson = new Gson();
        try {
            postJsonRequest(new JSONObject(gson.toJson(sast)),callBack);
        } catch (JSONException e) {
            Log.d("BJM posting sast","Error posting sast using rest.");
            throw new RuntimeException(e);
        }
    }


    /**
     * @param sast
     */
    public void setSast(SkillsAssessmentSquashTechnical sast) {
        this.sast = sast;
    }

    /**
     * @return
     */
    public SkillsAssessmentSquashTechnical getSast() {
        return this.sast;
    }


    public ArrayList<SkillsAssessmentSquashTechnical> getSastList() {
        return this.sastList;
    }

    public String buildUrl() {
        return this.scheme + this.ip + this.port + this.path;
    }

    public JSONObject mockJsonObject() {
        String json = "{\"id\":10,\"assessmentDate\":\"2024-01-06\",\"createdDateTime\":\"2024-01-06 01:38:48\",\"athleteName\":\"John Doe\",\"assessorName\":\"BJ MacLean\",\"forehandDrives\":11,\"backhandDrives\":5,\"forehandVolleyMax\":14,\"forehandVolleySum\":78,\"backhandVolleyMax\":6,\"backhandVolleySum\":59,\"technicalScore\":1085}";
        JSONObject request = null;
        try {
            request = new JSONObject(json);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return request;
    }

    /**
     * @param arb
     * @return
     */
    public int mockValidation(int arb) {
        if (arb > 9) {
            arb = 1;
        }
        return arb;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "API Call: " + this.scheme + this.ip + this.port + this.path + System.lineSeparator();
    }
}
