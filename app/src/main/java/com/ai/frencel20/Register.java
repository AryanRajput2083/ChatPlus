package com.ai.frencel20;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Register#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Register extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Register() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Register.
     */
    // TODO: Rename and change types and number of parameters
    public static Register newInstance(String param1, String param2) {
        Register fragment = new Register();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public interface Regist{
        public void Toas(String ss);
        public void createNewAc(final String fn,final String ln,final String eml,final String phn,final String dobb,final String pasw);
        public void verifyE(String mal,String pas);
    }
    Regist regist;

    public void onAttach(Context context){
        super.onAttach(context);
        try {
            regist = (Register.Regist) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    EditText fname,lname,emaill,phone,dob, pas1,pas2;

    Button createAccount,verfyE;
    ProgressBar pg;
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootview=inflater.inflate(R.layout.fragment_register, container, false);

        fname=(EditText)rootview.findViewById(R.id.firstName);
        lname=(EditText)rootview.findViewById(R.id.lastName);
        emaill=(EditText)rootview.findViewById(R.id.emailId);
        phone=(EditText)rootview.findViewById(R.id.phoneNum);
        dob=(EditText)rootview.findViewById(R.id.dob);
        createAccount=(Button)rootview.findViewById(R.id.createAcc);
        pg=(ProgressBar)rootview.findViewById(R.id.progress);
        pas1=(EditText)rootview.findViewById(R.id.pas1);
        pas2=(EditText)rootview.findViewById(R.id.pas2);
        verfyE=(Button)rootview.findViewById(R.id.verify);
        tv=(TextView)rootview.findViewById(R.id.message);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fname.getText().length()>0&&lname.getText().length()>0&&emaill.getText().length()>0){
                    if(phone.getText().length()>0&&dob.getText().length()>0){

                        if(pas1.getText().length()>0&&pas1.getText().toString().equals(pas2.getText().toString())) {

                            pg.setVisibility(View.VISIBLE);
                            pg.setIndeterminate(true);

                            regist.createNewAc(fname.getText().toString().trim(), lname.getText().toString().trim(),
                                    emaill.getText().toString().trim(), phone.getText().toString().trim(), dob.getText().toString().trim(), pas1.getText().toString());
                            createAccount.setEnabled(false);
                        }else{
                            regist.Toas("password mismatch");
                        }
                    }else{
                        regist.Toas("fill all fields");
                    }
                }else {
                    regist.Toas("fill all field");
                }
            }
        });

        verfyE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regist.verifyE(emaill.getText().toString(),pas1.getText().toString());
            }
        });
        return rootview;
    }

    public void stopProgress(Boolean b){
        pg.setIndeterminate(b);
    }

    public void verified(){
        verfyE.setEnabled(false);
        verfyE.setText("verified");
        tv.setText("Email verified");
    }
    public void setMessage(String ss){
        tv.setText(ss);
    }
}