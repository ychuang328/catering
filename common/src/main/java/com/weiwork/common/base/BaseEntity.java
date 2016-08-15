package com.weiwork.common.base;

import java.io.Serializable;

public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 3121418431571658519L;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
