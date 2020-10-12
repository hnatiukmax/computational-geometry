package first_task

import kit.extensions.buildGLCanvas
import kit.utils.setUpFrame

private const val title = "Computational geometry. First."
private const val windowWidth = 1280
private const val windowHeight = 720

fun main() {
    val lgEventListener = SimpleDrawingGLEventListener(windowWidth, windowHeight)
    val glCanvas = buildGLCanvas(windowWidth, windowHeight, lgEventListener)
    setUpFrame(windowWidth, windowHeight, title, glCanvas)
}