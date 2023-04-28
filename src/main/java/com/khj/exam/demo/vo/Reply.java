package com.khj.exam.demo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Reply {
	private int id;
	private String regDate;
	private String updateDate;
	private int memberId;
	private String relTypeCode;
	private int relId;
	private String body;
	private int goodReactionPoint;
	private int badReactionPoint;

	private String extra__writerName;
	private boolean extra__actorCanDelete;
	private boolean extra__actorCanModify;
	
	public String getRegDateForPrint() {
		return regDate.substring(2, 16);
	}
	
	public String getUpdateDateForPrint() {
		return regDate.substring(2, 16);
	}
	public String forPrintintTypeRegDate() {
		return regDate.substring(2, 16).replace(" ","<br>");
	}
	
	public String forPrintintTypeUpdateDate() {
		return updateDate.substring(2, 16).replace(" ","<br>");
	}
	
	public String getForPrintBody() {
		return body.replaceAll("\n","<br>");
	}
}
