package com.example.ourplace.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourplace.R;
import com.example.ourplace.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends BaseActivity {
    ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        setVariable();
    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();
            if (!email.isEmpty() && !password.isEmpty()) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "onComplete: ");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            String userEmail = user.getEmail();
                            Log.i(TAG, "User Email: " + userEmail);
                            Toast.makeText(LoginActivity.this, "Giriş yapılıyor: " + userEmail, Toast.LENGTH_SHORT).show();

                            // E-posta adresini MainActivity'ye gönder
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userEmail", userEmail);
                            startActivity(intent);
                        }
                    } else {
                        Log.i(TAG, "failure: " + task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Mail veya şifrenizi kontrol ediniz.", Toast.LENGTH_SHORT).show();
            }
        });

        TextView SignUpBtn = findViewById(R.id.SingUpBtn);
        SignUpBtn.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, SingupActivity.class)));
    }
}
