package com.ai.frencel20;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    Intent itt= new Intent();

    Button calu,man2,file,notes,tss,mtx ,dict,html,chatter;
    TextView logg;
    LinearLayout ll1;
    LoginActivity loginActivity;

    Boolean loggedIn, tt,autt;
    String ussr=new String();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ussr=getIntent().getStringExtra("username");
        autt=getIntent().getBooleanExtra("pow",false);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 //       .setAction("Action", null).show();
            }
        });

        loggedIn=false;
        tt=false;


        calu=(Button)findViewById(R.id.calu);
        man2=(Button)findViewById(R.id.main2);
        file=(Button)findViewById(R.id.file);
        notes=(Button)findViewById(R.id.nots);
        tss=(Button)findViewById(R.id.ttss);
        mtx=(Button)findViewById(R.id.mtxx);
        dict=(Button)findViewById(R.id.dictt);
        ll1=(LinearLayout)findViewById(R.id.ll1);
        chatter=(Button)findViewById(R.id.chatter);

        html=(Button)findViewById(R.id.httm);
        logg=(TextView)findViewById(R.id.log);

        dict.setEnabled(false);
        mtx.setEnabled(false);
        html.setEnabled(false);
        chatter.setEnabled(false);

        man2.setVisibility(View.INVISIBLE);
        file.setVisibility(View.INVISIBLE);

        loginActivity = new LoginActivity();


        checkLogin();

        calu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //itt.setClass(getApplicationContext(),MainCalc.class);
                //startActivity(itt);
            }
        });
        man2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //itt.setClass(getApplicationContext(),MainActivity.class);
                //startActivity(itt);
            }
        });
        file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //itt.setClass(getApplicationContext(),FileManage.class);
                //startActivity(itt);
            }
        });
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //itt.setClass(getApplicationContext(),Keep_notes.class);
                //startActivity(itt);
            }
        });
        tss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //itt.setClass(getApplicationContext(),Text_ts.class);
                //startActivity(itt);
            }
        });
        mtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // itt.setClass(getApplicationContext(),Matrix.class);
                //startActivity(itt);
            }
        });
        dict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itt.setClass(getApplicationContext(),Dictionary.class);
                startActivity(itt);
            }
        });
        html.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itt.setClass(getApplicationContext(),HtmlActivity.class);
                startActivity(itt);
            }
        });
        logg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt=true;
                itt.setClass(getApplicationContext(), LoginActivity.class);
                itt.putExtra("family","Prerna");
                startActivity(itt);

            }
        });
        chatter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itt.setClass(getApplicationContext(), AllChatActivity.class);
                itt.putExtra("uid","ussr");
                startActivity(itt);

            }
        });
    }

    public void checkLogin(){
        if(ussr.length()>1) {
            logg.setText("LOGOUT");
            loggedIn=true;
            visibilize();
        }else{

            loggedIn=false;
        }

    }

    private void visibilize(){
        dict.setEnabled(true);
        html.setEnabled(true);
        mtx.setEnabled(true);
        chatter.setEnabled(true);
        if(autt){
            man2.setVisibility(View.VISIBLE);
            file.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onStop() {


        super.onStop();
        if(tt) {
            finish();
        }
    }
}