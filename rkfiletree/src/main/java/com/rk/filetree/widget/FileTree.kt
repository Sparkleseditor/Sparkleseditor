package com.rk.filetree.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rk.filetree.adapters.FileTreeAdapter
import com.rk.filetree.interfaces.FileClickListener
import com.rk.filetree.interfaces.FileIconProvider
import com.rk.filetree.interfaces.FileLongClickListener
import com.rk.filetree.interfaces.FileObject
import com.rk.filetree.model.Node
import com.rk.filetree.provider.DefaultFileIconProvider
import com.rk.filetree.util.Sorter

/**
 * A custom RecyclerView widget that displays a hierarchical file structure. This view allows users
 * to interact with files in a tree-like format, supporting both click and long-click events on
 * individual file nodes.
 */
class FileTree : RecyclerView {

  private var fileTreeAdapter: FileTreeAdapter
  private lateinit var rootFileObject: FileObject

  // Constructors

  /**
   * Constructor used when creating the view programmatically.
   *
   * @param context The Context the view is running in, through which it can access the current
   *   theme, resources, etc.
   */
  constructor(context: Context) : super(context)

  /**
   * Constructor that is called when inflating the view from XML.
   *
   * @param context The Context the view is running in, through which it can access the current
   *   theme, resources, etc.
   * @param attrs The attributes of the XML tag that is inflating the view.
   */
  constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

  /**
   * Constructor called when inflating from XML with a default style attribute.
   *
   * @param context The Context the view is running in, through which it can access the current
   *   theme, resources, etc.
   * @param attrs The attributes of the XML tag that is inflating the view.
   * @param defStyleAttr An attribute in the current theme that contains a reference to a style
   *   resource that supplies default values for the view.
   */
  constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int,
  ) : super(context, attrs, defStyleAttr)

  // Initialization block
  init {
    setItemViewCacheSize(100)
    layoutManager = LinearLayoutManager(context)
    fileTreeAdapter = FileTreeAdapter(context, this)
  }

  /**
   * Sets a custom icon provider to supply icons for different file types.
   *
   * @param fileIconProvider The implementation of the FileIconProvider interface that will provide
   *   icons for file objects.
   */
  fun setIconProvider(fileIconProvider: FileIconProvider) {
    fileTreeAdapter.iconProvider = fileIconProvider
  }

  /**
   * Sets a listener that will be notified when a file node is clicked.
   *
   * @param clickListener The implementation of the FileClickListener interface that will handle
   *   click events on file nodes.
   */
  fun setOnFileClickListener(clickListener: FileClickListener) {
    fileTreeAdapter.onClickListener = clickListener
  }

  /**
   * Sets a listener that will be notified when a file node is long-clicked.
   *
   * @param longClickListener The implementation of the FileLongClickListener interface that will
   *   handle long-click events on file nodes.
   */
  fun setOnFileLongClickListener(longClickListener: FileLongClickListener) {
    fileTreeAdapter.onLongClickListener = longClickListener
  }

  private var init = false
  private var showRootNode: Boolean = true

  /**
   * Loads the file tree starting from the specified root file.
   *
   * @param file The FileObject representing the root directory to be displayed.
   * @param showRootNodeX Optional parameter to determine whether the root node should be displayed.
   *   If null or true, the root node will be shown.
   */
  fun loadFiles(file: FileObject, showRootNodeX: Boolean? = null) {
    rootFileObject = file

    showRootNodeX?.let { showRootNode = it }

    val nodes: List<Node<FileObject>> =
      if (showRootNode) {
        mutableListOf<Node<FileObject>>().apply { add(Node(file)) }
      } else {
        Sorter.sort(file)
      }

    if (!init) {
      if (fileTreeAdapter.iconProvider == null) {
        fileTreeAdapter.iconProvider = DefaultFileIconProvider(context)
      }
      adapter = fileTreeAdapter
      init = true
    }
    fileTreeAdapter.submitList(nodes)
    if (showRootNode) {
      fileTreeAdapter.expandNode(nodes[0])
    }
  }

  /** Reloads the file tree, refreshing the display with the current state of the root directory. */
  fun reloadFileTree() {
    val nodes: List<Node<FileObject>> =
      if (showRootNode) {
        mutableListOf<Node<FileObject>>().apply { add(Node(rootFileObject)) }
      } else {
        Sorter.sort(rootFileObject)
      }
    fileTreeAdapter.submitList(nodes)
  }
}
