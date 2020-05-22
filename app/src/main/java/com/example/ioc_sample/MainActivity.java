package com.example.ioc_sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.library.annotations.ContentView;
import com.example.library.annotations.BindView;

import androidx.annotation.Nullable;


@ContentView(R.layout.activity_main) // 替代setContentView(R.layout.activity_main);
public class MainActivity extends BaseActivity {

    // 替代  Button button = findViewById(R.id.btn);
    @BindView(R.id.btn)
    public Button button;

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,"反射", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
