package com.qqtech.core.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import com.qqtech.core.common.constant.CoreConst;

public class StringUtil {
	public final static String VALUE_CHINESE_CHA = "[\\u4e00-\\u9fbb]+";

	/**
	 * 比较两个版本号的大小(旧在前，新在后)，如果newVer>oldVer，则返回true
	 * 
	 * @param oldVer
	 *            最开始的版本
	 * @param newVer
	 *            需要更新的版本
	 * @return
	 */
	public static boolean isNewVersion(String oldVer, String newVer) {
		if (StringUtil.isNotBlank(oldVer) && StringUtil.isNotBlank(newVer) && !oldVer.equals(newVer)) {
			String[] appVers = oldVer.split("\\.");
			String[] settingVers = newVer.split("\\.");
			if (appVers != null && appVers.length > 0 && settingVers != null && settingVers.length > 0) {
				for (int i = 0; i < settingVers.length; i++) {
					if (new Integer(settingVers[i]).intValue() > new Integer(appVers[i]).intValue()) {
						return true;
					} else if (new Integer(settingVers[i]).intValue() < new Integer(appVers[i]).intValue()) {
						return false;
					}
				}
			}
		}
		return false;
	}

	/**
	 * MD5签名
	 * 
	 * @param str
	 * @param charset
	 * @return
	 */
	public static String md5Sign(String str, String charset) {
		HashFunction md5 = Hashing.md5();
		HashCode md5Hc = null;
		try {
			md5Hc = md5.hashBytes(str.getBytes(charset));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return md5Hc.toString();
	}

	/**
	 * 判断身份证号码是否正确
	 * 
	 * @param identityNum
	 * @return
	 */
	public static boolean isIdentityNum(String identityNum) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile(
				"^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$");
		m = p.matcher(identityNum);
		b = m.matches();
		return b;
	}

