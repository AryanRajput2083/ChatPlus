package com.ai.frencel20;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class Dictionary extends AppCompatActivity {

    EditText word;
    TextView wrd, mean;
    ImageView speak;
    LinearLayout ll;
    ProgressBar searchP;

    TextToSpeech tts;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference acdjn = database.getReference("dictionary/acdjn");
    DatabaseReference befi = database.getReference("dictionary/befi");
    DatabaseReference ghrp = database.getReference("dictionary/ghrp");
    DatabaseReference kloqs = database.getReference("dictionary/kloqs");
    DatabaseReference mtuvwxyz = database.getReference("dictionary/mtuvwxyz");

    HashMap<String,Object> map1= new HashMap<>();
    final ArrayList <HashMap<String,Object>> mapList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        word=(EditText)findViewById(R.id.edt_dic);
        wrd=(TextView) findViewById(R.id.wrdtype);
        mean=(TextView) findViewById(R.id.mean_dic);
        speak=(ImageView)findViewById(R.id.speak_dic);

        ll=(LinearLayout)findViewById(R.id.mmaaiinn);
        searchP=(ProgressBar)findViewById(R.id.searchP);
        ll.setElevation(20);


        tts= new TextToSpeech(getApplicationContext(),null);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();

                map1.clear();
                if(word.getText().toString().length()>0){
                    start1(word.getText().toString().trim().toLowerCase());
                    searchP.setVisibility(View.VISIBLE);
                    searchP.setIndeterminate(true);
                }else{
                    Snackbar.make(view, "please enter a word", Snackbar.LENGTH_SHORT)
                          .setAction("Action", null).show();
                }



            }
        });

        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(wrd.getText().toString(),TextToSpeech.QUEUE_ADD,null);
            }
        });

    }

    int indx(String c, String s){
        int i=0;
        for (i=0;i<s.length();i++){
            if(c.equals(s.substring(i,i+1))){
                break;
            }
        }

        return i;
    }
    void start1(String stst){
        String ch= stst.toLowerCase().substring(0,1);
        String st= ch.toUpperCase()+stst.substring(1,stst.length());
        if(ch.equals("a")||ch.equals("c")||ch.equals("d")||ch.equals("j")||ch.equals("n")){
            search(st,acdjn,indx(ch,"acdjn"));
        }
        else if(ch.equals("b")||ch.equals("e")||ch.equals("f")||ch.equals("i")){
            search(st,befi,indx(ch,"befi"));
        }
        else if(ch.equals("g")||ch.equals("h")||ch.equals("r")||ch.equals("p")){
            search(st,ghrp,indx(ch,"ghrp"));
        }
        else if(ch.equals("k")||ch.equals("l")||ch.equals("o")||ch.equals("q")||ch.equals("s")){
            search(st,kloqs,indx(ch,"kloqs"));
        }
        else if(ch.equals("m")||ch.equals("t")||ch.equals("u")||ch.equals("v")||ch.equals("w")||ch.equals("x")||ch.equals("y")||ch.equals("z")){
            search(st,mtuvwxyz,indx(ch,"mtuvwxyz"));
        }else{
            Toast.makeText(this, "invalid word", Toast.LENGTH_SHORT).show();
        }
    }

    void search(final String ss, DatabaseReference db, final int t){

        DatabaseReference ddb= db.child(ss.toLowerCase().substring(0,1));
        ddb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                    };
                    if(dataSnapshot.hasChild(ss)){

                        final String mnn= dataSnapshot.child(ss).getValue(String.class);
                        wrd.setText(ss);
                        print(mnn.replaceAll("  "," "));
                    }else{
                        Toast.makeText(Dictionary.this, "soory I don't know", Toast.LENGTH_SHORT).show();
                    }
                    searchP.setIndeterminate(false);
                    searchP.setVisibility(View.INVISIBLE);



                }
                catch(Exception _e){
                    _e.printStackTrace();
                    Toast.makeText(Dictionary.this, "failed", Toast.LENGTH_SHORT).show();
                }

                    //.........now do what u want with mapList.............



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void print(String mnt){
        String nmm="";
        ll.setVisibility(View.VISIBLE);
        String[] strings=mnt.split(" zyothryz ");
        for(String i :strings){
            String[] t2=i.split(" zartmenz ");
            for (String j: t2) {
                nmm = nmm + j + "\n";
            }
            nmm=nmm+"\n";
        }

        mean.setText(nmm);
        mean.setElevation(14);
    }
}