package com.android.research.researchproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;

/**
 * Created by Kislay on 11/26/2015.
 */
public class DummyHomeScreen extends Activity implements View.OnClickListener{

    private Toolbar mToolbar;
    Bundle bundle;
    Boolean isartist,skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dummyhome);

        try {
            bundle = getIntent().getBundleExtra("bundle");
        } catch (Exception e) {
            e.printStackTrace();
        }
        isartist = bundle.getBoolean("isartist");
        skip=bundle.getBoolean("skip");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        TextView back = (TextView) mToolbar.findViewById(R.id.back);
        TextView logout = (TextView) mToolbar.findViewById(R.id.logout);
        if(skip){
            logout.setVisibility(View.GONE);
        }
        TextView title = (TextView) findViewById(R.id.title);

        if (isartist){
           title.setText("Artist Home");
        }else{
            title.setText("User Home");
        }




        back.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        LoginManager.getInstance().logOut();
        finish();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.back:
                LoginManager.getInstance().logOut();
                finish();
                break;
            case R.id.logout:
                LoginManager.getInstance().logOut();
                Intent login = new Intent(DummyHomeScreen.this, MainActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                break;
        }
        }

}