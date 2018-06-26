package com.muhamem.jiyan.chatproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class RoomsAdapter extends ArrayAdapter<ModuleGetterRooms> {
    private DatabaseReference databaseReference;

    public RoomsAdapter(Context context,  ArrayList<ModuleGetterRooms> moduleGetterRooms) {
        super(context, 0, moduleGetterRooms);
        databaseReference = FirebaseDatabase.getInstance().getReference("rooms");
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final ModuleGetterRooms moduleGetterRooms = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.customlist_pageroom, parent, false);
        }

        TextView name;

        name = (TextView) convertView.findViewById(R.id.roomName);


        name.setText(moduleGetterRooms.getName());

        return convertView;
    }

}
