package com.example.zeus.jsonrealm;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Button show =(Button)findViewById(R.id.show);
        Button download=(Button)findViewById(R.id.download);
        Button clear=(Button)findViewById(R.id.clear);
        show.setOnClickListener(this);
        download.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        EditText gettext = (EditText) findViewById(R.id.gettext);
        TextView city = (TextView) findViewById(R.id.city);
        TextView house = (TextView) findViewById(R.id.house);
        TextView years = (TextView) findViewById(R.id.years);
        ImageView viewimage=(ImageView) findViewById(R.id.viewimage);
        if(id==R.id.download)
        {
            try {
                InputStream stream=getAssets().open("sample.json");
                Realm.init(context);
                Realm realm=Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.createAllFromJson(KingInfo.class,stream);
                realm.commitTransaction();
                for(int i=1;i<20;i++){
                    Toast.makeText(getApplicationContext(),+i+"completed",Toast.LENGTH_SHORT).show();
                    KingInfo info = realm.where(KingInfo.class).equalTo("id", i).findFirst();
                    realm.beginTransaction();
                    String imageURL=info.getUrl();
                    InputStream in = new java.net.URL(imageURL).openStream();
                    Bitmap bimage = BitmapFactory.decodeStream(in);
                    ByteArrayOutputStream istream = new ByteArrayOutputStream();
                    bimage.compress(Bitmap.CompressFormat.PNG, 100, istream);
                    byte[] byteArray = istream.toByteArray();
                    info.setBytearr(byteArray);
                    realm.commitTransaction();
                }
                Toast.makeText(getApplicationContext(),"Completed",Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (id==R.id.show) {

            String text = gettext.getText().toString();
            Realm.init(context);
            Realm realm=Realm.getDefaultInstance();
            KingInfo info = realm.where(KingInfo.class).equalTo("name", text).findAll().get(0);
            if (info != null) {
                realm.beginTransaction();
                String gotcity = info.getCity();
                String gothouse = info.getHouse();
                String gotyears = info.getYears();
                byte[] imagebyte=info.getBytearr();
                realm.commitTransaction();
                city.setText(gotcity);
                house.setText(gothouse);
                years.setText(gotyears);
                Bitmap bitmap = BitmapFactory.decodeByteArray(imagebyte, 0, imagebyte.length);
                viewimage.setImageBitmap(bitmap);
            }
            else
            {
                city.setText("CITY");
                house.setText("HOUSE");
                years.setText("YEARS");
                gettext.setText("");
                Toast.makeText(getApplicationContext(),"Data Not Found",Toast.LENGTH_SHORT).show();
            }
        }
        else if(id==R.id.clear)
        {
            city.setText("CITY");
            house.setText("HOUSE");
            years.setText("YEARS");
            gettext.setText("");
        }

    }
}
