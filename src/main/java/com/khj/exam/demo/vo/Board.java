package com.khj.exam.demo.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Board {

	private int id;
	private String regDate;
	private String updateDate;
	private String code;
	private String name;
	private boolean delaStatus;
	private boolean delDate;
}
