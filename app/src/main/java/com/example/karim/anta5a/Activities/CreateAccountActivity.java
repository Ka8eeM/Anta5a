package com.example.karim.anta5a.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.karim.anta5a.R;
import com.example.karim.anta5a.APIs.RetrofitClient;
import com.example.karim.anta5a.database.SharedPrefManager;
import com.example.karim.anta5a.models.User;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountActivity extends AppCompatActivity {

    Button btnAddAccount, addPic;
    EditText edName, edAddress, edEmail, edPhoneNumber, edPassword, edGender;
    private static final int PICK_IMAGE = 100;
    Uri uriImage;
    Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        btnAddAccount = findViewById(R.id.add_new_account);
        edName = findViewById(R.id.ed_name);
        edAddress = findViewById(R.id.ed_address);
        edEmail = findViewById(R.id.ed_email);
        edPhoneNumber = findViewById(R.id.ed_phone_number);
        edPassword = findViewById(R.id.ed_password);
        edGender = findViewById(R.id.ed_gender);
        addPic = findViewById(R.id.add_pic_);
        btnAddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
                Toast.makeText(CreateAccountActivity.this, "تم انشاء الحساب", Toast.LENGTH_SHORT).show();
            }
        });
        addPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK && requestCode == PICK_IMAGE) {
            uriImage = data.getData();
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(uriImage);
        }
    }

    private void signUp() {
        final String email = edEmail.getText().toString().trim();
        final String name = edName.getText().toString().trim();
        final String password = edPassword.getText().toString().trim();
        final String gender = edGender.getText().toString().trim();
        final String phone = edPhoneNumber.getText().toString().trim();
        final String address = edAddress.getText().toString().trim();
        final String image = "";
        if (email.isEmpty()) {
            edEmail.setError("e-mail is required");
            edEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edEmail.setError("Enter valid e-mail");
            edEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            edPassword.setError("password is required");
            edPassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            edPassword.setError("password should be at least 6 characters");
            edPassword.requestFocus();
            return;
        }
        if (name.isEmpty()) {
            edName.setError("Name is required");
            edName.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            edAddress.setError("Address is required");
            edAddress.requestFocus();
            return;
        }
        if (gender.isEmpty()) {
            edGender.setError("Confirm password is required");
            edGender.requestFocus();
            return;
        }
        if (phone.isEmpty()) {
            edPhoneNumber.setError("phone number is required");
            edPhoneNumber.requestFocus();
            return;
        }
        if (!Patterns.PHONE.matcher(phone).matches()) {
            edPhoneNumber.setError("Enter a valid phone number");
            edPhoneNumber.requestFocus();
            return;
        }
        /*user registeration using api call*/
        File file = new File("E:\\Apps\\Android\\Anta5a\\app\\src\\main\\res\\drawable", "back_ground_image.PNG");
        Call<String> call = RetrofitClient.getInstance()
                .getApi()
                .register(name
                        , gender
                        , email
                        , phone
                        , password
                        , address
                        , "");
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String res = response.body().toString();
                long ID = Long.parseLong(res);
                Log.e("Id creating account", "" + ID);
                User user = new User(ID, name, email, phone, password, address, gender, image);
                SharedPrefManager.getInstance(CreateAccountActivity.this).saveUser(user);
                Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }

        });
    }
}