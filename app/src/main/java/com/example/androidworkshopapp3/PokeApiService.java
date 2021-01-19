package com.example.androidworkshopapp3;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokeApiService {

    @GET("pokemon")
    Call<PokeApiResponse> getPokemonList();

}
