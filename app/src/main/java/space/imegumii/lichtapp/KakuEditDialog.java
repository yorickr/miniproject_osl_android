package space.imegumii.lichtapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by imegumii on 4/1/16.
 */
public class KakuEditDialog extends DialogFragment {
    Kaku k;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        k = MainActivity.main.kakus.get(getArguments().getInt("pos"));

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View customView = inflater.inflate(R.layout.kaku_dialog, null);

        ((EditText) customView.findViewById(R.id.kaku_dialog_channel)).setText(k.getChannel());
        ((EditText) customView.findViewById(R.id.kaku_dialog_group)).setText(k.getGroup());
        ((EditText) customView.findViewById(R.id.kaku_dialog_name)).setText(k.getName());
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(customView)
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Edit succesful, write.

                        k.setChannel(((EditText) customView.findViewById(R.id.kaku_dialog_channel)).getText().toString());
                        k.setGroup(((EditText) customView.findViewById(R.id.kaku_dialog_group)).getText().toString());
                        k.setName(((EditText) customView.findViewById(R.id.kaku_dialog_name)).getText().toString());
                        k.sync();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        //Nope, no write
                    }
                });

        return builder.create();
    }
}
