package com.frapp.test.data.webservice;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class NetworkRequest {

    private Context context;

    public NetworkRequest(Context context){
        this.context = context;
    }

    public void load(String url, final NetworkRequestContract networkRequestContract){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                networkRequestContract.onSuccess(s);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                networkRequestContract.onError(volleyError);
            }
        });
        VolleySingleton.get(context).addToRequestQueue(request);
    }

}
