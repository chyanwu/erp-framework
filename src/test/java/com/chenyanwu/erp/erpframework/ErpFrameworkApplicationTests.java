package com.chenyanwu.erp.erpframework;

import com.alibaba.druid.filter.config.ConfigTools;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ErpFrameworkApplicationTests {

//    @Autowired
//    StringEncryptor encryptor;

    @Test
    public void getPass() {
//        String url = encryptor.encrypt("jdbc:mysql://127.0.0.1:3306/erp-framework?useUnicode=true&characterEncoding=utf8");
//        String name = encryptor.encrypt("root");
//        String password = encryptor.encrypt("mysql");
//        System.out.println(url+"----------------");
//        System.out.println(name+"----------------");
//        System.out.println(password+"----------------");
//        Assert.assertTrue(name.length() > 0);
//        Assert.assertTrue(password.length() > 0);
    }

    @Test
    public void druidEncrypt() throws Exception {
        //密码明文
        String password = "mysql";
        System.out.println("明文密码: " + password);
        String[] keyPair = ConfigTools.genKeyPair(512);
        //私钥
        String privateKey = keyPair[0];
        //公钥
        String publicKey = keyPair[1];

        //用私钥加密后的密文
        password = ConfigTools.encrypt(privateKey, password);

        System.out.println("privateKey:" + privateKey);
        System.out.println("publicKey:" + publicKey);

        System.out.println("password:" + password);

        String decryptPassword = ConfigTools.decrypt(publicKey, password);
        System.out.println("解密后:" + decryptPassword);
    }

}

