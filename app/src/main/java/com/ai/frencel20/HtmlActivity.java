package com.ai.frencel20;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Environment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class HtmlActivity extends AppCompatActivity implements OpenFileFragment.inter, OutputFragment.inter{

    TextView proj,files,outpt,pathC;
    public Boolean bt;
    public String cPath;

    ListView lstV;
    LinearLayout llyt,ltt;
    ArrayList<String> stlt=new ArrayList<>();
    ArrayList<String> mainSt=new ArrayList<>();
    ArrayList<HashMap<String,Object>> mplst=new ArrayList<>();
    HashMap<String,Object> mpp=new HashMap<>();

    String abcc="",abcd="",aacd="";
    int test=0;

    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3,fab4,fab5;

    Button imgP;
    Button creatf;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstV=(ListView)findViewById(R.id.listV);
        proj=(TextView)findViewById(R.id.allProjects);
        files=(TextView)findViewById(R.id.pFiles);
        outpt=(TextView)findViewById(R.id.output);
        pathC=(TextView)findViewById(R.id.path);

        llyt=(LinearLayout)findViewById(R.id.lnly);
        ltt=(LinearLayout)findViewById(R.id.ltt);

        editText=(EditText)findViewById(R.id.textField);
        imgP=(Button)findViewById(R.id.addd) ;
        creatf=(Button)findViewById(R.id.creat);

        OpenFileFragment outputFragment =new OpenFileFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.mantm, outputFragment).commit();
        llyt.setVisibility(View.GONE);



        if(Build.VERSION.SDK_INT>=23){
            if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},1001);
            }else{
                listProjects(FileUtil.getExternalStorageDir()+"/Frencel_Webpages");
            }
        }

        bt=true;

        imgP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ltt.getVisibility()==View.GONE) {
                    ltt.setVisibility(View.VISIBLE);
                }else{
                    ltt.setVisibility(View.GONE);
                }
            }
        });
        creatf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText.getText().toString().length()!=0) {
                    FileUtil.writeFile(pathC.getText().toString() + "/" + editText.getText().toString(), "");
                    listProjects(pathC.getText().toString());
                    ltt.setVisibility(View.GONE);
                }
            }
        });

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab1 = findViewById(R.id.fab1);
        fab1.setVisibility(View.GONE);
        fab2 = findViewById(R.id.fab2);
        fab2.setVisibility(View.GONE);
        fab3 = findViewById(R.id.fab3);
        fab3.setVisibility(View.GONE);
        fab4 = findViewById(R.id.fab4);
        fab4.setVisibility(View.GONE);
        fab5 = findViewById(R.id.fab5);
        fab5.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                OpenFileFragment frag = (OpenFileFragment) getSupportFragmentManager().findFragmentById(R.id.mantm);
                if (frag != null) {
                    abcc=frag.data.getText().toString();

                    run_File(FileUtil.readFile(abcc),aacd);
                }else{
                    Toast.makeText(HtmlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                OpenFileFragment frag = (OpenFileFragment) getSupportFragmentManager().findFragmentById(R.id.mantm);
                if (frag != null) {
                    frag.setChar("<");
                }else{
                    Toast.makeText(HtmlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                OpenFileFragment frag = (OpenFileFragment) getSupportFragmentManager().findFragmentById(R.id.mantm);
                if (frag != null) {
                    frag.setChar("/");
                }else{
                    Toast.makeText(HtmlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                OpenFileFragment frag = (OpenFileFragment) getSupportFragmentManager().findFragmentById(R.id.mantm);
                if (frag != null) {
                    frag.setChar(">");
                }else{
                    Toast.makeText(HtmlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                OpenFileFragment frag = (OpenFileFragment) getSupportFragmentManager().findFragmentById(R.id.mantm);
                if (frag != null) {
                    frag.setChar("=");
                }else{
                    Toast.makeText(HtmlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
        fab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                OpenFileFragment frag = (OpenFileFragment) getSupportFragmentManager().findFragmentById(R.id.mantm);
                if (frag != null) {
                    frag.setChar("\"");
                }else{
                    Toast.makeText(HtmlActivity.this, "failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        proj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pathC.setVisibility(View.VISIBLE);
                imgP.setVisibility(View.VISIBLE);
                llyt.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);
                lstV.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
                fab3.setVisibility(View.GONE);
                fab4.setVisibility(View.GONE);
                fab5.setVisibility(View.GONE);
                //listProjects(FileUtil.getExternalStorageDir()+"/Frencel_Webpages");
                proj.setTextColor(0xffffff00);
                files.setTextColor(0xffffffff);
                outpt.setTextColor(0xffffffff);
            }
        });
        files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(test==4) {
                    open_File(FileUtil.readFile(aacd), aacd);
                    proj.setTextColor(0xffffffff);
                    files.setTextColor(0xffffff00);
                    outpt.setTextColor(0xffffffff);
                }
            }
        });
        /*outpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lstV.setVisibility(View.GONE);
                OutputFragment outputFragment =new OutputFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.mantm, outputFragment).commit();
                proj.setTextColor(0xffffffff);
                files.setTextColor(0xffffffff);
                outpt.setTextColor(0xffffff00);
            }
        });*/

        lstV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(FileUtil.isDirectory(mplst.get(i).get("path").toString())) {
                    Toast.makeText(HtmlActivity.this,"director d"+ mplst.get(i).get("path").toString(), Toast.LENGTH_SHORT).show();
                }
                if(FileUtil.isFile(mplst.get(i).get("path").toString())) {
                    abcc=FileUtil.readFile(mplst.get(i).get("path").toString());
                    abcd=mplst.get(i).get("last").toString();
                    aacd=mplst.get(i).get("path").toString();
                    open_File(FileUtil.readFile(mplst.get(i).get("path").toString()),mplst.get(i).get("path").toString());
                }else {
                    Toast.makeText(HtmlActivity.this, mplst.get(i).get("path").toString(), Toast.LENGTH_SHORT).show();
                    listProjects(mplst.get(i).get("path").toString());
                }
            }
        });

        lstV.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(FileUtil.isFile(mplst.get(i).get("path").toString())) {
                    abcc=FileUtil.readFile(mplst.get(i).get("path").toString());
                    abcd=mplst.get(i).get("path").toString();
                    run_File(FileUtil.readFile(mplst.get(i).get("path").toString()),mplst.get(i).get("path").toString());
                }

                return true;
            }
        });


    }

    @Override
    public String path() {
        return abcd;
    }

    @Override
    public String data() {
        return abcc;
    }

    @Override
    public String ppth() {
        return aacd;
    }


    public class listviewAdoptor extends BaseAdapter {
        ArrayList<HashMap<String,Object>> data_;
        public listviewAdoptor(ArrayList<HashMap<String,Object>> arr_){
            data_=arr_;
        }

        @Override
        public int getCount() {
            return data_.size();
        }

        @Override
        public HashMap<String,Object> getItem(int ij) {
            return data_.get(ij);
        }

        @Override
        public long getItemId(int ii) {
            return ii;
        }

        @Override
        public View getView(final int pi, View view, ViewGroup viewGroup) {

            LayoutInflater inflator=(LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View vv=view;
            if(vv==null){
                vv=inflator.inflate(R.layout.customlayout,null);
            }

            final ImageView img=(ImageView)vv.findViewById(R.id.imge);
            final TextView tvvw=(TextView)vv.findViewById(R.id.tView);

            tvvw.setText(data_.get(pi).get("last").toString());

            if(data_.get(pi).get("ext").toString().equals("html")){
                img.setImageResource(R.drawable.html);
            }
            if(data_.get(pi).get("ext").toString().equals("css")){
                img.setImageResource(R.drawable.css);
            }
            if(data_.get(pi).get("ext").toString().equals("unknown")){
                img.setImageResource(R.drawable.unknown);
            }
            if(data_.get(pi).get("ext").toString().equals("folder")){
                img.setImageResource(R.drawable.folder);
            }
            if(data_.get(pi).get("ext").toString().equals("add")){
                img.setImageResource(R.drawable.addf);
            }
            return vv;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permiss,int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permiss,grantResults);
        if(requestCode==1001){
            listProjects(FileUtil.getExternalStorageDir()+"/Frencel_Webpages");
        }
    }

    public Boolean btt(){
        return bt;
    }

    public String pth(){
        return cPath;
    }

    public void  listProjects(String pt){

        pathC.setText(pt);
        if(!FileUtil.isExistFile(pt)){
            FileUtil.makeDir(pt);
        }
        FileUtil.listDir(pt,stlt);
        //String bgj=String.valueOf(mplst.size());
        //Toast.makeText(this, bgj, Toast.LENGTH_SHORT).show();
        /*try {

            FileUtil.listDir(FileUtil.getExternalStorageDir()+"/",stlt);
            pathC.setText(stlt.get(0));
            Toast.makeText(this, "passed", Toast.LENGTH_SHORT).show();
        }catch(RuntimeException e){
            Toast.makeText(this, "failed ="+e.getMessage(), Toast.LENGTH_SHORT).show();
        }*/


        mplst=new ArrayList<>();
        for(int i=0;i<stlt.size();i++){

            mpp=new HashMap<>();
            mainSt.add(Uri.parse(stlt.get(i)).getLastPathSegment());
            mpp.put("path",stlt.get(i));
            mpp.put("last",Uri.parse(stlt.get(i)).getLastPathSegment());
            if(FileUtil.isFile(stlt.get(i))){
                if(stlt.get(i).endsWith(".html")){
                    mpp.put("ext","html");
                }else if(stlt.get(i).endsWith(".css")){
                    mpp.put("ext","css");
                }else{
                    mpp.put("ext","unknown");
                }
            }else{
                mpp.put("ext","folder");
            }
            mplst.add(mpp);

        }



        lstV.setAdapter(new listviewAdoptor(mplst));

        //File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/");
        //if (dir.exists()) {
            //Log.d("path", dir.toString());
            //File list[] = dir.listFiles();
            //Toast.makeText(this, dir.getName(), Toast.LENGTH_SHORT).show();
            /*for (int i = 0; i < list.length; i++) {
                stlt.add(list[i].getName());
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(HtmlActivity.this, android.R.layout.simple_list_item_1, stlt);
            lstV.setAdapter(arrayAdapter);*/
        //}


        //FileUtil.listDir(FileUtil.getExternalStorageDir(),stlt);
        //stlt.add("dytbdvufdsb");
        //String bgj=String.valueOf(mplst.size());
        //Toast.makeText(this, bgj, Toast.LENGTH_SHORT).show();
        //lstV.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1,stlt));*/

    }

    public void open_File(String datt,String ptthh){
        llyt.setVisibility(View.VISIBLE);
        lstV.setVisibility(View.GONE);
        test=2;
        pathC.setVisibility(View.GONE);
        imgP.setVisibility(View.GONE);
        ltt.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
        fab1.setVisibility(View.VISIBLE);
        fab2.setVisibility(View.VISIBLE);
        fab3.setVisibility(View.VISIBLE);
        fab4.setVisibility(View.VISIBLE);
        fab5.setVisibility(View.VISIBLE);
        proj.setTextColor(0xffffffff);
        files.setTextColor(0xffffff00);
        outpt.setTextColor(0xffffffff);
        OpenFileFragment outputFragment =new OpenFileFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mantm, outputFragment).commit();
    }

    public void run_File(String datt,String ptthh){
        llyt.setVisibility(View.VISIBLE);
        lstV.setVisibility(View.GONE);
        test=4;
        pathC.setVisibility(View.GONE);
        imgP.setVisibility(View.GONE);
        ltt.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);
        fab1.setVisibility(View.GONE);
        fab2.setVisibility(View.GONE);
        fab3.setVisibility(View.GONE);
        fab4.setVisibility(View.GONE);
        fab5.setVisibility(View.GONE);
        proj.setTextColor(0xffffffff);
        files.setTextColor(0xffffffff);
        outpt.setTextColor(0xffffff00);
        OutputFragment outputFragment =new OutputFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.mantm, outputFragment).commit();
    }



}