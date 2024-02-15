package supermetroid

import supermetroid.memory.MemoryReader
import supermetroid.memory.NetworkReader
import supermetroid.memory.WindowsProcessReader
import supermetroid.trackable.Trackable
import java.awt.Point
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.io.InputStream
import javax.imageio.ImageIO
import javax.swing.ImageIcon

fun main(args: Array<String>) {
    val memoryReader = when(args.getOrElse(0) {"memory"}) {
        "network" -> NetworkReader()
        else -> WindowsProcessReader()
    }

    val trackers = when (args.getOrElse(1) {"memory"}) {
        "bounty-blaster" -> Layout.bountyBlaster()
        "objectives" -> Layout.objectives()
        else -> Layout.standard()
    }

    val majorTracker = MajorTracker(trackers)
    majorTracker.setLocation(10, 10)
    majorTracker.isVisible = true
    majorTracker.addWindowListener(object : WindowAdapter() {
        override fun windowClosing(we: WindowEvent) {
            memoryReader.close()
        }
    })
    val bounds = majorTracker.bounds

    val minorTracker = MinorTracker()
    minorTracker.setLocation(10, (bounds.getY() + bounds.getHeight() + 10).toInt())
    minorTracker.isVisible = true

    Thread(TrackerThread(memoryReader, majorTracker, minorTracker)).start()
}

fun readResource(resource: String): InputStream = object {}.javaClass.getResourceAsStream(resource)!!
fun readResourceToIcon(resource: String) = ImageIcon(ImageIO.read(readResource(resource)))