package com.weiwork.common.utils.string;


import java.util.HashSet;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 拼音工具类
 * @author   杨闯
 * @Date	 2015-12-31 	 
 */
public class PinYinUtil {
	/**
	* Logger for this class
	*/
	private static final Logger logger = LoggerFactory.getLogger(PinYinUtil.class);

	/**   
	   * 获取拼音集合   
	   * @author wyh   
	   * @param src   
	   * @return Set<String>   
	   */
	@SuppressWarnings("cast")
	private static Set<String> getPinyinSet(final String src) {
		Set<String> pinyinSet = new HashSet<String>();
		if (src != null && !src.trim().equalsIgnoreCase("")) {
			char[] srcChar;
			srcChar = src.toCharArray();
			//汉语拼音格式输出类       
			HanyuPinyinOutputFormat hanYuPinOutputFormat = new HanyuPinyinOutputFormat();
			//输出设置，大小写，音标方式等       
			hanYuPinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
			hanYuPinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
			hanYuPinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
			String[][] temp = new String[src.length()][];
			for (int i = 0; i < srcChar.length; i++) {
				char c = srcChar[i];
				//是中文或者a-z或者A-Z转换拼音(我的需求，是保留中文或者a-z或者A-Z)       
				if (String.valueOf(c).matches("[\\u4E00-\\u9FA5]+")) {
					try {
						temp[i] = PinyinHelper.toHanyuPinyinStringArray(srcChar[i], hanYuPinOutputFormat);
					} catch (BadHanyuPinyinOutputFormatCombination e) {
						logger.debug("getPinyinSet(String) - BadHanyuPinyinOutputFormatCombination e", e); //$NON-NLS-1$
					}
				} else if (((int) c >= 65 && (int) c <= 90) || ((int) c >= 97 && (int) c <= 122)
						|| ((int) c >= 48 && (int) c <= 57)) {
					temp[i] = new String[] { String.valueOf(srcChar[i]) };
				} else {
					temp[i] = new String[] { "" };
				}
			}
			String[] pingyinArray = exchange(temp);
			if (pingyinArray == null || pingyinArray.length == 0) {
				return pinyinSet;
			}
			for (String element : pingyinArray) {
				pinyinSet.add(element);
			}
			return pinyinSet;
		}
		return pinyinSet;
	}

	/**   
	  * 递归   
	  * @author wyh   
	  * @param strJaggedArray   
	  * @return   
	  */
	private static String[] exchange(final String[][] strJaggedArray) {
		String[][] temp = doExchange(strJaggedArray);
		if (temp == null) {
			return null;
		}
		return temp[0];
	}

	/**   
	  * 递归   
	  * @author wyh   
	  * @param strJaggedArray   
	  * @return   
	  */
	private static String[][] doExchange(final String[][] strJaggedArray) {
		int len = strJaggedArray.length;
		if (len >= 2) {
			String[] array0 = strJaggedArray[0];
			String[] array1 = strJaggedArray[1];
			if (array0==null || array1==null) {
				return strJaggedArray;
			}
			int len1 = array0.length;
			int len2 = array1.length;
			int newlen = len1 * len2;
			String[] temp = new String[newlen];
			int Index = 0;
			for (int i = 0; i < len1; i++) {
				for (int j = 0; j < len2; j++) {
					temp[Index] = array0[i] + array1[j];
					Index++;
				}
			}
			String[][] newArray = new String[len - 1][];
			for (int i = 2; i < len; i++) {
				newArray[i - 1] = strJaggedArray[i];
			}
			newArray[0] = temp;
			return doExchange(newArray);
		} else {
			return strJaggedArray;
		}
	}

	/**
	 * 获取汉字全拼
	 *
	 * @param src 汉字
	 * @return 全拼
	 */
	public static String getPinyin(final String src) {
		if (src==null ||src.trim().length()==0) {
			return "";
		}
		Set<String> stringSet = getPinyinSet(src);
		if (stringSet == null || stringSet.isEmpty()) {
			return "";
		}
		return stringSet.iterator().next().toLowerCase();
	}

}
