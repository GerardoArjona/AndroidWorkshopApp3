package com.example.androidworkshopapp3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private int offset = 0;
    private boolean readyToFetch = true;
    PokemonAdapter adapter;
    ArrayList<Pokemon> items = new ArrayList<Pokemon>();
    private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new PokemonAdapter(this, 0, items);
        spinner = (ProgressBar)findViewById(R.id.fetchingApiProgressBarSinglePokemon);

        retrofit = new Retrofit.Builder()
                .baseUrl("http://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        getPokemonWithOffset(offset);


        ListView listView = (ListView) findViewById(R.id.pokemon_list);
        listView.setAdapter(adapter);


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE
                        && (listView.getLastVisiblePosition() - listView.getHeaderViewsCount() -
                        listView.getFooterViewsCount()) >= (adapter.getCount() - 1)) {

                    if(readyToFetch == true && offset < 140){
                        readyToFetch = false;
                        offset = offset + 20;

                        getPokemonWithOffset(offset);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent pokemonDetail = new Intent(getBaseContext(), PokemonDetail.class);
                //Log.e("POKEMON", "pokNum main activity: " + String.valueOf(items.get(position).getNumber()));
                pokemonDetail.putExtra("POK_NUMBER", String.valueOf(items.get(position).getNumber()));
                startActivity(pokemonDetail);
            }
        });
    }

    private void getPokemonWithOffset(int off) {
        readyToFetch = false;
        spinner.setVisibility(View.VISIBLE);
        PokeApiService service = retrofit.create(PokeApiService.class);
        Call<PokeApiResponse> pokemonResponseCall = service.getPokemonList(20, off);

        pokemonResponseCall.enqueue(new Callback<PokeApiResponse>() {
            @Override
            public void onResponse(Call<PokeApiResponse> call, Response<PokeApiResponse> response) {
                readyToFetch = true;
                spinner.setVisibility(View.GONE);
                if(response.isSuccessful()){

                    PokeApiResponse pokemonApiResponse = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonApiResponse.getResults();

                    // for(int i = 0; i < pokemonList.size(); i++){
                    //    Pokemon pokemon = pokemonList.get(i);
                    //    Log.e("POKEMON", "onResponse: " + pokemon.getName());
                    // }

                    adapter.addPokemonList(pokemonList);

                }else{
                    Log.e("POKEMON", "onResponseError: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokeApiResponse> call, Throwable t) {
                readyToFetch = true;
                Log.e("POKEMON", "onFailure: " + t.getMessage());
            }
        });
    }
}