package com.example.webviewandviewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class masukActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText;
    private Button loginButton;
    private databaseScript dbHelper;

    public static void showLoginFailedDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Login Gagal")
                .setMessage("Silakan periksa kembali nama pengguna dan kata sandi.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tombol OK ditekan
                        dialog.dismiss(); // Tutup dialog
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masuk);

        dbHelper = new databaseScript(this);

        usernameEditText = findViewById(R.id.edtUsername);
        passwordEditText = findViewById(R.id.edtPassword);
        loginButton = findViewById(R.id.btnSelesai1);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                boolean isLoginSuccessful = dbHelper.login(username, password);

                if (isLoginSuccessful) {
                    Intent i = new Intent(masukActivity.this, homePageActivity.class);
                    String userDapat = usernameEditText.getText().toString();
                    i.putExtra("username", userDapat);
                    startActivity(i);
                } else {
                    masukActivity.showLoginFailedDialog(masukActivity.this);

                }
            }
        });

    }
}