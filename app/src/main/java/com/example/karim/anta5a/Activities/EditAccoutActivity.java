package com.example.karim.anta5a.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karim.anta5a.R;
import com.example.karim.anta5a.APIs.RetrofitClient;
import com.example.karim.anta5a.database.SharedPrefManager;
import com.example.karim.anta5a.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAccoutActivity extends AppCompatActivity {

    /* @Override
     protected void onStart() {
         super.onStart();
         if (SharedPrefManager.getInstance(this).isLoggedIn()) {
             Intent intent = new Intent(EditAccoutActivity.this, MainActivity.class);
         intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(intent);
         }
     }*/
    Button button;
    EditText edName, edPhone, edEmail, edAddress, edGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_accout);
        button = findViewById(R.id.save_edit);
        edName = findViewById(R.id.ed_edit_name);
        edEmail = findViewById(R.id.ed_edit_mail);
        edAddress = findViewById(R.id.ed_edit_address);
        edPhone = findViewById(R.id.ed_edit_phone_nnumber);
        edGender = findViewById(R.id.ed_edit_gender);
        loadUser();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUser();
    }

    private void loadUser() {
        User user = SharedPrefManager.getInstance(EditAccoutActivity.this).getUser();
        Log.e(getLocalClassName() + "", "id is" + user.getId());
        edName.setText(user.getName() + "");
        edGender.setText(user.getGender() + "");
        edPhone.setText(user.getPhoneNumber() + "");
        edEmail.setText(user.getEmail() + "");
        edAddress.setText(user.getAddress() + "");
    }

    private void updateUser() {
        final String name, add, phone, gender, email;
        name = edName.getText().toString().trim();
        add = edAddress.getText().toString().trim();
        phone = edPhone.getText().toString().trim();
        gender = edGender.getText().toString().trim();
        email = edEmail.getText().toString().trim();
        if (name.equals("")) {
            edName.setError("name is required");
            edName.requestFocus();
            return;
        }
        if (add.equals("")) {
            edAddress.setError("Address is required");
            edAddress.requestFocus();
            return;
        }

        if (email.equals("")) {
            edEmail.setError("Email is required");
            edEmail.requestFocus();
            return;
        }

        if (phone.equals("")) {
            edPhone.setError("Phone is required");
            edPhone.requestFocus();
            return;
        }

        if (gender.equals("")) {
            edGender.setError("Address is required");
            edGender.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            edPhone.setError("Enter correct phone number");
            edPhone.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edPhone.setError("Enter correct Email address");
            edPhone.requestFocus();
            return;
        }
        final User user = SharedPrefManager.getInstance(EditAccoutActivity.this).getUser();
        final long id = user.getId();
        retrofit2.Call<String> call = RetrofitClient.getInstance().getApi().updateProfile(
                id,
                name,
                email,
                phone,
                add,
                gender
        );
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body();
                boolean ok = Boolean.parseBoolean(res);
                if (ok) {
                    String img = user.getImage();
                    String pas = user.getPassword();
                    SharedPrefManager.getInstance(EditAccoutActivity.this).clear();
                    User us = new User(id, name, email, phone, pas, add, gender, img);
                    SharedPrefManager.getInstance(EditAccoutActivity.this).saveUser(us);
                    Toast.makeText(EditAccoutActivity.this, "تم تعديل الحساب", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditAccoutActivity.this, "لم يتم تعديل الحساب", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}