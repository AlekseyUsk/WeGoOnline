package com.bignerdranch.android.wegoonline;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadDogImage();
    }

    private void loadDogImage() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(BASE_URL);                                                    /**передал запрос в url*/
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); /**открыл соеденение*/
                    InputStream inputStream = httpURLConnection.getInputStream();                   /**чтение данных по байтово*/
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);       /**чтение данных по символам*/
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);          /**оболочка над InputStreamReader чтение целыми строками */
                    String result = bufferedReader.readLine();                                      /**считал ответ сервера и вывел в LOG*/

                    Log.d("MainActivity", result);
                } catch (Exception e) {
                    Log.d("MainActivity", e.toString());
                }
            }
        }).start();

    }
}