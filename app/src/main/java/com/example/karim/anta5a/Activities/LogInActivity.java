package com.example.karim.anta5a.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karim.anta5a.APIs.RetrofitClient;
import com.example.karim.anta5a.R;
import com.example.karim.anta5a.database.SharedPrefManager;
import com.example.karim.anta5a.models.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    Button btnLogin;
    TextView txtCreateNew;
    EditText edPhoneNumber, edPassword;
    private static String pas, phon;

    public static String getPas() {
        return pas;
    }

    public static String getPhon() {
        return phon;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        btnLogin = findViewById(R.id.btn_login);
        txtCreateNew = findViewById(R.id.create_new_account);
        edPhoneNumber = findViewById(R.id.phone_number_login);
        edPassword = findViewById(R.id.password_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });
        txtCreateNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    private void logIn() {
        String user = edPhoneNumber.getText().toString().trim();
        String pass = edPassword.getText().toString().trim();
        if (user.isEmpty()) {
            edPhoneNumber.setError("Phone number is required");
            edPhoneNumber.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            edPassword.setError("Password is required");
            edPassword.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(user).matches()) {
            edPhoneNumber.setError("Enter correct Phone number");
            edPhoneNumber.requestFocus();
            return;
        }
        if (pass.length() < 6) {
            edPassword.setError("Password should be at least 6 characters");
            edPassword.requestFocus();
            return;
        }
        pas = pass;
        phon = user;
        Call<LoginResponse> call = RetrofitClient.getInstance()
                .getApi().userLogin(user, pass);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                if (loginResponse.getId() != (long) -1) {
                    Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(LogInActivity.this, "user is not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LogInActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}