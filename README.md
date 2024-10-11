# CryptoXAndroid

**CryptoXAndroid** is an Android app designed for testing cryptographic algorithms learned at university.

## Implemented Algorithms

- [x] Hill Cipher
- [ ] Other
- [ ] Other

### What is the Hill Cipher?

The **Hill cipher** is a polygraphic substitution cipher that encrypts text using linear algebra. It encrypts pairs of letters (blocks) by multiplying them with a key matrix. The key matrix must be invertible for the decryption process to work.

### How It Works:

1. **Key Matrix**: A 2x2 matrix derived from a 4-letter key.
2. **Text Preparation**: The plaintext is converted to lowercase and padded with 'x' if its length is odd.
3. **Encryption**: Divide the text into pairs of letters and convert letters to numerical values, then multiply each pair by the key matrix to get the ciphertext.
4. **Decryption**: Convert the ciphertext back into numerical values and multiply by the inverse of the key matrix to recover the plaintext.


### Test the Program

Feel free to download the app from the releases section and test the Hill cipher functionality.
![Preview](https://github.com/user-attachments/assets/440887ad-713b-426d-bf1f-222fd5d8ad57)

### MATLAB Implementation

As part of the coursework, the Hill cipher was also implemented in MATLAB. Below is the MATLAB code demonstrating the encryption and decryption processes:

```matlab
%% Main Functions

function encrypt(text, key)
    [text_arr, key_arr] = chars_to_arr(text, key);
    check_key_invertible(key_arr);
    encrypted_arr = mod(text_arr * key_arr, 26);
    encrypted_text = arr_to_chars(encrypted_arr);
    disp(['Encrypted text: ', encrypted_text]);
end

function decrypt(text, key)
    [text_arr, key_arr] = chars_to_arr(text, key);
    key_inv = mod_inv_matrix(key_arr, 26);
    decrypted_arr = mod(text_arr * key_inv, 26);
    decrypted_text = arr_to_chars(decrypted_arr);
    disp(['Decrypted text: ', decrypted_text]);
end

%% Utility Functions

function [text_arr, key_arr] = chars_to_arr(text, key)
    text_arr = reshape(erase(lower(char(text)), ' ') - 'a', 2, []);
    key_arr = reshape(erase(lower(char(key)), ' ') - 'a', 2, 2);
end

function text_char = arr_to_chars(text)
    text_char = reshape(char(text + 'a'), 1, []);
end

%% Matrix Operations

function key_inv = mod_inv_matrix(key, mod_val)
    det_inv = mod_inv(round(det(key)), mod_val);
    adj = [key(2,2), -key(1,2); -key(2,1), key(1,1)];
    key_inv = mod(det_inv * adj, mod_val);
end

function inv = mod_inv(a, mod_val)
    [~, x, ~] = gcd(a, mod_val);
    inv = mod(x, mod_val);
end

%% Validation Functions

function check_key_invertible(key)
    if gcd(round(det(key)), 26) ~= 1
        error('Key is not invertible');
    end
end

%% Run

encrypt('hill', 'dsfd');    
decrypt('loqv', 'dsfd');
```
