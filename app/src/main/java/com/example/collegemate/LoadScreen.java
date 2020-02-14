package com.example.collegemate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoadScreen extends AppCompatActivity {
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_screen);

        //Refrencing
        pb = findViewById(R.id.loadscreen_pb);

        //Check User Existence
        if (isUserExists()){
            loadDataAndStartActivity();
        }else{
            startActivity(new Intent(LoadScreen.this,HomeActivity.class));
            finish();
        }


    }

    public void loadDataAndStartActivity(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Global.userRef = db.collection("users").document(FirebaseAuth.getInstance().getUid());
        Global.userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult()!=null){
                    Global.documentData = task.getResult().toObject(Global.UserData.class);
                    startActivity(new Intent(LoadScreen.this,Home.class));
                    finish();
                }
            }
        });
    }

    public boolean isUserExists(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            return true;
        }
        return false;
    }
}
