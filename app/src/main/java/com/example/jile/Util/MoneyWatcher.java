package com.example.jile.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MoneyWatcher implements TextWatcher {
    public MoneyWatcher(){

    }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String temp = s.toString();
            int posDot = temp.indexOf(".");
            if (posDot < 0) return;
            //输入以 “ . ”开头的情况，自动在.前面补0
            if (temp.startsWith(".") && posDot == 0) {
                s.insert(0, "0");
                return;
            }
            //输入"08" 等类似情况
            if (temp.startsWith("0") && temp.length() > 1 && (posDot == -1 || posDot > 1)) {
                s.delete(0, 1);
                return;
            }
            //小数点后面只能有两位小数
            if (temp.length() - posDot - 1 > 2) {
                s.delete(posDot + 3, posDot + 4);
            }
        }
}
