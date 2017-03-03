package com.sodevan.hotshots.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sodevan.hotshots.Adapters.RecyclerView_Home;
import com.sodevan.hotshots.Databases.DatabaseHandler;
import com.sodevan.hotshots.Models.Item;
import com.sodevan.hotshots.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    DatabaseHandler mdb;
    Activity activity;
    Bitmap bp;
    String encoded_string="0";
    ImageButton screenshot;
    List<Item> list;
    RecyclerView recycler;
    RecyclerView_Home adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final View mView= getLayoutInflater().inflate(R.layout.dialogue,null);
        screenshot= (ImageButton) mView.findViewById(R.id.screenshot);
        list=new ArrayList<>();
        mdb = new DatabaseHandler(getApplicationContext());
        activity=HomeActivity.this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recycler= (RecyclerView) findViewById(R.id.recycler_view);
        adapter=new RecyclerView_Home(list);
        RecyclerView.LayoutManager mlayoutmanager=new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(mlayoutmanager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(adapter);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //myaction of writing new entry
                AlertDialog.Builder mbuilder=new AlertDialog.Builder(HomeActivity.this);
                final EditText box= (EditText) mView.findViewById(R.id.editText2);
                Button writetext= (Button) mView.findViewById(R.id.writetext);
                final EditText people= (EditText) mView.findViewById(R.id.people);
                screenshot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
// Show only images, no videos or anything else
                        encoded_string="0";
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
// Always show the chooser (if there are multiple options available)
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                    }
                });
                writetext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String text=box.getText().toString();
                        String timestamp=String.valueOf(System.currentTimeMillis());
                        String peeps=people.getText().toString();
                        mdb.open();
                        mdb.Write(text,peeps,encoded_string,timestamp);
                        mdb.close();

                    }
                });

                mbuilder.setView(mView);
                AlertDialog dialog=mbuilder.create();
                dialog.show();



            }
        });
        mdb.open();
        if(mdb.read()!=null) {
            String[] data = mdb.read().split("`");
            for (int i = 0; i < data.length; i++) {
                String[] Aitem = data[i].split("\t");
                Item item=new Item(Aitem[0],Aitem[1],Aitem[2],Aitem[3],Aitem[4]);
                list.add(item);
                adapter.notifyDataSetChanged();
            }
        }





        Log.d("TAG",list.size()+"");
        mdb.close();





    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST&&resultCode==RESULT_OK&&data!=null&&data.getData()!=null){
            Uri uri=data.getData();
            try {
                bp= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bp.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            screenshot.setImageBitmap(bp);



        }
    }
}
