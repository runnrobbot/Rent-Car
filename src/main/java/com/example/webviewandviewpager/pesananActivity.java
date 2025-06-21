package com.example.webviewandviewpager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class pesananActivity extends AppCompatActivity {
    private ListView listView;
    private databaseScript dbHelper;
    private TextView txtNama, txtAlamat, txtEmail, txtNomor, txtPinjam;
    private CustomCursorAdapter cursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesanan);
        Log.d("pesananActivity", "onCreate");

        listView = findViewById(R.id.listView1);
        dbHelper = new databaseScript(this);

        txtNama = findViewById(R.id.txtNama);
        txtAlamat = findViewById(R.id.txtAlamat);
        txtEmail = findViewById(R.id.txtEmail);
        txtNomor = findViewById(R.id.txtNomor);
        txtPinjam = findViewById(R.id.txtPinjam);

        cursorAdapter = new CustomCursorAdapter(this, null);
        listView.setAdapter(cursorAdapter);

        displayData();

        Button btnSelesai = findViewById(R.id.btnSelesai1);
        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    displayData(); // Refresh tampilan setelah menghapus data

                    Intent i = new Intent(pesananActivity.this, homePageActivity.class);
                    startActivity(i);
            }
        });

        ImageButton imageBtnBack = findViewById(R.id.imageBtnBack);
        imageBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(pesananActivity.this, homePageActivity.class);
                startActivity(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                if (cursor != null) {
                    int columnIndexNama = cursor.getColumnIndex("username");
                    int columnIndexAlamat = cursor.getColumnIndex("alamat");
                    int columnIndexEmail = cursor.getColumnIndex("email");
                    int columnIndexNomor = cursor.getColumnIndex("nomor");
                    int columnIndexPinjam = cursor.getColumnIndex("peminjaman");
                    int columnIndexID = cursor.getColumnIndex("_id");

                    if (columnIndexID != -1) {
                        txtNama.setText("Nama: " + cursor.getString(columnIndexNama));
                        txtAlamat.setText("Alamat: " + cursor.getString(columnIndexAlamat));
                        txtEmail.setText("Email: " + cursor.getString(columnIndexEmail));
                        txtNomor.setText("Nomor: " + cursor.getString(columnIndexNomor));
                        txtPinjam.setText("Pinjam: " + cursor.getString(columnIndexPinjam));
                    } else {
                        Log.e("ItemClick", "Column '_id' not found in cursor.");
                    }
                }
            }
        });
    }

    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    private void displayData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            cursor = dbHelper.dataPesananAmbil();

            // Update cursor
            cursorAdapter.swapMyCursor(cursor);

            // Notif cursor
            cursorAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Tidak perlu menutup cursor atau database di sini, karena sudah dihandle oleh CursorAdapter
        }
    }

}

class CustomCursorAdapter extends CursorAdapter {
    private databaseScript dbHelper;

    public CustomCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public void swapMyCursor(Cursor newCursor) {
        if (getCursor() != null && !getCursor().isClosed()) {
            getCursor().close();
        }

        // Swap the cursor
        super.swapCursor(newCursor);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        Log.d("CustomCursorAdapter", "bindView");

        TextView txtNama = view.findViewById(R.id.txtNama);
        TextView txtAlamat = view.findViewById(R.id.txtAlamat);
        TextView txtEmail = view.findViewById(R.id.txtEmail);
        TextView txtNomor = view.findViewById(R.id.txtNomor);
        TextView txtPinjam = view.findViewById(R.id.txtPinjam);

        int columnIndexNama = cursor.getColumnIndex("username");
        int columnIndexAlamat = cursor.getColumnIndex("alamat");
        int columnIndexEmail = cursor.getColumnIndex("email");
        int columnIndexNomor = cursor.getColumnIndex("nomor");
        int columnIndexPinjam = cursor.getColumnIndex("peminjaman");


        if (cursor != null && !cursor.isClosed()) {
            if (columnIndexNama != -1) {
                txtNama.setText("Nama: " + cursor.getString(columnIndexNama));
            } else {
                txtNama.setText("Nama: N/A");
            }

            if (columnIndexAlamat != -1) {
                txtAlamat.setText("Alamat: " + cursor.getString(columnIndexAlamat));
            } else {
                txtAlamat.setText("Alamat: N/A");
            }

            if (columnIndexEmail != -1) {
                txtEmail.setText("Email: " + cursor.getString(columnIndexEmail));
            } else {
                txtEmail.setText("Email: N/A");
            }

            if (columnIndexNomor != -1) {
                txtNomor.setText("Nomor: " + cursor.getString(columnIndexNomor));
            } else {
                txtNomor.setText("Nomor: N/A");
            }

            if (columnIndexPinjam != -1) {
                txtPinjam.setText("Peminjaman: " + cursor.getString(columnIndexPinjam));
            } else {
                txtPinjam.setText("Peminjaman: N/A");
            }

        } else {
            Log.e("CustomCursorAdapter", "Cursor is null or closed");
        }

        Log.d("CustomCursorAdapter", "Item count: " + getCount());
    }
}
