package com.example.karim.anta5a.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karim.anta5a.R;
import com.example.karim.anta5a.APIs.RetrofitClient;
import com.example.karim.anta5a.database.SharedPrefManager;
import com.example.karim.anta5a.models.CustomerDataResponse;
import com.example.karim.anta5a.models.LoginResponse;
import com.example.karim.anta5a.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    /* @Override
     protected void onStart() {
         super.onStart();
         if (SharedPrefManager.getInstance(this).isLoggedIn()) {
             Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
             startActivity(intent);
         }
     }*/
    Toolbar toolbar;
    Button btnEdit, btnDelete;
    TextView tvName, tvEmail, tvPhone, tvAddress, tvOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar_);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnEdit = findViewById(R.id.edit_account);
        btnDelete = findViewById(R.id.delete_account);
        tvName = findViewById(R.id.tv_name_owner);
        tvAddress = findViewById(R.id.tv_address_owner);
        tvEmail = findViewById(R.id.tv_email_owner);
        tvPhone = findViewById(R.id.tv_phone_owner);
        tvOwner = findViewById(R.id.tv_account_owner);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAccount();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditAccoutActivity.class));
            }
        });
        getUserDate();
    }

    private void deleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("هل انت متأكد؟");
        builder.setMessage("مسح الحساب");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
                long id = user.getId();
                Log.e("id from delete->", id + "");
                Call<String> call = RetrofitClient.getInstance().getApi().deleteUser(id);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String res = response.body();
                        boolean ok = Boolean.parseBoolean(res);
                        if (ok) {
                            Toast.makeText(ProfileActivity.this, "تم مسح الحساب", Toast.LENGTH_SHORT).show();
                            SharedPrefManager.getInstance(ProfileActivity.this).clear();
                            Intent intent = new Intent(ProfileActivity.this, LogInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(ProfileActivity.this, "لم يتم مسح الحساب", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUserDate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDate();
    }

    private void getUserDate() {
        User user = SharedPrefManager.getInstance(ProfileActivity.this).getUser();
        long id = user.getId();
        Log.e("thisistheid->>>>>>>", "" + id);
        if (id != -1) {
            Call<CustomerDataResponse> call = RetrofitClient.getInstance().getApi().getUserLogged(id);
            call.enqueue(new Callback<CustomerDataResponse>() {
                @Override
                public void onResponse(Call<CustomerDataResponse> call, Response<CustomerDataResponse> response) {
                    CustomerDataResponse res = response.body();
                    Log.e("asdasdasd", res.getId() + "");
                    tvName.setText(res.getName() + "الاسم:");
                    tvAddress.setText(res.getAddress() + "العنوان:");
                    tvEmail.setText(res.getEmail() + "الايميل:");
                    tvPhone.setText(res.getPhonenumber() + "رقم الهاتف:");
                    tvOwner.setText(res.getName() + "مرحبا بك:");
                }

                @Override
                public void onFailure(Call<CustomerDataResponse> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, "Can not fetch user data", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(LogInActivity.getPhon(), LogInActivity.getPas());
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    LoginResponse res1 = response.body();
                    if (res1.getId() != -1) {
                        Call<CustomerDataResponse> call2 = RetrofitClient.getInstance().getApi().getUserLogged(res1.getId());
                        call2.enqueue(new Callback<CustomerDataResponse>() {
                            @Override
                            public void onResponse(Call<CustomerDataResponse> call, Response<CustomerDataResponse> response) {
                                CustomerDataResponse res = response.body();
                                Log.e("asdasdasd", res.getId() + "");
                                tvName.setText(res.getName() + "الاسم:");
                                tvAddress.setText(res.getAddress() + "العنوان:");
                                tvEmail.setText(res.getEmail() + "الايميل:");
                                tvPhone.setText(res.getPhonenumber() + "رقم الهاتف:");
                                tvOwner.setText(res.getName() + "مرحبا بك:");
                                User user = new User(res.getId(), res.getName()
                                        , res.getEmail()
                                        , res.getPhonenumber(), LogInActivity.getPas(), res.getAddress()
                                        , res.getGender(), res.getImage());
                                SharedPrefManager.getInstance(ProfileActivity.this).saveUser(user);
                            }

                            @Override
                            public void onFailure(Call<CustomerDataResponse> call, Throwable t) {
                                Toast.makeText(ProfileActivity.this, "Can not fetch user data", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(ProfileActivity.this, "حدث خطأ اثناء الاتصال", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(ProfileActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}