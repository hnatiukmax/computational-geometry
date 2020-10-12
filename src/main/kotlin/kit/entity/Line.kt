package kit.entity

class Line(var start: Point, var end: Point)

fun Line.pointList(stepCount: Int): List<Point> {
    val points = mutableListOf<Point>()

    var fX: Double = start.x
    var fY: Double = start.y

    val stepX: Double = countStep(start.x, end.x, stepCount.toDouble())
    val stepY: Double = countStep(start.y, end.y, stepCount.toDouble())

    var i = 0
    while (i <= stepCount) {
        val p = Point()
        p.x = fX
        p.y = fY
        points.add(p)
        i++
        fX += stepX
        fY += stepY
    }

    return points
}

private fun countStep(start: Double, end: Double, steps: Double): Double {
    return (end - start) / steps
}