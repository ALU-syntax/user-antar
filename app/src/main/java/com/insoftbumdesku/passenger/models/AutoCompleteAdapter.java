package com.insoftbumdesku.passenger.models;

import android.content.Context;
import android.graphics.Typeface;
import android.text.style.CharacterStyle;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class AutoCompleteAdapter extends ArrayAdapter<AutocompletePrediction> implements Filterable {

    private ArrayList<AutocompletePrediction> mResultList;
    private PlacesClient placesClient;
    private RectangularBounds mBounds;
    private static final String TAG = "AutoCompleteAdapter";
    private static final CharacterStyle STYLE_BOLD = new StyleSpan(Typeface.BOLD);
    private static final CharacterStyle STYLE_NORMAL = new StyleSpan(Typeface.NORMAL);

    public AutoCompleteAdapter(Context context, PlacesClient placesClient, RectangularBounds bounds) {
        super(context, android.R.layout.simple_expandable_list_item_2, android.R.id.text1);
        this.placesClient = placesClient;
        mBounds = bounds;
    }

    public void setBounds(RectangularBounds bounds) {
        mBounds = bounds;
    }

    @Override
    public int getCount() {
        return mResultList.size(); //error check
    }

    @Override
    public AutocompletePrediction getItem(int position) {
        return mResultList.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View row = super.getView(position, convertView, parent);

        AutocompletePrediction item = getItem(position);

        TextView textView1 = row.findViewById(android.R.id.text1);
        TextView textView2 = row.findViewById(android.R.id.text2);
        if (item != null) {
            textView1.setText(item.getPrimaryText(STYLE_BOLD));
            textView2.setText(item.getSecondaryText(STYLE_NORMAL));
        }

        return row;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                // We need a separate list to store the results, since
                // this is run asynchronously.
                ArrayList<AutocompletePrediction> filterData = new ArrayList<>();
                // Skip the autocomplete query if no constraints are given.
                if (charSequence != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    filterData = getAutocomplete(charSequence);
                }
                results.values = filterData;
                if (filterData != null) {
                    results.count = filterData.size();
                } else {
                    results.count = 0;
                }
                return results;
            }


            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {

                if (results != null && results.count > 0) {
                    // The API returned at least one result, update the data.
                    mResultList = (ArrayList<AutocompletePrediction>) results.values;
                    notifyDataSetChanged();
                } else {
                    // The API did not return any results, invalidate the data set.
                    notifyDataSetInvalidated();
                }
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                // Override this method to display a readable result in the AutocompleteTextView
                // when clicked.
                if (resultValue instanceof AutocompletePrediction) {
                    return ((AutocompletePrediction) resultValue).getFullText(null);
                } else {
                    return super.convertResultToString(resultValue);
                }
            }
        };
    }


    private ArrayList<AutocompletePrediction> getAutocomplete(CharSequence constraint) {
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
        //Create a RectangularBounds object.
        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(-33.880490, 151.184363),
                new LatLng(-33.858754, 151.229596));

        FindAutocompletePredictionsRequest.Builder requestBuilder =
                FindAutocompletePredictionsRequest.builder()
                        .setQuery(constraint.toString())
                        .setSessionToken(token)
                        .setCountry("");

        Task<FindAutocompletePredictionsResponse> results =
                placesClient.findAutocompletePredictions(requestBuilder.build());

        //Wait to get results.
        try {
            Tasks.await(results, 60, TimeUnit.SECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
        }

        if (results.isSuccessful()) {
            //Toast.makeText(getContext(), "Success: " + results.toString(),Toast.LENGTH_LONG).show();
            if (results.getResult() != null) {
                return (ArrayList<AutocompletePrediction>) results.getResult().getAutocompletePredictions();
            }
            return null;
        } else {
            //  Toast.makeText(getContext(), "Contacting API: " + results.toString(),Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}