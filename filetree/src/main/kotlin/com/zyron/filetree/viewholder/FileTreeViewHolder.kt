/**
 * Copyright 2024 Zyron Official.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.zyron.filetree.viewholder

// import com.google.android.material.imageview.ShapeableImageView
// import com.google.android.material.textview.MaterialTextView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zyron.filetree.R

/**
 * ViewHolder for the FileTreeRecyclerView.
 *
 * Holds references to the views for each item in the RecyclerView, including the chevron icon, file
 * icon, and file name.
 *
 * @param itemView The view of the item in the RecyclerView.
 */
class FileTreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
  /** View for the chevron icon indicating expandable or collapsible state. */
  val chevronIconView: ImageView = itemView.findViewById(R.id.chevronIconView)

  /** View for the file icon representing the type of file. */
  val fileIconView: ImageView = itemView.findViewById(R.id.fileIconView)

  /** View for displaying the name of the file or folder. */
  val fileNameView: TextView = itemView.findViewById(R.id.fileNameView)
}
