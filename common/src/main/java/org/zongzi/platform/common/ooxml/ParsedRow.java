package org.zongzi.platform.common.ooxml;

import lombok.Data;
import org.apache.poi.ss.usermodel.Row;

@Data
public class ParsedRow<T> {

    private Row source;

    private boolean skip = true;

    private boolean error = false;

    private String errorMsg = "";

    private T target;
}
