package supermetroid

import supermetroid.trackable.Trackable
import java.awt.Color
import java.awt.GridLayout
import java.awt.Point
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*
import java.util.function.Consumer
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPanel

class MajorTracker(trackablesByLocation: Map<Point, Trackable>) : JFrame() {
    var reset: Boolean = false
    val trackables: List<Trackable>

    init {
        title = "Super Metroid - Major Tracker"
        isResizable = false
        defaultCloseOperation = EXIT_ON_CLOSE
        addKeyListener(object : KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                when (e.keyChar) {
                    'r' -> reset = true
                    's' -> takeScreenshot()
                }
            }
        })

        val panel = JPanel(GridLayout(4, 5, 2, 2))
        panel.background = Color.BLACK
        contentPane = panel
        trackables = ArrayList(trackablesByLocation.values)

        val black = readResourceToIcon("/black.png")

        for (row in 0..3) {
            for (col in 0..4) {
                var p: Point

                val label = JLabel()
                panel.add(label)
                label.setSize(64, 64)
                label.icon = black

                if (trackablesByLocation.containsKey(Point(col, row).also { p = it })) {
                    trackablesByLocation[p]!!.initLabel(label)
                }
            }
        }

        pack()
    }

    fun updateState(state: State) {
        trackables.forEach(Consumer { trackable: Trackable -> trackable.setIcon(state) })
    }

    fun takeScreenshot() {
        val image = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics2D = image.createGraphics()
        paint(graphics2D)
        try {
            ImageIO.write(image, "png", File(System.getProperty("user.home") + "/Desktop/tracker.png"))
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
    }
}
