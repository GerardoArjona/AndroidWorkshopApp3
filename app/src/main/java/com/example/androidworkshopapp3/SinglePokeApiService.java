package com.example.androidworkshopapp3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SinglePokeApiService {

    @GET("pokemon/{pokemonNumber}")
    Call<SinglePokeApiResponse> getPokemon(
            @Path("pokemonNumber") String pokNum
    );

}
