package com.smart.springbootfacerecognition.gson;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.*;

@Slf4j
public class GenerateId {

    public static void main(String... arguments) {
        try {
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

            String randomNum = Integer.valueOf(prng.nextInt()).toString();

            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] result = sha.digest(randomNum.getBytes());

            //1857942503
            //220901077
            System.out.println("Random number: " + randomNum);
            System.out.println("Message digest: " + hexEncode(result));
        } catch (NoSuchAlgorithmException ex) {
            System.err.println(ex);
        }
    }

    static private String hexEncode(byte[] input) {
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f'};
        for (int idx = 0; idx < input.length; ++idx) {
            byte b = input[idx];
            result.append(digits[(b & 0xf0) >> 4]);
            result.append(digits[b & 0x0f]);
        }
        return result.toString();
    }


    public static String[] chars = new String[] {
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V","W", "X", "Y", "Z"
    };


    public static String getShortUuid() {
        StringBuffer stringBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) { // 32 -> 8
            String str = uuid.substring(i * 4, i * 4 + 4);
            // 16进制为基解析
            int strInteger = Integer.parseInt(str, 16);
            // 0x3E -> 字典总数 62
            stringBuffer.append(chars[strInteger % 0x3E]);
        }
        return uuid;
    }

    private static int inc = 0;

    private static long getId(){

        long id = Long.parseLong(String.valueOf(System.currentTimeMillis())
                .substring(1,10)
                .concat(String.valueOf(inc)));
        inc = (inc+1)%10;
        return id;
    }

    /**
     * 获得一个去掉“-”符号的UUID
     * @return String UUID
     */
    public static String getUUID(){
        String s = UUID.randomUUID().toString();
        //去掉“-”符号
        return s.substring(0,8)+s.substring(9,13)+s.substring(14,18)+s.substring(19,23)+s.substring(24);
    }

    public static String[] getUUID(int number) {
        if (number < 1) {
            return null;
        }
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }


    @Test
    public void test1(){
       // System.out.println(getUUID(10).toString());
        System.out.println(getShortUuid());
       /* List list =new ArrayList();
        for (int i = 0; i < 1000; i++) {

            list.add(getUUID(10));
        }


        Set<String> stringSet=new HashSet<>(list);
        log.info("stringSet.size: {}",stringSet.size());
        log.info("List.list: {}",list.size());*/
    }
}
