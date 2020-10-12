package kit.utils

import com.jogamp.opengl.awt.GLCanvas
import javax.swing.JFrame

fun setUpFrame(width: Int, height: Int, title: String, glCanvas: GLCanvas) = JFrame(title).apply {
    defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    add(glCanvas)
    setSize(width, height)
    pack()
    isVisible = true
}