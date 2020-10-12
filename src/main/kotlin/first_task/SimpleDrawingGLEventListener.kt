package first_task

import com.jogamp.opengl.GL
import com.jogamp.opengl.GLAutoDrawable
import kit.entity.Line
import kit.entity.Point
import kit.entity.pointList
import kit.extensions.drawLineStrip
import kit.extensions.drawPointsInMultiplySize
import kit.utils.SimpleGLEventListener
import java.awt.Color

class SimpleDrawingGLEventListener(private val width: Int, private val height: Int) : SimpleGLEventListener() {

    override fun display(drawable: GLAutoDrawable?) = with(gl2) {
        glClear(GL.GL_COLOR_BUFFER_BIT)
        glEnable(GL.GL_MULTISAMPLE)

        val pointSize = 1f
        val stepCount = 9

        val firstLine = buildFirstLine(startPointFirst, endPointFirst)
        drawLineStrip(firstLine, Color.LIGHT_GRAY, firstLineWidth, firstLineStipple)
        drawPointsInMultiplySize(firstLine.pointList(stepCount), pointSize, firstPointsColor)

        val secondLine = buildSecondLine(startPointSecond, endPointSecond)
        drawLineStrip(secondLine, Color.LIGHT_GRAY, secondLineWidth, secondLineStipple)
        drawPointsInMultiplySize(secondLine.pointList(stepCount), pointSize, secondPointsColor)

        glFlush()
    }

    private fun buildFirstLine(start: Point, end: Point) : Line {
        val startPointLine = Point().apply {
            x = (width / start.x) / 1000
            y = (height / start.y) / 1000
        }

        val endPointLine = Point().apply {
            x = (width / end.x) / 1000
            y = (height / end.y) / 1000
        }

        return Line(startPointLine, endPointLine)
    }

    private fun buildSecondLine(start: Point, end: Point) : Line {
        val startPointLine = Point().apply {
            x = (width / start.x) / 1000
            y = (height / start.y) / 1000
        }

        val endPointLine = Point().apply {
            x = (width / end.x) / 1000
            y = (height / end.y) / 1000
        }

        return Line(startPointLine, endPointLine)
    }
}