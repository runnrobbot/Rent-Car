package com.example.webviewandviewpager;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Profile_Activity extends AppCompatActivity {
    private ImageView imageView;
    private ImageButton buttonChangeImage;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageView = findViewById(R.id.imageView1);
        buttonChangeImage = findViewById(R.id.imageButtonGantiGambar);

        ShapeDrawable shapeDrawable = new ShapeDrawable(new OvalShape());
        shapeDrawable.getPaint().setColor(Color.RED);

        buttonChangeImage.setBackground(shapeDrawable);

        Button buttonProfileBack = (Button) findViewById(R.id.buttonProfileBack);
        buttonProfileBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Profile_Activity.this, homePageActivity.class);
                startActivity(i);
            }
        });

        buttonChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImagePicker();
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

                    TextView textViewUsername = findViewById(R.id.txtProfileName);
                    textViewUsername.setText(usernameBaru);

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


    private void openImagePicker() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

