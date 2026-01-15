package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

public class ExcelUtils {
	
	  public static List<Map<String, String>> readExcelData(String filePath, String sheetName) throws IOException {
	        FileInputStream fis = new FileInputStream(filePath);
	        Workbook workbook = new XSSFWorkbook(fis);
	        Sheet sheet = workbook.getSheet(sheetName);

	        List<Map<String, String>> data = new ArrayList<>();
	        Row headerRow = sheet.getRow(0);
	        int rowCount = sheet.getPhysicalNumberOfRows();

	        for (int i = 1; i < rowCount; i++) { // Start from row 1, skipping the header row
	            Row currentRow = sheet.getRow(i);

	            if (currentRow == null || isRowEmpty(currentRow)) {
	                continue; // Skip empty rows
	            }

	            Map<String, String> rowData = new HashMap<>();
	            for (int j = 0; j < headerRow.getPhysicalNumberOfCells(); j++) {
	                String header = headerRow.getCell(j).getStringCellValue();
	                Cell cell = currentRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

	                rowData.put(header, cell.toString().trim()); // Handle empty cells gracefully
	            }

	            data.add(rowData);
	        }

	        workbook.close();
	        fis.close();
	        return data;
	    }

	    private static boolean isRowEmpty(Row row) {
	        for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
	            Cell cell = row.getCell(cellIndex, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
	            if (cell != null && !cell.toString().trim().isEmpty()) {
	                return false; // Row contains data
	            }
	        }
	        return true; // Row is empty
	    }
	    
	    
	    public static void writeCell(String filePath, String sheetName, String scenarioName, String columnName, String value)
	            throws IOException, InvalidFormatException {

	        FileInputStream fis = new FileInputStream(filePath);
	        Workbook workbook = WorkbookFactory.create(fis);
	        Sheet sheet = workbook.getSheet(sheetName);

	        int scenarioCol = -1;
	        int targetCol = -1;

	        Row headerRow = sheet.getRow(0);

	        // Find column indexes
	        for (Cell cell : headerRow) {
	            if (cell.getStringCellValue().equalsIgnoreCase("ScenarioName")) {
	                scenarioCol = cell.getColumnIndex();
	            }
	            if (cell.getStringCellValue().equalsIgnoreCase(columnName)) {
	                targetCol = cell.getColumnIndex();
	            }
	        }

	        if (scenarioCol == -1 || targetCol == -1) {
	            throw new RuntimeException("Column not found in Excel: " + columnName);
	        }

	        // Find row for scenario
	        for (Row row : sheet) {
	            Cell scenarioCell = row.getCell(scenarioCol);
	            if (scenarioCell != null && scenarioCell.getStringCellValue().equalsIgnoreCase(scenarioName)) {
	                row.createCell(targetCol).setCellValue(value);
	                break;
	            }
	        }

	        fis.close();

	        FileOutputStream fos = new FileOutputStream(filePath);
	        workbook.write(fos);
	        fos.close();
	        workbook.close();
	    }


}
