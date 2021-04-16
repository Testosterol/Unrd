package com.testosterolapp.unrd.util

import android.widget.ImageView

interface Divot {
    var position: Int
    val closeOffset: Float
    val farOffset: Float

    fun asImageView(): ImageView?
    fun assignContactFromEmail(emailAddress: String?)

    companion object {
        // Distance, in dips, from the corner of the image to the start of the divot.
        // Used for non-middle positions.  For middle positions this distance is basically
        // to the middle of edge.
        const val CORNER_OFFSET = 12f
        const val WIDTH = 6f
        const val HEIGHT = 16f

        // Where to draw the divot.  LEFT_UPPER, for example, means the upper edge but to the
        // left.  TOP_RIGHT means the right edge but to the top.
        const val LEFT_UPPER = 1
        const val LEFT_MIDDLE = 2
        const val LEFT_LOWER = 3
        const val RIGHT_UPPER = 4
        const val RIGHT_MIDDLE = 5
        const val RIGHT_LOWER = 6
        const val TOP_LEFT = 7
        const val TOP_MIDDLE = 8
        const val TOP_RIGHT = 9
        const val BOTTOM_LEFT = 10
        const val BOTTOM_MIDDLE = 11
        const val BOTTOM_RIGHT = 12
        val sPositionChoices = arrayOf(
                "",
                "left_upper",
                "left_middle",
                "left_lower",
                "right_upper",
                "right_middle",
                "right_lower",
                "top_left",
                "top_middle",
                "top_right",
                "bottom_left",
                "bottom_middle",
                "bottom_right")
    }
}