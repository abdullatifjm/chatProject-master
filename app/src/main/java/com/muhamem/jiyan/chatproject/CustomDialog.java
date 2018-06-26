package com.muhamem.jiyan.chatproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CustomDialog extends Dialog implements android.view.View.OnClickListener {

    private Activity activity;
    private RadioGroup radioGroup;
    private boolean aBoolean;
    private EditText nameEditText , passEditText ;
    private Button enter;
    private String Idroom , Uid;
    private DatabaseReference mMyRef;
    private FirebaseAuth mAuth;




    public CustomDialog(Activity activity , String IdRoom) {
        super(activity);
        this.activity = activity;
        this.Idroom = IdRoom;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.enterroom);

        radioGroup = (RadioGroup)findViewById(R.id.radioGroup);
        nameEditText = (EditText) findViewById(R.id.nikname);
        passEditText = (EditText)findViewById(R.id.password);
        enter = (Button)findViewById(R.id.enter);
        mMyRef = FirebaseDatabase.getInstance().getReference().child(Idroom);
        mAuth = FirebaseAuth.getInstance();




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup ,int checkedId) {
                switch(checkedId){
                    case R.id.radioMember:
                        // do operations specific to this selection
                        passEditText.setVisibility(View.VISIBLE);
                        aBoolean = true;
                        break;
                    case R.id.radioGuest:
                        // do operations specific to this selection
                        passEditText.setVisibility(View.GONE);
                        aBoolean = false;

                        break;
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = nameEditText.getText().toString();
                final String pass = passEditText.getText().toString();

                if(!TextUtils.isEmpty(name)) {
                    if (aBoolean == true){
                        mMyRef.child("members").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(name)){
                                    if (pass.equals(dataSnapshot.child(name).getValue().toString())){
                                    AuthUser(name , Idroom);
                                }}
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    else if (aBoolean == false){
                        AuthUser(name , Idroom);
                    }
            }
            }
        });
//        yes = (Button) findViewById(R.id.btn_yes);
//        no = (Button) findViewById(R.id.btn_no);
//        yes.setOnClickListener(this);
//        no.setOnClickListener(this);

    }

    private void AuthUser(final String Name , final String Id){

        mAuth.signInAnonymously().addOnCompleteListener(activity,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Uid = mAuth.getCurrentUser().getUid().toString();
                            SignIn(Name , Id ,Uid);

                        } else {
                            // If sign in fails, display a message to the user.
                        }

                    }
                });
    }


    private void SignIn(String Name , String Id , String Uid){

        mMyRef.child("users").child(Uid).setValue(Name);
        Intent intent = new Intent(activity , LoggedIn.class);
        intent.putExtra("idroom" , Id);
        intent.putExtra("name",Name);
        intent.putExtra("uid" , Uid);
        activity.startActivity(intent);
        activity.finish();
   }

    @Override
    public void onClick(View v) {

    }
}
