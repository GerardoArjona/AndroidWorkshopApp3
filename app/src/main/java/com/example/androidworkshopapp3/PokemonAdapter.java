package com.example.androidworkshopapp3;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {

    private Activity activity;
    private ArrayList<Pokemon> items;
    private static LayoutInflater inflater = null;

    public PokemonAdapter (Activity activity, int textViewResourceId,ArrayList<Pokemon> _pokemons) {
        super(activity, textViewResourceId, _pokemons);
        try {
            this.activity = activity;
            this.items = _pokemons;

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public void addPokemonList(ArrayList<Pokemon> pokemonList){
        items.addAll(pokemonList);
        notifyDataSetChanged();
    }

    public int getCount() {
        return items.size();
    }

    public Pokemon getItem(Pokemon position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView display_pokemonName;
        public ImageView display_pokeball;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.single_pokemon, null);
                holder = new ViewHolder();

                holder.display_pokemonName = (TextView) vi.findViewById(R.id.tvSinglePokemonName);
                holder.display_pokeball = (ImageView) vi.findViewById((R.id.ivPokeball));

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            holder.display_pokemonName.setText("#" + String.valueOf(position + 1) + " " + items.get(position).name);
            holder.display_pokeball.setImageResource(R.drawable.pokeball);


        } catch (Exception e) {


        }
        return vi;
    }
}
