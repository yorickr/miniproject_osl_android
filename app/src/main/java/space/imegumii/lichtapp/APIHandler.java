package space.imegumii.lichtapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by imegumii on 9-12-15.
 */
public class APIHandler {
    public static String apikey;
    private String hostname;
    private int port;
    private MainActivity parent;

    public APIHandler(MainActivity parent, String hostname, int port) {
        this.parent = parent;
        this.hostname = hostname;
        this.port = port;
        if (MainActivity.settings.getString("apikey", null) != null) {
            apikey = MainActivity.settings.getString("apikey", "");
        }
    }

    public void showToast(String msg){
        Context context = MainActivity.main.getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void login() {
        if (!MainActivity.settings.getBoolean("registered", false)) {
            //Must register first!
            showToast("Must register first!");
            return;
        }
        JSONObject o = new JSONObject();
        try {
            o.put("username", MainActivity.settings.getString("username", "invalid"));
            o.put("password", MainActivity.settings.getString("password", "invalid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println("Sending " + o.toString() + " in login");

        new NetworkHandler(NetworkHandler.Requests.Post, new TaskListener() {
            @Override
            public void onFinished(String result) {
                System.out.println(result);
                try {
                    JSONObject x = new JSONObject(result);
                    MainActivity.settings.edit().putString("apikey", x.getString("token")).commit();
                    showToast("Login succesful!");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).setJson(o.toString()).execute("http://" + hostname + ":" + port + "/api/login");
    }

    public void getCurrentWeather() {
        new NetworkHandler(NetworkHandler.Requests.Get, new TaskListener() {
            @Override
            public void onFinished(String result) {
                try {
                    JSONObject o = new JSONObject(result);
                    JSONArray weather = o.getJSONArray("weather");
                    int weatherId = weather.getJSONObject((int) Math.floor(Math.random() * weather.length())).getInt("id");
                    System.out.println(weatherId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).execute("http://api.openweathermap.org/data/2.5/weather?id=2758401&appid=35ecaf59475b958644a56e07b6ac0700");
    }

    public void setValues(Kaku k) {
        JSONObject o = new JSONObject();
        new NetworkHandler(NetworkHandler.Requests.Get, new TaskListener() {
            @Override
            public void onFinished(String result) {
                System.out.println(result);
            }
        }).setJson(o.toString()).execute("http://" + hostname + ":" + port + "/api/light/" + k.getName() + "/" + (k.getState() ? "on" : "off"));
    }

    public void updateKaku(Kaku k){
        JSONObject o = new JSONObject();

        try {
            o.put("name", k.getName());
            o.put("group", k.getGroup());
            o.put("channel", k.getChannel());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new NetworkHandler(NetworkHandler.Requests.Put, new TaskListener() {
            @Override
            public void onFinished(String result) {
                System.out.println(result);
            }
        }).setJson(o.toString()).execute("http://" + hostname + ":" + port + "/api/light");
    }

    public void setLight(int id, boolean on) {
        JSONObject o = new JSONObject();
        new NetworkHandler(NetworkHandler.Requests.Get, new TaskListener() {
            @Override
            public void onFinished(String result) {
                System.out.println(result);
            }
        }).setJson(null).execute("http://" + hostname + ":" + port + "/api/light/" + id + "/" + (on ? "on" : "off"));

    }

    public void getLights() {
        new NetworkHandler(NetworkHandler.Requests.Get, new TaskListener() {
            @Override
            public void onFinished(String result) {
                try {
                    JSONArray o = new JSONArray(result);
                    ArrayList<Kaku> kakus = new ArrayList<Kaku>();
                    System.out.println(o.toString());
                    JSONArray lights = o.getJSONObject(0).getJSONArray("lights");
                    for(int i = 0; i < lights.length();i++) {
                        JSONObject kaku = lights.getJSONObject(i);
                        kakus.add(new Kaku(kaku.getString("group"), kaku.getString("name"), kaku.getString("channel")));
                    }
                    MainActivity.main.setKakus(kakus);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("http://" + hostname + ":" + port + "/api/lights");
    }

    public void registerName(final TaskListener taskListener) {
        JSONObject o = new JSONObject();
        try {
            o.put("username", MainActivity.settings.getString("username", "invalid"));
            o.put("password", MainActivity.settings.getString("password", "invalid"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("Sending " + o.toString() + " in register");

        new NetworkHandler(NetworkHandler.Requests.Post, new TaskListener() {
            @Override
            public void onFinished(String result) {
                try {
                    System.out.println(result);
                    JSONArray o = new JSONArray(result);
                    if (o.getJSONObject(0).getBoolean("success")) {
                        //Registered succesfully! Write to config
                        System.out.println("Registered succesfully!");
                        MainActivity.settings.edit().putBoolean("registered", true).commit();
                        return;
                    }
                    else {
                        MainActivity.settings.edit().putBoolean("registered", false).commit();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).setJson(o.toString()).execute("http://" + hostname + ":" + port + "/api/register");

    }

    public void registerName() {
    }

}