package sixth_task

import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.util.*
import javax.swing.JScrollBar
import javax.swing.SwingUtilities
import javax.swing.SwingWorker
import kotlin.math.abs
import kotlin.math.pow

class NewtonFractal(private val width: Int, private val height: Int, private val polinomial: Polynomial) : SwingWorker<BufferedImage, Void?>() {

    var zoomFactor = DEFAULT_ZOOM
    var topLeftX = DEFAULT_TOP_LEFT_X
    var topLeftY = DEFAULT_TOP_LEFT_Y

    companion object {
        const val DEFAULT_ZOOM = 100.0
        const val DEFAULT_TOP_LEFT_X = -4.5
        const val DEFAULT_TOP_LEFT_Y = 4.0
        private val TOLERANCE = 10.0.pow(-6.0)
        private const val MAXITER = 100
    }

    private fun getXPos(x: Double) = x / zoomFactor + topLeftX

    private fun getYPos(y: Double) = y / zoomFactor - topLeftY

    private var roots: MutableMap<Point, RootPoint>? = null
    private var rootColors: ArrayList<Complex>? = null
    private fun clamp01(value: Float): Float {
        return 0f.coerceAtLeast(1f.coerceAtMost(value))
    }

    private fun getColorFromRoot(rootPoint: RootPoint?): Color {
        for (i in rootColors!!.indices) {
            if (rootColors!![i].equals(rootPoint!!.point, TOLERANCE)) {
                var saturation: Float
                val hue: Float = clamp01(abs((0.5f - rootPoint.point.arg() / (Math.PI * 2.0f)).toFloat()))
                saturation = clamp01(abs(0.59f / rootPoint.point.abs().toFloat()))
                val brightness: Float = 0.95f * (1.0f - rootPoint.numIter.toFloat() * 0.025f).coerceAtLeast(0.05f)
                if (rootPoint.point.abs() < 0.1) {
                    saturation = 0.0f
                }
                return Color.getHSBColor(hue, saturation, brightness)
            }
        }
        return Color.black
    }

    private fun applyNewtonMethod(x: Int, y: Int) {
        val point = Complex(getXPos(x.toDouble()), getYPos(y.toDouble()))
        val rtApprox = RootApproximator(polinomial, point)
        val rootPoint = rtApprox.rootPoint
        if (!containsRoot(rootPoint.point)) {
            rootColors!!.add(rootPoint.point)
        }
        roots!![Point(x, y)] = rootPoint
    }

    private fun containsRoot(root: Complex): Boolean {
        for (z in rootColors!!) {
            if (z.equals(root, TOLERANCE)) {
                return true
            }
        }
        return false
    }

    override fun doInBackground(): BufferedImage {
        progress = 0
        val fractalImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
        rootColors = ArrayList()
        roots = HashMap()
        var totalSteps = 0
        val calculationSteps = width * height
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (isCancelled) {
                    return fractalImage
                }
                applyNewtonMethod(x, y)
                val c = getColorFromRoot((roots as HashMap<Point, RootPoint>)[Point(x, y)])
                fractalImage.setRGB(x, y, c.rgb)
                super.setProgress(Math.round(100.0f * totalSteps++ / calculationSteps))
            }
        }
        return fractalImage
    }

    public override fun done() {
        FractalViewer.setFieldsEnabled(true)
        SwingUtilities.invokeLater {
            val bounds: Rectangle = FractalViewer.previewScrollPane.viewport.viewRect
            val horizontal: JScrollBar = FractalViewer.previewScrollPane.horizontalScrollBar
            horizontal.value = (horizontal.maximum - bounds.width) / 2
            val vertical: JScrollBar = FractalViewer.previewScrollPane.verticalScrollBar
            vertical.value = (vertical.maximum - bounds.height) / 2
        }
    }

    private inner class RootApproximator(private val pol: Polynomial, guess: Complex) {
        private val dpol: Polynomial?
        private var guess: Complex
        private fun nextGuess(): Double {
            val nextGuess = guess.subtract(pol.evaluate(guess).divide(dpol!!.evaluate(guess)))
            val distance = nextGuess.euclideanDistance(guess)
            guess = nextGuess
            return distance
        }

        val rootPoint: RootPoint
            get() {
                var diff = 10.0
                var iter = 0
                while (diff > TOLERANCE && iter < MAXITER) {
                    iter++
                    diff = nextGuess()
                }
                return RootPoint(guess, iter)
            }

        init {
            dpol = pol.derivative()
            this.guess = guess
        }
    }

    private inner class RootPoint(val point: Complex, val numIter: Int)
}