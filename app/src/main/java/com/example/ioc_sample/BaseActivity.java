package com.example.ioc_sample;

import android.os.Bundle;

import com.example.library.InjectManaget;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 帮助子类进行 布局的注入
        InjectManaget.inject(this);
    }
}
