package dev.trindadedev.fastui.preferences

/*
 *  This file is part of Robok © 2024.
 *
 *  Robok is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Robok is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *   along with Robok.  If not, see <https://www.gnu.org/licenses/>.
 */

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import dev.trindadedev.fastui.R

class Preference
@JvmOverloads
constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
  LinearLayout(context, attrs, defStyleAttr) {

  public val preferenceTitle: TextView
  public val preferenceDescription: TextView
  public val preference: View

  init {
    LayoutInflater.from(context).inflate(R.layout.layout_preference, this, true)

    preferenceTitle = findViewById(R.id.preference_title)
    preferenceDescription = findViewById(R.id.preference_description)
    preference = findViewById(R.id.preference)

    context.theme.obtainStyledAttributes(attrs, R.styleable.Preference, 0, 0).apply {
      try {
        val title = getString(R.styleable.Preference_preferenceTitle) ?: ""
        val description = getString(R.styleable.Preference_preferenceDescription) ?: ""
        preferenceTitle.text = title
        preferenceDescription.text = description
      } finally {
        recycle()
      }
    }
  }

  fun setPreferenceClickListener(listenerClick: View.OnClickListener) {
    preference.setOnClickListener(listenerClick)
  }

  fun setTitle(value: String) {
    preferenceTitle.text = value
  }

  fun setDescription(value: String) {
    preferenceDescription.text = value
  }
}
