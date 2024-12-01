package com.example.passwordchek;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class donation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.donation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            TextView backtext = findViewById(R.id.backtext);
            backtext.setOnClickListener(v1 -> finish());

            WebView myWebView = findViewById(R.id.myWebView);

            String url = "https://buy.stripe.com/test_8wMcNF7RD0lG4ow9AA";
            myWebView.setWebViewClient(new WebViewClient()); // Keeps navigation within the WebView
            myWebView.loadUrl(url);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.getSettings();
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}