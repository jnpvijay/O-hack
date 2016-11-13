package com.tm.teameverest.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.tm.teameverest.R;

/**
 * Created by user on 12/11/16.
 */
public class Fragment_Blogs extends Fragment {

    private WebView webView;

    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blogs, null);

        initializeUI(view);

        return view;
    }

    /**
     * current view
     */
    private void initializeUI(View view) {

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        webView = (WebView) view.findViewById(R.id.webview_blogs);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
                if (progress == 100) {
                    progressBar.setVisibility(View.GONE);

                } else {
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://teameverestindia.org/blog/");

    }
}
