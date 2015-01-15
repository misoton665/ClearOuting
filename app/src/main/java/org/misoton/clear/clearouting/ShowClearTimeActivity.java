package org.misoton.clear.clearouting;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.misoton.clear.clearouting.weather.Weather;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ShowClearTimeActivity extends ActionBarActivity {
    private List<Weather> weatherList;
    private int cnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_clear_time);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.clear_outing);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background_res));

        try {
            Intent intent = getIntent();
            ObjectMapper mapper = new ObjectMapper();
            weatherList = mapper.readValue(intent.getStringExtra("list"), new TypeReference<List<Weather>>() {});
            for(Weather weather: weatherList){
                String weather_condition = weather.weather.main.toLowerCase();
                if(!(weather_condition.equals("clear") || weather_condition.equals("clouds"))){
                    weatherList.remove(weather);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            finish();
        }

        ViewPager viewPager = (ViewPager) this.findViewById(R.id.show_pager);
        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager()));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return weatherList.get(position).dt + "";
            return "unk";
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;

            try {
                ObjectMapper mapper = new ObjectMapper();
                Bundle bundle = new Bundle();
                bundle.putString("weather", mapper.writeValueAsString(weatherList.get(position)));
                fragment = new ShowClearTimeFragment();
                fragment.setArguments(bundle);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                finish();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return weatherList.size();
        }
    }

}
