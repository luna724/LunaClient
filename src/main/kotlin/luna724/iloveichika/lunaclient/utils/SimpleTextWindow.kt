package luna724.iloveichika.lunaclient.utils

import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JButton
import java.awt.BorderLayout

fun showPrimaryTextWindow(title: String, text: String) {
    Thread {
        val frame = JFrame(title)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(300, 150)

        val label = JLabel(text, JLabel.CENTER)
        val button = JButton("Close")
        button.addActionListener {
            frame.dispose()
        }

        frame.layout = BorderLayout()
        frame.add(label, BorderLayout.CENTER)
        frame.add(button, BorderLayout.SOUTH)

        // ウィンドウを最前面に設定
        frame.isAlwaysOnTop = true
        frame.isVisible = true
    }.start()
}
