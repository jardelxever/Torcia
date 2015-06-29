package com.example.lmasiello.torcia;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.*;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends ActionBarActivity {
    android.hardware.Camera camera;
    Toast t;
    boolean FlashOn=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if( this.getPackageManager().hasSystemFeature( PackageManager.FEATURE_CAMERA_FLASH )){
            t=new Toast(this);
            t.makeText(this, "FLASH SUPPORTATO", Toast.LENGTH_LONG).show();
        }
        else{
            t=new Toast(this);
            t.makeText(this, "FLASH NON SUPPORTATO", Toast.LENGTH_LONG).show();
        }
        ImageButton imgTasto = (ImageButton) findViewById(R.id.imgTastoAccensione);
        imgTasto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Turn_On_Off_Torch();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    private void Turn_On_Off_Torch (){
        if (FlashOn == false){
            //CameraManager cameramng = (CameraManager) getSystemService(this.CAMERA_SERVICE);
            camera = android.hardware.Camera.open();
            android.hardware.Camera.Parameters p = camera.getParameters();
            p.setFlashMode(android.hardware.Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(p);
            camera.startPreview();
            FlashOn = true;
        }
        else{
            camera.release();
        }
    }
    protected void onPause(){
        if( camera != null ){
            camera.release();
            camera = null;
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        camera = android.hardware.Camera.open();
        super.onResume();
    }
}
