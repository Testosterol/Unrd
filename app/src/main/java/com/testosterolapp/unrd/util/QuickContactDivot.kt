package com.testosterolapp.unrd.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.QuickContactBadge
import com.testosterolapp.unrd.R
import com.testosterolapp.unrd.util.Divot.Companion.sPositionChoices

class QuickContactDivot : QuickContactBadge, Divot {
    private var mDrawable: Drawable? = null
    private var mDrawableIntrinsicWidth = 0
    private var mDrawableIntrinsicHeight = 0
    private var mPosition = 0

    // The screen density.  Multiple this by dips to get pixels.
    private var mDensity = 0f

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        initialize(attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context?) : super(context) {
        initialize(null)
    }

    private fun initialize(attrs: AttributeSet?) {
        if (attrs != null) {
            mPosition = attrs.getAttributeListValue(null, "position", sPositionChoices, -1)
        }
        val r = context.resources
        mDensity = r.displayMetrics.density
        setDrawable()
    }

    private fun setDrawable() {
        val r = context.resources
        when (mPosition) {
            Divot.LEFT_UPPER, Divot.LEFT_MIDDLE, Divot.LEFT_LOWER -> mDrawable = r.getDrawable(R.drawable.msg_bubble_right)
            Divot.RIGHT_UPPER, Divot.RIGHT_MIDDLE, Divot.RIGHT_LOWER -> mDrawable = r.getDrawable(R.drawable.msg_bubble_left)
        }
        if (mDrawable != null) {
            mDrawableIntrinsicWidth = mDrawable!!.intrinsicWidth
            mDrawableIntrinsicHeight = mDrawable!!.intrinsicHeight
        }
    }

    override fun onClick(v: View) {
        try {
            super.onClick(v)
        } catch (e: Exception) {
        }
    }

    public override fun onDraw(c: Canvas) {
        super.onDraw(c)
        c.save()
        if (mDrawable != null) {
            computeBounds(c)
            mDrawable!!.draw(c)
        }
        c.restore()
    }

    override var position: Int
        get() = mPosition
        set(position) {
            mPosition = position
            setDrawable()
            invalidate()
        }

    // multiply by density to get pixels
    override val closeOffset: Float
        get() = Divot.CORNER_OFFSET * mDensity // multiply by density to get pixels

    override fun asImageView(): ImageView? {
        return this
    }

    override fun assignContactFromEmail(emailAddress: String?) {
        try {
            assignContactFromEmail(emailAddress, true)
        } catch (e: Exception) {
        }
    }

    override val farOffset: Float
        get() = closeOffset + mDrawableIntrinsicHeight

    private fun computeBounds(c: Canvas) {
        val left = 0
        val top = 0
        val right = width
        val middle = right / 2
        val bottom = height
        val cornerOffset = closeOffset.toInt()
        when (mPosition) {
            Divot.RIGHT_UPPER -> mDrawable!!.setBounds(
                    right - mDrawableIntrinsicWidth,
                    top + cornerOffset,
                    right,
                    top + cornerOffset + mDrawableIntrinsicHeight)
            Divot.LEFT_UPPER -> mDrawable!!.setBounds(
                    left,
                    top + cornerOffset,
                    left + mDrawableIntrinsicWidth,
                    top + cornerOffset + mDrawableIntrinsicHeight)
            Divot.BOTTOM_MIDDLE -> {
                val halfWidth = mDrawableIntrinsicWidth / 2
                mDrawable!!.setBounds(
                        (middle - halfWidth),
                        (bottom - mDrawableIntrinsicHeight),
                        (middle + halfWidth),
                        bottom)
            }
        }
    }
}