package sixth_task

import java.awt.BorderLayout
import java.awt.Toolkit
import java.awt.Window
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.beans.PropertyChangeEvent
import java.beans.PropertyChangeListener
import javax.swing.*
import kotlin.math.abs

class FractalViewer : JPanel(), ActionListener, PropertyChangeListener {

    var operation: NewtonFractal? = null
    var coefficients: DoubleArray = DoubleArray(10)

    companion object {
        private const val GENERATE_ACTION = "GENERATE_ACTION"

        private const val DEFAULT_WIDTH = 890
        private const val DEFAULT_HEIGHT = 700

        lateinit var previewScrollPane: JScrollPane
        lateinit var genButton: JButton

        var progressMonitor: ProgressMonitor? = null
        fun setFieldsEnabled(b: Boolean) {
            genButton.isEnabled = b
        }

        private fun centreWindow(frame: Window) {
            val dimension = Toolkit.getDefaultToolkit().screenSize
            val x = ((dimension.getWidth() - frame.width) / 2).toInt()
            val y = ((dimension.getHeight() - frame.height) / 2).toInt()
            frame.setLocation(x, y)
        }

        fun createAndShowGUI() {
            val frame = JFrame("Newton Fractal Explorer")
            frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
            frame.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT)
            val contentPane: JComponent = FractalViewer()
            contentPane.isOpaque = true
            frame.contentPane = contentPane
            centreWindow(frame)
            frame.isVisible = true
        }
    }

    override fun actionPerformed(event: ActionEvent) {
        if (event.actionCommand == GENERATE_ACTION) {
            coefficients.forEachIndexed { index, _ ->
                when (index) {
                    0 -> coefficients[index] = -4.0
                    3 -> coefficients[index] = 24.0
                    else -> coefficients[index] = 1.0
                }
            }
            var absCoeffSum = 0.0
            for (i in 9 downTo 2) {
                absCoeffSum += abs(coefficients[i])
            }
            if (absCoeffSum == 0.0) {
                JOptionPane.showMessageDialog(this, "The polynomial must be of at least order 2, ie include a Z² or higher term", "Error", JOptionPane.ERROR_MESSAGE)
                return
            }
            progressMonitor = ProgressMonitor(this@FractalViewer, "Generating fractal...", "", 0, 100)
            progressMonitor!!.setProgress(0)

            operation = NewtonFractal(DEFAULT_WIDTH - 4, DEFAULT_WIDTH - 65, Polynomial(coefficients)).apply {
                addPropertyChangeListener(this@FractalViewer)
                execute()
            }
            setFieldsEnabled(false)
        }
    }

    override fun propertyChange(event: PropertyChangeEvent) {
        if (progressMonitor!!.isCanceled) {
            operation!!.cancel(true)
        } else if (event.propertyName == "progress") {
            val progress = (event.newValue as Int).toInt()
            progressMonitor!!.setProgress(progress)
        }
        if (operation!!.isDone) {
            try {
                previewScrollPane.viewport.add(JLabel(ImageIcon(operation!!.get())))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    init {
        val settingsPanel = JPanel()
        settingsPanel.isFocusable = true

        genButton = JButton("Generate")
        genButton.actionCommand = GENERATE_ACTION
        genButton.addActionListener(this)
        settingsPanel.add(genButton)
        previewScrollPane = JScrollPane()
        layout = BorderLayout(10, 0)

        val topScrollPanel = JScrollPane(settingsPanel)
        add(topScrollPanel, BorderLayout.PAGE_START)
        add(previewScrollPane, BorderLayout.CENTER)
    }
}
