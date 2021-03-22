package com.ai.frencel20;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class AllChatActivity extends AppCompatActivity {

    ListView lstv;
    ArrayList<HashMap<String,Object>> messageList= new ArrayList<HashMap<String, Object>>();
    ArrayList<String> listt=new ArrayList<>();
    ArrayList<String> list2=new ArrayList<>();

    private FirebaseAuth mAuth;


    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("chat/accounts");

    Intent iii=new Intent();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        lstv=(ListView)findViewById(R.id.listV);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        getdata();
        lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                iii.setClass(getApplicationContext(),ChatActivity.class);
                iii.putExtra("uid",list2.get(i));
                iii.putExtra("ref",i);
                startActivity(iii);
                //Toast.makeText(AllChatActivity.this, listt.get(i)+"\n"+list2.get(i)+"\n"+list3.get(i), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void getdata(){

        messageList=new ArrayList<>();
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                    };
                    for (DataSnapshot _data : dataSnapshot.getChildren()) {

                        HashMap<String, Object> _map = _data.getValue(ind);
                        if(!_data.getKey().equals(mAuth.getCurrentUser().getUid())) {
                            list2.add(_data.getKey());

                            listt.add(_map.get("name").toString());
                        }

                    }

                    lstv.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,listt));

                }
                catch(Exception _e){
                    //_e.printStackTrace();
                    Toast.makeText(AllChatActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }

                //.........now do what u want with mapList.............
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}