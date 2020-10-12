package kit.extensions

import com.jogamp.opengl.GL2
import com.jogamp.opengl.GLCapabilities
import com.jogamp.opengl.GLEventListener
import com.jogamp.opengl.GLProfile
import com.jogamp.opengl.awt.GLCanvas
import kit.entity.Line
import kit.entity.Point
import kit.entity.Triangle
import java.awt.Color

fun buildGLCanvas(width: Int, height: Int, listener: GLEventListener) : GLCanvas {
    val glProfile = GLProfile.get(GLProfile.GL2)
    val glCapabilities = GLCapabilities(glProfile)
    return GLCanvas(glCapabilities).apply {
        addGLEventListener(listener)
        setSize(width, height)
    }
}

fun GL2.drawTriangle(triangle: Triangle, color: Color) {
    drawLineStrip(triangle.firstVertex, triangle.secondVertex, color)
    drawLineStrip(triangle.firstVertex, triangle.thirdVertex, color)
    drawLineStrip(triangle.secondVertex, triangle.thirdVertex, color)
}

fun GL2.drawLineStrip(start: Point, end: Point, color: Color) {
    drawLineStrip(Line(start, end), color)
}

fun GL2.drawLineStrip(line: Line, color: Color, lineWidth: Float = 1f, lineStipple: Short = "11111111".toShort(2)) {
    val rgb = color.rgbArray
    glColor3f(rgb[0], rgb[1], rgb[2])
    glLineWidth(lineWidth)
    glLineStipple(1, lineStipple)

    draw(GL2.GL_LINE_STRIP) {
        glVertex3d(line.start.x, line.start.y, 0.0)
        glVertex3d(line.end.x, line.end.y, 0.0)
    }
}

fun GL2.drawPointsInMultiplySize(points: List<Point>, pointSize: Float, color: Color) {
    points.forEachIndexed { index, point ->
        val pointSizeComputational = pointSize * (index.takeIf { it != 0 } ?: 1)
        drawPoint(point, pointSizeComputational, color)
    }
}

fun GL2.drawPoint(point: Point, pointSize: Float, color: Color) {
    val rgb = color.rgbArray
    glColor3f(rgb[0], rgb[1], rgb[2])
    glPointSize(pointSize)

    draw(GL2.GL_POINTS) {
        glVertex3d(point.x, point.y, .0)
    }
}

private fun GL2.draw(mode: Int, drawBlock: () -> Unit) {
    glBegin(mode)
    drawBlock()
    glEnd()
}