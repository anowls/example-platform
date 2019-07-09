package org.zongzi.platform.common.ooxml;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DefaultDocumentParser implements DocumentParser {

    private OutputStream outputStream = null;

    private DataCustomizer<String[]> dataCustomizer = null;

    public DefaultDocumentParser(OutputStream outputStream, DataCustomizer<String[]> dataCustomizer) {
        this.outputStream = outputStream;
        this.dataCustomizer = dataCustomizer;
    }

    @Override
    public void parse(File file) {
        try (Workbook workbook = WorkbookFactory.create(file)) {
            parse0(workbook);
        } catch (EncryptedDocumentException | IOException e) {
            throw new DocumentParseException(e.getMessage());
        }
    }

    @Override
    public void parse(InputStream in) {
        try (Workbook workbook = WorkbookFactory.create(in)) {
            parse0(workbook);
        } catch (EncryptedDocumentException | IOException e) {
            throw new DocumentParseException(e.getMessage());
        }
    }

    private void parse0(Workbook workbook) throws IOException {
        Sheet sheet = workbook.getSheetAt(0);

        Row headerRow = sheet.getRow(dataCustomizer.getHeaderIndex());
        int cellNumbers = headerRow.getPhysicalNumberOfCells();
        int rowNumbers = sheet.getPhysicalNumberOfRows();

        ParsedRow<String[]> parsedRow;
        List<ParsedRow<String[]>> parsedRowList = new ArrayList<>(rowNumbers);

        for (int j = 0; j < rowNumbers; j++) {
            parsedRow = new ParsedRow<>();
            parsedRow.setSource(sheet.getRow(j));
            parsedRowList.add(parsedRow);

            if (j > dataCustomizer.getHeaderIndex()) {
                boolean emptyValue = true;
                String[] targat = new String[cellNumbers];
                for (int c = 0; c < cellNumbers; c++) {
                    targat[c] = null;
                    String cellValue = parsedRow.getSource().getCell(c).getStringCellValue();
                    if (StringUtils.hasLength(cellValue)) {
                        targat[c] = cellValue;
                        emptyValue = false;
                    }
                }
                if (!emptyValue) {
                    parsedRow.setSkip(false);
                    parsedRow.setTarget(targat);
                }
            }
        }

        boolean verifyPass = dataCustomizer.verify(parsedRowList);
        if (!verifyPass) { // 如果验证不通过的数据，则在对应的行后面添加提示信息并将行标红
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.RED.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            for (ParsedRow<String[]> row : parsedRowList) {
                if (!row.isSkip() && row.isError()) {
                    Row source = row.getSource();
                    Cell cell = source.createCell(source.getPhysicalNumberOfCells());
                    cell.setCellValue(row.getErrorMsg());
                    source.forEach(ce -> ce.setCellStyle(cellStyle));
                }
            }
            workbook.write(outputStream);
        }
    }
}
