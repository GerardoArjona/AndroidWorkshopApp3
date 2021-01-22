package com.example.androidworkshopapp3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

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
    ImageView ivPokemonDetailType1;
    ImageView ivPokemonDetailType2;
    ImageView ivPokemonDetail;
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
        ivPokemonDetailType1 = findViewById(R.id.ivPokemonDetailType1);
        ivPokemonDetailType2 = findViewById(R.id.ivPokemonDetailType2);
        ivPokemonDetail = findViewById(R.id.ivPokemonDetail);

        ivPokemonDetailType1.setVisibility(View.GONE);
        ivPokemonDetailType2.setVisibility(View.GONE);

        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokNum + ".png")
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPokemonDetail);

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

                    if(pokemonApiResponse.types[0] != null){
                        ivPokemonDetailType1.setVisibility(View.VISIBLE);
                        if(pokemonApiResponse.types[0].type.name.equals("bug")){
                            ivPokemonDetailType1.setImageResource(R.drawable.bug);
                        }else if(pokemonApiResponse.types[0].type.name.equals("dragon")){
                            ivPokemonDetailType1.setImageResource(R.drawable.dragon);
                        }else if(pokemonApiResponse.types[0].type.name.equals("electric")){
                            ivPokemonDetailType1.setImageResource(R.drawable.electric);
                        }else if(pokemonApiResponse.types[0].type.name.equals("fight")){
                            ivPokemonDetailType1.setImageResource(R.drawable.fight);
                        }else if(pokemonApiResponse.types[0].type.name.equals("fire")){
                            ivPokemonDetailType1.setImageResource(R.drawable.fire);
                        }else if(pokemonApiResponse.types[0].type.name.equals("flying")){
                            ivPokemonDetailType1.setImageResource(R.drawable.flying);
                        }else if(pokemonApiResponse.types[0].type.name.equals("ghost")){
                            ivPokemonDetailType1.setImageResource(R.drawable.ghost);
                        }else if(pokemonApiResponse.types[0].type.name.equals("grass")){
                            ivPokemonDetailType1.setImageResource(R.drawable.grass);
                        }else if(pokemonApiResponse.types[0].type.name.equals("ground")){
                            ivPokemonDetailType1.setImageResource(R.drawable.ground);
                        }else if(pokemonApiResponse.types[0].type.name.equals("ice")){
                            ivPokemonDetailType1.setImageResource(R.drawable.ice);
                        }else if(pokemonApiResponse.types[0].type.name.equals("normal")){
                            ivPokemonDetailType1.setImageResource(R.drawable.normal);
                        }else if(pokemonApiResponse.types[0].type.name.equals("poison")){
                            ivPokemonDetailType1.setImageResource(R.drawable.poison);
                        }else if(pokemonApiResponse.types[0].type.name.equals("psychic")){
                            ivPokemonDetailType1.setImageResource(R.drawable.psychc);
                        }else if(pokemonApiResponse.types[0].type.name.equals("rock")){
                            ivPokemonDetailType1.setImageResource(R.drawable.rock);
                        }else if(pokemonApiResponse.types[0].type.name.equals("water")){
                            ivPokemonDetailType1.setImageResource(R.drawable.water);
                        }else{
                            ivPokemonDetailType1.setImageResource(R.drawable.pokeball);
                        }
                    }

                    if(pokemonApiResponse.types.length >= 2){
                        ivPokemonDetailType2.setVisibility(View.VISIBLE);
                        if(pokemonApiResponse.types[1].type.name.equals("bug")){
                            ivPokemonDetailType2.setImageResource(R.drawable.bug);
                        }else if(pokemonApiResponse.types[1].type.name.equals("dragon")){
                            ivPokemonDetailType2.setImageResource(R.drawable.dragon);
                        }else if(pokemonApiResponse.types[1].type.name.equals("electric")){
                            ivPokemonDetailType2.setImageResource(R.drawable.electric);
                        }else if(pokemonApiResponse.types[1].type.name.equals("fight")){
                            ivPokemonDetailType2.setImageResource(R.drawable.fight);
                        }else if(pokemonApiResponse.types[1].type.name.equals("fire")){
                            ivPokemonDetailType2.setImageResource(R.drawable.fire);
                        }else if(pokemonApiResponse.types[1].type.name.equals("flying")){
                            ivPokemonDetailType2.setImageResource(R.drawable.flying);
                        }else if(pokemonApiResponse.types[1].type.name.equals("ghost")){
                            ivPokemonDetailType2.setImageResource(R.drawable.ghost);
                        }else if(pokemonApiResponse.types[1].type.name.equals("grass")){
                            ivPokemonDetailType2.setImageResource(R.drawable.grass);
                        }else if(pokemonApiResponse.types[1].type.name.equals("ground")){
                            ivPokemonDetailType2.setImageResource(R.drawable.ground);
                        }else if(pokemonApiResponse.types[1].type.name.equals("ice")){
                            ivPokemonDetailType2.setImageResource(R.drawable.ice);
                        }else if(pokemonApiResponse.types[1].type.name.equals("normal")){
                            ivPokemonDetailType2.setImageResource(R.drawable.normal);
                        }else if(pokemonApiResponse.types[1].type.name.equals("poison")){
                            ivPokemonDetailType2.setImageResource(R.drawable.poison);
                        }else if(pokemonApiResponse.types[1].type.name.equals("psychic")){
                            ivPokemonDetailType2.setImageResource(R.drawable.psychc);
                        }else if(pokemonApiResponse.types[1].type.name.equals("rock")){
                            ivPokemonDetailType2.setImageResource(R.drawable.rock);
                        }else if(pokemonApiResponse.types[1].type.name.equals("water")){
                            ivPokemonDetailType2.setImageResource(R.drawable.water);
                        }else{

                            ivPokemonDetailType2.setVisibility(View.GONE);
                            ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.clPokemonDetail);
                            ConstraintSet constraints = new ConstraintSet();
                            constraints.clone(layout);

                            constraints.setHorizontalBias(ivPokemonDetailType1.getId(), 0.5F);

                            constraints.applyTo(layout);
                        }
                    }else{

                        ConstraintLayout layout = (ConstraintLayout) findViewById(R.id.clPokemonDetail);
                        ConstraintSet constraints = new ConstraintSet();
                        constraints.clone(layout);

                        constraints.setHorizontalBias(ivPokemonDetailType1.getId(), 0.5F);

                        constraints.applyTo(layout);
                    }


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