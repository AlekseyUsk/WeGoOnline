package com.bignerdranch.android.wegoonline.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ЭТОТ КЛАСС ЗАНИМАЕТСЯ СОЗДАНИЕМ РЕАЛИЗАЦИИ ИНТЕРФЕЙСА ApiService
 * ПРИЧЕМ РЕАЛИЗАЦИЯ ЭТОГО ИНТЕРФЕЙСА ДОЛЖА БЫТЬ ОДНА И ТУТ ПРИМЕНЯЕМ ПАТЕРН СИНГЛТОН
 * СОЗДАЕМ СТАТИЧНУЮ ПЕРЕМЕННУЮ И СТАТИЧНЫЙ МЕТОД КОТОРЫЦЙ ВЫПОЛНИТ ПРОВЕРКУ И ВЕРНЕТ ApiService
 */
public class ApiFactory {

    private static final String BASE_URL = "https://dog.ceo/api/breeds/image/"; //тут без энд поинта

    private static ApiService apiService;

    public static ApiService getApiService() {
        if (apiService == null) {
            /**создаем ретрофит и передаем в Builder() базовый URL
             *теперь указываем адаптер который
             *  будет преобразовывать Json обьекты в экземпляры моего класса - .addConverterFactory(GsonConverterFactory.create())*/
            Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()) // УКАЗАЛ ЧТО БУДУ ПРЕОБРАЗОВЫВАТЬ ОБЬЕКТЫ ПРИ ПОМОЩИ JSON биьлиотеки
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) //добпавил поддержку Rxjava
                    .build();
            apiService = retrofit.create(ApiService.class);
        }
        return apiService;
    }
}
