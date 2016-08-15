package com.weiwork.common.utils.upload;

/**
 * 图片resize时用到的属性
 *
 */
public class ResizeAttr {
	/** 宽度 */
	private int width;
	/** 高度 */
	private int height;
	/** 文件名后缀（非扩展名） */
	private String suffix;

	public ResizeAttr(int width, int height, String suffix) {
		super();
		this.width = width;
		this.height = height;
		this.suffix = suffix;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
