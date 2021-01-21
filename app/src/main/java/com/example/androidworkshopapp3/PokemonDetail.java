package com.example.androidworkshopapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetail extends AppCompatActivity {

    Intent mainIntent;
    private Retrofit retrofit;
    TextView tvPokemonDetailName;
    TextView tvPokemonDetailExp;
    TextView tvPokemonDetailHeight;
    TextView tvPokemonDetailWeight;
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_detail);

        String pokNum = getIntent().getStringExtra("POK_NUMBER");
        spinner = (ProgressBar)findViewById(R.id.fetchingApiProgressBarSinglePokemon);
        spinner.setVisibility(View.VISIBLE);

        // Log.e("POKEMON", "pokNum single pokemon activity: " + pokNum);

        tvPokemonDetailName = findViewById(R.id.tvPokemonDetailName);
        tvPokemonDetailExp = findViewById(R.id.tvPokemonDetailExp);
        tvPokemonDetailHeight = findViewById(R.id.tvPokemonDetailHeight);
        tvPokemonDetailWeight = findViewById(R.id.tvPokemonDetailWeight);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SinglePokeApiService service = retrofit.create(SinglePokeApiService.class);
        Call<SinglePokeApiResponse> pokemonResponseCall = service.getPokemon(pokNum);

        pokemonResponseCall.enqueue(new Callback<SinglePokeApiResponse>() {
            @Override
            public void onResponse(Call<SinglePokeApiResponse> call, Response<SinglePokeApiResponse> response) {
                // Log.e("POKEMON", "onResponseErr: " + response);
                spinner.setVisibility(View.GONE);
                if(response.isSuccessful()){

                    SinglePokeApiResponse pokemonApiResponse = response.body();

                    // Log.e("POKEMON", "onResponse: " + response.body().toString());

                    tvPokemonDetailName.setText(pokemonApiResponse.name);
                    tvPokemonDetailExp.setText(pokemonApiResponse.base_experience + " EXP");
                    tvPokemonDetailHeight.setText( Float.toString(Float.parseFloat(pokemonApiResponse.height)/10) + " m");
                    tvPokemonDetailWeight.setText( Float.toString(Float.parseFloat(pokemonApiResponse.weight)/10) + " kg");


                }else{
                    Log.e("POKEMON", "onResponseError: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<SinglePokeApiResponse> call, Throwable t) {
                Log.e("POKEMON", "onFailure: " + t.getMessage());
            }
        });
    }
}