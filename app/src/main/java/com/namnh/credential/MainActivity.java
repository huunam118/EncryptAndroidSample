package com.namnh.credential;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText mEdtInputText;
    private TextView mTvEncryptedText;
    private TextView mTvDecryptedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEdtInputText = (EditText) findViewById(R.id.edt_plaintext);
        mTvEncryptedText = (TextView) findViewById(R.id.tv_encrypted);
        mTvDecryptedText = (TextView) findViewById(R.id.tv_decrypted);

        (findViewById(R.id.btn_start)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mEdtInputText.getText())) {
                    Toast.makeText(MainActivity.this, "Please input your plain text!",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                hideKeyBoard();
                processWithPlainText();
            }
        });
    }

    private void hideKeyBoard() {
        InputMethodManager imm =
                (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEdtInputText.getWindowToken(), 0);
    }

    private void processWithPlainText() {
        try {
            String encryptedText = CredentialsAccessor.encrypt(mEdtInputText.getText().toString());
            mTvEncryptedText.setText(encryptedText);
            String decryptedText = CredentialsAccessor.decrypt(encryptedText);
            mTvDecryptedText.setText(decryptedText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
