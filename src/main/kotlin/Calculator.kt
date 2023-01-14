import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import tornadofx.*
import kotlin.math.*

class Calculator:View() {
    override val root: VBox by fxml()

    @FXML lateinit var display: Label

    init {
        title ="Kotlin calculator"
        root.lookupAll(".button").forEach { b ->
            b.setOnMouseClicked {
                operator((b as Button).text)
            }
        }
        root.addEventFilter(KeyEvent.KEY_TYPED) {
            operator(it.character.toUpperCase().replace("\r","="))
        }
    }

    var state: Operator = Operator.Add(0.0)
    fun onAction(fn: Operator) {
        state =fn
        display.text = ""
    }


    fun factorial(n: Double): Double =
         if (n == 0.0) 1.0 else n * factorial(n - 1)

    val displayValue: Double
        get() = when(display.text){
            "" -> 0.0
            else -> display.text.toDouble()
        }

    private fun operator(x: String) {
        if(Regex("[0-9]").matches(x)) {
            display.text += x
        } else {
            when(x) {
                "+" -> onAction(Operator.Add(displayValue))
                "-" -> onAction(Operator.Subtract(displayValue))
                "/" -> onAction(Operator.Div(displayValue))
                "*" -> onAction(Operator.Mul(displayValue))
                "%" -> { onAction(Operator.Add(displayValue/ 100))
                    operator("=")}
                "C" -> onAction(Operator.Add(0.0))
                "+/-" -> {onAction(Operator.Add(-1 * displayValue))
                    operator("=")}
                "=" -> display.text = state.calculate(displayValue).toString()
                "." -> display.text += "."
                "Pi" -> display.text += "3.14159"
                "rad" -> display.text = Math.toRadians(displayValue).toString()
                "√" -> {
                    display.text = sqrt(displayValue).toString()
                }
                "x^2" -> {
                    display.text = displayValue.pow(2.0).toString()
                }
                "x^y" -> {
                    onAction(Operator.Power(displayValue))
                }
                "e^x" -> {
                    display.text = exp(displayValue).toString()
                }
                "ln" -> {
                    display.text = ln(displayValue).toString()
                }
                "log" -> {
                    display.text = log10(displayValue).toString()
                }
                "1/x" -> {
                    onAction(Operator.Add(1 / displayValue))
                    operator("=")
                }
                "x!" -> {
                    display.text = factorial(displayValue).toString()
                }
                "sin" -> display.text = sin(displayValue).toString()
                "cos" -> display.text = cos(displayValue).toString()
                "tan" -> display.text = (sin(displayValue) / cos(displayValue)).toString()
                "sin-1" -> display.text =  (1 / sin(displayValue)).toString()
                "cos-1" -> display.text = (1 / cos(displayValue)).toString()
                "tan-1" -> display.text = (cos(displayValue) / sin(displayValue)).toString()
                "e" -> display.text = "2.71828182846"
                "10^x" -> display.text = 10.0.pow(displayValue).toString()
                "y√x" -> {
                    onAction(Operator.SquareRoot(displayValue))
                }
            }
        }
    }
}