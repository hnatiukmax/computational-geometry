package kit.utils

import com.jogamp.opengl.GL
import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.fixedfunc.GLMatrixFunc

abstract class SimpleGLEventListener : GLEventListener {

    protected lateinit var gl2: GL2

    override fun init(drawable: GLAutoDrawable?) {
        drawable?.gl?.gL2?.apply {
            glShadeModel(GL.GL_LINE_SMOOTH)
            glEnable(GL2.GL_MULTISAMPLE)
            glMatrixMode(GLMatrixFunc.GL_MODELVIEW)
            gl2 = this
        }
    }

    override fun display(drawable: GLAutoDrawable?) {}

    override fun reshape(drawable: GLAutoDrawable?, x: Int, y: Int, width: Int, height: Int) {}

    override fun dispose(drawable: GLAutoDrawable?) {}
}