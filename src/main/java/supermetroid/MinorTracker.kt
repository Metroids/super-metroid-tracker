package supermetroid

import java.awt.Color
import java.awt.Dimension
import java.awt.Font
import javax.swing.ImageIcon
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JLayeredPane

class MinorTracker : JFrame() {
    private var on: List<ImageIcon>
    private var off: List<ImageIcon>
    private var images = arrayOf(JLabel(),JLabel(),JLabel(),JLabel(),JLabel())
    private var counts = arrayOf(JLabel(),JLabel(),JLabel(),JLabel(),JLabel())
    private var panel: JLayeredPane

    init {
        title = "Super Metroid - Minor Tracker"
        isResizable = false
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize((64 * 5) + (2 * 4), 64)
        panel = JLayeredPane()
        panel.preferredSize = Dimension((64 * 5) + (2 * 4), 64)
        panel.background = Color.BLACK
        panel.isOpaque = true
        contentPane = panel

        on = (0..4).map {readResourceToIcon("/on/4-$it.png")}
        off = (0..4).map {readResourceToIcon("/off/4-$it.png")}

        for (col in 0..4) {
            panel.add(images[col], 0)
            images[col].setSize(64, 64)
            images[col].setLocation((64 * col) + (2 * col), 0)
            images[col].icon = off[col]

            panel.add(counts[col], 1)
            counts[col].isOpaque = true
            counts[col].background = Color.black
            counts[col].setLocation(7 + (64 * col) + (2 * col), 42)
            counts[col].foreground = Color.WHITE
            counts[col].font = Font("Calibri", Font.BOLD, 16)
        }

        pack()
    }

    fun setIconStatus(count: Count, value: Int) {
        if (value > 0) {
            images[count.col].icon = on[count.col]
            counts[count.col].text = " $value"
            counts[count.col] .setSize((if (value < 10) 16 else (if (value < 100) 24 else 30)), 30)
            panel.moveToFront(counts[count.col])
        } else {
            images[count.col].icon = off[count.col]
            counts[count.col].text = ""
            counts[count.col].setSize(0, 30)
        }
    }
}
