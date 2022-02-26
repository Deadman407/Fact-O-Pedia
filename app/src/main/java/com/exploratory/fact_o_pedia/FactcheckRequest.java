package com.exploratory.fact_o_pedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class FactcheckRequest extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factcheck_request);

        Bundle bundle = getIntent().getExtras();
        String query = bundle.getString("q");

        webView = findViewById(R.id.webview2);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com/search?q=" + query);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }
}