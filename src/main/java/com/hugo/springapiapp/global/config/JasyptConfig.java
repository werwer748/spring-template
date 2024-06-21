package com.hugo.springapiapp.global.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Value("${jasypt.password}")
    private String password;

    @Bean
    public PooledPBEStringEncryptor jasyptStringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor(); // 멀티코어 시스템에서 해독을 병렬처리(성능상 좋다고..)
        encryptor.setPoolSize(4); // 머신의 코어수와 동일하게 사용하는게 좋다.
        encryptor.setPassword(password);
        encryptor.setAlgorithm("PBEWithMD5AndTripleDes");
        return encryptor;
    }
}