	/**
	 * 如果字符串为空指针，则返回空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static String getStrNotNUll(String str) {
		if (StringUtil.isBlank(str)) {
			return "";
		}
		return str;
	}

	public static String lowerCase(String str) {
		return StringUtils.lowerCase(str);
	}

	public static String removeCrlf(String str) {
		if (str == null)
			return null;
		return StringUtil.join(StringUtil.tokenizeToStringArray(str, "\t\n\r\f"), " ");
	}

	private static final Map<String, String> XML = new HashMap<String, String>();

	static {
		XML.put("apos", "'");
		XML.put("quot", "\"");
		XML.put("amp", "&");
		XML.put("lt", "<");
		XML.put("gt", ">");
	}

	public static String unescapeXml(String str) {
		if (str == null)
			return null;
		for (String key : XML.keySet()) {
			String value = XML.get(key);
			str = StringUtil.replace(str, "&" + key + ";", value);
		}
		return str;
	}

	public static String removePrefix(String str, String prefix) {
		return removePrefix(str, prefix, false);
	}

	public static String removePrefix(String str, String prefix, boolean ignoreCase) {
		if (str == null)
			return null;
		if (prefix == null)
			return str;
		if (ignoreCase) {
			if (str.toLowerCase().startsWith(prefix.toLowerCase())) {
				return str.substring(prefix.length());
			}
		} else {
			if (str.startsWith(prefix)) {
				return str.substring(prefix.length());
			}
		}
		return str;
	}

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static String getExtension(String str) {
		if (str == null)
			return null;
		int i = str.lastIndexOf('.');
		if (i >= 0) {
			return str.substring(i + 1);
		}
		return null;
	}

	/**
	 * Count the occurrences of the substring in string s.
	 * 
	 * @param str
	 *            string to search in. Return 0 if this is null.
	 * @param sub
	 *            string to search for. Return 0 if this is null.
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0;
		int pos = 0;
		int idx;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	public static boolean contains(String str, String... keywords) {
		if (str == null)
			return false;
		if (keywords == null)
			throw new IllegalArgumentException("'keywords' must be not null");

		for (String keyword : keywords) {
			if (str.contains(keyword.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	public static String defaultString(Object value) {
		if (value == null) {
			return "";
		}
		return value.toString();
	}

	public static String defaultIfEmpty(Object value, String defaultValue) {
		if (value == null || "".equals(value)) {
			return defaultValue;
		}
		return value.toString();
	}

	public static String makeAllWordFirstLetterUpperCase(String sqlName) {
		String[] strs = sqlName.toLowerCase().split("_");
		String result = "";
		String preStr = "";
		for (int i = 0; i < strs.length; i++) {
			if (preStr.length() == 1) {
				result += strs[i];
			} else {
				result += capitalize(strs[i]);
			}
			preStr = strs[i];
		}
		return result;
	}

	public static int indexOfByRegex(String input, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if (m.find()) {
			return m.start();
		}
		return -1;
	}

	public static String toJavaVariableName(String str) {
		return uncapitalize(toJavaClassName(str));
	}

	public static String toJavaClassName(String str) {
		return makeAllWordFirstLetterUpperCase(StringUtil.toUnderscoreName(str));
	}

	public static String removeMany(String inString, String... keywords) {
		if (inString == null) {
			return null;
		}
		for (String k : keywords) {
			inString = replace(inString, k, "");
		}
		return inString;
	}

	public static String replace(String inString, String oldPattern, String newPattern) {
		if (inString == null) {
			return null;
		}
		if (oldPattern == null || newPattern == null) {
			return inString;
		}

		StringBuffer sbuf = new StringBuffer();
		// output StringBuffer we'll build up
		int pos = 0; // our position in the old string
		int index = inString.indexOf(oldPattern);
		// the index of an occurrence we've found, or -1
		int patLen = oldPattern.length();
		while (index >= 0) {
			sbuf.append(inString.substring(pos, index));
			sbuf.append(newPattern);
			pos = index + patLen;
			index = inString.indexOf(oldPattern, pos);
		}
		sbuf.append(inString.substring(pos));

		// remember to append any characters to the right of a match
		return sbuf.toString();
	}

	private static final Random RANDOM = new Random();

	public static String randomNumeric(int count) {
		return random(count, false, true);
	}

	public static String random(int count, boolean letters, boolean numbers) {
		return random(count, 0, 0, letters, numbers);
	}

	public static String random(int count, int start, int end, boolean letters, boolean numbers) {
		return random(count, start, end, letters, numbers, null, RANDOM);
	}

	public static String random(int count, int start, int end, boolean letters, boolean numbers, char[] chars,
			Random random) {
		if (count == 0) {
			return "";
		} else if (count < 0) {
			throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
		}
		if ((start == 0) && (end == 0)) {
			end = 'z' + 1;
			start = ' ';
			if (!letters && !numbers) {
				start = 0;
				end = Integer.MAX_VALUE;
			}
		}

		char[] buffer = new char[count];
		int gap = end - start;

		while (count-- != 0) {
			char ch;
			if (chars == null) {
				ch = (char) (random.nextInt(gap) + start);
			} else {
				ch = chars[random.nextInt(gap) + start];
			}
			if ((letters && Character.isLetter(ch)) || (numbers && Character.isDigit(ch)) || (!letters && !numbers)) {
				if (ch >= 56320 && ch <= 57343) {
					if (count == 0) {
						count++;
					} else {
						// low surrogate, insert high surrogate after putting it
						// in
						buffer[count] = ch;
						count--;
						buffer[count] = (char) (55296 + random.nextInt(128));
					}
				} else if (ch >= 55296 && ch <= 56191) {
					if (count == 0) {
						count++;
					} else {
						// high surrogate, insert low surrogate before putting
						// it in
						buffer[count] = (char) (56320 + random.nextInt(128));
						count--;
						buffer[count] = ch;
					}
				} else if (ch >= 56192 && ch <= 56319) {
					// private high surrogate, no effing clue, so skip it
					count++;
				} else {
					buffer[count] = ch;
				}
			} else {
				count++;
			}
		}
		return new String(buffer);
	}

	/**
	 * Convert a name in camelCase to an underscored name in lower case. Any
	 * upper case letters are converted to lower case with a preceding
	 * underscore.
	 * 
	 * @param filteredName
	 *            the string containing original name
	 * @return the converted name
	 */
	public static String toUnderscoreName(String name) {
		if (name == null)
			return null;

		String filteredName = name;
		if (filteredName.indexOf("_") >= 0 && filteredName.equals(filteredName.toUpperCase())) {
			filteredName = filteredName.toLowerCase();
		}
		if (filteredName.indexOf("_") == -1 && filteredName.equals(filteredName.toUpperCase())) {
			filteredName = filteredName.toLowerCase();
		}

		StringBuffer result = new StringBuffer();
		if (filteredName != null && filteredName.length() > 0) {
			result.append(filteredName.substring(0, 1).toLowerCase());
			for (int i = 1; i < filteredName.length(); i++) {
				String preChart = filteredName.substring(i - 1, i);
				String c = filteredName.substring(i, i + 1);
				if (c.equals("_")) {
					result.append("_");
					continue;
				}
				if (preChart.equals("_")) {
					result.append(c.toLowerCase());
					continue;
				}
				if (c.matches("\\d")) {
					result.append(c);
				} else if (c.equals(c.toUpperCase())) {
					result.append("_");
					result.append(c.toLowerCase());
				} else {
					result.append(c);
				}
			}
		}
		return result.toString();
	}

