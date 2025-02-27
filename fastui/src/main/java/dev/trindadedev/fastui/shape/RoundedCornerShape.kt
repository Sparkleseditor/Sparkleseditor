package dev.trindadedev.fastui.shape

/*
 * Copyright 2025 Aquiles Trindade.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.View

/**
 * A utility class for creating rounded corner shapes using [GradientDrawable]. This class extracts
 * the background color from the given view (if it's a [ColorDrawable]) and applies rounded corners
 * based on the provided radii values.
 */
open class RoundedCornerShape: Shape {

  /** The drawable representing the rounded shape. */
  private val drawable = GradientDrawable()

  /**  */
  private var imageMode = false

  /**
   * Creates a [RoundedCornerShape] with the specified corner radii.
   *
   * @param topStart The radius for the top-left corner.
   * @param topEnd The radius for the top-right corner.
   * @param bottomEnd The radius for the bottom-right corner.
   * @param bottomStart The radius for the bottom-left corner.
   */
  constructor(
    topStart: Float = 0f,
    topEnd: Float = 0f,
    bottomEnd: Float = 0f,
    bottomStart: Float = 0f,
  ) {
    drawable.shape = GradientDrawable.RECTANGLE
    drawable.cornerRadii =
      floatArrayOf(
        topStart,
        topStart,
        topEnd,
        topEnd,
        bottomEnd,
        bottomEnd,
        bottomStart,
        bottomStart,
      )
  }

  /**
   * Creates a [RoundedCornerShape] with the specified corner radii but in int.
   *
   * @param topStart The radius for the top-left corner.
   * @param topEnd The radius for the top-right corner.
   * @param bottomEnd The radius for the bottom-right corner.
   * @param bottomStart The radius for the bottom-left corner.
   */
  constructor(topStart: Int = 0, topEnd: Int = 0, bottomEnd: Int = 0, bottomStart: Int = 0) {
    drawable.shape = GradientDrawable.RECTANGLE
    drawable.cornerRadii =
      floatArrayOf(
        topStart.toFloat(),
        topStart.toFloat(),
        topEnd.toFloat(),
        topEnd.toFloat(),
        bottomEnd.toFloat(),
        bottomEnd.toFloat(),
        bottomStart.toFloat(),
        bottomStart.toFloat(),
      )
  }

  /**
   * Creates a [RoundedCornerShape] with the same radius value in all corners.
   *
   * @param all The radius value to be used in all corners.
   */
  constructor(all: Float) : this(all, all, all, all)

  /**
   * Creates a [RoundedCornerShape] with the same radius value in all corners but in int.
   *
   * @param all The radius value to be used in all corners.
   */
  constructor(all: Int) : this(all, all, all, all)

  /** Set the mode to image Will use Transparent Colors instead get color from background */
  fun imageMode(): RoundedCornerShape {
    imageMode = true
    return this
  }

  /**
   * Applies the shaped background.
   *
   * @param view View to be applied.
   */
  open fun applyTo(view: View) {
    val viewDrawable = view.background
    if (viewDrawable is ColorDrawable && !imageMode) {
      drawable.setColor(viewDrawable.color)
    } else {
      drawable.setColor(Color.TRANSPARENT)
    }
    view.background = drawable
  }
}
