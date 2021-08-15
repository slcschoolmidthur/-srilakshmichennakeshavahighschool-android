package com.srilakshmichennakeshavahighschoolmidthur;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.view.Window;
import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

class DetectConnection {
    public static boolean checkInternetConnection(Context context) {

        ConnectivityManager con_manager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected());
    }
}

public class MainActivity extends Activity {

    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // START -- copy from here
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // END -- copy till here

        setContentView(R.layout.activity_main);

        CustomWebViewClient client = new CustomWebViewClient(this);
        webView = findViewById(R.id.webView);
        webView.setWebViewClient(client);
/*
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccess(true);

        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webView.loadUrl("file:///error.html");

            }
        });

 */
        webView.getSettings().setJavaScriptEnabled(true);
        if (!DetectConnection.checkInternetConnection(this)) {
            Toast.makeText(getApplicationContext(), "No Internet!", Toast.LENGTH_LONG).show();
        } else {
            try {
                webView.loadUrl("https://slc-school.com/post");
            } catch (Exception e) {

            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK && this.webView.canGoBack()){
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}

class CustomWebViewClient extends WebViewClient{


    private Activity activity;


    public CustomWebViewClient(Activity activity){
        this.activity = activity;
    }



    //API level less than 24
    @SuppressWarnings("deprecation")
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {

        if (!DetectConnection.checkInternetConnection(webView.getContext())) {
            Toast.makeText(webView.getContext(), "No Internet!", Toast.LENGTH_LONG).show();
            return true;
        } else {

            if (url.contains("slc-school.com")) {
                //do what you want here
                return false;
            } else if (url.contains("facebook.com")) {
                try {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    facebookIntent.setData(Uri.parse("fb://facewebmodal/f?href=" + url));
                    webView.getContext().startActivity(facebookIntent);
                    return true;
                } catch (Exception e) {
                    webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    //    intent.setPackage("com.whatsapp");
                    webView.getContext().startActivity(intent);
                    return true;
                } catch (Exception e) {
                    return true;
                }

            }
        }
    }


    //API level greater than= 24
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, WebResourceRequest request) {

        String url = request.getUrl().toString();

        if (!DetectConnection.checkInternetConnection(webView.getContext())) {
            Toast.makeText(webView.getContext(), "No Internet!", Toast.LENGTH_LONG).show();
            return true;
        } else {

            if (url.contains("slc-school.com")) {
                //do what you want here
                return false;
            } else if (url.contains("facebook.com")) {
                try {
                    Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                    facebookIntent.setData(Uri.parse("fb://facewebmodal/f?href=" + url));
                    webView.getContext().startActivity(facebookIntent);
                    return true;
                } catch (Exception e) {
                    webView.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    //    intent.setPackage("com.whatsapp");
                    webView.getContext().startActivity(intent);
                    return true;
                } catch (Exception e) {
                    return true;
                }

            }

        }


    }




}

