package com.mu.mealcardauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WaringActivity extends AppCompatActivity {
    private String studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waring);

        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        Intent intent = getIntent();
        studentId = intent.getStringExtra("Id");
        String studentName = intent.getStringExtra("Name");
        String studentImage = intent.getStringExtra("Images");

        TextView studentNameTextView = findViewById(R.id.studentName);
        TextView studentIdTextView = findViewById(R.id.StudentID);
        ImageView studentImageView = findViewById(R.id.studentImage);

        studentNameTextView.setText(Objects.requireNonNullElse(studentName, "Name not available"));
        studentIdTextView.setText(Objects.requireNonNullElse(studentId, "ID not available"));

        if (studentImage != null) {
            Glide.with(this)
                    .load(ApiConfiguration.Storage_URL + studentImage)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(studentImageView);
        } else {
            studentImageView.setImageResource(R.drawable.user);
        }

        Button backButton = findViewById(R.id.btnBack);
        backButton.setOnClickListener(v -> finish());

        Button reportButton = findViewById(R.id.btnReport);
        reportButton.setOnClickListener(v -> reportStudent());
    }

    private void reportStudent() {
        if (studentId == null || studentId.trim().isEmpty()) {
            Toast.makeText(WaringActivity.this, "Invalid student ID", Toast.LENGTH_SHORT).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ApiConfiguration.REPORT_URL;

        Log.d("Report Student", "Student ID: " + studentId.trim());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                this::handleReportResponse, error -> {
            Toast.makeText(WaringActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            Log.e("Network Error", error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("id", studentId.trim());
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void handleReportResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Report_PostRequest");

            String status = "";
            String message = "";

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                status = jsonObject2.getString("status");
            }

            Log.d("API Response", response);
            Log.d("Report Response", "Status: " + status);

            switch (status) {
                case "success":
                    finish();
                    Toast.makeText(WaringActivity.this, "Report submitted successfully.", Toast.LENGTH_LONG).show();
                    break;
                case "exists":
                    Toast.makeText(WaringActivity.this, "The ID has already been reported.", Toast.LENGTH_LONG).show();
                    break;
                case "402_error":
                    Toast.makeText(WaringActivity.this, "Database error occurred while reporting.", Toast.LENGTH_LONG).show();
                    break;
                case "404_error":
                    Toast.makeText(WaringActivity.this, "The ID is unregistered or invalid.", Toast.LENGTH_LONG).show();
                    break;
                default:
                    Toast.makeText(WaringActivity.this, "Failed to submit report. Please try again.", Toast.LENGTH_LONG).show();
                    break;
            }
        } catch (JSONException e) {
            Toast.makeText(WaringActivity.this, "An error occurred while processing the response.", Toast.LENGTH_LONG).show();
            Log.e("JSON Error", e.toString());
        }
    }
}