package com.hugo.springapiapp;

import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptTest {

    @Test
    public void jasyptTest() {
        String password = ""; //! 암호화할때 사용하고 끝나면 꼭 지울것, 사용한 문자열은 어딘가에 잘 적어두자
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setPoolSize(4);
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDes");
        String content = ""; //! 암호화 할 내용 암호화한 후에 꼭 지울것
        String encryptedContent = encryptor.encrypt(content); // 암호화
        String decryptedContent = encryptor.decrypt(encryptedContent); // 복호화
        System.out.println("encryptedContent = " + encryptedContent);
        System.out.println("decryptedContent = " + decryptedContent);
    }
}
