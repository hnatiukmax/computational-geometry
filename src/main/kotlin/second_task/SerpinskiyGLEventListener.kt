package second_task

import com.jogamp.opengl.GLAutoDrawable
import kit.entity.Point
import kit.entity.Triangle
import kit.entity.middleXOf
import kit.entity.middleYOf
import kit.extensions.drawTriangle
import kit.utils.SimpleGLEventListener
import java.awt.Color

class SerpinskiyGLEventListener(private val width: Int, private val height: Int) : SimpleGLEventListener() {

    private val deepFractal = 6

    override fun display(drawable: GLAutoDrawable?) {
        val triangle = getStartTriangle(width, height)
        drawDeepTriangle(triangle, Color.PINK, 0)
        gl2.glFlush()
    }

    private fun drawDeepTriangle(triangle: Triangle, color: Color, deep: Int) {
        var deepCopy = deep
        if (deep < deepFractal) {
            gl2.drawTriangle(triangle, color)
            deepCopy++
            val triangles = buildNextTriangle(triangle)
            for (nextTriangle in triangles) {
                drawDeepTriangle(nextTriangle, color, deepCopy)
            }
        }
    }

    private fun buildNextTriangle(triangle: Triangle): List<Triangle> {
        val firstTriangle = Triangle().apply {
            firstVertex = triangle.firstVertex
            secondVertex = Point(triangle.firstVertex.middleXOf(triangle.secondVertex), triangle.firstVertex.middleYOf(triangle.secondVertex))
            thirdVertex = Point(triangle.firstVertex.middleXOf(triangle.thirdVertex), triangle.firstVertex.middleYOf(triangle.thirdVertex))
        }

        val secondTriangle = Triangle().apply {
            firstVertex = triangle.secondVertex
            secondVertex = Point(triangle.secondVertex.middleXOf(triangle.firstVertex), triangle.secondVertex.middleYOf(triangle.firstVertex))
            thirdVertex = Point(triangle.secondVertex.middleXOf(triangle.thirdVertex), triangle.secondVertex.middleYOf(triangle.thirdVertex))
        }

        val thirdTriangle = Triangle().apply {
            firstVertex = triangle.thirdVertex
            secondVertex = Point(triangle.thirdVertex.middleXOf(triangle.firstVertex), triangle.thirdVertex.middleYOf(triangle.firstVertex))
            thirdVertex = Point(triangle.secondVertex.middleXOf(triangle.thirdVertex), triangle.secondVertex.middleYOf(triangle.thirdVertex))
        }


        return listOf(firstTriangle, secondTriangle, thirdTriangle)
    }
}