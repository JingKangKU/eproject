package com.chinaums.common.utils.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * 数字长度输入限制器
 */
public class DecimalDigitsInputFilter implements InputFilter {

    private final int decimalDigits;
    private final int integerDigits;

    /**
     * Constructor.
     *
     * @param decimalDigits maximum decimal digits
     */
    public DecimalDigitsInputFilter(int decimalDigits) {
        this.decimalDigits = decimalDigits;
        this.integerDigits = 0;
    }

    public DecimalDigitsInputFilter(int decimalDigits, int integerDigits) {
        this.decimalDigits = decimalDigits;
        this.integerDigits = integerDigits;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

        int dotPos = -1;
        int len = dest.length();
        for (int i = 0; i < len; i++) {
            char c = dest.charAt(i);
            if (c == '.' || c == ',') {
                dotPos = i;
                break;
            }
        }
        if (dotPos >= 0) {

            // protects against many dots
            if (source.equals(".") || source.equals(",")) {
                return "";
            }
            if (dend > dotPos && len - dotPos > decimalDigits) {
                return "";
            }
        }
        if (integerDigits > 0) {
            if (dotPos < 0 && len >= integerDigits && !source.equals(".")) {
                return "";
            } else if (dend <= dotPos && dotPos >= integerDigits) {
                return "";
            }
        }

        return null;
    }

}
