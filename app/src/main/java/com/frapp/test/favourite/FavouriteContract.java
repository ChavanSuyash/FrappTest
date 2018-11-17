package com.frapp.test.favourite;

import com.frapp.test.data.DataModel;

import java.util.List;
import java.util.Map;

public class FavouriteContract {

    interface View{
        void showFavouriteList(Map<String, List<DataModel>> dataModelListMap);
    }

    interface UserActionsListener{
        void loadFavouriteList();
    }
}
