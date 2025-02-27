package dev.trindadedev.fastui.preferences;

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

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.google.android.material.materialswitch.MaterialSwitch;
import dev.trindadedev.fastui.R;

public class PreferenceSwitch extends RelativeLayout implements View.OnClickListener {

  public boolean value = false;
  public TextView preferenceName;
  public TextView preferenceDescription;
  public MaterialSwitch preferenceSwitch;

  public PreferenceSwitch(Context context) {
    super(context);
    initialize(context, null);
  }

  public PreferenceSwitch(Context context, AttributeSet attrs) {
    super(context, attrs);
    initialize(context, attrs);
  }

  public PreferenceSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    initialize(context, attrs);
  }

  public void initialize(Context context, AttributeSet attrs) {
    LayoutInflater.from(context).inflate(R.layout.layout_preference_switch, this, true);

    preferenceName = findViewById(R.id.preference_name);
    preferenceDescription = findViewById(R.id.preference_description);
    preferenceSwitch = findViewById(R.id.preference_switch);

    setOnClickListener(this);

    if (attrs != null) {
      TypedArray a =
          context.getTheme().obtainStyledAttributes(attrs, R.styleable.PreferenceSwitch, 0, 0);

      try {
        String title = a.getString(R.styleable.PreferenceSwitch_preferenceSwitchTitle);
        String description = a.getString(R.styleable.PreferenceSwitch_preferenceSwitchDescription);
        boolean defaultValue =
            a.getBoolean(R.styleable.PreferenceSwitch_preferenceSwitchDefaultValue, false);

        setTitle(title != null ? title : "");
        setDescription(description != null ? description : "");
        setValue(defaultValue);
      } finally {
        a.recycle();
      }
    }
  }

  public boolean getValue() {
    return value;
  }

  public void setValue(boolean value) {
    this.value = value;
    preferenceSwitch.setChecked(value);
  }

  @Override
  public void onClick(View view) {
    setValue(!value);
  }

  public void setDescription(String value) {
    preferenceDescription.setText(value);
  }

  public void setTitle(String value) {
    preferenceName.setText(value);
  }

  public void setSwitchChangedListener(
      CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
    preferenceSwitch.setOnCheckedChangeListener(onCheckedChangeListener);
  }
}
