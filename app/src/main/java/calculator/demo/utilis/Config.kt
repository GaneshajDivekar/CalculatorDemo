package calculator.demo.utilis

import android.content.Context
import com.simplemobiletools.commons.helpers.BaseConfig

class Config (context: Context) : BaseConfig(context) {
    companion object {
        fun newInstance(context: Context) = Config(context)
    }
}