package com.example.adsxml

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import com.example.adsxml.ads.AdsEntryManager
import com.monetization.composeviews.SdkNativeAd
import com.monetization.composeviews.statefull.nativeAd.SdkNativeViewModel
import com.monetization.core.commons.NativeTemplates
import com.monetization.core.commons.Utils.toShimmerView
import org.koin.androidx.compose.koinViewModel
import video.downloader.remoteconfig.SdkRemoteConfigConstants.toConfigString

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AdsEntryManager.initAds(this)

        setContent {
            val sdkNativeViewModel: SdkNativeViewModel = koinViewModel()
            var showDialog by rememberSaveable {
                mutableStateOf(false)
            }
            if (showDialog) {
                Dialog(onDismissRequest = {
                    sdkNativeViewModel.destroyAdByKey("Test")
                    showDialog = false
                }) {
                    Surface {

                    }
                }
            }
            Column {
                SdkNativeAd(
                    activity = this@ComposeActivity,
                    adLayout = NativeTemplates.LargeNative,
                    adKey = "Main",
                    placementKey = true.toConfigString(),
                    showNewAdEveryTime = true,
                    sdkNativeViewModel = sdkNativeViewModel,
                    showShimmerLayout = com.monetization.nativeads.R.layout.large_native_ad_shimmer.toShimmerView(
                        this@ComposeActivity
                    )
                )
                Button(
                    onClick = {
                        showDialog = true
                    }
                ) {

                }
            }
        }
    }
}