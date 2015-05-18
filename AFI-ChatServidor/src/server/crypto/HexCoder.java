package server.crypto;

/**
 * The following is a simple set of static methods for converting from hex to
 * bytes and vice-versa
 * 
 * @author Haravikk Mistral
 * @date Sep 15, 2008, 3:26:42 PM
 * @version 1.0
 */
public class HexCoder {
	/**
	 * Quick converts bytes to hex-characters
	 * 
	 * @param bytes
	 *            the byte-array to convert
	 * @return the hex-representation
	 */
	public static String bytesToHex(final byte[] bytes) {
		final StringBuffer s = new StringBuffer(bytes.length * 2);
		for (int i = 0; i < bytes.length; ++i) {
			s.append(Character.forDigit((bytes[i] >> 4) & 0xF, 16));
			s.append(Character.forDigit(bytes[i] & 0xF, 16));
		}
		return s.toString();
	}
 
	/**
	 * Quickly converts hex-characters to bytes
	 * 
	 * @param s
	 *            the hex-string
	 * @return the bytes represented
	 */
	public static byte[] hexToBytes(final String s) {
		final byte[] bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; ++i)
			bytes[i] = (byte) Integer.parseInt(
				s.substring(2 * i, (2 * i) + 2),
				16);
		return bytes;
	}
}