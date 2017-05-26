package br.com.luan2.baseapp.extras;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by Luan on 09/05/17.
 */

    public class SuperBrazilianTelephoneMask implements TextWatcher {
        public EditText editText;

        protected boolean isUpdating;
        protected String mOldString = "";
        protected String mMask;

        public static final String DEFAULT_BRAZIL_MASK_NEW = "(##) ####-#####";
        public static final String DEFAULT_BRAZIL_MASK = "(##) ####-####";

        String befores = "";

        public SuperBrazilianTelephoneMask(EditText editText) {
            this.editText = editText;
        }


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            befores = s.toString().replaceAll("[^\\d]", "");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str = s.toString().replaceAll("[^\\d]", "");

         /*String old = "";
        String mascara = "";
        if (isUpdating) {
            old = str;
            isUpdating = false;
            return;
        }
        int i = 0;
        for (char m : mMask.toCharArray()) {
            if (m != '#' && str.length() > old.length()) {
                mascara += m;
                continue;
            }
            try {
                mascara += str.charAt(i);
            } catch (Exception e) {
                break;
            }
            i++;
        }
        isUpdating = true;
        editText.setText(mascara);
        editText.setSelection(mascara.length());*/
            if (str.length() == 0) {
                return;
            }

            if (before == 1 && befores.length() > 0 && !isUpdating) {
                String last = befores.substring(befores.length(), befores.length());
                String rep = last.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");
                if (rep.length() == 0) {
                    str = str.substring(0, befores.length() - 1);
                }
            }

            if (str.length() > 10) {
                mMask = DEFAULT_BRAZIL_MASK_NEW;
            } else {
                mMask = DEFAULT_BRAZIL_MASK;
            }

            StringBuilder mask = new StringBuilder();
            if (isUpdating) {
                mOldString = str;
                isUpdating = false;
                return;
            }
            int i = 0;
            for (char m : mMask.toCharArray()) {
                if (m != '#') {
                    mask.append(m);
                    continue;
                }
                try {
                    mask.append(str.charAt(i));
                } catch (Exception e) {
                    break;
                }
                i++;
            }
            isUpdating = true;
            String x = mask.toString();
            editText.setText(x);
            editText.setSelection(mask.length());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
}
