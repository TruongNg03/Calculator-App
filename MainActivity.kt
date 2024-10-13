package com.example.newcalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.isDigitsOnly
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var textResult: TextView
    lateinit var textMath: TextView

    var state: Int  = 1
    var op: Int = 0
    var op1: Int = 0
    var op2: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textResult = findViewById(R.id.result)
        textMath = findViewById(R.id.mathText)

        findViewById<Button>(R.id.btn0).setOnClickListener(this)
        findViewById<Button>(R.id.btn1).setOnClickListener(this)
        findViewById<Button>(R.id.btn2).setOnClickListener(this)
        findViewById<Button>(R.id.btn3).setOnClickListener(this)
        findViewById<Button>(R.id.btn4).setOnClickListener(this)
        findViewById<Button>(R.id.btn5).setOnClickListener(this)
        findViewById<Button>(R.id.btn6).setOnClickListener(this)
        findViewById<Button>(R.id.btn7).setOnClickListener(this)
        findViewById<Button>(R.id.btn8).setOnClickListener(this)
        findViewById<Button>(R.id.btn9).setOnClickListener(this)

        findViewById<Button>(R.id.btnAC).setOnClickListener(this)
        findViewById<Button>(R.id.btnCE).setOnClickListener(this)

        findViewById<Button>(R.id.btnAdd).setOnClickListener(this)
        findViewById<Button>(R.id.btnSub).setOnClickListener(this)
        findViewById<Button>(R.id.btnMulti).setOnClickListener(this)
        findViewById<Button>(R.id.btnDivide).setOnClickListener(this)
        findViewById<Button>(R.id.btnEqual).setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val id = p0?.id

        if(id == R.id.btnAC){
            op1 = 0
            op2 = 0
            op = 0
            state = 1
            textResult.text = "0"
            textMath.text = "0"
        }

        if(id == R.id.btnCE){
            deleteLastDigit()
        }

        if (id == R.id.btn0) {
            addDigit(0)
            plusChar('0')
        } else if (id == R.id.btn1) {
            addDigit(1)
            plusChar('1')
        } else if (id == R.id.btn2) {
            addDigit(2)
            plusChar('2')
        } else if (id == R.id.btn3) {
            addDigit(3)
            plusChar('3')
        } else if (id == R.id.btn4) {
            addDigit(4)
            plusChar('4')
        } else if (id == R.id.btn5) {
            addDigit(5)
            plusChar('5')
        } else if (id == R.id.btn6) {
            addDigit(6)
            plusChar('6')
        } else if (id == R.id.btn7) {
            addDigit(7)
            plusChar('7')
        } else if (id == R.id.btn8) {
            addDigit(8)
            plusChar('8')
        } else if (id == R.id.btn9) {
            addDigit(9)
            plusChar('9')
        } else if (id == R.id.btnAdd) {
            changeOperator()
            op = 1
            state = 2
            plusChar('+')
        } else if (id == R.id.btnSub) {
            changeOperator()
            op = 2
            state = 2
            plusChar('-')
        } else if (id == R.id.btnMulti) {
            changeOperator()
            op = 3
            state = 2
            plusChar('*')
        } else if (id == R.id.btnDivide) {
            changeOperator()
            op = 4
            state = 2
            plusChar('/')
        } else if(id == R.id.btnEqual) {
            changeOperator()
            state = 1
        }
    }

    private fun plusChar(s: Char) {
        var str = textMath.text.toString()

        if(str.length == 1) {
            if(str == "0" && s.isDigit()) {
                str = str.dropLast(1)
            }
        } else {
            if(!s.isDigit() && !str.last().isDigit()) {
                str = str.dropLast(1)
            }
        }

        str = str.plus(s.toString())

        textMath.text = str
    }

    private fun addDigit(i: Int) {
        if(state == 1) {
            op1 = op1 * 10 + i
        } else {
            op2 = op2 * 10 + i
        }
        showResult()
    }

    // CE btn
    private fun deleteLastDigit() {
        var str = textMath.text.toString()

        if(state == 1) {
            op1 /= 10
//            textMath.text = "$op1"
        } else {
            op2 /= 10
//            textMath.text = "$op2"
        }

        if(str.length > 1) {
            str = str.dropLast(1)
            // delete operator char
            if(!str.last().isDigit()) {
                op = 0
            }
        } else {
            str = "0"
        }

        showResult()
        textMath.text = str
    }

    // only show result first
    private fun showResult() {
        var str = textMath.text.toString()
        var result = 0;

        // op =
        if(op == 0) {
            result = op1
        }

        // operator +
        if(op == 1) {
            result = op1 + op2
        }

        // operator -
        if(op == 2) {
            result = op1 - op2
        }

        // operator *
        if(op == 3) {
            if(!str.last().isDigit()) {
                result = op1
            } else {
                result = op1 * op2
                textResult.text = "$result"
            }
        }

        // operator /
        if(op == 4) {
            if(!str.last().isDigit()) {
                result = op1
            } else {
                result = op1 / op2
                textResult.text = "$result"
            }
        }

        textResult.text = "$result"
    }

    private fun changeOperator() {
        var str = textMath.text.toString()
        var result = 0

        // op =
        if(op == 0) {
            result = op1
            state = 1
        }

        // operator +
        if(op == 1) {
            result = op1 + op2
            op1 = result
        }

        // operator -
        if(op == 2) {
            result = op1 - op2
            op1 = result
        }

        // operator *
        if(op == 3) {
            if(!str.last().isDigit()) {
                result = op1
            } else {
                result = op1 * op2
            }
            op1 = result
        }

        // operator /
        if(op == 4) {
            if(!str.last().isDigit()) {
                result = op1
            } else {
                result = op1 / op2
            }
            op1 = result
        }

        textResult.text = "$result"
        textMath.text = "$result"
        op = 0
        op2 = 0
    }
}