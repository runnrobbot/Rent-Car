package com.example.webviewandviewpager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

public class LoginActivity extends AppCompatActivity {
    private VideoView videoView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        videoView = findViewById(R.id.videoView1);

        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video1;
        Uri videoUri = Uri.parse(videoPath);

        // Set MediaController untuk mengaktifkan kontrol pemutaran, jeda, dll.
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoUri);

        // Set listener untuk deteksi akhir video
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                // Ketika video selesai, restart video
                videoView.seekTo(0);
                videoView.start();
            }
        });

        // Mulai memutar video
        videoView.start();

        Button btnMasuk = (Button)findViewById(R.id.btnMasuk);
        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, masukActivity.class);
                startActivity(i);
            }
        });

        Button btnDaftar = (Button)findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, daftarActivity.class);
                startActivity(i);
            }
        });
    }
}