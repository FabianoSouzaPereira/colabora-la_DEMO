package com.fabianospdev.android.colabora_la;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dasboard");
        init();
    }

    private void init() {
        BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_navigation );
        bottomNavigationView.setSelectedItemId( R.id.dashboard );
        bottomNavigationView.setOnNavigationItemSelectedListener( new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( @NonNull MenuItem menuItem ) {

                int itemId = menuItem.getItemId ( );
                if ( itemId == R.id.dashboard ) {
                    return true;
                } else if ( itemId == R.id.home ) {
                    startActivity ( new Intent ( getApplicationContext ( ) , MainActivity.class ) );
                    overridePendingTransition ( 0 , 0 );
                    return true;
                } else if ( itemId == R.id.camera ) {
                    startActivity ( new Intent ( getApplicationContext ( ) , CameraActivity.class ) );
                    overridePendingTransition ( 0 , 0 );
                    return true;
                } else if ( itemId == R.id.notifications ) {
                    startActivity ( new Intent ( getApplicationContext ( ) , NotificationActivity.class ) );
                    overridePendingTransition ( 0 , 0 );
                    return true;
                }
                return false;
            }

        } );
    }
}