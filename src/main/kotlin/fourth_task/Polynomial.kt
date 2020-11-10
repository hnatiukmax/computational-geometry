package sixth_task

class Polynomial(var coefficients: DoubleArray = doubleArrayOf()) {

    fun derivative(): Polynomial? {
        val newCoefficients = DoubleArray(coefficients.size - 1)
        for (i in newCoefficients.indices) {
            newCoefficients[i] = coefficients[i + 1] * (i + 1)
        }
        return Polynomial(newCoefficients)
    }

    fun evaluate(z: Complex): Complex {
        var result = Complex()
        for (i in coefficients.indices) {

            if (i == 0) {
                result = result.add(coefficients[0])
                continue
            }

            if (coefficients[i] == 0.0) {
                continue
            }

            result = result.add(z.pow(i.toDouble()).multiply(coefficients[i]))
        }

        return result
    }
}