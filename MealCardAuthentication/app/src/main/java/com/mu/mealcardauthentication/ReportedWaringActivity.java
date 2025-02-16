package com.mu.mealcardauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ReportedWaringActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reported_waring);
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Button BackButton = findViewById(R.id.btnBack);
        BackButton.setOnClickListener(v -> {
            Intent intent = new Intent(ReportedWaringActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}