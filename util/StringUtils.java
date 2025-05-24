package br.com.netservicos.novosms.emissao.util;


/**
 * 
 * <b>Class Description :</b>Utility class used for Strings<br>
 * <br>
 * <br><b>Project Name : NETFaturaPDF</b><br>
 * <br><b>Date Created : 12/06/2006 </b><br>
 * @author Robin Michael Gray - rgray@dynamic.com.br<br>
 * @version 
 * <br><b>Modifications :</b><br>
 * <b>	DATE		|		USER			|		DESCRIPTION </b><br>
 * *****************************************************************************<br>
 * 	  12/06/2006 			Robin Michael Gray		Initial Version
 *
 *
 */
public class StringUtils {
    
	/**
	 * Replace occurrences of a substring.
	 *
	 * StringUtils.replace("1-2-3", "-", "|");<br>
	 * result: "1|2|3"<br>
	 * StringUtils.replace("-1--2-", "-", "|");<br>
	 * result: "|1||2|"<br>
	 * StringUtils.replace("123", "", "|");<br>
	 * result: "123"<br>
	 * StringUtils.replace("1-2---3----4", "--", "|");<br>
	 * result: "1-2|-3||4"<br>
	 * StringUtils.replace("1-2---3----4", "--", "---");<br>
	 * result: "1-2----3------4"<br>
	 *
	 * @param s String to be modified.
	 * @param find String to find.
	 * @param replace String to replace.
	 * @return a string with all the occurrences of the string to find replaced.
	 * @throws NullPointerException if s is null.
	 *
	 */
	public static String replace(String s, String find, String replace) {
		if (s==null) {
			return null;
		}
		int findLength;
		// the next statement has the side effect of throwing a null pointer
		// exception if s is null.
		int stringLength = s.length();
		if (find == null || (findLength = find.length()) == 0) {
			// If there is nothing to find, we won't try and find it.
			return s;
		}
		if (replace == null) {
			// a null string and an empty string are the same
			// for replacement purposes.
			replace = "";
		}
		int replaceLength = replace.length();

		// We need to figure out how long our resulting string will be.
		// This is required because without it, the possible resizing
		// and copying of memory structures could lead to an unacceptable runtime.
		// In the worst case it would have to be resized n times with each
		// resize having a O(n) copy leading to an O(n^2) algorithm.
		int length;
		if (findLength == replaceLength) {
			// special case in which we don't need to count the replacements
			// because the count falls out of the length formula.
			length = stringLength;
		} else {
			int count;
			int start;
			int end;

			// Scan s and count the number of times we find our target.
			count = 0;
			start = 0;
			while ((end = s.indexOf(find, start)) != -1) {
				count++;
				start = end + findLength;
			}
			if (count == 0) {
				// special case in which on first pass, we find there is nothing
				// to be replaced.  No need to do a second pass or create a string buffer.
				return s;
			}
			length = stringLength - (count * (findLength - replaceLength));
		}

		int start = 0;
		int end = s.indexOf(find, start);
		if (end == -1) {
			// nothing was found in the string to replace.
			// we can get this if the find and replace strings
			// are the same length because we didn't check before.
			// in this case, we will return the original string
			return s;
		}
		// it looks like we actually have something to replace
		// *sigh* allocate memory for it.
		StringBuffer sb = new StringBuffer(length);

		// Scan s and do the replacements
		while (end != -1) {
			sb.append(s.substring(start, end));
			sb.append(replace);
			start = end + findLength;
			end = s.indexOf(find, start);
		}
		end = stringLength;
		sb.append(s.substring(start, end));

		return (sb.toString());
	}

