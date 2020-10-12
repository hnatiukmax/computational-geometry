package second_task

import kit.entity.Point
import kit.entity.Triangle

/**
 * According to my variant (2).
 */
fun getStartTriangle(width: Int, height: Int) = Triangle(
    Point((width / -3.0) / 1000, ((height / -2.5) / 1000)),
    Point(.0, (height / 2.5) / 1000),
    Point((width / 3.0) / 1000, (height / -2.5) / 1000)
)