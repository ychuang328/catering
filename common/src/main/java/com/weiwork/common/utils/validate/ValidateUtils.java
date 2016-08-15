package com.weiwork.common.utils.validate;

/**
 * 验证工具
  * @date 2015-4-13
  *
 */
public class ValidateUtils {

    /**
     * 判断是否是字母 isLetter
     * 
     * @param c
     * @return
     * @throws
     */
    public static boolean isLetter( char c ) {
        int k = 0x80;
        return c / k == 0 ? true : false;
    }

    /**
     * 判断字符串是否为空
     * 
     * @param str
     * @return
     */
    public static boolean isNull( String str ) {
        if ( str == null || str.trim().equals( "" )
                || str.trim().equalsIgnoreCase( "null" ) )
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为2,英文字符长度为1
     * 
     * @param String
     *            s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static int length( String s ) {
        if ( s == null )
            return 0;
        char[] c = s.toCharArray();
        int len = 0;
        for ( int i = 0; i < c.length; i++ )
        {
            len++;
            if ( !isLetter( c[i] ) )
            {
                len++;
            }
        }
        return len;
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5
     * 
     * @param String
     *            s 需要得到长度的字符串
     * @return int 得到的字符串长度
     */
    public static long getLength( String s ) {
        long valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
        for ( int i = 0; i < s.length(); i++ )
        {
            // 获取一个字符
            String temp = s.substring( i, i + 1 );
            // 判断是否为中文字符
            if ( temp.matches( chinese ) )
            {
                // 中文字符长度为1
                valueLength += 3;
            }
            else
            {
                // 其他字符长度为0.5
                valueLength += 1;
            }
        }
        // 进位取整
//        return Math.ceil( valueLength );
        return valueLength;
    }

    /**
     * 验证长度,超过length参数返回false
      * validLength
      * @param content
      * @param length
      * @return    
      * @throws
     */
    public static boolean validLength(String content,long length) {
        return getLength(content) < length;
    }
}
