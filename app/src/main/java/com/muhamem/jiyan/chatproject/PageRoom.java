package com.muhamem.jiyan.chatproject;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PageRoom extends AppCompatActivity {

    ListView listView;
    private ArrayList<ModuleGetterRooms> rooms;
    private DatabaseReference mMyRef;
//    private Dialog Dialog;
    private CustomDialog customDialog;
    private String idroom ;
    private ProgressBar progressRoom;
//    private LinearLayout layoutDialog ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_room);
        progressRoom = (ProgressBar) findViewById(R.id.progress_room);
        progressRoom.setVisibility(View.VISIBLE);

//        Dialog = new Dialog(this);
//        layoutDialog = new LinearLayout(this);
        mMyRef = FirebaseDatabase.getInstance().getReference();





        listView =(ListView)findViewById(R.id.listPage);


        mMyRef.child("rooms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rooms = new ArrayList<ModuleGetterRooms>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ModuleGetterRooms  moduleGetterRooms = postSnapshot.getValue(ModuleGetterRooms.class);
                    rooms.add(moduleGetterRooms);
                }
                progressRoom.setVisibility(View.GONE);
                RoomsAdapter roomsAdapter = new RoomsAdapter(PageRoom.this , rooms);
                listView.setAdapter(roomsAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("loadPost:onCancelled", databaseError.toException());

            }
        });




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                ModuleGetterRooms moduleGetterRooms = rooms.get(position);
                idroom = moduleGetterRooms.getId();
                showDialog(idroom);



            }
        });







}

private void showDialog(final String Idroom){


    customDialog = new CustomDialog(PageRoom.this , Idroom);
    customDialog.show();
}


}


