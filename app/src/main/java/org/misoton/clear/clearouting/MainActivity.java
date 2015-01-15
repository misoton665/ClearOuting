package org.misoton.clear.clearouting;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<String>, View.OnClickListener {

    private EditText where_et;
    private int id_count = 0;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String testData = "{\"cod\":\"200\",\"message\":0.0045,\n" +
                "\"city\":{\"id\":1851632,\"name\":\"Shuzenji\",\n" +
                "\"coord\":{\"lon\":138.933334,\"lat\":34.966671},\n" +
                "\"country\":\"JP\"},\n" +
                "\"cnt\":38,\n" +
                "\"list\":[{\n" +
                "        \"dt\":1406106000,\n" +
                "        \"main\":{\n" +
                "            \"temp\":298.77,\n" +
                "            \"temp_min\":298.77,\n" +
                "            \"temp_max\":298.774,\n" +
                "            \"pressure\":1005.93,\n" +
                "            \"sea_level\":1018.18,\n" +
                "            \"grnd_level\":1005.93,\n" +
                "            \"humidity\":87},\n" +
                "        \"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\n" +
                "        \"clouds\":{\"all\":88},\n" +
                "        \"wind\":{\"speed\":5.71,\"deg\":229.501},\n" +
                "        \"sys\":{\"pod\":\"d\"},\n" +
                "        \"dt_txt\":\"2014-07-23 09:00:00\"}\n" +
                "        ]}";


        Typeface tf = Typeface.createFromAsset(this.getAssets(), "satellite.ttf");

        TextView where_tv = (TextView)this.findViewById(R.id.main_where_tv);
        where_tv.setTypeface(tf);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setLogo(R.drawable.clear_outing);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_background_res));

        where_et = (EditText) this.findViewById(R.id.main_where_et);

        Button search_bt = (Button) this.findViewById(R.id.main_search_bt);
        search_bt.setOnClickListener(this);
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        WeatherApiLoader loader = new WeatherApiLoader(getApplication());
        loader.setLocation(where_et.getText().toString());
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.d("Main", data);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_search_bt:
                getLoaderManager().initLoader(id_count++, null, this);
                dialog = new ProgressDialog(this, R.style.MyDialog);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage(getString(R.string.text_connection_dialog_message));
                dialog.show();
                break;
            default:
        }
    }
}
