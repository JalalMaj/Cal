import kotlin.math.pow

sealed class Operator(val x: Double) {
    abstract fun calculate(y: Double): Double

    class Add(x: Double): Operator(x) {
        override fun calculate(y: Double)= x + y
    }

    class Subtract(x: Double): Operator(x) {
        override fun calculate(y: Double)= x - y
    }

    class Mul(x: Double): Operator(x) {
        override fun calculate(y: Double)= x * y
    }

    class Div(x: Double): Operator(x) {
        override fun calculate(y: Double)= x / y
    }
    class Power(x: Double): Operator(x) {
        override fun calculate(y: Double)= x.pow(y)
    }

    class SquareRoot(x: Double): Operator(x) {
        override fun calculate(y: Double)= x.pow(1/y)
    }

}