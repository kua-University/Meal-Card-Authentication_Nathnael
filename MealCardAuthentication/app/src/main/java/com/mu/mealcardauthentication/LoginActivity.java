package com.mu.mealcardauthentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumberEditText;
    EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(getResources().getColor(R.color.v_light_blue));

        phoneNumberEditText = findViewById(R.id.phone_number);
        passwordEditText = findViewById(R.id.password);
        Button SigninButton = findViewById(R.id.btnSignin);
        SigninButton.setOnClickListener(v -> {
            validateInput(phoneNumberEditText.getText().toString().trim(),passwordEditText.getText().toString().trim());
            verifyLog();
        });
    }
    private void validateInput(String phoneNumber, String password) {
        if (phoneNumber.isEmpty()) {
            Toast.makeText(this, "Phone number cannot be Empty", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Password cannot be Empty", Toast.LENGTH_SHORT).show();
        }
    }

    public void verifyLog(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ApiConfiguration.LOGIN_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                this::verifyUser, error -> Toast.makeText(LoginActivity.this, "Please Check your Internet Connection", Toast.LENGTH_SHORT).show()){
            protected Map<String, String> getParams() {
                Map<String, String> paramVar = new HashMap<>();
                paramVar.put("phoneNo", phoneNumberEditText.getText().toString().trim());
                paramVar.put("Passkey", passwordEditText.getText().toString().trim());
                return paramVar;
            }
        };
        queue.add(stringRequest);
    }
    public void verifyUser(String response) {
        try {
            String statusVar = "",userVar = "",profilePictureVar = "";
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Login_Validation");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                statusVar = jsonObject2.getString("status");
                userVar = jsonObject2.getString("user");
                profilePictureVar = jsonObject2.getString("profile_picture");
            }
            Log.d("API Response",response );
            Log.d("verifyUser Response",statusVar );

            if (statusVar.equals("success")) {
                SharedPrefManager.getInstance(this).saveUserData(userVar, profilePictureVar);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "In Invalid User or Passkey", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}