package com.mastek.appengage;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.mastek.appengage.campaigns.CampaignsFragment;
import com.mastek.appengage.cities.CitiesFragment;
import com.mastek.appengage.crash.CrashPieChartFragment;
import com.mastek.appengage.dashboard.CardDashboardFragment;
import com.mastek.appengage.device.DeviceFragment;
import com.mastek.appengage.events.EventSummaryFragment;
import com.mastek.appengage.insights.InsightFragment;
import com.mastek.appengage.login.LoginActivity;
import com.mastek.appengage.login.LoginResponseModel;
import com.mastek.appengage.screen_analytics.ScreenAnalyticsFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final int REQ_STORAGE = 555;
    Toolbar toolbar;
    TextView loginUserName,login;
    LoginResponseModel mLoginUser;
    WebServices webServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        loginUserName = (TextView) header.findViewById(R.id.loginUserName);
        login=(TextView) header.findViewById(R.id.login) ;

        mLoginUser=App.getInstance().getLoginUser();
        if(mLoginUser!=null){
            loginUserName.setText(mLoginUser.getName());
            login.setText(mLoginUser.getLogin());
        }

        navigationView.setNavigationItemSelectedListener(this);

        loadFragment(R.id.frmContainer,new CardDashboardFragment(),null);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            toolbar.setTitle("Dashboard");
            loadFragment(R.id.frmContainer,new CardDashboardFragment(),null);

        } else if (id == R.id.nav_insight) {
            toolbar.setTitle("Insights");
            loadFragment(R.id.frmContainer,new InsightFragment(),null);

            /*final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.setContentView(R.layout.layout_nav_insights);
            dialog.setCancelable(false);
            RadioGroup radioGroup= (RadioGroup) dialog.findViewById(R.id.radioGrpDevice);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId)
                    {
                        case R.id.btnSession:
                            Log.e( "onCheckedChanged: ","btnSession" );
                            dialog.dismiss();
                            break;
                        case R.id.btnUsers:
                            Log.e( "onCheckedChanged: ","btnUsers" );
                            dialog.dismiss();
                            break;
                    }
                }
            });
            dialog.show();*/


        } else if (id == R.id.nav_device) {
            toolbar.setTitle("Device");
            loadFragment(R.id.frmContainer,new DeviceFragment(),null);
            /*final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.setContentView(R.layout.layout_nav_devices);
            dialog.setCancelable(false);
            RadioGroup radioGroup= (RadioGroup) dialog.findViewById(R.id.radioGrpDevice);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId)
                    {
                        case R.id.btnManufacturer:
                            Log.e( "onCheckedChanged: ","btnManufacturer" );
                            dialog.dismiss();
                            break;
                        case R.id.btnModel:
                            Log.e( "onCheckedChanged: ","btnModel" );
                            dialog.dismiss();
                            break;
                        case R.id.btnType:
                            Log.e( "onCheckedChanged: ","btnType" );
                            dialog.dismiss();
                            break;
                        case R.id.btnPlatform:
                            Log.e( "onCheckedChanged: ","btnPlatform" );
                            dialog.dismiss();
                            break;
                        case R.id.btnOSVersion:
                            Log.e( "onCheckedChanged: ","btnOSVersion" );
                            dialog.dismiss();
                            break;
                        case R.id.btnAppVersions:
                            Log.e( "onCheckedChanged: ","btnAppVersions" );
                            dialog.dismiss();
                            break;
                        case R.id.btnResolutions:
                            Log.e( "onCheckedChanged: ","btnResolutions" );
                            dialog.dismiss();
                            break;
                    }
                }
            });
            dialog.show();*/

        }else if (id == R.id.nav_cities) {
            toolbar.setTitle("Cities");
            if(checkPermissions()){
                loadFragment(R.id.frmContainer,new CitiesFragment(),null);
            }else {
                requestPermission();
            }

        }else if (id == R.id.nav_events) {
            toolbar.setTitle("Events");
            loadFragment(R.id.frmContainer,new EventSummaryFragment(),null);
            /*final Dialog dialog = new Dialog(HomeActivity.this);
            dialog.setContentView(R.layout.layout_nav_events);
            dialog.setCancelable(false);
            RadioGroup radioGroup= (RadioGroup) dialog.findViewById(R.id.radioGrpDevice);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch(checkedId)
                    {
                        case R.id.btnSummary:
                            Log.e( "onCheckedChanged: ","btnSummary" );
                            dialog.dismiss();
                            break;
                        case R.id.btnCompare:
                            Log.e( "onCheckedChanged: ","btnCompare" );
                            dialog.dismiss();
                            break;
                    }
                }
            });
            dialog.show();*/


        }else if (id == R.id.nav_cohort) {
            toolbar.setTitle("Cohorts");
            loadFragment(R.id.frmContainer,new CohortsFragment(),null);

        }else if (id == R.id.nav_crash_report) {
            toolbar.setTitle("Crashes");
            loadFragment(R.id.frmContainer,new CrashPieChartFragment(),null);
        }else if (id == R.id.nav_screen_analytics) {
            toolbar.setTitle("Screen Analytics");
            loadFragment(R.id.frmContainer,new ScreenAnalyticsFragment(),null);
        }/*else if (id == R.id.nav_campains) {
            loadFragment(R.id.frmContainer,new CampaignsFragment(),null);

        }else if (id == R.id.nav_user_data) {

        }*/else if (id == R.id.nav_user_signOut) {
            App.getInstance().clearLoginUser();
            MA.removeCustomAppUser();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void loadFragment(int containerId, Fragment fragment, String tag) {

        Fragment contFragment = getSupportFragmentManager().findFragmentById(containerId);

        if (contFragment == null || !(contFragment.getClass()
                == (fragment.getClass()))) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction()
                    .replace(containerId, fragment);
            if (tag != null) {
                fragmentTransaction.addToBackStack(tag);
            }
            fragmentTransaction.commit();
        }
    }


    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQ_STORAGE);
    }

    public boolean checkPermissions() {
        int read = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int write = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (read == PackageManager.PERMISSION_GRANTED && write == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }


    private boolean isStoragePermissionGranted = false;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode == REQ_STORAGE){
            boolean checkReadPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
            boolean checkWritePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
            if (checkReadPermission && checkWritePermission) {
                isStoragePermissionGranted = true;
                //loadFragment(R.id.frmContainer,new CitiesFragment(),null);
            }else {

                if (!ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                    Toast.makeText(HomeActivity.this,"Please Grant Storage Permission",Toast.LENGTH_LONG).show();
                    try {
                        showMessageOKCancel("Go to Settings to Grant the Permission", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String packageName = getPackageName();
                                Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + packageName));
                                startActivity(intent);
                            }
                        });
                        //Open the specific App Info page:
                    } catch (ActivityNotFoundException e) {
                        //e.printStackTrace();
                        Log.e("inside", "catch");
                        //Open the generic Apps page:
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                        startActivity(intent);

                    }
                }
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(HomeActivity.this)
                .setMessage(message)
                .setPositiveButton("Settings", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (isStoragePermissionGranted) {
            loadFragment(R.id.frmContainer,new CitiesFragment(),null);
            isStoragePermissionGranted = false;
        }

    }
}
