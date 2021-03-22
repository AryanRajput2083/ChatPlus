package com.ai.frencel20;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    Logg logg;
    public interface Logg{
        public void  changeFragment();
        public void  login(String s1,String s2);
        public void  skip(Boolean bbb);
        public void logou();
        public Boolean abcc();
        public void showd();
        public void deleteAc();
        public void forgetP(String s);
        public void Toas(String ss);
    }

    public void onAttach(Context context){
        super.onAttach(context);
        try {
            logg = (LoginFragment.Logg) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnImageClickListener");
        }
    }

    EditText user,pswd;
    TextView detail, register,forgetPs;
    Button login,skip,logout,delet;
    ProgressBar progressBar;
    LinearLayout l11;
    CheckBox cb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootview=inflater.inflate(R.layout.fragment_login, container, false);

        register=(TextView)rootview.findViewById(R.id.register);
        detail=(TextView)rootview.findViewById(R.id.detail);
        cb=(CheckBox)rootview.findViewById(R.id.checkbox);
        logout = (Button)rootview.findViewById(R.id.logout);
        user= (EditText)rootview.findViewById(R.id.username);
        pswd= (EditText)rootview.findViewById(R.id.password);
        login =(Button)rootview.findViewById(R.id.login);
        progressBar=(ProgressBar)rootview.findViewById(R.id.progress);
        l11=(LinearLayout)rootview.findViewById(R.id.l11);
        skip=(Button)rootview.findViewById(R.id.skip);
        delet=(Button)rootview.findViewById(R.id.delete);
        forgetPs=(TextView)rootview.findViewById(R.id.forgetP);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l11.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setIndeterminate(true);
                logg.login(user.getText().toString(),pswd.getText().toString());

            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logg.skip(cb.isChecked());



            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logg.logou();


                logout.setVisibility(View.GONE);
                l11.setVisibility(View.VISIBLE);
                skip.setVisibility(View.VISIBLE);
                delet.setVisibility(View.GONE);

                detail.setText("access all features after login");
                }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logg.changeFragment();
            }
        });
        delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logg.Toas("long click to delete account");
            }
        });
        delet.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                logg.deleteAc();
                return false;
            }
        });
        forgetPs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.getText().toString().length()>0) {
                    logg.forgetP(user.getText().toString());
                }else{
                    logg.Toas("enter your email first");
                }
            }
        });

        if(logg.abcc()){
            change();
            logg.showd();

        }
        return rootview;
    }

    public void change(){

        logout.setVisibility(View.VISIBLE);
        l11.setVisibility(View.INVISIBLE);
        skip.setVisibility(View.GONE);
        delet.setVisibility(View.VISIBLE);
    }

    public void stop(){
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    public  void showde(String nam,String eml){
        detail.setText(nam+"\n"+eml);
    }
}