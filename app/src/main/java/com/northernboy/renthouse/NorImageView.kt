package com.northernboy.renthouse

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.util.AttributeSet


class NorImageView(context: Context, attributeSet: AttributeSet) : androidx.appcompat.widget.AppCompatImageView(context, attributeSet){

    var path = Path()
    var short = 0f
    var long = 0f

    override fun onDraw(canvas: Canvas?) {
        short = if(width > height){
            height.toFloat()
        }else{
            width.toFloat()
        }
        path.addCircle(width.div(2).toFloat(), height.div(2).toFloat(), short/2, Path.Direction.CCW)
        canvas?.clipPath(path)
        super.onDraw(canvas)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
    }
}