package com.example.passwordmanager.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.passwordmanager.data.model.LoggedInUser;
import com.example.passwordmanager.data.model.Acount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CRUDManager {
    private FirebaseAuth auth;
    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private static final String TAG= "CRUDManager";
    private static List<Acount> acountList = new ArrayList<>();

    public interface CrudCallback {
        void onComplete(boolean success, String errorMessage);
    }

    public interface AcountListCallback{
        void onAcountsLoaded(List<Acount> acounts);
        void onAcountsLoadedError(String errorMessage);
    }

    ///////// CREATE section /////////
    public void writeNewAcount(Acount t, CrudCallback callback){
        //set acount and get a reference
        DatabaseReference currentRef = databaseReference.child("acounts");

        //generate a unique key using the push
        String fbid = currentRef.push().getKey();
        //set the id and save
        if(fbid!=null){
            t.setId(fbid);
            currentRef.child(fbid).setValue(t).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    callback.onComplete(true,null);
                } else {
                    callback.onComplete(false,task.getException().getMessage());
                }
            });}
}

    ///////// READ section /////////
    public static void readAllAcount(AcountListCallback callback) {
        //Go obtain a "snapshot" or current state of the DB
        databaseReference.child("acounts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Acount> tList = new ArrayList<>();
                for(DataSnapshot eachAcount : snapshot.getChildren()){
                    Acount t = eachAcount.getValue(Acount.class);
                    if(t!=null){
                    tList.add(t);}
                }
                acountList = tList;
                Log.d(TAG, String.valueOf(acountList));
            callback.onAcountsLoaded(tList);}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onAcountsLoadedError(error.getMessage());

            }


        });

    }

    ///////UPDATE SECTION ////////////
    public void updateAcount(Acount t, CrudCallback callback){
        //put the t where it should go
        databaseReference.child("acounts").child(t.getId()).setValue(t).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                callback.onComplete(true,null);
            } else {
                callback.onComplete(false,task.getException().getMessage());
            }});}

    /////////REMOVE SECTION//////////
    public void removeAcount(Acount t, CrudCallback callback){
        //remove the t
        databaseReference.child("acounts").child(t.getId()).removeValue().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                callback.onComplete(true,null);
            } else {
                callback.onComplete(false,task.getException().getMessage());
            }});}

    public static List<Acount> getAcountList(){
        return acountList;}
}
