package first_task

import kit.entity.Point
import java.awt.Color

/**
 * According to my variant (2).
 */
const val firstLineWidth = 2f
val firstLineStipple = "11001100".toShort(2)

val firstPointsColor: Color = Color.RED
val startPointFirst = Point(-5.0, 2.5)
val endPointFirst = Point(-5.0, -2.5)

const val secondLineWidth = 1f
val secondLineStipple = "11001100".toShort(2)

val secondPointsColor: Color = Color.GREEN
val startPointSecond = Point(5.0, -2.5)
val endPointSecond = Point(5.0, 2.5)