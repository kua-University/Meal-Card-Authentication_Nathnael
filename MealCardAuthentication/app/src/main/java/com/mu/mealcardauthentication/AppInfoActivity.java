package com.mu.mealcardauthentication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AppInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        getWindow().setStatusBarColor(getResources().getColor(R.color.light_blue));

        Button closeButton = findViewById(R.id.btnClose);
        closeButton.setOnClickListener(v -> finish());

        Log.d("AppInfoActivity", "Activity created.");
    }
}