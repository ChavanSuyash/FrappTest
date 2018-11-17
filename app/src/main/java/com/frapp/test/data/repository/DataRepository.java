package com.frapp.test.data.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.frapp.test.data.DataModel;
import com.frapp.test.util.Constants;
import com.frapp.test.util.Database;

import java.util.ArrayList;
import java.util.List;

public class DataRepository {

    private static SQLiteDatabase sqLiteDatabase = null;

    public DataRepository(){}

    public void open(Context context) {
        sqLiteDatabase = context.openOrCreateDatabase(Database.DATABASE_NAME, Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL(Database.FAVOURITE_LIST_TABLE_CREATE_SCRIPT);
    }

    public void close() {
        if(sqLiteDatabase != null)
            sqLiteDatabase.close();
    }


    public Long insertFavouriteItem(DataModel dataModel){

        ContentValues values = new ContentValues();
        values.put(Database.TITLE,dataModel.getTitle());
        values.put(Database.LOGO, dataModel.getLogo());
        values.put(Database.DESCRIPTION,dataModel.getDescription());
        values.put(Database.VIEWS,dataModel.getViews());
        values.put(Database.FEATURED,dataModel.getFeatured());
        values.put(Database.TYPE ,dataModel.getType());

        Long res = sqLiteDatabase.insert(Database.FAVOURITE_LIST_TABLE, null, values);

        return res;
    }

    public List<DataModel> getFavouriteInternships(){

        String title, description, logo, type;
        int views, featured;

        List<DataModel> dataModelList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + Database.FAVOURITE_LIST_TABLE + " WHERE "
                + Database.TYPE + " = '" + Constants.featuredInternships + "' or " + Database.TYPE + " = '" + Constants.nonFeaturedInternships + "'", null);
        while (cursor.moveToNext()) {
            title = cursor.getString(cursor.getColumnIndex(Database.TITLE));
            description = cursor.getString(cursor.getColumnIndex(Database.DESCRIPTION));
            logo = cursor.getString(cursor.getColumnIndex(Database.LOGO));
            views = Integer.valueOf(cursor.getString(cursor.getColumnIndex(Database.VIEWS)));
            featured = Integer.valueOf(cursor.getString(cursor.getColumnIndex(Database.FEATURED)));
            type = cursor.getString(cursor.getColumnIndex(Database.TYPE));

            dataModelList.add(new DataModel(title,logo,description,views,featured,0,type));
        }

        return dataModelList;
    }

    public List<DataModel> getFavouriteMissions(){

        String title, description, logo, type;
        int views, featured;

        List<DataModel> dataModelList = new ArrayList<>();

        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + Database.FAVOURITE_LIST_TABLE + " WHERE "
                + Database.TYPE + " = '" + Constants.featuredMissions + "' or " + Database.TYPE + " = '" + Constants.nonFeaturedMissions + "'", null);
        while (cursor.moveToNext()) {
            title = cursor.getString(cursor.getColumnIndex(Database.TITLE));
            description = cursor.getString(cursor.getColumnIndex(Database.DESCRIPTION));
            logo = cursor.getString(cursor.getColumnIndex(Database.LOGO));
            views = Integer.valueOf(cursor.getString(cursor.getColumnIndex(Database.VIEWS)));
            featured = Integer.valueOf(cursor.getString(cursor.getColumnIndex(Database.FEATURED)));
            type = cursor.getString(cursor.getColumnIndex(Database.TYPE));

            dataModelList.add(new DataModel(title,logo,description,views,featured,0,type));
        }

        return dataModelList;
    }
}
