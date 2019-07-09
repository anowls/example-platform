package org.zongzi.platform.common.ooxml;

import java.io.File;
import java.io.InputStream;

public interface DocumentParser {

	void parse(File file);

	void parse(InputStream in);
}
