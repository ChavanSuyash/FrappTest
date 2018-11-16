package com.frapp.test.data.webservice;

import com.android.volley.VolleyError;

public interface NetworkRequestContract {

    void onSuccess(String response, String tag);

    void onError(VolleyError error, String tag);

}
