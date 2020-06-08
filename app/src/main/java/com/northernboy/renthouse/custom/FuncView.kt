package com.northernboy.renthouse.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.northernboy.renthouse.R
import org.w3c.dom.Text

class FuncView(context: Context, attributeSet: AttributeSet) : ConstraintLayout(context, attributeSet){

    private val paint = Paint()
    private var drawLine : Boolean
    private var isDown = false

    private var funcLayout: LinearLayout
    private lateinit var funcText: TextView

    init {
        inflate(context, R.layout.view_func, this)
        val funcIcon = findViewById<ImageView>(R.id.func_icon)
        funcText = findViewById<TextView>(R.id.func_description)
        val funcMoreIcon = findViewById<ImageView>(R.id.func_more)
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.FuncView)
        funcLayout = findViewById(R.id.func)
        drawLine = attrs.getBoolean(R.styleable.FuncView_drawLine, false)
        funcIcon.setImageDrawable(attrs.getDrawable(R.styleable.FuncView_funIcon))
        funcText.text = attrs.getText(R.styleable.FuncView_funDescription)
        funcText.setTextAppearance(attrs.getResourceId(R.styleable.FuncView_funDescriptionTextAppearance, android.R.style.TextAppearance_Material_Small))
        funcMoreIcon.setImageDrawable(attrs.getDrawable(R.styleable.FuncView_funMoreIcon))
        attrs.recycle()
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(!isClickable){
            return false
        }
        Log.d("FuncView", event?.actionMasked.toString())
        when(event?.actionMasked){
            MotionEvent.ACTION_DOWN -> {
                isDown = true
                funcLayout.background = context.getDrawable(R.color.background_grey)
            }
            MotionEvent.ACTION_UP -> {
                funcLayout.background = context.getDrawable(R.color.design_default_color_on_primary)
                if(isDown) {
                    performClick()
                }
            }
            else -> {
                isDown = false
                funcLayout.background = context.getDrawable(R.color.design_default_color_on_primary)
                event?.action = MotionEvent.ACTION_CANCEL
            }
        }
        return true
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        if(drawLine){
            paint.color = context.getColor(R.color.background_grey)
            paint.strokeWidth = 2f
            canvas?.drawLine(150f, (height-1).toFloat(), width.toFloat(), (height-1).toFloat(), paint)
        }
    }

    fun setFunDescription(des: String){
        funcText.text = des
    }
}