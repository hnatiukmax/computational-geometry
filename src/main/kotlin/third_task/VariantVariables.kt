package third_task

import kotlin.math.cos

/**
 * According to my variant (2).
 * f (x)=10 −10cos(2x /10)+ 3cos(4x /10)+ 6cos(6x /10) (x = [0, 10])
 */
fun function(x: Double) : Double {
    return 10.0 - 10.0 * cos((2.0 * Math.PI * x) / 10.0) + 3.0 * cos((4.0 * Math.PI * x) / 10.0) + 6 * cos((6.0 * Math.PI * x) / 10.0)
}