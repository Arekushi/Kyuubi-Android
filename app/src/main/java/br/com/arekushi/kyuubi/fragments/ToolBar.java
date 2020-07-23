package br.com.arekushi.kyuubi.fragments;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import java.util.ArrayList;

import br.com.arekushi.kyuubi.CreateAlertDialog;
import br.com.arekushi.kyuubi.GoogleLogin;
import br.com.arekushi.kyuubi.R;
import br.com.arekushi.kyuubi.WebSiteURL;
import br.com.arekushi.kyuubi.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

public class ToolBar extends Fragment implements PopupMenu.OnMenuItemClickListener {

    Typeface typeface;
    GoogleLogin login;
    WebView webView;
    EditText search;
    PopupMenu menu;
    User user;
    View view;

    int textlen;
    boolean canDeleteAll = false;

    ArrayList<String> listSites;
    int index = 0;

    public ToolBar(WebView webView) {
        this.webView = webView;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toolbar, container, false);
        typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/Font-Awesome.otf");
        login = new GoogleLogin(getContext());
        listSites = new ArrayList<>();
        listSites.add("https://google.com.br");

        getAccount();
        getAccountImage(view);

        addHome();
        addSearchBar();
        addUserAvatar();
        addOptions();
        setWebView();

        ((TextView) view.findViewById(R.id.btn_home)).setTypeface(typeface);
        ((TextView) view.findViewById(R.id.btn_options)).setTypeface(typeface);

        return view;
    }

    private void addHome() {
        view.findViewById(R.id.btn_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl("https://google.com.br");
            }
        });
    }

    private void addSearchBar() {
        search = view.findViewById(R.id.edt_search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_DONE) {
                    String url = textView.getText().toString().trim();
                    url = WebSiteURL.checkURL(url);

                    Toast.makeText(getContext(), url, Toast.LENGTH_SHORT).show();
                    addSite(url);

                    webView.loadUrl(url);
                    return true;
                }

                return false;
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textlen = charSequence.length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                boolean back = textlen > editable.length();

                if(back && canDeleteAll) {
                    search.setText(null);
                    canDeleteAll = false;
                }
            }
        });
    }

    private void addUserAvatar() {
        view.findViewById(R.id.imv_avatar_user).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            new UserDialog(user, getContext());
            }
        });
    }

    private void addOptions() {
        view.findViewById(R.id.btn_options).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            showPopup(view);
            }
        });
    }

    private void getAccount() {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getContext());

        assert acct != null;
        user = new User(acct.getEmail(), acct.getDisplayName(), acct.getPhotoUrl());
    }

    private void getAccountImage(final View view) {
        Picasso.get().load(user.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ((CircleImageView) view.findViewById(R.id.imv_avatar_user)).setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    private void setWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                search.setText(url.substring(8));
                canDeleteAll = true;
            }
        });
    }

    private void addSite(String site) {
        if(!listSites.contains(site)) {
            listSites.add(site);
        }

        index++;
    }

    private void avancar() {
        if(index != listSites.size() - 1 && listSites.size() != 0){
            webView.loadUrl(listSites.get(index + 1));
            index++;
        }
    }

    public void voltar() {
        if(index !=  0) {
            webView.loadUrl(listSites.get(index - 1));
            index--;
        }
    }

    public void showPopup(View v) {
        menu = new PopupMenu(getContext(), v);
        menu.setOnMenuItemClickListener(this);
        menu.inflate(R.menu.menu_dropdown);
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case (R.id.avancar):
                avancar();
                return true;

            case (R.id.voltar):
                voltar();
                return true;

            case (R.id.reload):
                webView.reload();
                return true;

            case (R.id.about):
                new CreateAlertDialog(getContext(), R.layout.fragment_dialog_about).create();
                return true;

            case (R.id.exit):
                login.signOut();
                return true;
        }

        return false;
    }
}
