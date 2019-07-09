package org.zongzi.platform.common.ooxml;

public class DocumentParseException extends RuntimeException {

	private static final long serialVersionUID = 7350037537466386146L;

	public DocumentParseException(String errorMsg) {
		super(errorMsg);
	}
}
