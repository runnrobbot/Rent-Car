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

public class daftarActivity extends AppCompatActivity {

    private EditText usernameEditText, passwordEditText, emailEditText, alamatEditText, nomorEditText;
    private Button registerButton;
    private databaseScript dbHelper;

    public static void showLoginFailedDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pendaftaran Gagal")
                .setMessage("Silakan periksa kembali nama pengguna dan kata sandi.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
    public static void showRegisterSuccess(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pendaftaran Berhasil")
                .setMessage("Silakan masukan username dan password dihalaman login!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);

        dbHelper = new databaseScript(this);

        usernameEditText = findViewById(R.id.edtUsername1);
        passwordEditText = findViewById(R.id.edtPassword1);
        emailEditText = findViewById(R.id.edtEmail1);
        alamatEditText = findViewById(R.id.edtAlamat1);
        nomorEditText = findViewById(R.id.edtNomor1);


        registerButton = findViewById(R.id.btnDaftar2);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String alamat = alamatEditText.getText().toString();
                String nomor = nomorEditText.getText().toString();

                long result = dbHelper.registerUser(username, password, email, alamat, nomor);

                if (result != -1) {
                    daftarActivity.showRegisterSuccess(daftarActivity.this);
                    Intent i = new Intent(daftarActivity.this, masukActivity.class);
                    startActivity(i);
                } else {
                    daftarActivity.showLoginFailedDialog(daftarActivity.this);
                }
            }
        });

    }
}