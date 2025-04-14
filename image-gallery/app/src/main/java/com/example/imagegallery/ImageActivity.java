package com.example.imagegallery;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class ImageActivity extends AppCompatActivity {

    Button back;
    ImageView fullImage;
    TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image);
        String url=getIntent().getStringExtra("url");
        String titlee=getIntent().getStringExtra("title");
        fullImage=findViewById(R.id.full_image);
        back=findViewById(R.id.back);
        title=findViewById(R.id.textView);
        title.setText(titlee);
        Glide.with(this)
                .load(url)
                .placeholder(R.drawable.atg)
            //    .centerCrop()
                .into(fullImage);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}