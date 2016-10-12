package in.ac.gectcr.sinfora;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Date;

public class Web extends AppCompatActivity {

    WebView wv;
    Boolean auth = false;
    Date last_time;
    Web web;
    @Override
    protected void onResume() {
        super.onResume();
        if(!auth && (new Date().getTime()-last_time.getTime())/1000 > 10){
            this.finish();

//            Log.i("date",String.valueOf());
//            this.finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        web=this;
        Intent intent = getIntent();
        auth = intent.getBooleanExtra(getString(R.string.login),false);
        if(!auth){
            this.finish();
        }
        CookieManager cookieManager = CookieManager.getInstance();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        cookieManager.setAcceptCookie(true);

        wv = (WebView) findViewById(R.id.webview);
        wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wv.loadUrl("http://sinfora.gectcr.ac.in");
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient() {
            private ProgressDialog mProgress;

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (mProgress == null) {
                    mProgress = new ProgressDialog(web);
                    mProgress.show();
                }
                mProgress.setMessage("Loading " + String.valueOf(progress) + "%");
                if (progress == 100) {
                    mProgress.dismiss();
                    mProgress = null;
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        auth = false;
        last_time = new Date();
//        CookieManager cookieManager = CookieManager.getInstance();
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            CookieSyncManager.createInstance(this);
//        }
//        cookieManager.flush();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && wv.canGoBack()) {
            wv.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }
}
