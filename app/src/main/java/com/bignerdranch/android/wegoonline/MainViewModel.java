package com.bignerdranch.android.wegoonline;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/random";
    private static final String MESSAGE_KEY = "message";
    private static final String STATUS_KEY = "status";
    private static final String TAG = "MainViewModel";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public LiveData<DogImage> getDogImage() {
        return dogImage;
    }

    private final MutableLiveData<DogImage> dogImage = new MutableLiveData<>();

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void loadDogImage() {
        isLoading.setValue(true);
        Disposable disposable = loadImageRxJava().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DogImage>() {
                    @Override
                    public void accept(DogImage image) throws Throwable {
                        isLoading.setValue(false);
                        dogImage.setValue(image);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Throwable {
                        isLoading.setValue(false);
                        Log.d(TAG, "error :" + throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    /**
     * RxJava - создаю Single так как надо вернуть обьект<DogImage> данные пожтому создаю метод loadImageRxJava()
     * этот метод выполнит все необходимые загрузки и вернет оьект DogImage
     */
    private Single<DogImage> loadImageRxJava() {
        return Single.fromCallable(new Callable<DogImage>() {
            @Override
            public DogImage call() throws Exception {

                URL url = new URL(BASE_URL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder data = new StringBuilder();
                String result;

                do {
                    result = bufferedReader.readLine();
                    if (result != null) {
                        data.append(result);
                    }
                } while (result != null);

                JSONObject jsonObject = new JSONObject(data.toString());
                String message = jsonObject.getString(MESSAGE_KEY);
                String status = jsonObject.getString(STATUS_KEY);
                return new DogImage(message, status);
            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
