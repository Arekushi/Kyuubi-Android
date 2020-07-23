package br.com.arekushi.kyuubi.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.EditText;
import br.com.arekushi.kyuubi.R;
import br.com.arekushi.kyuubi.fragments.ToolBar;

public class MainActivity extends AppCompatActivity {

    EditText search;
    ToolBar bar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final WebView webView = findViewById(R.id.main_webview);

        search = findViewById(R.id.toolbar_search);
        bar = new ToolBar(webView);

        if(savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frame_toolbar, bar, "toolbar")
                    .commit();
        }

        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(1);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://google.com.br");
    }

    @Override
    public void onBackPressed() {
        bar.voltar();
    }
}