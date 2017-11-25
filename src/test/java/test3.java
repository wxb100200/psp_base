import cn.hillwind.common.util.BASE64Codec;

import java.security.MessageDigest;

/**
 * Created by wxb on 2017/3/18.
 */
public class test3 {
    public static void main(String[] args) throws Exception{
        String salt = "XDMJXGNUFOJTIOFEMLNU";
        String txtPassword = "11111111";


        MessageDigest digest = MessageDigest.getInstance("MD5");
        digest.update((salt + txtPassword).getBytes("utf-8"));
        byte[] md5 = digest.digest();

        String password = new String( BASE64Codec.encode(md5 ));  // expect: Y8Qvg9cjHQCHcXyW5duqpA==
        System.out.println("salt:" + salt + ",password:" + txtPassword + ", result:" +password);
    }
}
