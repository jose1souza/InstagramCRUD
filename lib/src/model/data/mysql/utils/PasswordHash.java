package model.data.mysql.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash {
	public static String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hashBytes = digest.digest(password.getBytes());

			StringBuilder hexString = new StringBuilder();
			for (byte b : hashBytes) {
				String hex = Integer.toHexString(0xff & b);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}

			return hexString.toString();
		} catch (NoSuchAlgorithmException nsae) {
			throw new RuntimeException("Erro ao gerar hash da senha", nsae);
		}
	}

	public static boolean checkPassword(String plainPassword, String hashedPassword) {
		String hashedInput = hashPassword(plainPassword);
		return hashedInput.equals(hashedPassword);
	}
}
