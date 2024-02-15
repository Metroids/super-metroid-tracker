package supermetroid.trackable

import supermetroid.State
import javax.swing.JLabel

abstract class Trackable {
    protected var label = JLabel()

    fun initLabel(label: JLabel) {
        this.label = label
        setDefault()
    }

    abstract fun setIcon(state: State)
    abstract fun setDefault()
}
