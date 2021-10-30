package com.example.mysupermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.mysupermarket.databinding.ActivityFinalBinding;

public class FinalActivity extends AppCompatActivity {
    private ActivityFinalBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_final);
        Intent intent = getIntent();
        Item item = (Item)intent.getSerializableExtra("item");
        binding.tvName.setText(item.getType());
        Glide.with(this).load(item.getUrl()).into(binding.ivImage);
    }
}