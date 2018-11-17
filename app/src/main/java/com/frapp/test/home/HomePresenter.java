package com.frapp.test.home;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.frapp.test.data.DataModel;
import com.frapp.test.data.webservice.NetworkRequest;
import com.frapp.test.data.webservice.NetworkRequestContract;
import com.frapp.test.util.Constants;
import com.frapp.test.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.frapp.test.util.Utils.addTwoFeaturedListAndSort;

public class HomePresenter implements HomeContract.UserActionsListener, NetworkRequestContract {

    private final HomeContract.View mHomeView;
    private final NetworkRequest networkRequest;

    private int requestPendingCount = 4;

    private List<DataModel> nonFeaturedInternships, nonFeaturedMissions, featuredInternships, featuredMissions;

    public HomePresenter(Context context, HomeContract.View homeView){
        this.mHomeView = homeView;
        networkRequest = new NetworkRequest(context);
    }

    @Override
    public void loadInternshipsAndMissions() {
        mHomeView.setProgressIndicator(true);
        networkRequest.add("http://54.169.233.100:8080/featured_internships.json ", Constants.featuredInternships,this);
        networkRequest.add("http://54.169.233.100:8080/featured_missions.json", Constants.featuredMissions,this);
        networkRequest.add("http://54.169.233.100:8080/non_featured_internships.json",Constants.nonFeaturedInternships,this);
        networkRequest.add("http://54.169.233.100:8080/non_featured_missions.json", Constants.nonFeaturedMissions,this);
    }

    @Override
    public void onSuccess(String response, String tag) {
        switch (tag){

            case Constants.featuredInternships :
                featuredInternships = getDataModel(response,Constants.featuredInternships);

                requestPendingCount -= 1;
                if(requestPendingCount == 0){
                    //do the listing
                    mHomeView.setProgressIndicator(false);
                }

                break;

            case Constants.featuredMissions :
                featuredMissions = getDataModel(response, Constants.featuredMissions);

                requestPendingCount -= 1;
                if(requestPendingCount == 0){
                    //do the listing
                    mHomeView.setProgressIndicator(false);
                }

                break;

            case Constants.nonFeaturedInternships :
                nonFeaturedInternships = getDataModel(response, Constants.nonFeaturedInternships);

                requestPendingCount -= 1;
                if(requestPendingCount == 0){
                    //do the listing
                    mHomeView.setProgressIndicator(false);
                }

                break;
            case Constants.nonFeaturedMissions :
                nonFeaturedMissions = getDataModel(response, Constants.nonFeaturedMissions);

                requestPendingCount -= 1;
                if(requestPendingCount == 0){
                    //do the listing
                    mHomeView.setProgressIndicator(false);
                }

                break;

            default:
        }
    }

    @Override
    public void onError(VolleyError error, String tag) {

        switch (tag) {
            case Constants.featuredInternships :
                requestPendingCount -= 1;
                //Show error
                break;

            case Constants.featuredMissions :
                requestPendingCount -= 1;
                //Show error
                break;

            case Constants.nonFeaturedInternships :
                requestPendingCount -= 1;
                //Show error
                break;

            case Constants.nonFeaturedMissions :
                requestPendingCount -= 1;
                //Show error
                break;
        }

    }

    private List<DataModel> getDataModel(String response, String type){
        int position = 0;

        List<DataModel> dataModelList = new ArrayList<>();
        try {
            JSONArray responseJsonArray = new JSONArray(response);

            for (int i = 0; i < responseJsonArray.length(); i++){

                JSONObject datModelJson = responseJsonArray.getJSONObject(i);

                String title = (datModelJson.has("name")) ? datModelJson.getString("name") : datModelJson.getString("title");
                String description = datModelJson.getString("description");

                String logo = datModelJson.getString("logo");

                int views = datModelJson.getInt("views");

                int featured = 0;
                if(datModelJson.has("featured")){
                    featured = datModelJson.getInt("featured");
                    position = featured;
                }else position ++;


                dataModelList.add(new DataModel(title,logo,description,views,featured,position,type));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataModelList;
    }
}

