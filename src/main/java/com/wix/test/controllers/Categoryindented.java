package com.wix.test.controllers;

import com.wix.test.model.Category;

public class Categoryindented {

	private Long id;
	private String description;
	private String parentDescription;
	private int indent;

	public Categoryindented(Category cat) {
		this.id = cat.getId();
		this.description = cat.getDescription();
		this.indent = 0;
	}

	public Categoryindented(Category cat, int i) {
		this.id = cat.getId();
		this.description = cat.getDescription();
		this.indent = i;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getParentDescription() {
		return parentDescription;
	}

	public void setParentDescription(String parentDescription) {
		this.parentDescription = parentDescription;
	}

	public int getIndent() {
		return indent;
	}

	public void setIndent(int indent) {
		this.indent = indent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
