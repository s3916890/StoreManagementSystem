package lib.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    public static String hashing(String data){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());
            byte[] resultByteArr = messageDigest.digest();
            StringBuilder sb = new StringBuilder();

            for(byte b: resultByteArr){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return "";
    }
}
