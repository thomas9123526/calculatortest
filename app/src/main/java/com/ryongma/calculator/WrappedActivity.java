package com.ryongma.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.cryptolib.SimpleCrypto;
import com.example.mathops.MulDivCalculator;

public class WrappedActivity extends AppCompatActivity {

    public static final String EXTRA_OPERATOR = "extra_operator";
    public static final String EXTRA_LEFT = "extra_left";
    public static final String EXTRA_RIGHT = "extra_right";
    public static final String EXTRA_RESULT = "extra_result";
    public static final String EXTRA_SOURCE = "extra_source";

    private EditText inputA;
    private EditText inputB;
    private TextView resultText;
    private EditText cipherText;
    private TextView decryptedText;

    private boolean hasResult = false;
    private char lastOperator;
    private double lastLeft;
    private double lastRight;
    private double lastResult;
    private String lastSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        inputA = findViewById(R.id.inputA);
        inputB = findViewById(R.id.inputB);
        resultText = findViewById(R.id.resultText);
        cipherText = findViewById(R.id.cipherText);
        decryptedText = findViewById(R.id.decryptedText);

        Button btnAdd = findViewById(R.id.btnAdd);
        Button btnSub = findViewById(R.id.btnSub);
        Button btnMul = findViewById(R.id.btnMul);
        Button btnDiv = findViewById(R.id.btnDiv);
        Button btnEncrypt = findViewById(R.id.btnEncrypt);
        Button btnDecrypt = findViewById(R.id.btnDecrypt);
        Button btnFinish = findViewById(R.id.btnFinish);

        btnAdd.setOnClickListener(v -> compute('+'));
        btnSub.setOnClickListener(v -> compute('-'));
        btnMul.setOnClickListener(v -> compute('*'));
        btnDiv.setOnClickListener(v -> compute('/'));
        btnEncrypt.setOnClickListener(v -> encryptResult());
        btnDecrypt.setOnClickListener(v -> decryptCipher());
        btnFinish.setOnClickListener(v -> finishWithResult());
    }

    private void encryptResult() {
        if (!hasResult) {
            Toast.makeText(this, "Run a calculation first", Toast.LENGTH_SHORT).show();
            return;
        }
        String plain = String.valueOf(lastResult);
        String cipher = SimpleCrypto.encrypt(plain);
        cipherText.setText(cipher);
        decryptedText.setText("Decrypted: -");
    }

    private void decryptCipher() {
        String cipher = cipherText.getText().toString().trim();
        if (cipher.isEmpty()) {
            Toast.makeText(this, "Enter cipher text first", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String plain = SimpleCrypto.decrypt(cipher);
            decryptedText.setText("Decrypted: " + plain);
        } catch (IllegalArgumentException e) {
            decryptedText.setText("Decrypt error: invalid cipher");
        }
    }

    private void compute(char op) {
        Double a = parse(inputA);
        Double b = parse(inputB);
        if (a == null || b == null) {
            Toast.makeText(this, "Enter two numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double result;
            String source;
            switch (op) {
                case '+':
                    result = NativeCalc.add(a, b);
                    source = "JNI";
                    break;
                case '-':
                    result = NativeCalc.subtract(a, b);
                    source = "JNI";
                    break;
                case '*':
                    result = MulDivCalculator.multiply(a, b);
                    source = "JAR";
                    break;
                case '/':
                    result = MulDivCalculator.divide(a, b);
                    source = "JAR";
                    break;
                default:
                    return;
            }
            resultText.setText("Result (" + source + "): " + result);

            hasResult = true;
            lastOperator = op;
            lastLeft = a;
            lastRight = b;
            lastResult = result;
            lastSource = source;
        } catch (ArithmeticException e) {
            resultText.setText("Error: " + e.getMessage());
            hasResult = false;
        }
    }

    private void finishWithResult() {
        if (!hasResult) {
            Toast.makeText(this, "Run a calculation first", Toast.LENGTH_SHORT).show();
            setResult(Activity.RESULT_CANCELED);
            finish();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_OPERATOR, String.valueOf(lastOperator));
        data.putExtra(EXTRA_LEFT, lastLeft);
        data.putExtra(EXTRA_RIGHT, lastRight);
        data.putExtra(EXTRA_RESULT, lastResult);
        data.putExtra(EXTRA_SOURCE, lastSource);
        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private Double parse(EditText field) {
        String text = field.getText().toString().trim();
        if (text.isEmpty()) return null;
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
