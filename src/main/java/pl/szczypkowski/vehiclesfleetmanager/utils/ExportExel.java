package pl.szczypkowski.vehiclesfleetmanager.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExportExel {

    private static final String FILE_EXTENSION = ".xlsx";

    private static final String PARSE_ERROR_MESSAGE = "Błąd parsowania {} ({}) podczas exportu";

    /**
     * DATE, <br />
     * PROCENT, // Format without % calculation. eg. input 100 result 100 % <br />
     * NUMERIC, // Numeric without thousand separator <br />
     * VALUE, // Numeric values with thousand separator <br />
     * MONEY, // Numeric value with local currency <br />
     * NONE // Default string format
     */
    public enum FormatCell {
        DATE, DATE_TIME, TIME, PROCENT, NUMERIC, VALUE, MONEY, MULTILINE, NONE, HEADER
    }

    public static final boolean export(String filePath, List<List<String>> data, boolean filtr) {
        return export(filePath, data, null, filtr);
    }

    public static final boolean export(String filePath, List<List<String>> data, Map<Integer, FormatCell> columnFormat,
                                       boolean filtr) {
        return export(filePath, data, columnFormat, filtr, false);

    }

    public static final boolean export(String filePath, List<List<String>> data, Map<Integer, FormatCell> columnFormat,
                                       boolean filtr, boolean autosize) {
        if (!filePath.endsWith(FILE_EXTENSION)) {
            filePath = filePath.concat(FILE_EXTENSION);
        }
        if (columnFormat == null) {
            columnFormat = new HashMap<>();
        }

        String sheetName = FilenameUtils.getBaseName(filePath);

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);
        Map<FormatCell, CellStyle> styles = createStyles(wb);

        fillSheet(sheet, data, styles, columnFormat, filtr);
        if (autosize && !data.isEmpty()) {
            for (int c = 0; c < data.get(0).size(); c++) {
                sheet.autoSizeColumn(c);
            }
        }

        return saveFile(filePath, wb);

    }

    public static final boolean exportMultipleSheets(String filePath, Map<String, List<List<String>>> data,
                                                     boolean filtr) {
        return exportMultipleSheets(filePath, data, null, filtr);
    }

    public static final boolean exportMultipleSheets(String filePath, Map<String, List<List<String>>> data,
                                                     Map<String, Map<Integer, FormatCell>> columnFormat, boolean filtr) {
        if (!filePath.endsWith(FILE_EXTENSION)) {
            filePath = filePath.concat(FILE_EXTENSION);
        }
        if (columnFormat == null) {
            columnFormat = new HashMap<>();
        }

        SXSSFWorkbook wb = new SXSSFWorkbook(200); // to fix out of memory exception
        Map<FormatCell, CellStyle> styles = createStyles(wb);

        for (Map.Entry<String, List<List<String>>> entry : data.entrySet()) {
            SXSSFSheet sheet = wb.createSheet(entry.getKey());
            sheet.trackAllColumnsForAutoSizing();
            fillSheet(sheet, entry.getValue(), styles,
                    columnFormat.containsKey(entry.getKey()) ? columnFormat.get(entry.getKey()) : new HashMap<>(),
                    filtr);
        }

        return saveFile(filePath, wb);
    }

    private static void fillSheet(Sheet sheet, List<List<String>> data, Map<FormatCell, CellStyle> styles,
                                  Map<Integer, FormatCell> columnFormat, boolean filtr) {
        // iterating r number of rows
        for (int r = 0; r < data.size(); r++) {
            Row row = sheet.createRow(r);

            // iterating c number of columns
            for (int c = 0; c < data.get(r).size(); c++) {
                Cell cell = row.createCell(c);
                if (r == 0) {
                    cell.setCellValue(data.get(r).get(c));
                    cell.setCellStyle(styles.get(FormatCell.HEADER));
                    sheet.setColumnWidth(c, 16 * 256);
                } else {
                    prepareFormatedValue(cell, styles, data.get(r).get(c), columnFormat.get(c));
                }
            }
        }
        if (filtr && !data.isEmpty() && !data.get(0).isEmpty()) {
            sheet.setAutoFilter(new CellRangeAddress(0, data.size() - 1, 0, data.get(0).size() - 1));
        }
    }

    private static boolean saveFile(String filePath, Workbook wb) {
        try {
            // write this workbook to an Outputstream.
            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                // write this workbook to an Outputstream.
                wb.write(fileOut);
                fileOut.flush();
            }
            return true;
        } catch (IOException ex) {
            //log.error(ex.getLocalizedMessage(), ex);
            return false;
        }
    }

    private static void prepareFormatedValue(Cell cell, Map<FormatCell, CellStyle> styles, String value,
                                             FormatCell format) {

        // in case value exceeds max cell size
        if (value != null && value.length() > 30_000) {
            value = value.substring(0, 30_000);
        }

        try {
            format = (format != null) ? format : FormatCell.NONE;
            switch (format) {
                case DATE:
                    setDateFormat(styles.get(format), cell, value);
                    break;
                case DATE_TIME:
                    setDateTimeFormat(styles.get(format), cell, value);
                    break;
                case TIME:
                    setTimeFormat(styles.get(format), cell, value);
                    break;
                case PROCENT:
                    setProcentFormat(styles.get(format), cell, value);
                    break;
                case NUMERIC:
                    setNumericFormat(styles.get(format), cell, value);
                    break;
                case VALUE:
                    setValueFormat(styles.get(format), cell, value);
                    break;
                case MONEY:
                    setMoneyFormat(styles.get(format), cell, value);
                    break;
                case MULTILINE:
                    cell.setCellValue(value);
                    cell.setCellStyle(styles.get(format));
                    break;
                case NONE:
                default:
                    cell.setCellValue(value);
            }
        } catch (Exception e) {
            // log.error("Błąd formatowania komórki", e);
        }
    }

    private static Map<FormatCell, CellStyle> createStyles(Workbook wb) {
        Map<FormatCell, CellStyle> out = new HashMap<>();
        CellStyle dateStyle = wb.createCellStyle();
        dateStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy-mm-dd"));
        dateStyle.setAlignment(HorizontalAlignment.CENTER);
        out.put(FormatCell.DATE, dateStyle);

        CellStyle dateTimeStyle = wb.createCellStyle();
        dateTimeStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));
        dateTimeStyle.setAlignment(HorizontalAlignment.CENTER);
        out.put(FormatCell.DATE_TIME, dateTimeStyle);

        CellStyle timeStyle = wb.createCellStyle();
        timeStyle.setDataFormat(wb.createDataFormat().getFormat("h:mm;@"));
        timeStyle.setAlignment(HorizontalAlignment.CENTER);
        out.put(FormatCell.TIME, timeStyle);

        CellStyle procentStyle = wb.createCellStyle();
        procentStyle.setDataFormat(wb.createDataFormat().getFormat("0%"));
        procentStyle.setAlignment(HorizontalAlignment.CENTER);
        out.put(FormatCell.PROCENT, procentStyle);

        CellStyle numeric = wb.createCellStyle();
        numeric.setDataFormat(wb.createDataFormat().getFormat("0"));
        numeric.setAlignment(HorizontalAlignment.CENTER);
        out.put(FormatCell.NUMERIC, numeric);

        CellStyle value = wb.createCellStyle();
        value.setDataFormat(wb.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(4)));
        value.setAlignment(HorizontalAlignment.CENTER);
        out.put(FormatCell.VALUE, value);

        CellStyle money = wb.createCellStyle();
        money.setDataFormat(wb.createDataFormat().getFormat(BuiltinFormats.getBuiltinFormat(7)));
        money.setAlignment(HorizontalAlignment.CENTER);
        out.put(FormatCell.MONEY, money);

        CellStyle multiline = wb.createCellStyle();
        multiline.setWrapText(true);
        out.put(FormatCell.MULTILINE, multiline);

        CellStyle header = wb.createCellStyle();
        header.setBorderBottom(BorderStyle.DOUBLE);
        header.setWrapText(true);
        header.setAlignment(HorizontalAlignment.CENTER);
        header.setVerticalAlignment(VerticalAlignment.CENTER);
        out.put(FormatCell.HEADER, header);

        return out;
    }

    private static void setDateFormat(CellStyle style, Cell cell, String value) {
        try {
            cell.setCellValue(String.valueOf(LocalDate.parse(value)));
            cell.setCellStyle(style);
        } catch (DateTimeParseException | NullPointerException e) {
            cell.setCellValue(value);
            //log.warn(PARSE_ERROR_MESSAGE, "daty", value);
        }
    }

    private static void setDateTimeFormat(CellStyle style, Cell cell, String value) {
        try {
            if (value.length() > 19) {
                value = value.substring(0, 19);
            }
            cell.setCellValue(String.valueOf(LocalDateTime.parse(value)));
            cell.setCellStyle(style);
        } catch (DateTimeParseException | NullPointerException e) {
            cell.setCellValue(value);
            // log.warn(PARSE_ERROR_MESSAGE, "daty", value);
        }
    }

    private static void setTimeFormat(CellStyle style, Cell cell, String value) {
        try {
            LocalTime time = LocalTime.parse(value);
            cell.setCellValue(value);
            cell.setCellStyle(style);
        } catch (DateTimeParseException | NullPointerException e) {
            cell.setCellValue(value);
            //log.warn(PARSE_ERROR_MESSAGE, "godziny", value);
        }
    }

    private static void setProcentFormat(CellStyle style, Cell cell, String value) {
        try {
            cell.setCellValue(Float.parseFloat(value) / 100);
            cell.setCellStyle(style);
        } catch (NumberFormatException | NullPointerException e) {
            cell.setCellValue(value);
            //log.warn(PARSE_ERROR_MESSAGE, "wartości procentowej", value);
        }
    }

    private static void setNumericFormat(CellStyle style, Cell cell, String value) {
        try {
            cell.setCellValue(Long.parseLong(value.replace(",", ".")));
            cell.setCellStyle(style);
        } catch (NumberFormatException | NullPointerException e) {
            cell.setCellValue(value);
            // log.warn(PARSE_ERROR_MESSAGE, "wartości liczbowej", value);
        }
    }

    private static void setValueFormat(CellStyle style, Cell cell, String value) {
        try {
            cell.setCellValue(Double.parseDouble(value.replace(",", ".")));
            cell.setCellStyle(style);
        } catch (NumberFormatException | NullPointerException e) {
            cell.setCellValue(value);
            //log.warn(PARSE_ERROR_MESSAGE, "wartości liczbowej", value);
        }
    }

    private static void setMoneyFormat(CellStyle style, Cell cell, String value) {
        try {
            cell.setCellValue(Double.parseDouble(value.replace(" ", "").replace(",", ".")));
            cell.setCellStyle(style);
        } catch (NumberFormatException | NullPointerException e) {
            cell.setCellValue(value);
            //log.warn(PARSE_ERROR_MESSAGE, "kwoty", value);
        }
    }

    public static final boolean export(String filePath, List<List<String>> data, Map<Integer, FormatCell> columnFormat,
                                       boolean filtr, boolean autosize, String raport) {
        if (!filePath.endsWith(FILE_EXTENSION)) {
            filePath = filePath.concat(FILE_EXTENSION);
        }
        if (columnFormat == null) {
            columnFormat = new HashMap<>();
        }

        String sheetName = FilenameUtils.getBaseName(filePath);

        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet(sheetName);
        Map<FormatCell, CellStyle> styles = createStyles(wb);

        fillSheet(sheet, data, styles, columnFormat, filtr, raport);

        if (autosize && !data.isEmpty()) {
            for (int c = 0; c < data.get(0).size(); c++) {
                sheet.autoSizeColumn(c);
            }
        }

        return saveFile(filePath, wb);

    }

    public static boolean isBeforeOrEqual(LocalDate date, LocalDate compareToDate) {
        if (date == null || compareToDate == null) {
            return false;
        }
        return date.isBefore(compareToDate);// || compareToDate.isEqual(date);
    }

    public static boolean isAfterOrEqual(LocalDate date, LocalDate compareToDate) {
        if (date == null || compareToDate == null) {
            return false;
        }
        return date.isAfter(compareToDate); // || compareToDate.isEqual(date);
    }

    private static void fillSheet(Sheet sheet, List<List<String>> data, Map<FormatCell, CellStyle> styles,
                                  Map<Integer, FormatCell> columnFormat, boolean filtr, String raport) {
        // iterating r number of rows
        for (int r = 0; r < data.size(); r++) {
            Row row = sheet.createRow(r);

            // iterating c number of columns
            for (int c = 0; c < data.get(r).size(); c++) {
                Cell cell = row.createCell(c);
                if (r == 0) {
                    cell.setCellValue(data.get(r).get(c));
                    cell.setCellStyle(styles.get(FormatCell.HEADER));
                    sheet.setColumnWidth(c, 16 * 256);
                } else {
                    if (c > 10 && c < 16 && !data.get(r).get(5).isEmpty() && !data.get(r).get(6).isEmpty()) {
                        LocalDate from = LocalDate.parse(data.get(r).get(5));
                        LocalDate toDate = LocalDate.parse(data.get(r).get(6));
                        LocalDate toCompare = LocalDate.parse(data.get(0).get(c));
                        if ((from.isEqual(toCompare) || from.isBefore(toCompare)) && (toCompare.isBefore(toDate) || toCompare.equals(toDate))) {
                            CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
                            headerCellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);
                            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            cell.setCellStyle(headerCellStyle);
                            prepareFormatedValue(cell, styles, data.get(r).get(c), columnFormat.get(c));
                        }
                    } else if (c > 10 && c < 16 && !data.get(r).get(5).isEmpty() && data.get(r).get(6).isEmpty()) {
                        LocalDate from = LocalDate.parse(data.get(r).get(5));
                        LocalDate toCompare = LocalDate.parse(data.get(0).get(c));
                        if ((from.isEqual(toCompare))) {
                            CellStyle headerCellStyle = sheet.getWorkbook().createCellStyle();
                            headerCellStyle.setFillForegroundColor(IndexedColors.RED.index);
                            headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                            cell.setCellStyle(headerCellStyle);
                            prepareFormatedValue(cell, styles, data.get(r).get(c), columnFormat.get(c));
                        }
                    } else {
                        prepareFormatedValue(cell, styles, data.get(r).get(c), columnFormat.get(c));
                    }
                }
            }
        }
        if (filtr && !data.isEmpty() && !data.get(0).isEmpty()) {
            sheet.setAutoFilter(new CellRangeAddress(0, data.size() - 1, 0, data.get(0).size() - 1));
        }
    }
}
