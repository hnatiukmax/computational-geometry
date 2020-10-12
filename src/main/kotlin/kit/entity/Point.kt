package kit.entity

data class Point(var x: Double = .0, var y: Double = .0)

val Point.middlePoint: Point get() = Point(
    x = x / 2.0,
    y = y / 2.0
)

fun Point.middleXOf(other: Point) = (x + other.x) / 2.0

fun Point.middleYOf(other: Point) = (y + other.y) / 2.0