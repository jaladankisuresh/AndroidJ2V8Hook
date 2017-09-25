package com.imnotout.androidj2v8hook.NetworkIO;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;


public class WebServiceClient
        implements IWebApi, Response.Listener<String>, Response.ErrorListener{

    // NOTE: While you the change the Service base url, make sure you also change the hostname
    // in the HttpsConnectionManager.java verify() method

    private final String LOG_APP_TAG = "androidj2v8hook";
    private Listener webApiListener;
    private Context mContext;
    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    private static String apiBaseUrl = "http://10.0.2.2:3000/";
    private static final int VOLLEY_ERR_RESPONSE_CODE = -11;

    public WebServiceClient(Listener webApiListener) {
        this.webApiListener = webApiListener;
        this.mContext = webApiListener.getListenerContext();
    }
    @Override
    public void makeGet(String uri) {
        this.makeRequest(RequestType.GET, (uri), null);
    }

    @Override
    public void makePost(String uri, String dataJSON){
        this.makeRequest(RequestType.POST, (uri), dataJSON);
    }

    private void makeRequest(RequestType requestType, String url, String dataJSON){

        requestQueue = VolleyService.getInstance(mContext).getHttpRequestQueue();
        final int timeout = 10000, maxRetries = 2, backOffMult = 2;

        try {
            Log.d("WebServiceClient:", "Url: " + apiBaseUrl + url + " requested");
            stringRequest = new StringRequest(requestType.getValue(), apiBaseUrl + url, this,this);
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, maxRetries, backOffMult));

            requestQueue.add(stringRequest);
        }
        catch(Exception err){
            Log.e("Gson parse err", err.toString(), err);
        }
    }

    @Override
    public void onResponse(String response) {
        webApiListener.onSuccessResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError e) {
        String errMessage = (e instanceof TimeoutError) ? e.toString() : e.getMessage();
       // Log.e(LOG_APP_TAG, errMessage);
        Log.d(LOG_APP_TAG, Log.getStackTraceString(e));

        webApiListener.onErrorResponse(errMessage);
    }

    // We are trying map enum RequestType to volley request types where GET - 0 and POST - 1
    public enum RequestType {
        GET(Request.Method.GET),
        POST(Request.Method.POST);

        private final int volleyType;
        RequestType(int volleyType){
            this.volleyType = volleyType;
        }

        public int getValue() {
            return volleyType;
        }
    }

}
