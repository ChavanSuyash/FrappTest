package com.frapp.test.favourite;

import android.content.Context;

import com.frapp.test.data.DataModel;
import com.frapp.test.data.repository.DataRepository;
import com.frapp.test.util.Constants;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FavouritePresenter implements FavouriteContract.UserActionsListener {

    private FavouriteContract.View mFavouriteView;
    private DataRepository dataRepository;

    public FavouritePresenter(Context context, FavouriteContract.View favouriteView){
        this.mFavouriteView = favouriteView;

        dataRepository = new DataRepository();
        dataRepository.open(context);
    }

    @Override
    public void loadFavouriteList() {
        Map<String, List<DataModel>> dataModelListMap = new LinkedHashMap<>();

        List<DataModel> favouriteInternships = dataRepository.getFavouriteInternships();
        List<DataModel> favouriteMissions = dataRepository.getFavouriteMissions();

        dataModelListMap.put(Constants.internships, favouriteInternships);
        dataModelListMap.put(Constants.missions, favouriteMissions);

        mFavouriteView.showFavouriteList(dataModelListMap);
    }
}
