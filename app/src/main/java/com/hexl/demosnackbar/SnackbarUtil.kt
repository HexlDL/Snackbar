package com.hexl.demosnackbar


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast.LENGTH_SHORT

/**
 * @desc 提示框
 * @author Hexl
 * @date 2019/1/7
 */
class SnackbarUtil {
    private var snackbar: Snackbar
    private var context: Context

    private constructor(context: Context, resId: String) {
        this.context = context
        snackbar = Snackbar.make((context as Activity).findViewById(android.R.id.content), resId, LENGTH_SHORT)
    }

    private constructor(context: Context, resId: String, duration: Int) {
        this.context = context
        snackbar = Snackbar.make((context as Activity).findViewById(android.R.id.content), resId, duration)
    }

    /**
     * 设置显示时间
     *
     * @param duration [com.air.basemvp_kotlin.utils.android.SnackbarUtil.setDuration], 自定义时间 in milliseconds.
     */
    private fun setDuration(duration: Int) {
        snackbar.duration = duration
    }

    /**
     * 设置右侧文本和监听
     * listener 可为null,默认执行 Snackbar.dismiss();
     *
     * @param text     文本内容
     * @param listener 监听器
     */
    private fun setActionText(@StringRes text: Int, listener: OnActionClickListener?) {
        snackbar.setAction(text, { v ->
            if (listener != null) {
                listener.onClick(v)
            } else {
                snackbar.dismiss()
            }
        })
    }

    /**
     * 设置右侧文本颜色(actionText)
     *
     * @param color 颜色值
     */
    private fun setActionTextColor(color: Int) = snackbar.setActionTextColor(color)

    /**
     * 设置文本
     *
     * @param text 文字
     */
    private fun setText(@StringRes text: Int) = snackbar.setText(text)

    /**
     * 改变背景Drawable和文本color
     *
     * @param drawable 背景Drawable对象
     * @param color    文本颜色
     */
    private fun changeBgAndTextColor(drawable: Drawable?, color: Int) {
        val snackbarView = snackbar.view
        snackbarView.background = drawable
        val text = snackbarView.findViewById<TextView>(R.id.snackbar_text)
        text.setTextColor(color)
    }

    /**
     * 改变背景颜色
     *
     * @param color 背景颜色
     */
    private fun changeBackgroundColor(color: Int) = snackbar.view.setBackgroundColor(color)

    /**
     * 改变背景drawable
     *
     * @param resource 背景drawable
     */
    private fun changeBackgroundResource(resource: Int) = snackbar.view.setBackgroundResource(resource)

    /**
     * 改变text文字颜色
     *
     * @param color 文本颜色
     */
    private fun changeTextColor(color: Int) = snackbar.view.findViewById<TextView>(R.id.snackbar_text).setTextColor(color)

    /**
     * 改变弹出位置,从屏幕的上方或下方弹出
     *
     * @param gravity 建议设置为 Gravity.TOP or Gravity.BOTTOM
     */
    private fun changeSnackbarMarginGravity(gravity: Int) {
        val params = FrameLayout.LayoutParams(snackbar.view.layoutParams)
        params.gravity = gravity
        params.setMargins(48, 0, 48, 0)
        snackbar.view.layoutParams = params
    }

    /**
     * 自定义snackbar显示屏幕上的任何位置
     * 该功能目前仅支持 上 、下
     * `
     * new SnackbarUtil.SnackbarBuilder(this, llView, "显示在某个指定view的上方")
     * .changeSnackbarMarginGravity(btnAssignation, Gravity.TOP)
     * .setActionTextConfig(R.string.title_text, view1 ->
     * Toast.makeText(this, "点击Action", Toast.LENGTH_SHORT).show())
     * .show()
    ` *
     *
     * @param view    要显示该view的哪个位置(上、下、左、右)
     * @param gravity Gravity.BOTTOM,Gravity.TOP
     */
    private fun changeSnackbarMarginGravity(view: View, gravity: Int) {
        val params = FrameLayout.LayoutParams(snackbar.view.layoutParams)
        // snackbar的高度
        val snackbarHeight = calculateSnackBarHeight()
        var viewBound = -1
        when (gravity) {
            Gravity.BOTTOM ->
                // view下边界距离 + view 自身height + view padding 值
                viewBound = view.bottom + view.height + view.paddingBottom + view.paddingTop
            // view 下边界 - Snackbar 自身 height
            Gravity.TOP -> viewBound = view.top - snackbarHeight
        }
        params.setMargins(48, viewBound, 48, 0)
        snackbar.view.layoutParams = params
    }

