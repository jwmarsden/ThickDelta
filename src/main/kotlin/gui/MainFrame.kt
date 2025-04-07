package com.ventia.gui

import java.awt.Image
import java.io.IOException
import java.net.URL
import javax.imageio.ImageIO
import javax.swing.JFrame


class MainFrame(title: String = "Main Frame"): JFrame() {

    override fun addNotify() {
        super.addNotify()
        val images: MutableList<Image> = mutableListOf()
        val icon16: URL? = this::class.java.getResource("/image/icon/logo-ventia/logo-ventia-16.png")
        val icon32: URL? = this::class.java.getResource("/image/icon/logo-ventia/logo-ventia-32.png")
        val icon96: URL? = this::class.java.getResource("/image/icon/logo-ventia/logo-ventia-96.png")
        try {
            images.add(ImageIO.read(icon96))
            images.add(ImageIO.read(icon32))
            images.add(ImageIO.read(icon16))
        } catch (e: IOException) {
            println(e)
        }
        this.setIconImages(images)

    }

}