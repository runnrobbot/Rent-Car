package com.example.webviewandviewpager;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class orderActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, alamatEditText, nomorEditText, peminjamanEditText;
    private Button bookButton;
    private databaseScript dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        dbHelper = new databaseScript(this);

        usernameEditText = findViewById(R.id.edtUsername);
        emailEditText = findViewById(R.id.edtEmail);
        alamatEditText = findViewById(R.id.edtAlamat);
        nomorEditText = findViewById(R.id.edtNomor);
        peminjamanEditText = findViewById(R.id.edtPeminjaman);
        bookButton = findViewById(R.id.btnBook);

        ImageButton imageBtnBack = findViewById(R.id.imageBtnBack);
        imageBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(orderActivity.this, homePageActivity.class);
                startActivity(i);
            }
        });

        bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String alamat = alamatEditText.getText().toString();
                String nomor = nomorEditText.getText().toString();
                String peminjaman = peminjamanEditText.getText().toString();

                if (username.isEmpty() || email.isEmpty() || alamat.isEmpty() || nomor.isEmpty() || peminjaman.isEmpty()) {
                    Toast.makeText(orderActivity.this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
                    return;
                }

                long result = dbHelper.orderUser(username, email, alamat, nomor, peminjaman);

                if (result != -1) {
                    Toast.makeText(orderActivity.this, "Pesanan berhasil dibuat.", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(orderActivity.this, pesananActivity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(orderActivity.this, "Gagal membuat pesanan.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
