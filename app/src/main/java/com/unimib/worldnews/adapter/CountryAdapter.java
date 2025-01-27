package com.unimib.worldnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;
import com.unimib.worldnews.R;
import com.unimib.worldnews.model.Country;
import com.unimib.worldnews.util.Constants;
import com.unimib.worldnews.util.SharedPreferencesUtils;

import java.util.ArrayList;

//CountryAdapter è una classe che estende ArrayAdapter e gestisce oggetti di tipo Country
public class CountryAdapter extends ArrayAdapter<Country> {

    private int layout;
    private ArrayList<Country> countriesList;

    /*il parametro layout è il layout della card che rappresenta un country (card_country.xml)*/
    public CountryAdapter(@NonNull Context context, @NonNull int layout, @NonNull ArrayList<Country> countriesList) {
        super(context, layout, countriesList);
        this.layout = layout;
        this.countriesList = countriesList;
    }


    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(layout, parent, false); //Creo la view della carta

        /*Estraggo i campi title e imageView dalla carta per riempirli*/
        TextView title = convertView.findViewById(R.id.textView);
        ImageView imageView = convertView.findViewById(R.id.imageView);

        title.setText(countriesList.get(position).getName());
        imageView.setImageDrawable(countriesList.get(position).getImage());

        //faccio il cast da View a MaterialCardView
        MaterialCardView cardView = (MaterialCardView) convertView;

        //al click salvo la nazione di interesse nelle SharedPreferences e navigo verso il fragment delle categorie
        cardView.setOnClickListener(view -> {
            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils(getContext());

            sharedPreferencesUtils.writeStringData(Constants.SHARED_PREFERENCES_FILENAME,
                    Constants.SHARED_PREFERENCES_COUNTRY_OF_INTEREST,
                    countriesList.get(position).getCode());

            Navigation.findNavController(view).navigate(R.id.action_pickCountryFragment_to_pickCategoriesFragment);
        });

        return convertView;
    }
}
