package com.mu.mealcardauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LostItemActivity extends AppCompatActivity {

    private static final int QR_SCANNER_REQUEST = 100;
    EditText mekelleUniversityId;
    EditText academicYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_item);
        getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        String user = SharedPrefManager.getInstance(this).getUser();
        String image = SharedPrefManager.getInstance(this).getImage();

        TextView UserInfoTextView = findViewById(R.id.UserInfo);
        ImageView UserProfileImageView = findViewById(R.id.UserProfileImage);

        UserInfoTextView.setText(Objects.requireNonNullElse(user, "Name not available"));
        if (image != null) {
            Glide.with(this)
                    .load(ApiConfiguration.Storage_URL + image)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(UserProfileImageView);
        } else {
            UserProfileImageView.setImageResource(R.drawable.user);
        }

        mekelleUniversityId = findViewById(R.id.mekelleUniversityId);
        academicYear = findViewById(R.id.academicYear);

        ImageView MainButton = findViewById(R.id.btnHome);
        MainButton.setOnClickListener(v -> {
            Intent intent = new Intent(LostItemActivity.this, MainActivity.class);
            startActivity(intent);
        });

        ImageView LogoutButton = findViewById(R.id.btnLogout);
        LogoutButton.setOnClickListener(v -> {
            SharedPrefManager.getInstance(this).clearUserData();
            Intent intent = new Intent(LostItemActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        ImageView appInfoButton = findViewById(R.id.btnAppInfo);
        appInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(LostItemActivity.this, AppInfoActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnQR).setOnClickListener(v -> {
            Intent intent = new Intent(this, QRScannerActivity.class);
            startActivityForResult(intent, QR_SCANNER_REQUEST);
        });

        Button submitButton = findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(v -> Request());
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
    public void Request(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ApiConfiguration.LOST_ITEM_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                this::postRequest, error -> Toast.makeText(LostItemActivity.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show()){
            protected Map<String, String> getParams() {
                Map<String, String> paramVar = new HashMap<>();
                paramVar.put("id", mekelleUniversityId.getText().toString().trim());
                paramVar.put("academicYear", academicYear.getText().toString().trim());
                return paramVar;
            }
        };
        queue.add(stringRequest);
    }
    public void postRequest(String response) {
        try {
            String statusVar = "";
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("LostItem_PostRequest");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                statusVar = jsonObject2.getString("status");
            }
            Log.d("API Response",response );
            Log.d("postRequest Response",statusVar );

            switch (statusVar) {
                case "success":
                    Toast.makeText(LostItemActivity.this, "Lost Id reported successfully.", Toast.LENGTH_SHORT).show();
                    break;
                case "exists":
                    Toast.makeText(LostItemActivity.this, "The ID already reported as lost.", Toast.LENGTH_SHORT).show();
                    break;
                case "402_error":
                    Toast.makeText(LostItemActivity.this, "Database error Occurred while report Lost.", Toast.LENGTH_SHORT).show();
                    break;
                case "404_error":
                    Toast.makeText(LostItemActivity.this, "The ID you provided is unregistered or invalid.", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(LostItemActivity.this, "Failed to report lost Id. Please Try Again", Toast.LENGTH_SHORT).show();
                    break;
            }

        } catch (JSONException e) {
            Toast.makeText(LostItemActivity.this, "An Error Occurred while report Lost Id. Please Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}
