package third_task

import com.jogamp.opengl.GLAutoDrawable
import com.jogamp.opengl.util.awt.TextRenderer
import kit.entity.Point
import kit.extensions.drawLineStrip
import kit.utils.SimpleGLEventListener
import java.awt.Color
import java.awt.Font

class FunctionGLEventListener(private val width: Int, private val height: Int) : SimpleGLEventListener() {

    override fun display(drawable: GLAutoDrawable?) {
        drawCoordinates(Color.DARK_GRAY)
        drawFunction(Color.PINK)
        gl2.glFlush()
    }

    private fun drawFunction(color: Color) {
        val points = buildPointsByVariantFunction()
        for (i in 0 until (points.size - 1)) {
            gl2.drawLineStrip(points[i], points[i + 1], color)
        }
    }

    private fun buildPointsByVariantFunction() : List<Point> {
        val points = mutableListOf<Point>()
        val step = 0.01
        var currentStateX = 0.0

        while (currentStateX <= 10.0) {
            val nextPoint = Point(currentStateX / 10.0, function(currentStateX) / 10.0)
            points.add(nextPoint)
            currentStateX += step
        }

        return points
    }

    private fun drawCoordinates(color: Color) = with(gl2) {
        drawLineStrip(Point(-1.0, .0), Point(1.0, .0), color)
        drawLineStrip(Point(.0, -1.0), Point(.0, 1.0), color)

        val p1 = Point(-1.0, 0.02)
        val p2 = Point(-1.0, -0.02)
        val p3 = Point(0.02, -1.0)
        val p4 = Point(-0.02, -1.0)

        val textRenderer = getTextRenderer()
        var mWidth = 0
        var mHeight = 0

        for (i in 0..19) {
            textRenderer.draw((i - 10).toString() + "", mWidth, height / 2 - 20)
            mWidth += width / 20
            textRenderer.draw((i - 10).toString() + "", width / 2 - 25, mHeight)
            mHeight += height / 20
        }

        textRenderer.endRendering()

        for (i in 0..19) {
            drawLineStrip(p1, p2, Color.YELLOW)
            p1.x = (p1.x + 0.1)
            p2.x = (p2.x + 0.1)
            drawLineStrip(p3, p4, Color.YELLOW)
            p3.y = (p3.y + 0.1)
            p4.y = (p4.y + 0.1)
        }
    }

    private fun getTextRenderer() = TextRenderer(Font("Verdana", Font.BOLD, 12)).apply {
        beginRendering(width, height)
        setColor(Color.WHITE)
        smoothing = true
    }
}