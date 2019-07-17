package com.example.giphyapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        String url = getIntent().getStringExtra("url");

        ImageView bigPic = findViewById(R.id.iv_big_photo);

        Glide.with(this).load(url).into(bigPic);
    }
}
