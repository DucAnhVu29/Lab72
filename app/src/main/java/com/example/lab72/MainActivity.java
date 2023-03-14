package com.example.lab72;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final int PHONE_PERMISSION_CODE = 1;
    ImageView ivBrowser, ivMessage, ivPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivBrowser = findViewById(R.id.ivBrowser);
        ivMessage = findViewById(R.id.ivMessage);
        ivPhone = findViewById(R.id.ivPhone);

        ivBrowser.setOnClickListener(view -> {
            Intent intent =  new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.google.com/"));
            startActivity(intent);
        });

        ivMessage.setOnClickListener(v -> {
            Intent intent2 = new Intent();
            intent2.setAction(Intent.ACTION_SENDTO);
            intent2.putExtra("sms_body", "Hi there");
            intent2.setData(Uri.parse("sms:0123456789"));
            startActivity(intent2);
        });

        ivPhone.setOnClickListener(v -> checkPhonePermissionAndCall());
    }

    private void checkPhonePermissionAndCall() {
        //This app wont work with Android < 6
        if(Build.VERSION.SDK_INT < Build .VERSION_CODES.M) return;
        //If it's at Android 6 or above:
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, PHONE_PERMISSION_CODE);
        }else{
            makePhoneCall();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == PHONE_PERMISSION_CODE){
            if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ){
                makePhoneCall();
            }else{
                Toast.makeText(this, "Phone calling denied. Please grant permission!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makePhoneCall() {
        Intent intent3 = new Intent();
        intent3.setAction(Intent.ACTION_CALL);
        intent3.setData(Uri.parse("tel:0123456789"));
        startActivity(intent3);
    }
}