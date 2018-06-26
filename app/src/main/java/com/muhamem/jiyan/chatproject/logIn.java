package com.muhamem.jiyan.chatproject;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logIn extends AppCompatActivity {


    private FirebaseAuth auth;
    private Button pageRoom;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        auth = FirebaseAuth.getInstance();
        pageRoom = (Button) findViewById(R.id.roompag);


        pageRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPageRoom();
            }
        });

    }


    private void goToPageRoom(){
        startActivity( new Intent( logIn.this , PageRoom.class ));
        finish();

    }

}
