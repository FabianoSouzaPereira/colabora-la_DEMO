package com.fabianospdev.android.colabora_la;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;

public class NotificationActivity extends AppCompatActivity {

  @Override
  protected void onCreate( Bundle savedInstanceState ) {
    super.onCreate( savedInstanceState );
    setContentView( R.layout.activity_notification );
    Toolbar toolbar = findViewById ( R.id.toolbar );
    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Notifições");
    init();
  }

  private void init() {
    BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_navigation );
    bottomNavigationView.setSelectedItemId( R.id.notifications);
    bottomNavigationView.setOnNavigationItemSelectedListener( new OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected( @NonNull MenuItem menuItem ) {

        switch (menuItem.getItemId()){

        case R.id.home:
          startActivity(new Intent(getApplicationContext(),MainActivity.class));
          overridePendingTransition( 0,0 );
          return true;

        case R.id.dashboard:
          startActivity(new Intent(getApplicationContext(),DashboardActivity.class ));
          overridePendingTransition( 0,0 );
          return true;

        case R.id.camera:
          startActivity(new Intent(getApplicationContext(), CameraActivity.class));
          overridePendingTransition( 0,0 );
          return true;

        case R.id.notifications:
          return true;
        }
        return false;
      }

    } );
  }
}