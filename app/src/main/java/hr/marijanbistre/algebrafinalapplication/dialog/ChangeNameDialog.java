package hr.marijanbistre.algebrafinalapplication.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import hr.marijanbistre.algebrafinalapplication.MainActivity;
import hr.marijanbistre.algebrafinalapplication.R;

/**
 * Created by Marijan Bistre on 6.8.2017..
 */

public class ChangeNameDialog extends DialogFragment {

    private EditText etInsertName;

    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Name");
        builder.setMessage("Enter your new name: ");
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.text_dialog, null);
        builder.setView(view);
        etInsertName = (EditText) view.findViewById(R.id.etInsertName);
        builder.setCancelable(false);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setPreferences(etInsertName.getText().toString());
                if (etInsertName.getText().toString().trim().length()==0) {
                    mainActivity.setPreferences("guest");
                }
                dismiss();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        return builder.create();
    }

}
