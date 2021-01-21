package com.example.androidworkshopapp3;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokeApiService {

    @GET("pokemon")
    Call<PokeApiResponse> getPokemonList(
            @Query("limit") int limit,
            @Query("offset") int offset
    );

}
