package org.misoton.clear.clearouting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.misoton.clear.clearouting.weather.Weather;

import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowClearTimeFragment extends Fragment {
    private Weather weather;

    public ShowClearTimeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_show_clear_time, container, false);

        Bundle bundle = getArguments();

        try {
            ObjectMapper mapper = new ObjectMapper();
            weather = mapper.readValue(bundle.getString("weather"), Weather.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return v;
    }


}
