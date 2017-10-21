package com.yhj.wx.trans;

import java.util.List;

public class Data {
	private String from;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public List<com.yhj.wx.trans.TransResult> getTransResult() {
		return TransResult;
	}

	public void setTransResult(List<com.yhj.wx.trans.TransResult> transResult) {
		TransResult = transResult;
	}

	private String to;
	private List<TransResult> TransResult;
}
