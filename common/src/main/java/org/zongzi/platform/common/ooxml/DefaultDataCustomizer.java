package org.zongzi.platform.common.ooxml;

import java.util.List;

public class DefaultDataCustomizer implements DataCustomizer<String[]> {

    @Override
    public int getHeaderIndex() {
        return 0;
    }

    @Override
    public boolean verify(List<ParsedRow<String[]>> parsedRowList) {
        return true;
    }
}
