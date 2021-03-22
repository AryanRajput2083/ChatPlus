package com.ai.frencel20;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import java.io.IOError;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements LoginFragment.Logg, Register.Regist{


    private FirebaseAuth mAuth;

    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("chat/accounts");

    String ss="";
    Intent it= new Intent();

    Boolean abc=false,ult=false;
    SharedPreferences sspp;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //FragmentManager fragmentManager;
    Register register1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment loginFragment = new LoginFragment();
        register1=new Register();

        ult=false;




        mAuth = FirebaseAuth.getInstance();

        sspp=getSharedPreferences("Prerna", Activity.MODE_PRIVATE);

        try {
            if((getIntent().getStringExtra("family").equals("Prerna"))){
                ss=getIntent().getStringExtra("family");
                // stop
            }else {
                ss="aryan";
                // activity change
            }
        }catch (RuntimeException runtimeException){
            ss="aryan";
        }




        if(isLogged()){
            if(ss.equals("aryan")){
                it.setClass(getApplicationContext(),MainActivity2.class);
                String email=mAuth.getCurrentUser().getEmail();
                it.putExtra("username",mAuth.getCurrentUser().getUid());
                it.putExtra("pow",ult);
                startActivity(it);
            }else{


                abc=true;


            }
        }else if(sspp.getBoolean("f",true)&&(!ss.equals("Prerna"))){
            it.setClass(getApplicationContext(),MainActivity2.class);
            it.putExtra("username","");
            it.putExtra("pow",ult);
            startActivity(it);
        }

        fragmentManager.beginTransaction().add(R.id.logRegister, loginFragment).commit();

    }

    private void logi(final String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // )
                            if(mAuth.getCurrentUser().isEmailVerified()) {
                                Toast.makeText(LoginActivity.this, "u r logged in", Toast.LENGTH_SHORT).show();
                                FirebaseUser userr = mAuth.getCurrentUser();
                                it.setClass(getApplicationContext(), MainActivity2.class);
                                it.putExtra("username", email);
                                it.putExtra("pow",ult);
                                startActivity(it);
                            }else {
                                final LoginFragment frag = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                                if (frag != null) {
                                    frag.detail.setText("your email is not verified");
                                    mAuth.getCurrentUser()
                                            .sendEmailVerification()
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    frag.detail.setText("your email is not verified\nemail sent, check your inbox and then login again");

                                                }
                                            });
                                }
                                mAuth.signOut();
                            }


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Authentication failed."+task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();


                        }

                        LoginFragment lll=(LoginFragment)getSupportFragmentManager().findFragmentById(R.id.logRegister);
                        lll.stop();

                        // ...
                    }
                });
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
    }

    public Boolean isLogged(){
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            return true;
        }else{
            return false;
        }

    }



    @Override

    public void onStop() {


        super.onStop();
        if(FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
            finish();
        }
    }

    @Override
    public void changeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.logRegister, register1).commit();
    }

    @Override
    public void login(String s1,String s2){
        logi(s1,s2);

    }
    @Override
    public void skip(Boolean bbb){
        sspp.edit().putBoolean("f",bbb).commit();

        it.setClass(getApplicationContext(),MainActivity2.class);
        it.putExtra("username","");
        it.putExtra("pow",ult);
        startActivity(it);
    }
    @Override
    public void logou(){
        logout();
        Toast.makeText(LoginActivity.this, "logged out", Toast.LENGTH_SHORT).show();
    }

    @Override
    public Boolean abcc(){
        return abc;
    }

    @Override
    public void Toas(String ss) {
        Toast.makeText(this, ss, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void createNewAc(final String fn, final String ln, final String eml, final String phn, final String dobb, String pasw) {

        mAuth.createUserWithEmailAndPassword(eml,pasw).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    saveData(fn,ln,eml,phn,dobb);



                }else{
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Register frag = (Register) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                    if (frag != null) {
                        frag.stopProgress(false);
                    }
                }
            }
        });

    }
    protected void saveData(final String fn,final String ln, String eml,String phn,String dobb){
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("first", fn);
        user.put("last", ln);
        user.put("born", dobb);
        user.put("phone",phn);
        user.put("email",eml);
        user.put("power","normal");

// Add a new document with a generated ID
        db.collection("accounts").document(mAuth.getCurrentUser().getUid())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {


                    @Override
                    public void onSuccess(Void avoid) {
                        acc(fn+"_"+ln);
                        FirebaseUser userr = FirebaseAuth.getInstance().getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(fn+"_"+ln)
                                .build();

                        userr.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            //Log.d(TAG, "User profile updated.");
                                            Toast.makeText(LoginActivity.this, "successfully created", Toast.LENGTH_SHORT).show();
                                            Register frag = (Register) getSupportFragmentManager().findFragmentById(R.id.logRegister);

                                            if (frag != null&&!mAuth.getCurrentUser().isEmailVerified()) {
                                                frag.setMessage("account created\nverify your email now or you will not be able to login");
                                                frag.verfyE.setVisibility(View.VISIBLE);

                                            }
                                            //Register frag = (Register) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                                            if (frag != null) {
                                                frag.stopProgress(false);
                                            }
                                        }else{
                                            Toast.makeText(LoginActivity.this, "failed to update profile", Toast.LENGTH_SHORT).show();
                                            Register frag = (Register) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                                            if (frag != null) {
                                                frag.stopProgress(false);
                                            }
                                        }
                                    }
                                });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LoginActivity.this, "data not saved as "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        Register frag = (Register) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                        if (frag != null) {
                            frag.stopProgress(false);
                        }
                    }
                });









    }
    void acc(String nm){
        HashMap<String,Object> mppt=new HashMap<>();
        mppt.put("name",nm);
        mppt.put("uid",mAuth.getCurrentUser().getUid());
        ref.child(mAuth.getCurrentUser().getUid()).updateChildren(mppt);
    }

    void updateProf(String nm){

    }
    @Override
    public void showd(){

        db.collection("accounts").document(mAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {

                            DocumentSnapshot doc =task.getResult();
                            if(doc.exists()){
                                Map<String, Object> user = new HashMap<>();
                                user=doc.getData();

                                LoginFragment frag = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                                if (frag != null) {
                                    frag.showde(mAuth.getCurrentUser().getDisplayName(),mAuth.getCurrentUser().getEmail());

                                }else {
                                    Toast.makeText(LoginActivity.this, "frag is null", Toast.LENGTH_SHORT).show();
                                }
                                if(user.get("power").toString().equals("ultimate")){
                                    ult=true;
                                }
                            }else {
                                Toast.makeText(LoginActivity.this, "no data", Toast.LENGTH_SHORT).show();
                                LoginFragment frag = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                                if (frag != null) {
                                    frag.showde("__",mAuth.getCurrentUser().getEmail());

                                }else {
                                    Toast.makeText(LoginActivity.this, "frag is null", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "failed to load data", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    @Override
    public void deleteAc() {
        ref.child(mAuth.getCurrentUser().getUid()).removeValue();
        mAuth.getCurrentUser().delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "your account is deleted", Toast.LENGTH_SHORT).show();

                            LoginFragment frag = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                            if (frag != null) {
                                frag.logout.setVisibility(View.GONE);
                                frag.l11.setVisibility(View.VISIBLE);
                                frag.skip.setVisibility(View.VISIBLE);
                                frag.delet.setVisibility(View.GONE);

                                frag.detail.setText("access all features after login");
                            }
                        }
                    }
                });

    }

    @Override
    public void forgetP(String s) {
        mAuth.sendPasswordResetEmail(s)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            LoginFragment frag = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                            if (frag != null) {
                                frag.detail.setText("email sent on your email, reset your password from there");
                            }
                        }
                    }
                });
    }

    @Override
    public void verifyE(final String mal, String pas){
        Register frag = (Register) getSupportFragmentManager().findFragmentById(R.id.logRegister);
        if (frag != null) {
            frag.stopProgress(true);
        }
        emlVerify(mal);
    }

    public void emlVerify(String mal){
        if(mAuth.getCurrentUser()!=null){
            final Register frag = (Register) getSupportFragmentManager().findFragmentById(R.id.logRegister);

            if(mAuth.getCurrentUser().isEmailVerified()){
                if (frag != null) {
                    frag.verified();

                }
            }else{
                mAuth.getCurrentUser()
                        .sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (frag != null) {
                                    frag.setMessage("email sent, check your mail and then press verify button again");
                                    Register frag = (Register) getSupportFragmentManager().findFragmentById(R.id.logRegister);
                                    if (frag != null) {
                                        frag.stopProgress(false);
                                    }
                                }
                            }
                        });
            }
        }else{
            Toast.makeText(this, "you are not logged in", Toast.LENGTH_SHORT).show();
        }
    }

}