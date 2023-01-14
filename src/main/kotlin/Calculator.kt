import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.KeyEvent
import javafx.scene.layout.VBox
import tornadofx.*

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
                "√" -> display.text = Math.sqrt(displayValue).toString()
                "x^2" -> display.text = Math.pow(displayValue,2.0).toString()
                "x^y" -> onAction(Operator.Power(displayValue))
                "e^x" -> display.text = Math.exp(displayValue).toString()
                "log" -> display.text = Math.log(displayValue).toString()
            }
        }
    }
}