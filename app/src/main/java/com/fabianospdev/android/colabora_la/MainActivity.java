package com.fabianospdev.android.colabora_la;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fabianospdev.android.colabora_la.adapters.RecyclerAdapter;
import com.fabianospdev.android.colabora_la.model.Feed;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.functions.FirebaseFunctions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

//import com.fabianospdev.android.colabora_la.model.PostFeed;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_CODE_PERMISSIONS = 1001;
    Context context = MainActivity.this;
    public static final int  Permission_All = 1;
    public static final int PERMISSION_CODE = 3;
    public static final String[] Permissions = new String[]{
        Manifest.permission.CAMERA,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION};
    //Firebase
    public static FirebaseUser UI;
    public FirebaseAuth mAuth;
    public FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFunctions mFunctions;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference novaref0 = null;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private final StorageReference storeRef0 = null;
    public static String tag = "0";

    FloatingActionButton fabcamera;
    FloatingActionButton fabwrite;
    FloatingActionButton fab;

    public static String TAG = "TAG -> ";
    static int id = -1;
    private final boolean mItemPressed = false;
    private final boolean itemReturned = false;

    BottomSheetDialog mBottomDialogNotificationAction;
    ArrayList < Feed >feedList = new ArrayList <> ();
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView feedsRecyclerView = null;
    RelativeLayout relativeLayout;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        Toolbar toolbar = findViewById ( R.id.toolbar );
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Novidades");

        init();
        startingFirebase();
        listFeeds();

        FloatingActionButton fab = findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View view ) {
                Snackbar.make ( view , "Replace with your own action" , Snackbar.LENGTH_LONG ).setAction ( "Action" , null ).show ( );
            }
        } );
    }

    @Override
    protected void onStart ( ) {
        super.onStart ( );
    }

    @Override
    protected void onResume ( ) {
        super.onResume ( );

        if (!hasPhonePermissions( this, Permissions )) {
            ActivityCompat.requestPermissions( this,Permissions,Permission_All );
        }
    }


    private static boolean hasPhonePermissions( Context context, String... permissions) {
        if(context != null && permissions != null){
            for(String permission: permissions){
                if(ActivityCompat.checkSelfPermission( context, permission ) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_CODE){
            if(grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText( MainActivity.this, "Permission allowed" , Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText( MainActivity.this, "Permission denied" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        BottomNavigationView bottomNavigationView = findViewById( R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId( R.id.home );
        bottomNavigationView.setOnNavigationItemSelectedListener( new BottomNavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected( @NonNull MenuItem menuItem ) {

                try {
                    int itemId = menuItem.getItemId ( );
                    if ( itemId == R.id.home ) {
                        return true;
                    } else if ( itemId == R.id.dashboard ) {
                        startActivity ( new Intent ( getApplicationContext ( ) , DashboardActivity.class ) );
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
                } catch ( Exception e ) {
                    e.printStackTrace ( );
                    Log.d (TAG, "" + e );
                }
                return false;
            }

        } );
        relativeLayout = findViewById( R.id.container_main );
        feedsRecyclerView = findViewById( R.id.feedsRecyclerview );
        feedsRecyclerView.setHasFixedSize(true);
    }

    /* Load devices list from DB */
    void listFeeds() {
        List < Feed > feeds = new ArrayList <> ();
        novaref0 = databaseReference.child("news");
        Query query = novaref0.orderByChild ("createdAt");
        query.addValueEventListener ( new ValueEventListener ( ) {
            @Override
            public void onDataChange ( @NonNull DataSnapshot snapshot ) {
                feedList.clear();
                for( DataSnapshot ds:snapshot.getChildren ()){
                    Feed feed = ds.getValue ( Feed.class );
                    feedList.add ( feed );
                }
                for ( Feed f : feedList) {
                    feeds.add(f);
                }


                layoutManager = new LinearLayoutManager (context);
                feedsRecyclerView.setLayoutManager(layoutManager);
                mAdapter = new RecyclerAdapter (feeds);
                mAdapter.setHasStableIds(true);

                /* Open Bottom sheet with item selected form recyclerview */
                mAdapter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick( android.view.View v) {

                        try {
                            id = feeds.get(feedsRecyclerView.getChildAdapterPosition(v)).getId();
                            if (mItemPressed) {
                                // Multi-item swipes not handled
                                return;
                            }
                            //                              Feed feed= db.selectDevice(id);
                            //                              showDialogNotificationAction(feed,id);
                        } catch ( Exception e ) {
                            e.printStackTrace ( );
                        }
                    }
                });

                /* Open Editable Activity, sending Extra info to Activity about its your new state*/
                mAdapter.setOnLongClickListener(new android.view.View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick( android.view.View v) {
                        try {
//                            id = feeds.get(feedsRecyclerView.getChildAdapterPosition(v)).getId();
//                            Intent i= new Intent (context, PostFeed.class);
//                            String edit= "Editar";
//                            i.putExtra("Title", edit);
//                            i.putExtra("id", id);
//                            startActivity(i);
                            //  finish();
                            return false;
                        } catch ( Exception e ) {
                            e.printStackTrace ( );
                        }
                        return false;
                    }
                });


                mAdapter.notifyDataSetChanged ();
                feedsRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onCancelled ( @NonNull DatabaseError error ) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        } );

    }

    @Override
    public boolean onCreateOptionsMenu ( Menu menu ) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ( ).inflate ( R.menu.menu , menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected ( MenuItem item ) {
        int id = item.getItemId ( );
        if ( id == R.id.action_settings) {
            startActivity ( new Intent ( getApplicationContext ( ) , SettingsActivity.class) );
            overridePendingTransition ( 0 , 0 );
        }

        return super.onOptionsItemSelected ( item );
    }

    private void startingFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);  //inicializa  o SDK credenciais padrão do aplicativo do Google
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.keepSynced(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}