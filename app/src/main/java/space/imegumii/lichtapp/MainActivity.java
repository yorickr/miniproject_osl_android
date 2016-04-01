package space.imegumii.lichtapp;

import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String PREFS_NAME = "LichtPrefs";
    public static SharedPreferences settings;
    public static APIHandler api;
    public static MainActivity main;
    ListView lv;
    ArrayList<Kaku> kakus;
    KakuAdapter kakAdapter;


    public void showDialog(Kaku k) {
        DialogFragment newFragment = new KakuEditDialog();
        Bundle b = new Bundle();
        b.putInt("pos", kakus.indexOf(k));
        newFragment.setArguments(b);
        newFragment.show(getFragmentManager(), "kaku");
    }

    public void initializeHandlers() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialog(kakus.get(position));
                System.out.println("Item number " + position + " clicked!");
            }
        });
    }

    public void initializeFields() {
        //init settings before API
        main = this;
        settings = getSharedPreferences(PREFS_NAME, 0);
        api = new APIHandler(this, settings.getString("hostname", "145.48.205.40"), settings.getInt("port", 8080));

        lv = (ListView) findViewById(R.id.kaku_listview);
        kakus = new ArrayList<>();
        kakAdapter = new KakuAdapter(this, R.layout.item_kaku, kakus);


    }

    public void initialLogic() {
        lv.setAdapter(kakAdapter);
        if (MainActivity.settings.getString("username", "").equals("") || MainActivity.settings.getString("password", "").equals("")) {
            //Information not set, redirect to settings
            System.out.println("Information not set");
            Intent i = new Intent(MainActivity.main, SettingsActivity.class);
            MainActivity.main.startActivity(i);
        } else {
            if (!MainActivity.settings.getBoolean("registered", false)) {
                api.registerName(null);
            }

            if (MainActivity.settings.getString("apikey", "").equals("")) {

                api.login();

            }
            else
            {
                api.getLights();
            }

        }
    }

    public void refresh_onclick(View v) {
        api.getLights();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeFields();
        initializeHandlers();
        initialLogic();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    public void setKakus(ArrayList<Kaku> kakus) {
        this.kakus.clear();
        for (Kaku k : kakus) {
            this.kakus.add(k);
        }
        kakAdapter.notifyDataSetChanged();

    }

}