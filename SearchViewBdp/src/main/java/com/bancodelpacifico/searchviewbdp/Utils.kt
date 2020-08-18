package com.bancodelpacifico.searchviewbdp

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.ViewPropertyAnimatorListener
import kotlin.math.roundToInt

class Utils {
    companion object{
        fun showInputMethod(view: View): Boolean {
            val imm: InputMethodManager = view.context.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            return imm.showSoftInput(view, 0)
        }

        fun hideInputMethod(view: View): Boolean {
            val imm: InputMethodManager = view.context.getSystemService(
                Context.INPUT_METHOD_SERVICE
            ) as InputMethodManager
            return imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        // Animations Utils
        class AnimationCallback {
            fun onAnimationEnd() {}
            fun onAnimationCancel() {}
        }

        val DEFAULT_DURATION: Int = -1
        const val NO_DELAY = 0
        fun fadeIn(fadeIn: View, durationMs: Int) {
            fadeIn(fadeIn, durationMs, NO_DELAY, null)
        }

        private fun fadeIn(
            fadeIn: View, durationMs: Int, delay: Int,
            callback: AnimationCallback?
        ) {
            fadeIn.alpha = 0f
            val animator = ViewCompat.animate(fadeIn)
            animator.cancel()
            animator.startDelay = delay.toLong()
            animator.alpha(1f).withLayer().setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View) {
                    fadeIn.visibility = View.VISIBLE
                }

                override fun onAnimationCancel(view: View) {
                    fadeIn.alpha = 1f
                    callback?.onAnimationCancel()
                }

                override fun onAnimationEnd(view: View) {
                    callback?.onAnimationEnd()
                }
            })
            if (durationMs != DEFAULT_DURATION) {
                animator.duration = durationMs.toLong()
            }
            animator.start()
        }
        fun fadeOut(fadeOut: View, duration: Int) {
            fadeOut(fadeOut, duration, null)
        }

        private fun fadeOut(
            fadeOut: View, durationMs: Int,
            callback: AnimationCallback?
        ) {
            fadeOut.alpha = 1f
            val animator = ViewCompat.animate(fadeOut)
            animator.cancel()
            animator.alpha(0f).withLayer().setListener(object : ViewPropertyAnimatorListener {
                override fun onAnimationStart(view: View) {}
                override fun onAnimationEnd(view: View) {
                    fadeOut.visibility = View.GONE
                    callback?.onAnimationEnd()
                }

                override fun onAnimationCancel(view: View) {
                    fadeOut.visibility = View.GONE
                    fadeOut.alpha = 0f
                    callback?.onAnimationCancel()
                }
            })
            if (durationMs != DEFAULT_DURATION) {
                animator.duration = durationMs.toLong()
            }
            animator.start()
        }
        fun crossFadeViews(
            fadeIn: View?,
            fadeOut: View?,
            duration: Int
        ) {
            fadeIn(fadeIn!!, duration)
            fadeOut(fadeOut!!, duration)
        }

        fun animateHeight(
            view: View,
            from: Int,
            to: Int,
            duration: Int
        ) {
            val expanding = to > from
            val anim = ValueAnimator.ofInt(from, to)
            anim.addUpdateListener { valueAnimator ->
                val `val` = valueAnimator.animatedValue as Int
                val layoutParams = view.layoutParams
                layoutParams.height = `val`
                view.layoutParams = layoutParams
            }
            anim.duration = duration.toLong()
            anim.start()
            view.animate().alpha((if (expanding) 1 else 0.toFloat()) as Float)
                .setDuration(duration / 2.toLong()).start()
        }
        /*
        *   paddingInDp : all
        *   paddingAlt : left - right
        * */
        fun setPaddingAll(v: View, paddingInDp: Float, paddingAlt:Float? = null) {

            v.setPadding(
                dpToPx(v.context, paddingAlt ?: paddingInDp),
                dpToPx(v.context, paddingInDp),
                dpToPx(v.context, paddingAlt ?: paddingInDp),
                dpToPx(v.context, paddingInDp)
            )
        }
        private fun dpToPx(context: Context, dp: Float): Int {
            val density = context.resources.displayMetrics.density
            return (dp * density).roundToInt()
        }
        fun getSizeOfScreen(activity: Activity): Point? {
            val display = activity.windowManager.defaultDisplay
            val size = Point()
            display.getSize(size)
            return size
        }
    }
}
