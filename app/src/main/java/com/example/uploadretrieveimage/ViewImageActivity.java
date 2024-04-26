package com.example.uploadretrieveimage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ViewImageActivity extends AppCompatActivity {
    private String imageUriString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        ImageView imageView = findViewById(R.id.imageView);
        ImageButton deleteButton = findViewById(R.id.deleteButton);
        imageUriString = getIntent().getStringExtra("imageUri");
        System.out.println("Image URI: " + imageUriString);
        Glide.with(this).load(imageUriString).into(imageView);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteImage();
            }
        });
    }
    private void deleteImage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageUriString);
        storageReference.delete().addOnSuccessListener(aVoid -> {
            Toast.makeText(ViewImageActivity.this, "Image deleted", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> {
            Toast.makeText(ViewImageActivity.this, "Failed to delete image: " + e.getMessage(), Toast.LENGTH_LONG).show();
        });
    }
}