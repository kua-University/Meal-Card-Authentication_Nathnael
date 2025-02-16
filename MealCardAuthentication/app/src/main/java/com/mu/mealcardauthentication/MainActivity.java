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
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final int QR_SCANNER_REQUEST = 100;
    TextView foodTitle;
    TextView foodDate;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        container = findViewById(R.id.containerforfood);
        fetchData();

        ImageView appInfoButton = findViewById(R.id.btnAppInfo);
        appInfoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AppInfoActivity.class);
            startActivity(intent);
        });

        ImageView lostItemButton = findViewById(R.id.btnLostItem);
        lostItemButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LostItemActivity.class);
            startActivity(intent);
        });

        ImageView LogoutButton = findViewById(R.id.btnLogout);
        LogoutButton.setOnClickListener(v -> {
            SharedPrefManager.getInstance(this).clearUserData();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        TextView upcomingTasksButton = findViewById(R.id.LostIdTasks);
        upcomingTasksButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, LostIdActivity.class);
            startActivity(intent);
        });

        TextView remarkUserTasksButton = findViewById(R.id.RemarkUserTasks);
        remarkUserTasksButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, RemarkUserActivity.class);
            startActivity(intent);
        });
//
//        mealViewModel.getBreakfast().observe(this, meal -> updateCard(R.id.BreakFastCard, meal));
//        mealViewModel.getLunch().observe(this, meal -> updateCard(R.id.LunchCard, meal));
//        mealViewModel.getDinner().observe(this, meal -> updateCard(R.id.DinnerCard, meal));

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
                Toast.makeText(this, "Scanned: ", Toast.LENGTH_LONG).show();
            }
        }
    } public void fetchData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ApiConfiguration.FOOD_MENU_URL;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                this::parseJson, error -> Toast.makeText(MainActivity.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show());

        queue.add(stringRequest);
    }
    public void parseJson(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Food_Menu");

            for (int i = 0; i < jsonArray.length(); i++) {
                String typeVar, food_nameVar;
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);

                typeVar = jsonObject2.getString("type");
                food_nameVar = jsonObject2.getString("food_name");

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.food_card, container, false);

                foodTitle = relativeLayout.findViewById(R.id.foodTitle);
                foodDate = relativeLayout.findViewById(R.id.foodDate);

                foodDate.setText(typeVar);
                foodTitle.setText(food_nameVar);

                container.addView(relativeLayout);
            }
        } catch (JSONException e) {
            Toast.makeText(this, "Failed to parse JSON data", Toast.LENGTH_SHORT).show();
        }
    }
}
