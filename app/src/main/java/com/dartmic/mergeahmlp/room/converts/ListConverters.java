package com.dartmic.mergeahmlp.room.converts;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.lang.reflect.Type;

public class ListConverters {
    @TypeConverter
    public static ArrayList<String> fromTimestamp(String value) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
        // return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static String arraylistToString(ArrayList<String> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);

        return json;
        // return date == null ? null : date.getTime();
    }
}
