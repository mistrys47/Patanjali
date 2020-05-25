package com.example.patanjali;

import android.Manifest;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    FrameLayout f1;
    Integer Storagepermession=1;
    String barcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkpermissionstat();
        f1 = (FrameLayout) findViewById(R.id.fl1);

        //Toast.makeText(getApplicationContext(),"in main",Toast.LENGTH_LONG).show();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                barcode= null;
                //Toast.makeText(getApplicationContext(),"in if",Toast.LENGTH_LONG).show();
            } else {
                barcode= extras.getString("barcode");

            }
        } else {
            //Toast.makeText(getApplicationContext(),"in else",Toast.LENGTH_LONG).show();
            barcode= (String) savedInstanceState.getSerializable("barcode");
        }
        FragmentManager fragmentManager=getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl1,(Fragment) new stock()).addToBackStack(null).commit();
        if(barcode!=null){
            try{
            Toast.makeText(getApplicationContext(),barcode,Toast.LENGTH_LONG).show();
            add_details x = new add_details();
            Bundle b = new Bundle();
            b.putString("barcode",barcode);
            x.setArguments(b);
            f1.removeAllViews();
            FragmentManager fragmentManager1=getSupportFragmentManager();
            fragmentManager1.beginTransaction().replace(R.id.fl1, new add_details()).addToBackStack(null).commit();}
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),e+"",Toast.LENGTH_LONG).show();
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.fl1);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;
        //Toast.makeText(getApplicationContext(),"here1",Toast.LENGTH_LONG).show();
        int id = item.getItemId();
        if (id == R.id.scan) {
            Intent intent = new Intent(this,scan.class);
            startActivity(intent);
        } else if (id == R.id.stock) {

            f1.removeAllViews();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,(Fragment) new stock()).addToBackStack(null).commit();
        }
        else if (id == R.id.sold) {
            f1.removeAllViews();
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fl1,(Fragment) new sold()).addToBackStack(null).commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean checkpermissionstat()
    {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==Storagepermession)
        {
            return true;
        }
        else
        {
            requestperm();
            return false;
        }
    }
    public boolean requestperm()
    {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},Storagepermession);
        return true;
    }
    public String getbarcode(){
        return barcode;
    }
}
