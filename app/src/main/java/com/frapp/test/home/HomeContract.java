package com.frapp.test.home;

import com.frapp.test.data.DataModel;

import java.util.List;

public class HomeContract {

    interface View{
        void setProgressIndicator(boolean active);

        void showInternshipsAndMissions(List<DataModel> dataModelList);

        void removeItem(int position);
    }

    interface UserActionsListener {

        void loadInternshipsAndMissions();

        void addToFavourite(int position, DataModel dataModel);

    }
}
