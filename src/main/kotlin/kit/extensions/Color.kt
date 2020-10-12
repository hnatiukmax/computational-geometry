package kit.extensions

import java.awt.Color

val Color.rgbArray: FloatArray get() = getRGBComponents(null)