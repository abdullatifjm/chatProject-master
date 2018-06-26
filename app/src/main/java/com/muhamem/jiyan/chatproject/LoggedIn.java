package com.muhamem.jiyan.chatproject;

import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoggedIn extends AppCompatActivity {

    private ImageView msndButton;
    private EditText mMssgtxt;
    private FirebaseAuth mAuth;
    //private String mEmail;
    private String mssg , getName , name ,id , uid;
//    private FirebaseDatabase mDatabase;
    private DatabaseReference mMyRef;
    ListView chatListView , usersListView;
    ArrayAdapter<String> chatArrayAdapter , usersArrayAdapter;
    ArrayList<String> messages , users;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
          setContentView(R.layout.drawer_main_layout);


        name = getIntent().getExtras().getString("name");
        id = getIntent().getExtras().getString("idroom");
        uid = getIntent().getExtras().getString("uid");
        msndButton = (ImageView) findViewById( R.id.btnsnd );
        mMssgtxt = (EditText) findViewById( R.id.ditTxtMssg );
        mAuth = FirebaseAuth.getInstance();
//        mDatabase = FirebaseDatabase.getInstance();
        mMyRef = FirebaseDatabase.getInstance().getReference(id);
        //mEmail = mAuth.getCurrentUser().getEmail();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle(name);
//        setTitle(name);
        messages = new ArrayList<String>();
        chatListView =(ListView)findViewById(R.id.listView_chat);
        chatArrayAdapter = new ArrayAdapter<String>(this,R.layout.custom_list_itm,R.id.sndmssg, messages);
        chatListView.setAdapter(chatArrayAdapter);
        users = new ArrayList<String>();
        usersListView=(ListView)findViewById(R.id.list_usrs_room);
        usersArrayAdapter = new ArrayAdapter<String>(this , android.R.layout.simple_list_item_1 , users);
        usersListView.setAdapter(usersArrayAdapter);
        mMyRef.child("mssg").setValue(name + " : "+"قام بالدخول للغرفة");



//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        msndButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sndmsg(  );
            }
        } );


        mMyRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                mssg = dataSnapshot.child("mssg").getValue(String.class);
                messages.add(mssg);
                chatListView.setSelection(messages.size());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        mMyRef.child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                users.add(dataSnapshot.getValue().toString());
                usersListView.setSelection(users.size());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                users.remove(dataSnapshot.getValue().toString());
                usersListView.setSelection(users.size());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    @Override
    protected void onStop() {
        super.onStop();
        mMyRef.child("users").child(uid).removeValue();
        mMyRef.child("mssg").setValue(name + " : "+"غادر الغرفة");



    }

    @Override
    protected void onPause() {
        super.onPause();


    }



    private void sndmsg(){
//        mMyRef.child("user").setValue(name);
        mMyRef.child("mssg").setValue(name + " : " + mMssgtxt.getText().toString());
        mMssgtxt.setText( null );
    }



}