	/**
	 * Removes all characters that are not a number
	 * @param string to be formatted
	 * @return String
	 */
	public static String removeAllNonNumbers(String string) {
	    if (string==null){
	    	return null;
	    }
    
		StringBuffer formattedString = new StringBuffer();
		char[] chars = string.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isDigit(chars[i])) {
				formattedString.append(chars[i]);
			}
		}
		return formattedString.toString();
	}
	
	/**
	 * Removes all charcters that are not a number or letter
	 * @param str to be formatted
	 * @return String
	 */
	public static String removeAllNonLettersAndNumbers(String str) {
	    if (str==null){
	    	return null;
	    }
  
		StringBuffer formattedString = new StringBuffer();
		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isDigit(chars[i])) {
				formattedString.append(chars[i]);
			}
			if (Character.isLetter(chars[i])) {
				formattedString.append(chars[i]);
			}
		}
		return formattedString.toString();

	}

	/**
	 * Split the given String into tokens.
	 * <P>
	 * This method is meant to be similar to the split
	 * function in other programming languages but it does
	 * not use regular expressions.  Rather the String is
	 * split on a single String literal.
	 * <P>
	 * Unlike java.util.StringTokenizer which accepts
	 * multiple character tokens as delimiters, the delimiter
	 * here is a single String literal.
	 * <P>
	 * Each null token is returned as an empty String.
	 * Delimiters are never returned as tokens.
	 * <P>
	 * If there is no delimiter because it is either empty or
	 * null, the only element in the result is the original String.
	 * <P>
	 * StringUtils.split("1-2-3", "-");<br>
	 * result: {"1", "2", "3"}<br>
	 * StringUtils.split("-1--2-", "-");<br>
	 * result: {"", "1", ,"", "2", ""}<br>
	 * StringUtils.split("123", "");<br>
	 * result: {"123"}<br>
	 * StringUtils.split("1-2---3----4", "--");<br>
	 * result: {"1-2", "-3", "", "4"}<br>
	 *
	 * @param s String to be split.
	 * @param delimiter String literal on which to split.
	 * @return an array of tokens.
	 * @throws NullPointerException if s is null.
	 *
	 */
	public static String[] split(String s, String delimiter) {
	    if (s==null){
	    	return null;
	    }
    
		int delimiterLength;
		// the next statement has the side effect of throwing a null pointer
		// exception if s is null.
		int stringLength = s.length();
		if (delimiter == null || (delimiterLength = delimiter.length()) == 0) {
			// it is not inherently clear what to do if there is no delimiter
			// On one hand it would make sense to return each character because
			// the null String can be found between each pair of characters in
			// a String.  However, it can be found many times there and we don'
			// want to be returning multiple null tokens.
			// returning the whole String will be defined as the correct behavior
			// in this instance.
			return new String[] { s };
		}

		// a two pass solution is used because a one pass solution would
		// require the possible resizing and copying of memory structures
		// In the worst case it would have to be resized n times with each
		// resize having a O(n) copy leading to an O(n^2) algorithm.

		int count;
		int start;
		int end;

		// Scan s and count the tokens.
		count = 0;
		start = 0;
		while ((end = s.indexOf(delimiter, start)) != -1) {
			count++;
			start = end + delimiterLength;
		}
		count++;

		// allocate an array to return the tokens,
		// we now know how big it should be
		String[] result = new String[count];

		// Scan s again, but this time pick out the tokens
		count = 0;
		start = 0;
		while ((end = s.indexOf(delimiter, start)) != -1) {
			result[count] = (s.substring(start, end));
			count++;
			start = end + delimiterLength;
		}
		end = stringLength;
		result[count] = s.substring(start, end);

		return (result);
	}

	/**
	 * Pad the beginning of the given String with spaces until
	 * the String is of the given length.
	 * <p>
	 * If a String is longer than the desired length,
	 * it will not be truncated, however no padding
	 * will be added.
	 *
	 * @param s String to be padded.
	 * @param length desired length of result.
	 * @return padded String.
	 * @throws NullPointerException if s is null.
	 *
	 */
	public static String prepad(String s, int length) {
		return prepad(s, length, ' ');
	}

	/**
	 * Pre-pend the given character to the String until
	 * the result is the desired length.
	 * <p>
	 * If a String is longer than the desired length,
	 * it will not be truncated, however no padding
	 * will be added.
	 *
	 * @param s String to be padded.
	 * @param length desired length of result.
	 * @param c padding character.
	 * @return padded String.
	 * @throws NullPointerException if s is null.
	 *
	 */
	public static String prepad(String s, int length, char c) {
		int needed = length - s.length();
		if (needed <= 0) {
			return s;
		}
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < needed; i++) {
			sb.append(c);
		}
		sb.append(s);
		return (sb.toString());
	}

	/**
	 * Pad the end of the given String with spaces until
	 * the String is of the given length.
	 * <p>
	 * If a String is longer than the desired length,
	 * it will not be truncated, however no padding
	 * will be added.
	 *
	 * @param s String to be padded.
	 * @param length desired length of result.
	 * @return padded String.
	 * @throws NullPointerException if s is null.
	 *
	 */
	public static String postpad(String s, int length) {
		return postpad(s, length, ' ');
	}

	/**
	 * Append the given character to the String until
	 * the result is  the desired length.
	 * <p>
	 * If a String is longer than the desired length,
	 * it will not be truncated, however no padding
	 * will be added.
	 *
	 * @param s String to be padded.
	 * @param length desired length of result.
	 * @param c padding character.
	 * @return padded String.
	 * @throws NullPointerException if s is null.
	 *
	 */
	public static String postpad(String s, int length, char c) {
		int needed = length - s.length();
		if (needed <= 0) {
			return s;
		}
		StringBuffer sb = new StringBuffer(length);
		sb.append(s);
		for (int i = 0; i < needed; i++) {
			sb.append(c);
		}
		return (sb.toString());
	}

	/**
	 * Pad the beginning and end of the given String with spaces until
	 * the String is of the given length.  The result is that the original
	 * String is centered in the middle of the new string.
	 * <p>
	 * If the number of characters to pad is even, then the padding
	 * will be split evenly between the beginning and end, otherwise,
	 * the extra character will be added to the end.
	 * <p>
	 * If a String is longer than the desired length,
	 * it will not be truncated, however no padding
	 * will be added.
	 *
	 * @param s String to be padded.
	 * @param length desired length of result.
	 * @return padded String.
	 * @throws NullPointerException if s is null.
	 *
	 */
	public static String midpad(String s, int length) {
		return midpad(s, length, ' ');
	}

	/**
	 * Pad the beginning and end of the given String with the given character
	 * until the result is  the desired length.  The result is that the original
	 * String is centered in the middle of the new string.
	 * <p>
	 * If the number of characters to pad is even, then the padding
	 * will be split evenly between the beginning and end, otherwise,
	 * the extra character will be added to the end.
	 * <p>
	 * If a String is longer than the desired length,
	 * it will not be truncated, however no padding
	 * will be added.
	 *
	 * @param s String to be padded.
	 * @param length desired length of result.
	 * @param c padding character.
	 * @return padded String.
	 * @throws NullPointerException if s is null.
	 *
	 */
	public static String midpad(String s, int length, char c) {
		int needed = length - s.length();
		if (needed <= 0) {
			return s;
		}
		int beginning = needed / 2;
		int end = beginning + needed % 2;
		StringBuffer sb = new StringBuffer(length);
		for (int i = 0; i < beginning; i++) {
			sb.append(c);
		}
		sb.append(s);
		for (int i = 0; i < end; i++) {
			sb.append(c);
		}
		return (sb.toString());
	}

	/**
	 * Method firstLetterToUpperCase
	 * @param str String
	 * @return String
	 */
	public static String firstLetterToUpperCase(String str) {
		if (str==null){
			return null;
		}  
		String first = str.substring(0,1);
		String last = str.substring(1);
		return first.toUpperCase() + last.toLowerCase();
	}
  
	/**
	 * Converts the string to a integer
	 * @param str String
	 * @return Integer of the string
	 */
	public static int stringToInt(String str) {
		
		if (str == null || str.length() <= 0) {
			return 0;
		} else {
			try {
				return Integer.valueOf(str).intValue();
			} catch (NumberFormatException e) {
				System.out.println(str + " is not a number." );
				return 0;
			}
		}
		
	}

	public static String formataMsgClaroClubeArquivo(String str){
		return str.replaceAll("|","").replaceAll("\\|","").replaceAll("\\#","").replaceAll("#","");
	}
	
	public static String formataMsgClaroClubePDF(String str){
		return str;
	}
	
	
	/**
	 * Adiciona zeros na esquerda do valor para fica do tamanhoFinal.
	 *
	 * @param tamanhoFinal
	 *            10
	 * @param valor
	 *            55
	 * @return 0000000055
	 */
	public static String completarEspaco(int tamanhoFinal, String valor) {

		StringBuffer zerosValor = new StringBuffer();

		for (int i = 0; i < tamanhoFinal - valor.length(); i++)
			zerosValor.append(" ");

		return valor.concat(zerosValor.toString());
	}

}
