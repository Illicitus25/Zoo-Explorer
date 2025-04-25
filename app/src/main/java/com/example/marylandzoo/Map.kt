package com.example.marylandzoo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView


class Map : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        val webView = view.findViewById<WebView>(R.id.webViewMap)

        webView.settings.javaScriptEnabled = true
        webView.settings.builtInZoomControls = true
        webView.settings.displayZoomControls = false
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true

        // Load local HTML or online URL
        webView.loadUrl("https://www.marylandzoo.org/interactive-map/") // if using local asset
        // or webView.loadUrl("https://your-zoo-map-url.com")

        return view
    }
}