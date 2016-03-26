package com.example.klayton.krishighor_imgae_test_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Second_activity extends AppCompatActivity {
    TextView name,price,category,location,contact;

    String n_name,n_price,n_category,n_location,n_contact,n_img_loc;
    ImageView imm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity);

        // Create default options which will be used for every
        //  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config); // Do it on Application start

   /*     Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */


        name = (TextView)findViewById(R.id.nname);
        price = (TextView)findViewById(R.id.n_pricey);
        category = (TextView)findViewById(R.id.n_category);
        location = (TextView)findViewById(R.id.n_location);
        contact = (TextView)findViewById(R.id.n_contact);
        imm = (ImageView)findViewById(R.id.imageView2);



        Intent i = getIntent();

        n_name = i.getStringExtra("name");
        n_price = i.getStringExtra("price");
        n_category = i.getStringExtra("category");
        n_location = i.getStringExtra("location");
        n_contact = i.getStringExtra("contact");
        n_img_loc = i.getStringExtra("img_loc");


        // Then later, when you want to display image
        ImageLoader.getInstance().displayImage(n_img_loc, imm); // Default options will be used
        name.setText(n_name);
        price.setText(n_price);
        category.setText(n_category);
        location.setText(n_location);
        contact.setText(n_contact);





    }

}
