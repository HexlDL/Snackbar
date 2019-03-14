package com.hexl.demosnackbar

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Snackbar 使用示例,这里只是简单提供了几种样式,也可以进行改变颜色等功能
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        snackbar_down.setOnClickListener {
            SnackbarUtil.SnackbarBuilder(this, "屏幕下方显示")
                    .show()
        }
        snackbar_up.setOnClickListener {
            SnackbarUtil.SnackbarBuilder(this, "屏幕上方显示")
                    .changeSnackbarMarginGravity(Gravity.TOP)
                    .show()
        }
        snackbar_top.setOnClickListener {
            SnackbarUtil.SnackbarBuilder(this, "指定 view 上方")
                    .changeSnackbarMarginGravity(snackbar_top, Gravity.TOP)
                    .show()
        }
        snackbar_bottom.setOnClickListener {
            SnackbarUtil.SnackbarBuilder(this, "指定 view 下方")
                    .changeSnackbarMarginGravity(snackbar_top, Gravity.BOTTOM)
                    .show()
        }
    }
}
