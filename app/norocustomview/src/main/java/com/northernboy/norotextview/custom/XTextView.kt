package com.northernboy.renthouse.custom

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.*
import com.northernboy.norotextview.R

class XTextView(context: Context, attributeSet: AttributeSet): LinearLayout(context, attributeSet), AdapterView.OnItemSelectedListener {

    var showTitle = true
    var showSpinner = false
    var showContent = true
    var xContent: EditText? = null
    var xSpinner: Spinner? = null
    var xTitle: TextView? = null
    var xSpinnerInitValue: Any? = null

    init {
        inflate(context, R.layout.view_xtext, this)
        xContent = findViewById(R.id.x_text_content)
        xSpinner = findViewById(R.id.x_text_spinner)
        xTitle = findViewById(R.id.x_text_title)

        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.XTextView)
        showTitle = attributes.getBoolean(R.styleable.XTextView_XTextShowTitle, true)
        showSpinner = attributes.getBoolean(R.styleable.XTextView_XTextShowSpinner, false)
        showContent = attributes.getBoolean(R.styleable.XTextView_XTextShowContent, true)
        if(showTitle){
            xTitle?.apply {
                text = attributes.getString(R.styleable.XTextView_XTextTitle)
                setTextAppearance(attributes.getResourceId(R.styleable.XTextView_XTextTitleTextAppearance, android.R.style.TextAppearance_Material_Small))
            }
        }else{
            xTitle?.visibility = View.GONE
        }
        if(showContent){
            xContent?.apply {
                setText(attributes.getString(R.styleable.XTextView_XTextContent))
                hint = attributes.getString(R.styleable.XTextView_XTextContentHint)
                setTextAppearance(attributes.getResourceId(R.styleable.XTextView_XTextContentTextAppearance, R.style.TextAppearance_AppCompat_Body2))
                setTextColor(Color.BLACK)
                background = context.getDrawable(attributes.getResourceId(R.styleable.XTextView_XTextContentBackground, R.drawable.background_edit_text))
                isEnabled = attributes.getBoolean(R.styleable.XTextView_XTextContentEditable, false)
            }
        }else{
            xContent?.visibility = View.GONE
        }
        if(showSpinner){
            xSpinner?.apply {
                ArrayAdapter.createFromResource(
                    context,
                    attributes.getResourceId(R.styleable.XTextView_XTextSpinnerEntry, android.R.array.emailAddressTypes),
                    android.R.layout.simple_spinner_item
                ).also { adapter->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    this.adapter = adapter
                }
            }
        }else{
            xSpinner?.visibility = View.GONE
        }
        attributes.recycle()
    }

    fun getContent(): String?{
        return xContent?.text.toString()
    }
    fun getSpinnerSelectedItem(): Any?{
        return xSpinner?.selectedItem
    }

    fun setXTextContent(content: String){
        xContent?.text = Editable.Factory().newEditable(content)
    }

    fun setXTextSpinnerInitValue(value: Int){
        xSpinnerInitValue = value
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        parent?.setSelection(xSpinnerInitValue as Int - 1)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }
}