package com.wing.tree.sid.sliding.puzzle.view

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.appcompat.app.AppCompatActivity
import com.wing.tree.sid.sliding.puzzle.databinding.ActivityMainBinding
import com.wing.tree.sid.sliding.puzzle.extension.navigationBarHeight
import com.wing.tree.sid.sliding.puzzle.extension.statusBarHeight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        hideSystemUi()

        with(viewBinding.navHostFragment) {
            setPadding(
                paddingLeft,
                paddingTop
                    .plus(statusBarHeight)
                    .plus(navigationBarHeight),
                paddingRight,
                paddingBottom
                    .plus(statusBarHeight)
                    .plus(navigationBarHeight)
            )
        }
    }

    private fun hideSystemUi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)

            window.insetsController?.let {
                val types = WindowInsets.Type.systemBars()

                it.hide(types)
                it.systemBarsBehavior =  WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            @Suppress("DEPRECATION")
            val visibility = View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = visibility
        }
    }
}
