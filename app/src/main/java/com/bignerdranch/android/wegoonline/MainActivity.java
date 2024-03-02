package com.bignerdranch.android.wegoonline;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

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

                    StringBuilder data = new StringBuilder();
                    String result;

                    do {
                        result = bufferedReader.readLine();
                        if (result != null) {
                            data.append(result);
                        }
                    } while (result != null);

                    JSONObject jsonObject = new JSONObject(data.toString());                        /**создал обьект Json для преобразования полученных данных из интернета*/
                    String message = jsonObject.getString("message");                         /**по ключу из данных из интернета там указанго было ("message") я получил данные;*/
                    String status = jsonObject.getString("status");                           /**то же самое*/
                    Dogs dogImage = new Dogs(message, status);                                      /**передал обьекту класса полученные данные*/

                    Log.d("MainActivity", dogImage.toString()                                   /**вывел в логи заранее переопределив toString в классе Dogs*/
                    );
                } catch (Exception e) {
                    Log.d("MainActivity", e.toString());
                }
            }
        }).start();

    }
}