    /**
     * 计算单行的Snackbar的高度值(单位 pix)
     *
     * @return
     */
    private fun calculateSnackBarHeight(): Int {
        /*
        <TextView
                android:id="@+id/snackbar_text"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_weight="1"
                android:paddingTop="@dimen/design_snackbar_padding_vertical"
                android:paddingBottom="@dimen/design_snackbar_padding_vertical"
                android:paddingLeft="@dimen/design_snackbar_padding_horizontal"
                android:paddingRight="@dimen/design_snackbar_padding_horizontal"
                android:textAppearance="@style/TextAppearance.Design.Snackbar.Message"
                android:maxLines="@integer/design_snackbar_text_max_lines"
                android:layout_gravity="center_vertical|left|start"
                android:ellipsize="end"
                android:textAlignment="viewStart"/>
        */
        //文字高度+paddingTop+paddingBottom : 14sp + 14dp*2
        return (84 + 28).toInt()
    }

    private fun show() {
        snackbar.show()
    }

    /**
     * 设置阴影部分高度
     *
     * @param elevation
     */
    private fun setElevation(elevation: Int) {
        snackbar.view.elevation = elevation.toFloat()
    }

    /**
     * 系统提示
     * 提供给框架使用
     */
    private fun systemPrompt(color: Int) {
        changeBackgroundColor(color)
        changeTextColor(Color.WHITE)
    }

    /**
     * Action 提示
     */
    private fun setActionTextConfig(@StringRes text: Int, listener: OnActionClickListener) {
        setActionText(text, listener)
    }

    class SnackbarBuilder {
        private var snackbarUtil: SnackbarUtil

        constructor(context: Context, resId: String) {
            snackbarUtil = SnackbarUtil(context, resId)
            snackbarUtil.changeSnackbarMarginGravity(Gravity.BOTTOM)
            snackbarUtil.setElevation(6)// 与UI约定的值,不建议更换
        }

        constructor(context: Context, resId: String, duration: Int) {
            snackbarUtil = SnackbarUtil(context, resId, duration)
        }

        fun setDuration(duration: Int): SnackbarBuilder {
            snackbarUtil.setDuration(duration)
            return this
        }

        fun setActionText(@StringRes text: Int, listener: OnActionClickListener): SnackbarBuilder {
            snackbarUtil.setActionText(text, listener)
            return this
        }

        fun setActionTextColor(color: Int): SnackbarBuilder {
            snackbarUtil.setActionTextColor(color)
            return this
        }

        fun setText(@StringRes text: Int): SnackbarBuilder {
            snackbarUtil.setText(text)
            return this
        }

        fun changeBgAndTextColor(drawable: Drawable, color: Int): SnackbarBuilder {
            snackbarUtil.changeBgAndTextColor(drawable, color)
            return this
        }

        fun changeBackgroundColor(color: Int): SnackbarBuilder {
            snackbarUtil.changeBackgroundColor(color)
            return this
        }

        fun changeTextColor(color: Int): SnackbarBuilder {
            snackbarUtil.changeTextColor(color)
            return this
        }

        /**
         * 改变弹出位置
         *
         * @param gravity 建议设置为 Gravity.TOP or Gravity.BOTTOM
         */
        fun changeSnackbarMarginGravity(gravity: Int): SnackbarBuilder {
            snackbarUtil.changeSnackbarMarginGravity(gravity)
            return this
        }

        /**
         * 自定义snackbar显示屏幕上的任何位置
         * 该功能目前仅支持 上 、下
         */
        fun changeSnackbarMarginGravity(view: View, gravity: Int): SnackbarBuilder {
            snackbarUtil.changeSnackbarMarginGravity(view, gravity)
            return this
        }

        /**
         * 设置阴影部分高度
         */
        fun setElevation(elevation: Int): SnackbarBuilder {
            snackbarUtil.setElevation(elevation)
            return this
        }

        /**
         * 改变背景drawable
         *
         * @param resource 背景drawable
         */
        fun changeBackgroundResource(resource: Int): SnackbarBuilder {
            snackbarUtil.changeBackgroundResource(resource)
            return this
        }

        /**
         * Action 提示
         */
        fun setActionTextConfig(@StringRes text: Int, listener: OnActionClickListener): SnackbarBuilder {
            snackbarUtil.setActionTextConfig(text, listener)
            return this
        }

        fun systemPrompt(color: Int): SnackbarBuilder {
            snackbarUtil.systemPrompt(color)
            return this
        }

        fun show() {
            snackbarUtil.show()
        }

        fun build() {
            snackbarUtil.show()
        }
    }

    interface OnActionClickListener {
        fun onClick(view: View)
    }
}