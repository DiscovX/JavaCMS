package util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMD5String(String str){
		MessageDigest messageDigest = null;
		
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] ba = messageDigest.digest();
		StringBuffer sb = new StringBuffer();
		for(int i = 0;i < ba.length;i++){
			int temp = 0xFF & ba[i];
			String hexString = Integer.toHexString(temp);
			if(hexString.length() == 1)
				sb.append("0").append(hexString);
			else
				sb.append(hexString);
		}
		
		return sb.toString();		
	}
}
