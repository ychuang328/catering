package com.weiwork.common.utils.string;

import java.util.UUID;

/**
 * UUID工具类
 * @Date	 2015-11-18
 */
public class UUIDUtil {

	/**
	 * 16进制表示的紧凑格式的 UUID
	 *
	 * @return 紧凑的64位
	 */
	public static String uu16() {
		UUID uu = UUID.randomUUID();
		return StringUtil.alignRight(Long.toHexString(uu.getMostSignificantBits()), 16, '0')
                + StringUtil.alignRight(Long.toHexString(uu.getLeastSignificantBits()), 16, '0');
	}
}
