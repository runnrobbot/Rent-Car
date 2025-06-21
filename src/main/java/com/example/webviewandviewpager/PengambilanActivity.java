package com.example.webviewandviewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PengambilanActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengambilan);

        Button btnPengambilanAmbil = findViewById(R.id.btnPengambilanAmbil);
        btnPengambilanAmbil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPengambilanDialog(PengambilanActivity.this);
            }
        });

        ImageButton imageBtnBack = findViewById(R.id.imageBtnBack);
        imageBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PengambilanActivity.this, homePageActivity.class);
                startActivity(i);
            }
        });
    }

    public static void showPengambilanDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pengambilan Disetujui")
                .setMessage("Silahkan Ambil Kendaraan, Kunci Kendaraan, dan Surat Kendaraaan")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Tombol OK ditekan
                        dialog.dismiss(); // Tutup dialog
                        // Pindah ke activity lain
                        Intent intent = new Intent(context, homePageActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }
}