package com.bignerdranch.android.wegoonline.retrofit;

import com.bignerdranch.android.wegoonline.DogImage;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface ApiService {

    @GET("random")  //тут энд поинт указываем
    Single<DogImage> loadDogImage();
}
