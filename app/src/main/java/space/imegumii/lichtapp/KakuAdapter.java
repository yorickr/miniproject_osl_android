package space.imegumii.lichtapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.List;

/**
 * Created by imegumii on 4/1/16.
 */
public class KakuAdapter extends ArrayAdapter<Kaku>{
    private List<Kaku> kakus;

    public KakuAdapter(Context context, int resource, List<Kaku> objects) {
        super(context, resource, objects);
        this.kakus = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Kaku k = getItem(position);
        if (convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_kaku, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.kaku_name);
        TextView channel = (TextView) convertView.findViewById(R.id.kaku_channel);
        TextView group = (TextView) convertView.findViewById(R.id.kaku_group);
        Button on = (Button) convertView.findViewById(R.id.kaku_on);
        Button off = (Button) convertView.findViewById(R.id.kaku_off);

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k.setState(true);
                MainActivity.api.setValues(k);
            }
        });

        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k.setState(false);
                MainActivity.api.setValues(k);
            }
        });

        name.setText(k.getName());
        channel.setText(k.getChannel());
        group.setText(k.getGroup());
        return convertView;
    }
}
