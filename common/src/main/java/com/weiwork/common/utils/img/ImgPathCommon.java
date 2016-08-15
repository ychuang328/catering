package com.weiwork.common.utils.img;

import org.apache.commons.lang3.StringUtils;

/**
 * 一些公共图片路径
 * @author zhangfei
 * 2016-5-19
 */
public class ImgPathCommon {
	
	public static final String STATIC_ROOT = "http://static.vko.cn";
	public static final String CDN_ROOT = "http://cdn.vkoimg.cn";
	public static final String USER_ROOT = "http://user.face.vkoimg.cn";
	public static final String COVER_ROOT = "http://cover.vkoimg.cn";
	
	//基础课封面
	public static final String GOODS_COVER_JC = COVER_ROOT + "/vko/images/other/def_jc.png";
	//专题封面
	public static final String GOODS_COVER_ZT = COVER_ROOT + "/vko/images/other/def_zt.png";
	//套餐封面
	public static final String GOODS_COVER_TC = COVER_ROOT + "/vko/images/other/def_tc.png";

	/**
	 * 获取用户头像-中
	 * 
	 * @param uid 用户编号
	 * @return
	 */
	public static String getMface(final long uid) {
		return USER_ROOT + "/upload/pic/user/face/" + uid + "_middle.jpg";
	}

	/**
	 * 获取用户头像-小
	 * 
	 * @param uid 用户编号
	 * @return
	 */
	public static String getSface(final long uid) {
		return USER_ROOT + "/upload/pic/user/face/" + uid + "_small.jpg";
	}

	/**
	 * 获取用户头像-大
	 * 
	 * @param uid 用户编号
	 * @return
	 */
	public static String getBface(final long uid) {
		return USER_ROOT + "/upload/pic/user/face/" + uid + "_big.jpg";
	}
	/**
	 * 获取视频封面
	 * @author zhangfei
	 * @param videoId
	 * @return
	 */
	public static String getVideoImg(final long videoId) {
		return COVER_ROOT + "/content/video/" + videoId + "bg.jpg";
	}
	
	
	/**
	 * 获取大包封面
	 * @author zhangfei
	 * @param groupId
	 * @param objType
	 * @param cover
	 * @return
	 */
	public static String getGroupImg(final long groupId,int objType,String cover ) {
		if(StringUtils.isEmpty( cover )){
			 return COVER_ROOT + "/content/"+objType+"/"+groupId+"bg.jpg";
		}
		return COVER_ROOT + "/content/"+objType+"/"+ cover;
	}
	
	/**
	 * 获取老师头像
	 * @author zhangfei
	 * @param tid
	 * @return
	 */
	public static String getTeacherFace(final long tid) {
		return USER_ROOT + "/upload/pic/user/face/" + tid + "_middle.jpg";
	}
	
}
