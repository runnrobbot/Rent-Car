package com.example.webviewandviewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PengembalianActivity extends AppCompatActivity {

    private databaseScript dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengembalian);

        dbHelper = new databaseScript(this);

        Button btnPengembaliankembali = findViewById(R.id.btnPengembaliankembali);
        btnPengembaliankembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPengebalianDialog(PengembalianActivity.this);
            }
        });

        ImageButton imageBtnBack = findViewById(R.id.imageBtnBack);
        imageBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PengembalianActivity.this, homePageActivity.class);
                startActivity(i);
            }
        });
    }

    public void hapusDataPesanan() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("Pesanan", null, null);
        db.close();

        if (rowsDeleted > 0) {
            Toast.makeText(this, "Data pesanan berhasil dihapus.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Tidak ada data yang perlu dihapus.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showPengebalianDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Pengembalian Berhasil")
                .setMessage("Silahkan Kembalikan Kendaraan, Kunci Kendaraan, dan Surat Kendaraan!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Hapus data pesanan dari database
                        hapusDataPesanan();

                        // Pindah ke activity lain
                        Intent intent = new Intent(context, homePageActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }
}
