package com.mu.mealcardauthentication;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MealCardAuthenticate {
    private final Context context;
    private final RequestQueue queue;

    public MealCardAuthenticate(Context context) {
        this.context = context;
        this.queue = Volley.newRequestQueue(context);
    }

    public void authenticateMealCard(String studentId) {
        String url = ApiConfiguration.MEAL_CARD_AUTHENTICATE_URL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                this::handleResponse, error -> {
            ((Activity) context).runOnUiThread(() -> {
                Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_LONG).show();
            });
            Log.e("API Error", error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Id", studentId);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void handleResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("Meal_Card_Authenticate");
            JSONObject result = jsonArray.getJSONObject(0);

            String status = result.getString("status");
            String studentId = result.getString("Id");
            String studentName = result.getString("Name");
            String studentImage = result.getString("Images");

            Log.d("API Response", response);
            Log.d("Status", status);
            Log.d("Student ID", studentId);
            Log.d("Student Name", studentName);
            Log.d("Student Image", studentImage);

            switch (status) {
                case "success":
                    ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "Meal card authenticated successfully.", Toast.LENGTH_LONG).show();
                    });
                    break;
                case "101_failed":
                    ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "The student is not a cafe user.", Toast.LENGTH_LONG).show();
                    });
                    break;
                case "102_failed":
                    ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "Cafe is not open at this time.", Toast.LENGTH_LONG).show();
                    });
                    break;
                case "103_failed":
                    ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "The ID does not exist in the database.", Toast.LENGTH_LONG).show();
                    });
                    break;
                case "exists":
                    ((Activity) context).runOnUiThread(() -> {
                        Toast.makeText(context, "Student already has authenticated for this meal.", Toast.LENGTH_LONG).show();
                    });
                    Intent intent_one = new Intent(context, WaringActivity.class);
                    intent_one.putExtra("Id", studentId);
                    intent_one.putExtra("Name", studentName);
                    intent_one.putExtra("Images", studentImage);
                    context.startActivity(intent_one);
                    break;
                case "402_error":
                    ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "Database error occurred.", Toast.LENGTH_LONG).show();
                    });
                    break;
                case "reported":
                ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "The Student has been Marketed or Reported", Toast.LENGTH_LONG).show();
                });
                    Intent intent_two = new Intent(context, ReportedWaringActivity.class);
                    context.startActivity(intent_two);
                break;
                default:
                    ((Activity) context).runOnUiThread(() -> {
                    Toast.makeText(context, "Failed to authenticate meal card. Please try again.", Toast.LENGTH_LONG).show();
                    });
                    break;
            }
        } catch (JSONException e) {
            ((Activity) context).runOnUiThread(() -> {
            Toast.makeText(context, "An error occurred while processing the response.", Toast.LENGTH_LONG).show();
            });
                    Log.e("JSON Error", e.toString());
        }
    }
}