	public static String removeEndWiths(String inputString, String... endWiths) {
		if (endWiths == null || endWiths.length == 0) {
			return inputString;
		}
		if (isBlank(inputString)) {
			return "";
		}
		for (String endWith : endWiths) {
			if (inputString.endsWith(endWith)) {
				return inputString.substring(0, inputString.length() - endWith.length());
			}
		}
		return inputString;
	}

	/**
	 * 将string转换为List<ColumnEnum> 格式为: "enumAlias(enumKey,enumDesc)"
	 */
	static Pattern three = Pattern.compile("(.*)\\((.*),(.*)\\)");
	static Pattern two = Pattern.compile("(.*)\\((.*)\\)");

	/**
	 * Test whether the given string matches the given substring at the given
	 * index.
	 * 
	 * @param str
	 *            the original string (or StringBuilder)
	 * @param index
	 *            the index in the original string to start matching against
	 * @param substring
	 *            the substring to match at the given index
	 */
	public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
		for (int j = 0; j < substring.length(); j++) {
			int i = index + j;
			if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] tokenizeToStringArray(String str, String seperators) {
		if (str == null)
			return new String[0];
		StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
		List result = new ArrayList();

		while (tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}
		return (String[]) result.toArray(new String[result.size()]);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String join(List list, String seperator) {
		return join(list.toArray(new Object[0]), seperator);
	}

	public static String replace(int start, int end, String str, String replacement) {
		String before = str.substring(0, start);
		String after = str.substring(end);
		return before + replacement + after;
	}

	public static String join(Object[] array, String seperator) {
		if (array == null)
			return null;
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			result.append(array[i]);
			if (i != array.length - 1) {
				result.append(seperator);
			}
		}
		return result.toString();
	}

	public static int containsCount(String string, String keyword) {
		if (string == null)
			return 0;
		int count = 0;
		for (int i = 0; i < string.length(); i++) {
			int indexOf = string.indexOf(keyword, i);
			if (indexOf < 0) {
				break;
			}
			count++;
			i = indexOf;
		}
		return count;
	}

	/**
	 * 字符串首字母小写
	 * 
	 * @param str
	 * @return
	 */
	public static String uncapitalize(String str) {
		return StringUtils.uncapitalize(str);
	}

	/**
	 * 字符串首字母大写
	 * 
	 * @param str
	 * @return
	 */
	public static String capitalize(String str) {
		return StringUtils.capitalize(str);
	}

	public static String getExtensionName(String fileName) {
		if ((fileName != null) && (fileName.length() > 0)) {
			int dot = fileName.lastIndexOf('.');
			if ((dot > -1) && (dot < (fileName.length() - 1))) {
				return fileName.substring(dot + 1);
			}
		}
		return fileName;
	}

	/**
	 * 获取客户端ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		String ip = request.getHeader("X-Real-IP");
		if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_REAL_IP");
		}
		if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtil.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (StringUtil.isNotBlank(ip)) {
			String[] ips = ip.split(",");
			if (ips != null && ips.length > 0) {
				ip = ips[0];
			}
		}
		return ip;
	}

	/**
	 * 获取数字随机数，长度为length
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomNum(int length) {
		StringBuffer sb = new StringBuffer();
		while (sb.length() < length) {
			sb.append((int) (Math.random() * 10));
		}
		return sb.toString();
	}

	public static String uuidWithNo_() {
		return generateUUID().replaceAll("-", "");
	}

	public static String generateToken() {
		return uuidWithNo_();
	}

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 判断手机号是否合法
	 * 
	 * @param phone
	 * @return
	 */
	public static boolean isPhone(String phone) {
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
		m = p.matcher(phone);
		b = m.matches();
		return b;
	}

