package com.mu.mealcardauthentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LostIdActivity extends AppCompatActivity {

    private static final int QR_SCANNER_REQUEST = 100;
    TextView studentName;
    TextView studentId;
    ImageView studentImage;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_id);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        String user = SharedPrefManager.getInstance(this).getUser();
        String image = SharedPrefManager.getInstance(this).getImage();

        TextView UserInfoTextView = findViewById(R.id.UserInfo);
        ImageView UserProfileImageView = findViewById(R.id.UserProfileImage);

        UserInfoTextView.setText(Objects.requireNonNullElse("Hi, "+user, "Name not available"));
        if (image != null) {
            Glide.with(this)
                    .load(ApiConfiguration.Storage_URL + image)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(UserProfileImageView);
        } else {
            UserProfileImageView.setImageResource(R.drawable.user);
        }

        container = findViewById(R.id.container);
        fetchData();

        ImageView MainButton = findViewById(R.id.btnHome);
        MainButton.setOnClickListener(v -> {
            Intent intent = new Intent(LostIdActivity.this, MainActivity.class);
            startActivity(intent);
        });
        ImageView appInfoButton = findViewById(R.id.btnAppInfo);
        appInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(LostIdActivity.this, AppInfoActivity.class);
            startActivity(intent);
        });

        ImageView lostItemButton = findViewById(R.id.btnLostItem);
        lostItemButton.setOnClickListener(v -> {
            Intent intent = new Intent(LostIdActivity.this, LostItemActivity.class);
            startActivity(intent);
        });

        ImageView LogoutButton = findViewById(R.id.btnLogout);
        LogoutButton.setOnClickListener(v -> {
            SharedPrefManager.getInstance(this).clearUserData();
            Intent intent = new Intent(LostIdActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        TextView upcomingTasksButton = findViewById(R.id.LostIdTasks);
        upcomingTasksButton.setOnClickListener(v -> {
            Intent intent = new Intent(LostIdActivity.this, LostIdActivity.class);
            startActivity(intent);
        });

        TextView UpcomingTasksButton = findViewById(R.id.UpcomingTasks);
        UpcomingTasksButton.setOnClickListener(v -> {
            Intent intent = new Intent(LostIdActivity.this, MainActivity.class);
            startActivity(intent);
        });

        TextView remarkUserTasksButton = findViewById(R.id.RemarkUserTasks);
        remarkUserTasksButton.setOnClickListener(v -> {
            Intent intent = new Intent(LostIdActivity.this, RemarkUserActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnQR).setOnClickListener(v -> {
            Intent intent = new Intent(this, QRScannerActivity.class);
            startActivityForResult(intent, QR_SCANNER_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QR_SCANNER_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                String scanResult = data.getStringExtra("SCAN_RESULT");
                assert scanResult != null;
                Log.e("Scanned QR", scanResult);

                MealCardAuthenticate mealCardAuthenticate = new MealCardAuthenticate(this);
                mealCardAuthenticate.authenticateMealCard(scanResult);
            }
        }
    }

    public void fetchData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ApiConfiguration.LostID_URL;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                this::parseJson, error -> Toast.makeText(LostIdActivity.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);
    }
    public void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("LostID_Data");

            for (int i = 0; i < jsonArray.length(); i++) {
                String lost_idVar, nameVar, profile_pictureVar;
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                lost_idVar = jsonObject2.getString("lost_id");
                nameVar = jsonObject2.getString("name");
                profile_pictureVar = jsonObject2.getString("profile_picture");

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.lost_id_card, container, false);

                studentName = relativeLayout.findViewById(R.id.studentName);
                studentId = relativeLayout.findViewById(R.id.studentId);
                studentImage = relativeLayout.findViewById(R.id.studentImage);

                studentName.setText(nameVar);
                studentId.setText(lost_idVar);

                Glide.with(this)
                        .load(ApiConfiguration.Storage_URL+profile_pictureVar)
                        .placeholder(R.drawable.user)
                        .error(R.drawable.user)
                        .into(studentImage);

                container.addView(relativeLayout);
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Failed to parse JSON data", Toast.LENGTH_SHORT).show();
        }
    }
}
