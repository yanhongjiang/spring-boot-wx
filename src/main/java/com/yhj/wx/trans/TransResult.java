package com.yhj.wx.trans;

/**
 * 翻译返回数据
 */
public class TransResult {
	/*
	*原对象
	 */
	private String src;
	/*
	*翻译后对象
	 */
	private String dst;

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDst() {
		return dst;
	}

	public void setDst(String dst) {
		this.dst = dst;
	}
}
