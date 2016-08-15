package com.weiwork.common.utils.string;

import java.io.File;
import java.nio.charset.Charset;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.collections.CollectionUtils;

/**
 * 字符串处理的工具类
 */
public class StringUtil {

	public static final String[] NUMBER = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
	public static final Pattern LETTER_P = Pattern.compile("[a-zA-Z]+");
	public static final String[] LETTER = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
			"P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j",
			"k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "0" };

	/**
	 * 
	 * 返回随机字符串
	 * @param length 字符串的长度
	 * @param array  字符串的源数组
	 * @return 字符串
	 */
	public final static String random(final int length, final String[] array) {
		if (array == null || array.length == 0) {
			return "";
		}
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int x = rand.nextInt(array.length);
			sb.append(array[x]);
		}
		return sb.toString();
	}

	/**
	 * 获取字符串的utf的byte数组
	 *
	 * @param value 待转换的字符串
	 * @return byte数组，如果value为empty就返回null
	 */
	public static byte[] getBytes(final String value) {
		if (isNullOrEmpty(value)) {
			return null;
		}
		return value.getBytes(Charset.forName("UTF-8"));
	}

	/**
	 * 获取随机字符串
	 * <p>
	 * 字符串由字母和数字组成
	 * @param length 长度
	 * @return 随机字符串
	 */
	public static String getRandom(final int length) {
		return random(length, LETTER);
	}

	/**
	 * 获取随机字符串
	 * <p>
	 * 字符串由字母和数字组成
	 * @param length 长度
	 * @return 随机字符串
	 */
	public static String getRandomInt(final int length) {
		return random(length, NUMBER);
	}

	/**
	 * 根据分隔符把集合拼接字符串
	 * <p>
	 * 拼接时自动过滤掉集合中null元素
	 * @param c 分隔符
	 * @param coll 集合
	 * @return 拼接后的字符串
	*/
	public static <T> String join(final Object c, final Collection<T> coll) {
		if (CollectionUtils.isEmpty(coll)) {
			return "";
		}
		Object otherc = "";
		if (c != null) {
			otherc = c;
		}
		StringBuilder sb = new StringBuilder();
		Iterator<T> it = coll.iterator();
		while (it.hasNext()) {
			T t = it.next();
			if (t == null) {
				continue;
			}
			sb.append(t).append(otherc);
		}
		int size = String.valueOf(otherc).length();
		return String.valueOf(sb).substring(0, sb.length() - size);
	}

	/**
	 * 自定义截取字符串
	 * @param content 源字符串
	 * @param size 截取大小
	 * @return 截取的字符串
	 */
	public static String sub(final String content, final int size) {
		if (isNullOrEmpty(content) || size <= 0) {
			return "";
		}
		int length = content.length();
		if (size >= length) {
			return content;
		}
		return content.substring(0, size) + "...";
	}

	/**
	 * 移除匹配字符串及前面的内容
	 *
	 * @param content 源字符串
	 * @param removed 匹配字符串
	 * @return 移除后的字符串
	 */
	public static String removeLeft(final String content, final String removed) {
		if (isNullOrEmpty(content) || isNullOrEmpty(removed)) {
			return content;
		}
		int index = content.indexOf(removed);
		if (index == -1) {
			return content;
		}
		return content.substring(index + removed.length());
	}

	/**
	 * 将文件路径中的反斜线变为http协议中的斜线
	 *
	 * @param path 路径
	 * @return 转换后的路径
	 */
	public static String path2Web(final String path) {
		if (isNullOrEmpty(path)) {
			return path;
		}
		return path.replace('\\', '/');
	}

	/**
	 * 把id按照固定位数建立为文件夹路径
	 * <p>
	 * 如 100000101,按3位文件夹计算，100/000/101
	 * @param id 
	 * @param folderNameLength 文件夹长度
	 * @return 建立的文件夹路径
	 */
	public static String toPath(final long id, final int folderNameLength) {
		String idStr = String.valueOf(id);
		return toPath(idStr, folderNameLength);
	}

	/**
	 * 把String按照固定位数建立为文件夹路径
	 * <p>
	 * 如 100000101,按3位文件夹计算，100/000/101
	 * @param str 要建立的字符串 
	 * @param folderNameLength 文件夹长度
	 * @return 建立的文件夹路径
	 */
	public static String toPath(final String str, final int folderNameLength) {
		if (isNullOrEmpty(str)) {
			return "";
		}
		int foldRealLength = folderNameLength;
		if (folderNameLength == 0) {
			foldRealLength = 3;
		}
		StringBuilder sb = new StringBuilder();
		String leftStr = str;
		while (leftStr.length() > foldRealLength) {
			sb.append(leftStr.substring(0, foldRealLength));
			sb.append(File.separator);
			leftStr = leftStr.substring(foldRealLength);
		}
		sb.append(leftStr);
		return sb.toString();
	}

	/**
	 * 截取含html代码的内容
	 * @param param 内容
	 * @param length 截取长度
	 */
	public static String subStringHTML(final String param, final int length) {
		StringBuffer result = new StringBuffer();
		int n = 0;
		char temp;
		boolean isCode = false; //是不是HTML代码
		boolean isHTML = false; //是不是HTML特殊字符,如&nbsp;
		for (int i = 0; i < param.length(); i++) {
			temp = param.charAt(i);
			if (temp == '<') {
				isCode = true;
			} else if (temp == '&') {
				isHTML = true;
			} else if (temp == '>' && isCode) {
				n = n - 1;
				isCode = false;
			} else if (temp == ';' && isHTML) {
				isHTML = false;
			}
			if (!isCode && !isHTML) {
				n = n + 1;
				//UNICODE码字符占两个字节
				if ((temp + "").getBytes().length > 1) {
					n = n + 1;
				}
			}
			result.append(temp);
			if (n >= length) {
				break;
			}
		}
		result.append("...");
		//取出截取字符串中的HTML标记
		String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
		//去掉不需要结素标记的HTML标记
		temp_result = temp_result
				.replaceAll(
						"</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
						"");
		//去掉成对的HTML标记
		temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
		//用正则表达式取出标记
		Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
		Matcher m = p.matcher(temp_result);
		List<String> endHTML = new ArrayList<String>();
		while (m.find()) {
			endHTML.add(m.group(1));
		}
		//补全不成对的HTML标记
		for (int i = endHTML.size() - 1; i >= 0; i--) {
			result.append("</");
			result.append(endHTML.get(i));
			result.append(">");
		}
		return result.toString();
	}

	public static boolean isLetter(final String str) {
		return LETTER_P.matcher(str).matches();
	}

	public static boolean isChinese(final char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean isEnglish(final char c) {
		if (c >= 'a' && c <= 'z') {
			return true;
		}
		if (c >= 'A' && c <= 'Z') {
			return true;
		}
		return false;
	}

	public static boolean isNumber(final char c) {
		if (c >= '0' && c <= '9') {
			return true;
		}
		return false;
	}

	public static boolean isASCII(final char c) {
		if (c >= 20 && c <= 126) {
			return true;
		}
		return false;
	}

	public static boolean isChinese(final String s) {
		for (int i = 0, n = s.length(); i < n; i++) {
			char c = s.charAt(i);
			if (isChinese(c)) {
				continue;
			}
			return false;
		}
		return true;
	}
	
	/**
	 * 如果为空，则返回空字符串
	 *
	 * @param content 待判定的值
	 * @return 返回结果
	 */
	public static boolean isNullOrEmpty(final String content) {
		return (null == content || content.trim().length()==0);
	}
	
	/**
	 * 如果为空，则返回空字符串
	 *
	 * @param content 待判定的值
	 * @return 返回结果
	 */
	public static String nvl(final String content) {
		if (isNullOrEmpty(content)) {
			return "";
		}
		return content;
	}
	
	/**
	 * 校验是否是手机号
	 *
	 * @param mobile 待校验的手机号
	 * @return 符合手机号的规则为真
	 */
	public static boolean isMobile(final String mobile) {
		if (isNullOrEmpty(mobile)) {
			return false;
		}
//		String str = "^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";
//		String str = "^1[3,4,5,7,8][0-9]{9}$";
		String str = "^1[0-9]{10}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(mobile);
		return m.matches();
	}
	
	/**
	 * 校验是否是邮箱
	 * @param email 待校验的邮箱
	 * @return 符合邮箱的规则为真
	 */
	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		return m.matches();
	}
	

    /**
     * 在字符串左侧填充一定数量的特殊字符
     * 
     * @param o
     *            可被 toString 的对象
     * @param width
     *            字符数量
     * @param c
     *            字符
     * @return 新字符串
     */
    public static String alignRight(Object o, int width, char c) {
        if (null == o)
            return null;
        String s = o.toString();
        int len = s.length();
        if (len >= width)
            return s;
        return new StringBuilder().append(dup(c, width - len)).append(s).toString();
    }

    /**
     * 在字符串右侧填充一定数量的特殊字符
     * 
     * @param o
     *            可被 toString 的对象
     * @param width
     *            字符数量
     * @param c
     *            字符
     * @return 新字符串
     */
    public static String alignLeft(Object o, int width, char c) {
        if (null == o)
            return null;
        String s = o.toString();
        int length = s.length();
        if (length >= width)
            return s.toString();
        return new StringBuilder().append(s).append(dup(c, width - length)).toString();
    }
    

    /**
     * 复制字符
     * 
     * @param c
     *            字符
     * @param num
     *            数量
     * @return 新字符串
     */
    public static String dup(char c, int num) {
        if (c == 0 || num < 1)
            return "";
        StringBuilder sb = new StringBuilder(num);
        for (int i = 0; i < num; i++)
            sb.append(c);
        return sb.toString();
    }
    
    /**
	 * 使用MessageFormat类的方式对字符串进行格式化
	 * <p>
	 * 传入空串或null时，返回空串
	 * 传入空参数时，返回原字符串
	 * 
	 * @param format 格式化串
	 * @param args 参数
	 * @return 格式化后的字符串
	 * @throws DataException 如果格式存在异常，则提示DataException
	 */
	public static String format(final String format, final Object... args) {
		return format(Locale.getDefault(), format, args);
	}

	/**
	 * 使用MessageFormat类的方式对字符串进行格式化
	 * <p>
	 * 传入空串或null时，返回空串
	 * 传入空参数时，返回原字符串
	 * @param locale 本地化语言
	 * @param format 格式化串
	 * @param args 参数
	 * @return 格式化后的字符串
	 * @throws RuntimeException("字符串格式化错误", e)
	 */
	public static String format(final Locale locale, final String format, final Object... args) {
		if (isNullOrEmpty(format)) {
			return "";
		}
		if (args == null) {
			return format;
		}
		try {
			MessageFormat formart = new MessageFormat(format, locale);
			return formart.format(args);
		} catch (Exception e) {
			throw new RuntimeException("字符串格式化错误", e);
		}
	}
	
	
	/**
	 * 给手机号加星
	 * @param str 
	 * @return 加星后的手机号
	 */
	public static String starMobile(String name){
		
		if(name.length()==11&&name.matches("^1\\d{10}$")){
			name=name.substring(0,3)+"****"+name.substring(7,11);
        }
		return name;
	}
	
	

}
