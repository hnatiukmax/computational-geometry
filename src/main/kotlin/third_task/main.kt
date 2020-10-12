package third_task

import kit.extensions.buildGLCanvas
import kit.utils.setUpFrame

private const val title = "Computational geometry. Third."
private const val windowWidth = 800
private const val windowHeight = 600

fun main() {
    val lgEventListener = FunctionGLEventListener(windowWidth, windowHeight)
    val glCanvas = buildGLCanvas(windowWidth, windowHeight, lgEventListener)
    setUpFrame(windowWidth, windowHeight, title, glCanvas)
}