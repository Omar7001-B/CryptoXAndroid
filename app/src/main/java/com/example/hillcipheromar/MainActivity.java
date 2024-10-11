package com.example.hillcipheromar;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.ViewCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;

import com.example.hillcipheromar.utils.HillCipher;

public class MainActivity extends AppCompatActivity {
    private EditText plainText, encryptionKey, cipherText, decryptionKey;
    private Button generateKeyBtn, encryptBtn, decryptBtn;
    private TextView encryptedTextView, decryptedTextView;
    private int[][] keyMatrix;  // Store the key matrix used for encryption and decryption

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeUIElements();
        buttonClickListeners();
    }

    private void initializeUIElements() {
        plainText = findViewById(R.id.plainText);
        encryptionKey = findViewById(R.id.encryptionKey);
        cipherText = findViewById(R.id.cipherText);
        decryptionKey = findViewById(R.id.decryptionKey);
        generateKeyBtn = findViewById(R.id.generateKeyBtn);
        encryptBtn = findViewById(R.id.encryptBtn);
        decryptBtn = findViewById(R.id.decryptBtn);
        encryptedTextView = findViewById(R.id.encryptedTextView);
        decryptedTextView = findViewById(R.id.decryptedTextView);
    }

    private void buttonClickListeners() {
        // Encrypt Button Listener
        encryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = plainText.getText().toString();
                String key = encryptionKey.getText().toString();
                if (key.length() != 4) {
                    encryptedTextView.setText("Key must be 4 letters.");
                    return;
                }
                int[][] keyMatrix = HillCipher.letterKeyToMatrix(key); // Convert letter key to matrix
                int determinant = HillCipher.determinant(keyMatrix);
                int positiveDeterminant = HillCipher.positiveDeterminant(keyMatrix);
                String encryptedText = HillCipher.encrypt(text, keyMatrix);
                encryptedTextView.setText("Encrypted text: " + encryptedText + "\n\nkeyDet: " + determinant + "%26 = " + positiveDeterminant + ""); // Show encrypted text
            }
        });

        // Decrypt Button Listener
        decryptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cipher = cipherText.getText().toString();
                String key = decryptionKey.getText().toString();
                if (key.length() != 4) {
                    decryptedTextView.setText("Key must be 4 letters.");
                    return;
                }
                int[][] keyMatrix = HillCipher.letterKeyToMatrix(key); // Convert letter key to matrix
                String decryptedText = HillCipher.decrypt(cipher, keyMatrix);
                decryptedTextView.setText("Decrypted text: " + decryptedText); // Show decrypted text
            }
        });

        // Generate Key Button Listener
        generateKeyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String generatedKey = HillCipher.generateLetterKey(); // Generate a key of letters
                encryptionKey.setText(generatedKey); // Display the letter key
            }
        });

        // Click on the encrypted text to auto-fill the decryption fields
        encryptedTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cipherText.setText(encryptedTextView.getText().toString().split(": ")[1].split("\n")[0]);
                decryptionKey.setText(encryptionKey.getText().toString());
            }
        });

        // Click on the author text to swtich between dark and light mode
        findViewById(R.id.authorTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { toggleDarkMode(); }
        });
    }

    private void toggleDarkMode() {
        int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (currentNightMode == Configuration.UI_MODE_NIGHT_YES) {
            // If currently in dark mode, switch to light mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            // If currently in light mode, switch to dark mode
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

}
