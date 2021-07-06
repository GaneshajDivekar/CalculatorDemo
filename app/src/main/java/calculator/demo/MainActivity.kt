package calculator.demo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import calculator.demo.databinding.ActivityMainBinding
import calculator.demo.mainmodule.di.DaggerMainActivityCompoment
import calculator.demo.mainmodule.di.MainActivityCompoment
import calculator.demo.mainmodule.di.MainActivityModule
import calculator.demo.utilis.AED_RATE
import calculator.demo.utilis.Calculator
import calculator.demo.utilis.CalculatorImpl

class MainActivity : AppCompatActivity(), View.OnClickListener, Calculator {
    lateinit var calc: CalculatorImpl
    lateinit var mainActivityCompoment: MainActivityCompoment
    lateinit var mainBinding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        DaggerMainActivityCompoment.builder()
                .appComponent((this.application as CalculatorApplications).appComponent)
                .mainActivityModule(MainActivityModule(this as AppCompatActivity))
                .build()
                .let { it1 ->
                    mainActivityCompoment = it1
                    mainActivityCompoment.inject(this)
                }
        calc = CalculatorImpl(this, applicationContext,this@MainActivity)
        mainBinding.btn0.setOnClickListener(this)
        mainBinding.btn1.setOnClickListener(this)
        mainBinding.btn2.setOnClickListener(this)
        mainBinding.btn3.setOnClickListener(this)
        mainBinding.btn4.setOnClickListener(this)
        mainBinding.btn5.setOnClickListener(this)
        mainBinding.btn6.setOnClickListener(this)
        mainBinding.btn7.setOnClickListener(this)
        mainBinding.btn8.setOnClickListener(this)
        mainBinding.btn9.setOnClickListener(this)
        mainBinding.btnDecimal.setOnClickListener(this)
        mainBinding.btn9.setOnClickListener(this)
        mainBinding.btnBack.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_1 -> {
                calc.addDigit(1)
            }
            R.id.btn_2 -> {
                calc.addDigit(2)
            }
            R.id.btn_3 -> {
                calc.addDigit(3)
            }
            R.id.btn_4 -> {
                calc.addDigit(4)
            }
            R.id.btn_5 -> {
                calc.addDigit(5)
            }
            R.id.btn_6 -> {
                calc.addDigit(6)
            }
            R.id.btn_7 -> {
                calc.addDigit(7)
            }
            R.id.btn_8 -> {
                calc.addDigit(8)
            }
            R.id.btn_9 -> {
                calc.addDigit(9)
            }
            R.id.btn_0 -> {
                calc.zeroClicked()
            }
            R.id.btn_decimal -> {
                calc.decimalClicked()
            }
            R.id.btn_back -> {
                calc.handleClear()
            }
        }

    }

    override fun showNewResult(value: String, context: Context) {
        mainBinding.result.setText(AED_RATE + value)
    }

    override fun showNewFormula(value: String, context: Context) {
        System.out.println("value==" + value)
    }
}