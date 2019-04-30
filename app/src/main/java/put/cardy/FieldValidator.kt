package put.cardy

import android.text.TextUtils
import android.widget.EditText

class FieldValidator {

    companion object {

        fun validateField(value: String, field: EditText): Boolean {
            if (TextUtils.isEmpty(value)) {
                field.error = "Field is required!"
                return false
            }
            return true
        }

    }
}