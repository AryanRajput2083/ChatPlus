package com.ai.frencel20;

import android.content.Context;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView mMessageRecycler;
    private ChatListAdapter mMessageAdapter;
    ArrayList<HashMap<String,Object>> messageList=new ArrayList<>();
    HashMap<String, Object> map=new HashMap<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String pdate ="";

    Boolean bb=false;
    String cp;
    private FirebaseAuth mAuth;

    Timer tm=new Timer();
    TimerTask timerTask;


    EditText editt;
    FirebaseDatabase database =FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("chat");

    ChildEventListener childlistener;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mAuth = FirebaseAuth.getInstance();
        map = new HashMap<>();

        editt=(EditText)findViewById(R.id.edit_message);
        String a = mAuth.getCurrentUser().getUid();
        String b = getIntent().getStringExtra("uid");
        int i = a.compareTo(b);
        if (i > 0) {
            cp = a + "_and_" + b;
        }
        if (i < 0) {
            cp = b + "_and_" + a;
        }
        if (i == 0) {
            Toast.makeText(this, "i=0 failed", Toast.LENGTH_SHORT).show();
            cp = "";
        }
        final DatabaseReference myRef= database.getReference("chat/"+cp);

        mMessageRecycler = (RecyclerView) findViewById(R.id.recycler_gchat);
        //mMessageRecycler.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bb=true;
                if(editt.getText().toString().length()>0) {
                    send(editt.getText().toString(), myRef);
                }
            }
        });
        getdata(myRef);
        childlistener=new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(bb) {
                    getdata(myRef);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //getdata(myRef);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myRef.addChildEventListener(childlistener);


        //scroll();

    }


    void scroll(){
        timerTask=new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMessageRecycler.scrollToPosition(messageList.size()-1);
                    }
                });
            }
        };
        tm.schedule(timerTask,10);
    }


    void getdata(DatabaseReference my){

        messageList=new ArrayList<>();
        my.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    GenericTypeIndicator<HashMap<String, Object>> ind = new GenericTypeIndicator<HashMap<String, Object>>() {
                    };
                    for (DataSnapshot _data : dataSnapshot.getChildren()) {

                        HashMap<String, Object> _map = _data.getValue(ind);
                        messageList.add(_map);

                    }
                    pdate="";
                    mMessageAdapter = new ChatListAdapter(ChatActivity.this, messageList);
                    mMessageRecycler.setAdapter(mMessageAdapter);


                }
                catch(Exception _e){
                    //_e.printStackTrace();
                    Toast.makeText(ChatActivity.this, "failed", Toast.LENGTH_SHORT).show();

                }

                    //.........now do what u want with mapList.............
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        scroll();

    }
    Calendar cln = Calendar.getInstance();
    void send(String mess,DatabaseReference my){
        map=new HashMap<>();
        map.put("message",mess);
        map.put("name",mAuth.getCurrentUser().getDisplayName());
        map.put("UserId",mAuth.getCurrentUser().getUid());
        cln=Calendar.getInstance();
        map.put("time",new SimpleDateFormat("HH:mm").format(cln.getTime()));
        map.put("date",new SimpleDateFormat("dd MMMM yyyy").format(cln.getTime()));

        editt.setText("");
        my.push().updateChildren(map);

    }



    /*

    void getMessageList () {

        db.collection("chat").document("all messages").collection(cp)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                map=new HashMap<>();
                                map.putAll(document.getData());
                                messageList.add(map);
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            mMessageAdapter = new ChatListAdapter(ChatActivity.this, messageList);
                            mMessageRecycler.setAdapter(mMessageAdapter);
                        } else {
                            //Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(ChatActivity.this, "failed to load new data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    void sendMessage(String messag){
        cln=Calendar.getInstance();
        map=new HashMap<>();
        map.put("message",messag);
        map.put("name",mAuth.getCurrentUser().getDisplayName());
        map.put("UserId",mAuth.getCurrentUser().getUid());

        map.put("time",new SimpleDateFormat("HH:mm").format(cln.getTime()));
        map.put("date",new SimpleDateFormat("MMMM dd, yyyy").format(cln.getTime()));

        Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
        db.collection("chat").document("all messages").collection(cp)
                .add(map)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {

                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        editt.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Log.w(TAG, "Error adding document", e);
                        Toast.makeText(ChatActivity.this, "failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
     */


    class ChatListAdapter extends RecyclerView.Adapter {
        private static final int VIEW_TYPE_MESSAGE_SENT = 1;
        private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;



        private Context mContext;
        private ArrayList<HashMap<String,Object>> mMessageList;

        public ChatListAdapter(Context context, ArrayList<HashMap<String,Object>> messageList) {
            mContext = context;
            mMessageList = messageList;
            pdate="";
        }

        @Override
        public int getItemCount() {
            return mMessageList.size();
        }

        // Determines the appropriate ViewType according to the sender of the message.
        @Override
        public int getItemViewType(int position) {
            HashMap<String,Object> message = new HashMap<>();
            message=mMessageList.get(position);
            mAuth = FirebaseAuth.getInstance();
            if (message.get("UserId").equals(mAuth.getCurrentUser().getUid())) {
                // If the current user is the sender of the message
                return VIEW_TYPE_MESSAGE_SENT;
            } else {
                // If some other user sent the message
                return VIEW_TYPE_MESSAGE_RECEIVED;
            }
        }

        // Inflates the appropriate layout according to the ViewType.
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;

            if (viewType == VIEW_TYPE_MESSAGE_SENT) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_me, parent, false);
                return new SentMessageHolder(view);
            } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.chat_other, parent, false);
                return new ReceivedMessageHolder(view);
            }

            return null;
        }

        // Passes the message object to a ViewHolder so that the contents can be bound to UI.
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            HashMap<String,Object> message = new HashMap<>();
            message=mMessageList.get(position);

            switch (holder.getItemViewType()) {
                case VIEW_TYPE_MESSAGE_SENT:
                    ((SentMessageHolder) holder).bind(message);
                    break;
                case VIEW_TYPE_MESSAGE_RECEIVED:
                    ((ReceivedMessageHolder) holder).bind(message);
            }
            //scroll();
        }

        private class SentMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText,date;

            SentMessageHolder(View itemView) {
                super(itemView);

                date=(TextView)itemView.findViewById(R.id.date_me);
                messageText = (TextView) itemView.findViewById(R.id.message_me);
                timeText = (TextView) itemView.findViewById(R.id.time_me);

            }

            void bind(HashMap<String,Object> message) {
                messageText.setText(message.get("message").toString());

                // Format the stored timestamp into a readable String using method.
                timeText.setText(message.get("time").toString());
                if(pdate.equalsIgnoreCase(message.get("date").toString())){
                    date.setVisibility(View.GONE);
                    //date.setText(message.get("date").toString());
                }else{
                    pdate=message.get("date").toString();
                    date.setText("a-"+message.get("date").toString());
                }

            }
        }

        private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
            TextView messageText, timeText, nameText,date;
            ImageView profileImage;

            ReceivedMessageHolder(View itemView) {
                super(itemView);
                date=(TextView)itemView.findViewById(R.id.date_other);
                messageText = (TextView) itemView.findViewById(R.id.message_other);
                timeText = (TextView) itemView.findViewById(R.id.time_other);
                nameText = (TextView) itemView.findViewById(R.id.username_other);
                //profileImage = (ImageView) itemView.findViewById(R.id.image_message_profile);
            }

            void bind(HashMap<String,Object> message) {
                messageText.setText(message.get("message").toString());

                // Format the stored timestamp into a readable String using method.
                timeText.setText(message.get("time").toString());
                if(pdate.equalsIgnoreCase(message.get("date").toString())){
                    date.setVisibility(View.GONE);
                    //date.setText(message.get("date").toString());
                }else{
                    pdate=message.get("date").toString();
                    date.setText("a-"+message.get("date").toString());
                }
                nameText.setText(message.get("name").toString());

                // Insert the profile image from the URL into the ImageView.
                //Utils.displayRoundImageFromUrl(mContext, message.getSender().getProfileUrl(), profileImage);
            }
        }
    }
}