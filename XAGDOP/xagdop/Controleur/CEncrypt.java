package xagdop.Controleur;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import xagdop.Util.ErrorManager;
import xagdop.ressources.Bundle;

//Encrypt passwords
public class CEncrypt {
	
	public static String getEncodedPassword(String key) throws Error{
		byte[] uniqueKey = key.getBytes();
		byte[] hash = null;
		try {
			hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
		} catch (NoSuchAlgorithmException e) {
			ErrorManager.getInstance().setErrMsg(Bundle.getText("cencrypt.err.msg"));
			ErrorManager.getInstance().setErrTitle(Bundle.getText("cencrypt.err.title"));
			throw new Error();
		}
		StringBuffer hashString = new StringBuffer();
		for ( int i = 0; i < hash.length; ++i ) {
			String hex = Integer.toHexString(hash[i]);
			if ( hex.length() == 1 ) {
				hashString.append('0');
				hashString.append(hex.charAt(hex.length()-1));
			} else {
				hashString.append(hex.substring(hex.length()-2));
			}
		}
		return hashString.toString();
	}
	
	public static boolean testPassword(String clearTextTestPassword,String encodedActualPassword) throws NoSuchAlgorithmException
	{
		String encodedTestPassword = CEncrypt.getEncodedPassword(clearTextTestPassword);
		
		return (encodedTestPassword.equals(encodedActualPassword));
	}
}
