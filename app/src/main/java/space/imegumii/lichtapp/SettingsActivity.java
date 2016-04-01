package space.imegumii.lichtapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        EditText hostfield = (EditText) findViewById(R.id.hostfield);
        EditText portfield = (EditText) findViewById(R.id.portfield);
        EditText userfield = (EditText) findViewById(R.id.userfield);
        EditText passfield = (EditText) findViewById(R.id.passfield);
        hostfield.setText(MainActivity.settings.getString("hostname", ""));
        portfield.setText(String.valueOf(MainActivity.settings.getInt("port", 8080)));
        userfield.setText(MainActivity.settings.getString("username", ""));
        passfield.setText(MainActivity.settings.getString("password", ""));
    }

    public void register_onclick(View v) {
        MainActivity.api.registerName(null);
        MainActivity.api.login();
    }

    public void save_onclick(View v) {
        SharedPreferences.Editor e = MainActivity.settings.edit();
        e.putString("hostname", ((EditText) findViewById(R.id.hostfield)).getText().toString());
        e.putInt("port", Integer.valueOf(((EditText) findViewById(R.id.portfield)).getText().toString()));
        e.putString("username", ((EditText) findViewById(R.id.userfield)).getText().toString());
        e.putString("password", ((EditText) findViewById(R.id.passfield)).getText().toString());
        e.commit();
    }
}
