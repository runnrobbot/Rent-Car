package com.example.webviewandviewpager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class homePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

//        String receivedUsername = getIntent().getStringExtra("username");
//        if (receivedUsername != null) {
//            TextView txtUsername = (TextView) findViewById(R.id.txtUsername);
//            txtUsername.setText("  Halo, " + receivedUsername + "!");
//        } else {
//            // Handle jika nilai tidak ada atau null
//        }

        Button btnCari = (Button) findViewById(R.id.btnCari);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePageActivity.this, MainActivity.class);

                startActivity(i);
            }
        });

        Button btnLokasi = (Button) findViewById(R.id.btnLokasi);
        btnLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePageActivity.this, lokasiActivity.class);

                startActivity(i);
            }
        });

        Button btnLihat = (Button) findViewById(R.id.btnLihat);
        btnLihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePageActivity.this, pesananActivity.class);

                startActivity(i);
            }
        });

        Button btnBuka1 = (Button) findViewById(R.id.btnBuka1);
        btnBuka1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePageActivity.this, PengambilanActivity.class);

                startActivity(i);
            }

        });

        Button btnBuka2 = (Button) findViewById(R.id.btnBuka2);
        btnBuka2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePageActivity.this, PengembalianActivity.class);

                startActivity(i);
             }
        });

        Button btnProfile = (Button) findViewById(R.id.btnProfile);
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homePageActivity.this, Profile_Activity.class);

                startActivity(i);
            }
        });
        displayUsernameFromDatabase();
    }

    private void displayUsernameFromDatabase() {
        databaseScript db = new databaseScript(this);
        displayUsernameInTextView();
    }

    public void displayUsernameInTextView() {
        databaseScript db = new databaseScript(this);
        Cursor cursor = db.dataUser();

        if (cursor != null && cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex("username");

            if (usernameIndex != -1) {
                do {
                    String usernameBaru = cursor.getString(usernameIndex);

                    TextView textViewUsername = findViewById(R.id.txtUsername);
                    textViewUsername.setText("  Halo, " + usernameBaru + "!");

                } while (cursor.moveToNext());
            } else {
                // Handle the case where "username" column is not found
                Toast.makeText(this, "Column 'username' not found", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        } else {
            // Handle the case where no data is found
            Toast.makeText(this, "Username not found", Toast.LENGTH_SHORT).show();
        }

    }
}