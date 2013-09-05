package com.gamealoon.algorithm;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * The Algorithm for securing password. Using PBKDF2 to secure the password by hashing and salting 
 * 
 * @author Partho
 *
 */
public class SecurePassword {

	public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";
	public static final int PBKDF2_ITERATIONS=1000;
	public static final int HASH_BYTE_SIZE=24;
	public static final int SALT_BYTE_SIZE=24;
	
	
	/**
	 * Main entry method for hash creation
	 * 
	 * @param password
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static HashMap<String, String> createHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		return createHash(password.toCharArray());
	}
	
	private static HashMap<String, String> createHash(char[] password)throws NoSuchAlgorithmException,InvalidKeySpecException
	{
		HashMap<String, String> hashSaltMap = new HashMap<>();
		byte[] salt= generateSalt();
		byte[] hash = encryptViaPbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		hashSaltMap.put("saltHex",toHex(salt));
		hashSaltMap.put("hashHex", toHex(hash));
		return hashSaltMap;
	}
	
	/**
	 * The method which compare incoming password hash with stored hash and salt
	 * 
	 * @param password
	 * @param storedHash
	 * @param storedSalt
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static boolean validatePassword(String password, String storedHash, String storedSalt) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		return validatePassword(password.toCharArray(), storedHash, storedSalt);
	}
	
	private static boolean validatePassword(char[] password, String storedHash, String storedSalt) throws NoSuchAlgorithmException, InvalidKeySpecException	
	{
		byte[] hash = fromHex(storedHash);
		byte[] salt = fromHex(storedSalt);
		byte[] newHash = encryptViaPbkdf2(password, salt, PBKDF2_ITERATIONS, HASH_BYTE_SIZE);
		return slowCompareBytes(hash, newHash);
 	}
	
	/**
	 * Check the difference between two byte arrays and if difference is 0 return true else false. This is done in constant length time and slow. 
	 * Therefore difficult for hackers to attack. 
	 * 
	 * @param hash
	 * @param newHash
	 * @return
	 */
	private static boolean slowCompareBytes(byte[] hash, byte[] newHash) {
		int difference = hash.length ^ newHash.length;
		for(int count=0; count<hash.length && count<newHash.length; ++count)
		{
			difference|= hash[count] ^ newHash[count];
		}
		
		return difference == 0;
	}

	/**
	 * Converting hex String into byte array
	 * 
	 * @param hex
	 * @return
	 */
	private static byte[] fromHex(String hex) {
		byte[] data = new byte[hex.length()/2];
		for(int count=0 ; count<data.length;++count)
		{
			data[count] = (byte)Integer.parseInt(hex.substring(2*count, 2*count+2), 16);
		}
		return data;
	}

	/**
	 * Converting byte array into Hex String
	 * 
	 * @param data
	 * @return
	 */
	private static String toHex(byte[] data)
	{
		BigInteger bigInteger = new BigInteger(1, data);
		String hex = bigInteger.toString(16);
		int paddingLength = (data.length*2)-hex.length();
		if(paddingLength>0)
		{
			return String.format("%0"+paddingLength+"d", 0)+hex;
		}
		else
		{
			return hex;
		}
		
	}
	/**
	 * This method generates salt for our password
	 * 
	 * @return
	 */
	private static byte[] generateSalt()
	{
		byte[] salt = new byte[SALT_BYTE_SIZE];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		return salt;
	}
	
	/**
	 * Password Hash computed
	 * 
	 * @param password
	 * @param salt
	 * @param iterations
	 * @param hashBytesLength
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	private static byte[] encryptViaPbkdf2(char[] password, byte[] salt, int iterations, int hashBytesLength) throws NoSuchAlgorithmException, InvalidKeySpecException
	{
		PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterations, hashBytesLength*2);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
		return keyFactory.generateSecret(keySpec).getEncoded();
	}
}
