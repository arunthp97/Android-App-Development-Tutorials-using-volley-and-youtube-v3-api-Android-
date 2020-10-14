package com.developer.arun.androidappdevelopmenttutorails;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;


public class Main2Activity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private AdView adView;

    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Button b1, b2, b3, b4, b5;
    ExampleBroadcastReciever exampleBroadcastReciever = new ExampleBroadcastReciever();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        AnimationDrawable animationDrawable = (AnimationDrawable) drawerLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        YoYo.with(Techniques.FadeIn).playOn(drawerLayout);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, Beginner.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, Advanced.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, Simple.class));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, Online.class));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this, Complete.class));
            }
        });
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        setUpToolbar();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_beginner:
                        startActivity(new Intent(Main2Activity.this, Beginner.class));
                        break;
                    case R.id.nav_advanced:
                        startActivity(new Intent(Main2Activity.this, Advanced.class));
                        break;
                    case R.id.nav_simple:
                        startActivity(new Intent(Main2Activity.this, Simple.class));
                        break;
                    case R.id.nav_networking:
                        startActivity(new Intent(Main2Activity.this, Online.class));
                        break;
                    case R.id.nav_complete:
                        startActivity(new Intent(Main2Activity.this, Complete.class));
                        break;
                    case R.id.nav_feedback:
                        Toast.makeText(getApplicationContext(), "Feedback Forum", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Main2Activity.this, Email.class));
                        break;
                    case R.id.nav_share:
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        String sharebody = "Learn android app development from scratch. \n https://play.google.com/store/apps/details?id=com.developer.arun.androidappdevelopmenttutorails";
                        String sharesub = "Android App Development Tutorials";
                        intent.putExtra(Intent.EXTRA_TEXT, sharebody);
                        intent.putExtra(Intent.EXTRA_TITLE, sharesub);
                        startActivity(Intent.createChooser(intent, "Share using"));
                        break;
                    case R.id.nav_exit:
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(exampleBroadcastReciever, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(exampleBroadcastReciever);
    }

    private void setUpToolbar() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_exit_to_app_black_24dp);
        builder.setTitle("Exit");
        builder.setMessage("Are you sure you want to leave.");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


}
