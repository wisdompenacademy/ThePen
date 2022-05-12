package com.thepen.thepen.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thepen.thepen.R;
import com.thepen.thepen.ThePen;

public class WebFragment  extends Fragment {
    WebView webView;
    View view;
    private static final String URL = "file:///android_asset/info/images/export_via_email.html";
    private String url;
    public WebFragment(String url) {
        this.url=url;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag_web, container, false);
        webView = view.findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/"+url);
        return view;
    }
}
