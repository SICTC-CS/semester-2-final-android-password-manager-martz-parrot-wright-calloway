package com.example.passwordmanager;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// from stack overflow
import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class AuthManager {
    private static final String TAG = "AuthManager";
    private FirebaseAuth auth;
    public AuthManager(){
        auth = FirebaseAuth.getInstance();
    }
    public interface AuthCallback{
        public void onComplete(boolean success, String errorMessage);
    }
    public void signUP(String email, String password, final AuthCallback callback){
        //password requirements

            auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Log.d(TAG,"Created user: "+email);
                        callback.onComplete(true,null);
                    }
                    else {
                        Log.d(TAG,"Failure to create user: "+email);
                        callback.onComplete(false,task.getException().getMessage());
                    }
                }
            });
    }

    public void signIn(String email, String password, final AuthCallback callback){
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.d(TAG,"Welcome: "+email);
                    callback.onComplete(true,null);
                }
                else {
                    Log.d(TAG,"Failure to sign in user: "+email);
                    callback.onComplete(false,task.getException().getMessage());
                }
            }
        });

    }
    public FirebaseUser getActiveUser(){
        return auth.getCurrentUser();
    }

    public void logout() {
        auth.signOut();
    }

    public void deleteAccount(final AuthCallback callback) {
        FirebaseUser user = auth.getCurrentUser();
        if (user != null) {
            user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        callback.onComplete(true, null);
                    } else {
                        callback.onComplete(false, task.getException().getMessage());
                    }
                }
            });
        } else {
            callback.onComplete(false, "No user signed in");
        }
    }


    public boolean passwordCheck(String password){
        //from StackOverflow to find special characters
        Pattern sp = Pattern.compile("[!@#$%^&*()_+=|<>?{}\\[\\]~-]"); //holds selected chars that are needed to be in the password
        Matcher sm = sp.matcher(password); // Matcher takes a pattern and finds the chars that are in the pattern

        Pattern up = Pattern.compile("[A-Z]");
        Matcher um = up.matcher(password);

        Pattern lp = Pattern.compile("[0-9]");
        Matcher lm = lp.matcher(password);

        Pattern wp = Pattern.compile("[a-z]");
        Matcher wm = wp.matcher(password);



        if (password.length() < 8 || !(sm.find())||!(um.find())||!lm.find() || !wm.find()){
            return false;
        }else {
        return true;
        }
    }



}
