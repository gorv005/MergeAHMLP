package com.dartmic.mergeahmlp;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {
    DatabaseHelper myDB;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_view_list_contents);
//
//        ListView listView = (ListView) findViewById(R.id.listView);
//        myDB = new DatabaseHelper(ViewListContents.this);
//
//        ArrayList<String> theList = new ArrayList<>();
//      //  Cursor data = myDB.getListContents();
//
//        if (data.getCount() == 0) {
//            Toast.makeText(ViewListContents.this, "database is empty :(.", Toast.LENGTH_SHORT).show();
//        } else {
//            while (data.moveToNext()) {
//                theList.add(data.getString(1));
//            }
//            ListAdapter listAdapter = new ArrayAdapter<>(ViewListContents.this, android.R.layout.simple_list_item_1, theList);
//            listView.setAdapter(listAdapter);
//        }
//    }
}
