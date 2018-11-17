package com.frapp.test.home;

import com.frapp.test.data.DataModel;

import java.util.List;

public class HomeContract {

    interface View{
        void setProgressIndicator(boolean active);

        void showInternshipsAndMissions(List<DataModel> dataModelList);
    }

    interface UserActionsListener {

        void loadInternshipsAndMissions();

    }
}