	/**
	 * 过滤掉str中的img元素
	 * 
	 * @param str
	 * @return
	 */
	public static String filterImage(String str) {
		return str.replaceAll("<\\s*img\\s*([^>]*)\\s*>", "");

	}

	/**
	 * 过滤掉str中的Html元素
	 * 
	 * @param str
	 * @return
	 */
	public static String filterHtml(String str) {
		String regEx_html = "<[^>]+>";
		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(str);
		String value = m_html.replaceAll("").replace("&nbsp;", "").replace("，", ",").replace("？", "?").replace("！",
				"!");
		return value.trim();
	}

	public static String getStringForLength(String value, int length) {
		StringBuffer strRT = new StringBuffer();
		int valueLength = 0;
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(VALUE_CHINESE_CHA)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
			if (valueLength > length) {
				break;
			}
			strRT.append(temp);
		}
		return strRT.toString();
	}

	public static int getLength(String value) {
		int valueLength = 0;
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			if (String.valueOf(c).matches(VALUE_CHINESE_CHA)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	}

	/**
	 * 去除空格
	 * 
	 * @param value
	 * @return
	 */
	public static String trim(String value) {
		if (StringUtil.isNotBlank(value)) {
			return value.trim();
		} else {
			return "";
		}
	}

	/**
	 * 判断请求是否来自移动设备
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isMobileReq(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtil.isNotBlank(userAgent)) {
			if (userAgent.toLowerCase().indexOf("mobile") >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断是否微信浏览器
	 * 
	 * @param request
	 * @return
	 */
	public static boolean isMicromsgReq(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtil.isNotBlank(userAgent)) {
			if (userAgent.toLowerCase().indexOf("micromessenger") >= 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取移动设备的系统信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getOs(HttpServletRequest request) {
		String userAgent = request.getHeader("User-Agent");
		if (StringUtil.isNotBlank(userAgent)) {
			userAgent = userAgent.toLowerCase();
			if (userAgent.indexOf("iphone") >= 0 || userAgent.indexOf("ipad") >= 0) {
				return "ios";
			} else if (userAgent.indexOf("android") >= 0) {
				return "android";
			}
		}
		return "other";
	}

	/**
	 * 清除掉所有特殊字符
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public static String stringFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	/**
	 * 判断是否Emoji字符
	 * 
	 * @param codePoint
	 * @return
	 */
	public static boolean isNotEmojiChar(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
				|| ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
				|| ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public static String filterEmoji(String source) {
		int len = source.length();
		StringBuilder buf = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isNotEmojiChar(codePoint)) {
				buf.append(codePoint);
			}
		}
		return buf.toString();
	}

	/**
	 * 截取字符串(过滤了Emoji表情、空格、回车、换行符、制表符)
	 * 
	 * @param source
	 * @param start
	 * @param end
	 * @return
	 */
	public static String substr(String source, int start, int end) {
		String dest = "";
		source = filterEmoji(source);
		if (start <= 0) {
			start = 1;
		}
		if (end <= 0) {
			end = 1;
		}
		if (StringUtil.isNotBlank(source)) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(source);
			dest = m.replaceAll("");
			if (StringUtil.isNotBlank(dest)) {
				int t = dest.length();
				if (t >= start + 1) {
					if (t < end) {
						end = t;
					}
					dest = dest.substring(start, end);
				}
			}
		}
		return dest;
	}

	public static String shaEncode(String inputStr) {
		MessageDigest sha = null;
		try {
			sha = MessageDigest.getInstance("SHA");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		byte[] byteArray;
		try {
			byteArray = inputStr.getBytes(CoreConst.DEFAULT_CODETYPE);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		byte[] md5Bytes = sha.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static void main(String[] args) {
		/*System.out.println(StringUtil.shaEncode("654321"));
		String f = "389";
		//String f = "";
		if (f.length() > 4) {
			System.out.println(f.substring(f.length() - 4, f.length()));
		} else {
			System.out.println(f);
		}*/
		try {
	        throw new RuntimeException("b");
	    } catch(RuntimeException e){
	        System.out.print("RuntimeException");
	    } catch(Exception e){
	        System.out.print("Exception");
	    }
		
	
	}
	
}