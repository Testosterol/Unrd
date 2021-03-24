package com.testosterolapp.unrd.serverCommunication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.testosterolapp.unrd.util.DbUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class GetServerData {

    public static final String TAG = GetServerData.class.getSimpleName();


    /**
     * Method that uses Volley and StringRequest to dispatch the Config into the server.
     */
    public void getServerData(Context context) {
        StringRequest strReq = new StringRequest(StringRequest.Method.GET, Url.URL_DATA, response -> {

            // server response
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(response);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            DbUtil.Companion.launchCoroutineForInsertHeadlineIntoDatabase(jsonObject, context);

        }, error -> Log.e(TAG, "Failed to retrieve server data" + error)) {
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                Log.i(TAG, "Status code from the server: " + response.statusCode);
                return super.parseNetworkResponse(response);

            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        AppController.getInstance(context).addToRequestQueue(strReq);
    }

}
