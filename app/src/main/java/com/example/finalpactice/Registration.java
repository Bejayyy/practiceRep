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

public class Registration extends AppCompatActivity {
    EditText regInputUsername;
    EditText regInputEmail;
    EditText regInputPassword;
    Button regRegBtn;
    Button regBackBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        regInputUsername = findViewById(R.id.regInputUsername);
        regInputEmail = findViewById(R.id.regInputEmail);
        regInputPassword = findViewById(R.id.regInputPassword);
        regRegBtn = findViewById(R.id.regRegBtn);
        regBackBtn = findViewById(R.id.regBackbtn);

        regRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = regInputUsername.getText().toString().trim();
                String email = regInputEmail.getText().toString().trim();
                String password = regInputPassword.getText().toString();

                User user =new User(username, password, email);

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Registration.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Retrofit Request
                ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
                Call<RegisterResponse> call = apiService.registerUser(user);

                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if (response.isSuccessful()){
                            RegisterResponse registerResponse = response.body();
                            if (registerResponse != null && "success".equals(registerResponse.getStatus())){
                                Toast.makeText(Registration.this, "Registration Succesful", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Registration.this, "Registration Failed: " + registerResponse.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Registration.this, "Registration Failed2: " + response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable throwable) {
                        Log.e("Registration","Registration Failed", throwable);
                        Toast.makeText(Registration.this, "Error" + throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        regBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Registration.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}