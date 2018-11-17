package com.frapp.test.util;

public class Database {

    public static final String DATABASE_NAME = "favourite_list_database";

    public static final String FAVOURITE_LIST_TABLE = "favourite_list_table";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    public static final String LOGO = "logo";
    public static final String VIEWS = "views";
    public static final String FEATURED = "featured";
    public static final String TYPE = "type";

    public static final String FAVOURITE_LIST_TABLE_CREATE_SCRIPT = "create table if not exists "
            + FAVOURITE_LIST_TABLE + " ( " + ID + " integer primary key autoincrement, "
            + TITLE + " Text, " + DESCRIPTION + " Text, "
            + LOGO + " Text, " + VIEWS + " Text, "
            + FEATURED + " Text," + TYPE + " Text );";


}
