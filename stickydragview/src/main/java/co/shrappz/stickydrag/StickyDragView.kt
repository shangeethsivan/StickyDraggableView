package co.shrappz.stickydrag

import android.content.Context
import android.support.v4.view.ViewCompat
import android.support.v4.widget.ViewDragHelper
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout


class StickyDragView : RelativeLayout {

    private lateinit var mDragHelper: ViewDragHelper
    private var mDragView: View? = null
    var finalLeft: Int = 0
    var finalTop: Int = 0
    val TAG = "StickyDragView"

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attributes: AttributeSet?) : this(context, attributes, 0)

    constructor(context: Context?, attributes: AttributeSet?, defStyle: Int) : super(context, attributes, defStyle) {
        mDragHelper = ViewDragHelper.create(this, 1.0f, DragHelperCallback())
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        val action = ev!!.action
        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
            mDragHelper.cancel()
            return false
        }
        return mDragHelper.shouldInterceptTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        mDragHelper.processTouchEvent(event!!)
        if (event.action == MotionEvent.ACTION_UP) {
            if (mDragView != null) {
                if (mDragHelper.smoothSlideViewTo(mDragView!!, finalLeft, finalTop)) {
                    ViewCompat.postInvalidateOnAnimation(this)
                }
            }
        }
        return true
    }

    override fun computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        }
    }


    inner class DragHelperCallback : ViewDragHelper.Callback() {


        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {
            super.onViewPositionChanged(changedView, left, top, dx, dy)
            Log.d(TAG, "onViewPositionChanged")
        }

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            super.onViewCaptured(capturedChild, activePointerId)
            Log.d(TAG, "onViewCaptured")
        }

        override fun onEdgeTouched(edgeFlags: Int, pointerId: Int) {
            Log.d(TAG, "onEdgeTouched")
            super.onEdgeTouched(edgeFlags, pointerId)
        }

        override fun onEdgeDragStarted(edgeFlags: Int, pointerId: Int) {
            super.onEdgeDragStarted(edgeFlags, pointerId)
        }

        override fun onEdgeLock(edgeFlags: Int): Boolean {
            return super.onEdgeLock(edgeFlags)
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            super.onViewReleased(releasedChild, xvel, yvel)
        }

        override fun getViewVerticalDragRange(child: View): Int {
            return super.getViewVerticalDragRange(child)
        }

        override fun getViewHorizontalDragRange(child: View): Int {
            return super.getViewHorizontalDragRange(child)
        }

        override fun getOrderedChildIndex(index: Int): Int {
            return super.getOrderedChildIndex(index)
        }

        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {

            val topBound = paddingTop
            val layoutParams = mDragView!!.layoutParams as (RelativeLayout.LayoutParams)
            val bottomBound = height - mDragView!!.height + layoutParams.topMargin + layoutParams.bottomMargin

            val newTop = Math.min(Math.max(top, topBound), bottomBound)


            var middleValue: Int = height / 2
            val thresholdValue: Int = (width * 0.1).toInt()

            if (dy < 0) {
                middleValue += thresholdValue
            } else {
                middleValue = thresholdValue
            }

            finalTop = if (newTop > middleValue) {
                bottomBound
            } else {
                topBound
            }

            return newTop
        }


        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            val leftBound = paddingLeft
            val layoutParams = mDragView!!.layoutParams as (RelativeLayout.LayoutParams)
            val rightBound =
                width - mDragView!!.width + Math.max(layoutParams.leftMargin, layoutParams.marginStart) + Math.max(
                    layoutParams.rightMargin,
                    layoutParams.marginEnd
                )

            val newLeft = Math.min(Math.max(left, leftBound), rightBound)

            var middleValue: Int = width / 2
            val thresholdValue: Int = (width * 0.1).toInt()

            if (dx < 0) {
                middleValue += thresholdValue
            } else {
                middleValue = thresholdValue
            }

            finalLeft = if (newLeft > middleValue) {
                rightBound
            } else {
                leftBound
            }
//
//            Log.d(TAG, "clampViewPositionHorizontal left: $left dx: $dx  x: ${child.x}")
//            Log.d(TAG, "clampViewPositionHorizontal leftbound: $leftBound rightbound: $rightBound newLeft :: $newLeft")

            return newLeft
        }


        override fun tryCaptureView(pView: View, p1: Int): Boolean {
            Log.d(TAG, "TryCaptureView")
            if (isDraggableView(pView)) {
                mDragView = pView
                return true
            }
            return false
        }

    }

    fun isDraggableView(pView: View): Boolean {
        return pView.parent == this && pView is FrameLayout
    }
}