const CryptoJS = require("crypto-js");

// Set the encryption key and data to encrypt
const key = "mySecretKey";
const dataToEncrypt = config.kakao_login_apikey;

// Encrypt the data
const encryptedData = CryptoJS.AES.encrypt(dataToEncrypt, key).toString();

// Decrypt the data
const decryptedData = CryptoJS.AES.decrypt(encryptedData, key).toString(CryptoJS.enc.Utf8);

console.log(`Original data: ${dataToEncrypt}`);
console.log(`Encrypted data: ${encryptedData}`);
console.log(`Decrypted data: ${decryptedData}`);