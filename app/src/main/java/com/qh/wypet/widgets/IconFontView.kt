package com.qh.wypet.widgets

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet

class IconFontView(context: Context, attributeSet: AttributeSet? = null) :
    androidx.appcompat.widget.AppCompatTextView(context, attributeSet) {

    init {
        if (typeface.isBold) {
            typeface = Typeface.DEFAULT
        }
        this.typeface = getIconFont(context)
        includeFontPadding = false
    }

    /**************************************************************************************************/

    companion object {
        //字体图标
        // https://www.iconfont.cn/manage/index?spm=a313x.7781069.1998910419.db775f1f3&manage_type=myprojects&projectId=2218389&keyword=&project_type=&page=
        private var iconFontTypeface: Typeface? = null
        
        fun getIconFont(context: Context): Typeface {
            if (iconFontTypeface == null) {
                iconFontTypeface = Typeface.createFromAsset(context.assets, "iconfont.ttf")
            }
            return iconFontTypeface!!
        }
    }
}