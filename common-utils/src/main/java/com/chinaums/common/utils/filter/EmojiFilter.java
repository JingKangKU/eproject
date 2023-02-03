package com.chinaums.common.utils.filter;

import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

public class EmojiFilter implements InputFilter {

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        StringBuffer buffer = new StringBuffer();
        for (int i = start; i < end; i++) {
            char codePoint = source.charAt(i);
            if (!getIsEmoji(codePoint)) {
                buffer.append(codePoint);
            } else {
                i++;
            }
        }
        if (source instanceof Spanned) {
            SpannableString sp = new SpannableString(buffer);
            TextUtils.copySpansFrom((Spanned) source, start, end, null, sp, 0);
            return sp;
        } else {
            return buffer;
        }
    }

    public boolean getIsEmoji(char codePoint) {
        return (codePoint != 0x0) && (codePoint != 0x9) && (codePoint != 0xA)
                && (codePoint != 0xD)
                && ((codePoint < 0x20) || (codePoint > 0x29))
                && ((codePoint < 0x2A) || (codePoint > 0x3A))
                && ((codePoint < 0x40) || (codePoint > 0xA8))
                && ((codePoint < 0xAF) || (codePoint > 0x203B))
                && ((codePoint < 0x203D) || (codePoint > 0x2048))
                && ((codePoint < 0x2050) || (codePoint > 0x20e2))
                && ((codePoint < 0x20e4) || (codePoint > 0x2100))
                && ((codePoint < 0x21AF) || (codePoint > 0x2300))
                && ((codePoint < 0x23FF) || (codePoint > 0X24C1))
                && ((codePoint < 0X24C3) || (codePoint > 0x2500))
                && ((codePoint < 0x2800) || (codePoint > 0x2933))
                && ((codePoint < 0x2936) || (codePoint > 0x2AFF))
                && ((codePoint < 0x2C00) || (codePoint > 0x3029))
                && ((codePoint < 0x3031) || (codePoint > 0x303C))
                && ((codePoint < 0x303E) || (codePoint > 0x3296))
                && ((codePoint < 0x32A0) || (codePoint > 0xD7FF))
                && ((codePoint < 0xE000) || (codePoint > 0xFE0E))
                && ((codePoint < 0xFE10) || (codePoint > 0xFFFD));
    }
}