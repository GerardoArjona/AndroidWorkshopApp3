package com.example.androidworkshopapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getPokemonWithOffset(0);
    }

    private void getPokemonWithOffset(int offset) {
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokeApiResponse> pokemonResponseCall = service.getPokemonList();

        pokemonResponseCall.enqueue(new Callback<PokeApiResponse>() {
            @Override
            public void onResponse(Call<PokeApiResponse> call, Response<PokeApiResponse> response) {
                if(response.isSuccessful()){

                    PokeApiResponse pokemonApiResponse = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonApiResponse.getResults();

                    for(int i = 0; i < pokemonList.size(); i++){
                        Pokemon pokemon = pokemonList.get(i);
                        Log.e("POKEMON", "onResponse: " + pokemon.getName());
                    }

                }else{
                    Log.e("POKEMON", "onResponseError: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokeApiResponse> call, Throwable t) {
                Log.e("POKEMON", "onFailure: " + t.getMessage());
            }
        });
    }
}