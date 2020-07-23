package br.com.arekushi.kyuubi.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import br.com.arekushi.kyuubi.CreateAlertDialog;
import br.com.arekushi.kyuubi.R;
import br.com.arekushi.kyuubi.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserDialog {

    public UserDialog(User user, final Context context) {
        int drawable = R.layout.fragment_dialog_person;
        final CreateAlertDialog alertDialog = new CreateAlertDialog(context, drawable);

        alertDialog.getView().findViewById(R.id.btn_close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.closeAlert();
            }
        });

        getAccountImage(alertDialog.getView(), user);
        ((TextView) alertDialog.getView().findViewById(R.id.txv_user_dialog)).setText(user.getName());
        ((TextView) alertDialog.getView().findViewById(R.id.txv_email_dialog)).setText(user.getEmail());

        alertDialog.create();
    }

    private void getAccountImage(final View view, User user) {
        Picasso.get().load(user.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ((CircleImageView) view.findViewById(R.id.imv_avatar_user_dialog)).setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

}
