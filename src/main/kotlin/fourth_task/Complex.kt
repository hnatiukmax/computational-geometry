package sixth_task

import kotlin.math.*

data class Complex(var re: Double = 0.0, var im: Double = 0.0) {

    fun add(n: Double) = Complex(n + re, im)

    fun add(z: Complex) = Complex(re + z.re, im + z.im)

    fun subtract(z: Complex) = Complex(re - z.re, im - z.im)

    fun multiply(n: Double) = Complex(n * re, n * im)

    fun divide(z2: Complex): Complex {
        val k = z2.im.pow(2.0) + z2.re.pow(2.0)
        return Complex(
            (re * z2.re + im * z2.im) / k,
            (im * z2.re - re * z2.im) / k
        )
    }

    fun pow(n: Double): Complex {
        val r = abs().pow(n)
        val theta: Double = n * arg()
        return Complex(cos(theta) * r, sin(theta) * r)
    }

    fun euclideanDistance(z2: Complex) = sqrt((re - z2.re).pow(2.0) + (im - z2.im).pow(2.0))

    fun abs() = sqrt(re.pow(2.0) + im.pow(2.0))

    fun arg() = atan2(im, re)

    fun equals(z2: Complex, tolerance: Double) = euclideanDistance(z2) <= tolerance

    override fun toString(): String {
        return "($re,$im)"
    }
}