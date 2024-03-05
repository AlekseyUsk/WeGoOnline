package com.bignerdranch.android.wegoonline;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button btnActivity;
    private ImageView imageActivity;
    private ProgressBar progressBar;

    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        onClick();
        observerViewSModel();

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.loadDogImage();

    }

    private void observerViewSModel() {
        viewModel.getDogImage().observe(this, new Observer<DogImage>() {
            @Override
            public void onChanged(DogImage dogImage) {
                Glide.with(MainActivity.this)
                        .load(dogImage.getMessage())
                        .into(imageActivity);
            }
        });

        viewModel.getIsError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isError) {
                if (isError) {
                    Toast.makeText(
                                    MainActivity.this, "ОШИБКА ЗАГРУЗКИ", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean loading) {
                if (loading) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    private void onClick() {
        btnActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.loadDogImage();
            }
        });
    }

    private void initViews() {
        btnActivity = findViewById(R.id.btnActivity);
        imageActivity = findViewById(R.id.imageActivity);
        progressBar = findViewById(R.id.progressBar);
    }


}