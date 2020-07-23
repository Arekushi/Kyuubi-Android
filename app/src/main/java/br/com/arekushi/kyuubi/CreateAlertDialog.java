package br.com.arekushi.kyuubi;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class CreateAlertDialog {

    private AlertDialog dialog;
    private View view;
    private Context context;

    public CreateAlertDialog(Context context, int drawable) {
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(drawable, null);
    }

    public void create() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        dialog = builder.show();
    }

    public void closeAlert() {
        dialog.dismiss();
    }

    public View getView() {
        return view;
    }
}
