package com.example.finalpactice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button mainRegBtn;
    EditText mainInputEmail;
    EditText mainInputPassword;
    Button mainLoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mainRegBtn = findViewById(R.id.mainRegBtn);

        mainInputEmail = findViewById(R.id.mainInputEmail);
        mainInputPassword = findViewById(R.id.mainInputPassword);
        mainLoginBtn = findViewById(R.id.mainLoginBtn);

        mainLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mainInputEmail.getText().toString().trim();
                String password = mainInputPassword.getText().toString();

                if (email.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Please fill in all fields",Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginRequest loginRequest = new LoginRequest(email,password);

                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                Call<LoginResponse> call = apiService.loginUser(loginRequest);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()){
                            LoginResponse loginResponse = response.body();
                            if (loginResponse != null && "success".equals(loginResponse.getStatus())){
                                Toast.makeText(MainActivity.this, "Login Succesful", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(MainActivity.this, Homepage.class);
                                startActivity(intent);
                                finish();
                            }else {
                                Toast.makeText(MainActivity.this, "Login Failed" + loginResponse.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Login Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable throwable) {
                        Log.e("MainActivity", "Login Failed", throwable);
                        Toast.makeText(MainActivity.this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });












        mainRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Registration.class);
                startActivity(intent);
            }
        });
    }
}