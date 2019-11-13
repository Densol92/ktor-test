package com.example.services

import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


suspend fun createPreview(path: String, outPath: String) {
    val input = File(path)

    val image: BufferedImage = ImageIO.read(input)

    val oImage = BufferedImage(100, 100, image.type)

    val g2d = oImage.createGraphics()
    g2d.drawImage(image, 0, 0, 100, 100, null)
    g2d.dispose()
    ImageIO.write(oImage, "jpg", File(outPath))
}
