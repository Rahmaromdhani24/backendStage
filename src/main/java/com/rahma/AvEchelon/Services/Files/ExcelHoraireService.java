package com.rahma.AvEchelon.Services.Files;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rahma.AvEchelon.Entity.HistoriqueAv;
import com.rahma.AvEchelon.Services.Avancement.AvancementProjection;
import com.rahma.AvEchelon.Services.Avancement.ServiceAvancement;
import com.rahma.AvEchelon.Services.Historique.ServiceHistorique;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.apache.poi.ss.util.CellRangeAddress;

@Service
public class ExcelHoraireService {

	  @Autowired private ServiceHistorique service;
	  @Autowired private ServiceAvancement serviceAvancement;

	public ResponseEntity<ByteArrayResource> generateExcelHoraire(String dateEffet) throws IOException, ParseException {
		 
		List<HistoriqueAv> avancements = service.recupereLesHistoriquePersonnelsHoraire(dateEffet); // Récupérer toutes les données depuis la base de données
		 avancements.sort(Comparator.comparingInt(av -> Integer.parseInt(av.getMle())));
		    /********************************************************************************/
		 XSSFWorkbook workbook = new XSSFWorkbook();
		    Sheet sheet = workbook.createSheet("etat Horaire_"+dateEffet);

		    // Create styles for titles and underlined text
		    CellStyle titleStyle = workbook.createCellStyle();
		    Font titleFont = workbook.createFont();
		    titleFont.setBold(true);
		    titleStyle.setFont(titleFont);

		    CellStyle underlineStyle = workbook.createCellStyle();
		    Font underlineFont = workbook.createFont();
		    underlineFont.setUnderline(Font.U_SINGLE);
		    underlineStyle.setFont(underlineFont);

		    // Style for bold and double underlined cells
		    CellStyle boldDoubleUnderlineStyle = workbook.createCellStyle();
		    Font boldDoubleUnderlineFont = workbook.createFont();
		    boldDoubleUnderlineFont.setBold(true);
		    boldDoubleUnderlineFont.setUnderline(Font.U_DOUBLE);
		    boldDoubleUnderlineStyle.setFont(boldDoubleUnderlineFont);

		    // Style with thicker border for header cells
		    CellStyle headerCellStyle = workbook.createCellStyle();
		    Font headerFont = workbook.createFont();
		    headerFont.setBold(true);
		    headerCellStyle.setFont(headerFont);
		    headerCellStyle.setBorderBottom(BorderStyle.MEDIUM);
		    headerCellStyle.setBorderTop(BorderStyle.MEDIUM);
		    headerCellStyle.setBorderRight(BorderStyle.MEDIUM);
		    headerCellStyle.setBorderLeft(BorderStyle.MEDIUM);
		    headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
		    headerCellStyle.setWrapText(true); // Enable text wrapping

		    // Style with border for data cells
		    CellStyle borderedCellStyle = workbook.createCellStyle();
		    borderedCellStyle.setBorderBottom(BorderStyle.THIN);
		    borderedCellStyle.setBorderTop(BorderStyle.THIN);
		    borderedCellStyle.setBorderRight(BorderStyle.THIN);
		    borderedCellStyle.setBorderLeft(BorderStyle.THIN);
		    borderedCellStyle.setAlignment(HorizontalAlignment.CENTER);
		    borderedCellStyle.setWrapText(true); // Enable text wrapping


		    // Create yellow cell style
	        CellStyle yellowCellStyle = createYellowCellStyle(workbook);
		    
		    // Add titles
		    Row row0 = sheet.createRow(0);
		    Cell cell0 = row0.createCell(0);
		    cell0.setCellValue("S.T.I.P");
		    cell0.setCellStyle(titleStyle);

		    Cell cell8 = row0.createCell(6);
		    cell8.setCellValue("ETAT D'AVANCEMENT D'ECHELON NORMAL - "+transformDateToMonthYear(dateEffet));
		    cell8.setCellStyle(boldDoubleUnderlineStyle);

		    Row row1 = sheet.createRow(1);
		    Cell cell1 = row1.createCell(0);
		    cell1.setCellValue("USINE M'SAKEN");
		    cell1.setCellStyle(titleStyle);

		    Row row2 = sheet.createRow(2);
		    Cell cell2 = row2.createCell(0);
		    cell2.setCellValue("SCE Développement R-H & Formation");
		    cell2.setCellStyle(titleStyle);

		    Cell cell10 = row2.createCell(8);
		    cell10.setCellValue("PERSONNEL : Horaire");
		    cell10.setCellStyle(boldDoubleUnderlineStyle);

		    // Create the table with specified headers
		    String[][] headers = {
		        { "Mle", "Nom & Prénom", "ANCIENNE SITUATION", "","", "", "", "", "NOUVELLE SITUATION", "","", "", "", "", "MOY\nNOTE", "SANCTION\n1er DEGRE", "SANCTION\n2eme DEGRE", "REPORT\n3 MOIS", "REPORT\n6 MOIS" },
		        { "", "", "CAT", "S/C","ECH", "SB/TH", "IND-DIFF", "D.EFFET", "CAT","S/C", "ECH", "SB/TH", "IND-DIFF", "D.EFFET", "", "", "", "", "" }
		    };

		    // First header row
		    Row headerRow1 = sheet.createRow(4); // Row 4 (0-based index)
		    for (int i = 0; i < headers[0].length; i++) {
		        Cell cell = headerRow1.createCell(i);
		        cell.setCellValue(headers[0][i]);
		        cell.setCellStyle(headerCellStyle);
		    }

		    // Second header row
		    Row headerRow2 = sheet.createRow(5); // Row 5 (0-based index)
		    for (int i = 0; i < headers[1].length; i++) {
		        Cell cell = headerRow2.createCell(i);
		        cell.setCellValue(headers[1][i]);
		        cell.setCellStyle(headerCellStyle);
		    }

		    // Merge cells for multi-line headers
		    sheet.addMergedRegion(new CellRangeAddress(4, 5, 0, 0)); // Mle
		    sheet.addMergedRegion(new CellRangeAddress(4, 5, 1, 1)); // Nom & Prénom
		    sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 7)); // ANCIENNE SITUATION
		    sheet.addMergedRegion(new CellRangeAddress(4, 4, 8, 13)); // NOUVELLE SITUATION
		    sheet.addMergedRegion(new CellRangeAddress(4, 5, 14, 14)); // MOY NOTE
		    sheet.addMergedRegion(new CellRangeAddress(4, 5, 15, 15)); // SANCTION 1er DEGRE
		    sheet.addMergedRegion(new CellRangeAddress(4, 5, 16, 16)); // SANCTION 2eme DEGRE
		    sheet.addMergedRegion(new CellRangeAddress(4, 5, 17, 17)); // REPORT 3 MOIS
		    sheet.addMergedRegion(new CellRangeAddress(4, 5, 18, 18)); // REPORT 6 MOIS

		    // Adjust column width for "Nom & Prénom"
		    sheet.setColumnWidth(1, 6000); // Adjust the width as needed (6000 = 6000/256 characters)
		    
		    // Adjust column width for the last columns
		    sheet.setColumnWidth(0, 1600); // Adjust the width for "Mle personnel"
		    sheet.setColumnWidth(15, 3000); // Adjust the width for "SANCTION 1er DEGRE"
		    sheet.setColumnWidth(16, 3200); // Adjust the width for "SANCTION 2eme DEGRE"
		    sheet.setColumnWidth(17, 2500); // Adjust the width for "REPORT 3 MOIS"
		    sheet.setColumnWidth(18, 2500); // Adjust the width for "REPORT 6 MOIS"
		    
		    sheet.setColumnWidth(14, 1800); // Adjust the width for "MOY NOTE"
		    sheet.setColumnWidth(2, 1400); // Adjust the width for "CAT"
		    sheet.setColumnWidth(3, 1400); // Adjust the width for "s/c"
		    sheet.setColumnWidth(4, 1400); // Adjust the width for "ECH"
		    sheet.setColumnWidth(8, 1400); // Adjust the width for "CAT"
		    sheet.setColumnWidth(9, 1400); // Adjust the width for "s/c"
		    sheet.setColumnWidth(10, 1400); // Adjust the width for "ECH"
		    
		    sheet.setColumnWidth(5, 2000); // Adjust the width for "SB/TH"
		    sheet.setColumnWidth(6, 2500); // Adjust the width for "IND-DIFF"
		    sheet.setColumnWidth(7, 3000); // Adjust the width for "D.EFFET"
		    
		    sheet.setColumnWidth(11, 2000); // Adjust the width for "SB/TH"
		    sheet.setColumnWidth(12, 2500); // Adjust the width for "IND-DIFF"
		    sheet.setColumnWidth(13, 3000); // Adjust the width for "D.EFFET"
		    
		 
		 /**************************************************************************************/
		    int currentRowIndex = 6; // Start with row index 6
            List<String[]> dataList = new ArrayList<>();

            for (int i = 0; i < avancements.size(); i++) {
                HistoriqueAv avancement = avancements.get(i);
                String indDiffA = avancement.getIndDiffA() != null ? avancement.getIndDiffA() : "-";
                String indDiffN = avancement.getIndDiffN() != null ? avancement.getIndDiffN() : "-";
                String notePersonnel = avancement.getNote() != 0 ? String.format("%.2f", avancement.getNote()) : "-";
                String mle = avancement.getMle();
                AvancementProjection test = serviceAvancement.recupereLesAvancementsPersonnelHoraireByPeriode(mle, dateEffet);

                if (test == null) {
                    dataList.add(new String[]{
                            avancement.getMle(),
                            avancement.getNom(),
                            avancement.getCatA(),
                            avancement.getSCatA(),
                            avancement.getEchA(),
                            avancement.getThA(),
                            indDiffA,
                            Optional.ofNullable(avancement.getDEffetA())
                                    .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
                                    .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
                                    .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
                                    .orElse(""),
                            avancement.getCatN(),
                            avancement.getSCatN(),
                            avancement.getEchN(),
                            avancement.getThN(),
                            indDiffN,
                            Optional.ofNullable(avancement.getDEffetN())
                                    .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
                                    .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
                                    .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
                                    .orElse(""),
                            notePersonnel,
                            avancement.getSan1(),
                            avancement.getSan2(),
                            "",
                            ""
                    });
                    currentRowIndex++;
                } else if (test != null) {
                    dataList.add(new String[]{
                            avancement.getMle(),
                            avancement.getNom(),
                            test.getCat(),
                            test.getsCat(),
                            test.getEch(),
                            test.getTh(),
                            test.getIndDiff()!= null ?  test.getIndDiff() : "-",
                            Optional.ofNullable(test.getdEffet())
                                    .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
                                    .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
                                    .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
                                    .orElse(""),
                            avancement.getCatN(),
                            avancement.getSCatN(),
                            avancement.getEchN(),
                            avancement.getThN(),
                            indDiffN,
                            Optional.ofNullable(avancement.getDEffetN())
                                    .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
                                    .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
                                    .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
                                    .orElse(""),
                            notePersonnel,
                            avancement.getSan1(),
                            avancement.getSan2(),
                            "",
                            ""
                    });
                    currentRowIndex++;

                    // Add the second row with additional info
                    dataList.add(new String[]{
                            "",
                            "",
                            avancement.getCatA(),
                            avancement.getSCatA(),
                            avancement.getEchA(),
                            avancement.getThA(),
                            indDiffA,
                            Optional.ofNullable(avancement.getDEffetA())
                                    .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
                                    .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
                                    .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
                                    .orElse(""),
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            "",
                            ""
                    });
                    currentRowIndex++;

                    int currentRowIndexForMerge = currentRowIndex - 2; // Adjust for the two added rows
                    // Ensure that indices are valid and cover more than one cell
                    if (currentRowIndexForMerge < currentRowIndex - 1) {
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 0, 0); // Merge column 0
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 1, 1); // Merge column 1
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 8, 8); // Merge column 8
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 9, 9); // Merge column 9
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 10, 10); // Merge column 10
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 11, 11); // Merge column 11
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 12, 12); // Merge column 12
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 13, 13); // Merge column 13
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 14, 14); // Merge column 14
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 15, 15); // Merge column 15
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 16, 16); // Merge column 16
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 17, 17); // Merge column 16
                        mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 18, 18); // Merge column 16

                    }
                }
            }

            // Write the data to the sheet
            int rowIndexx = 6;
            for (String[] rowData : dataList) {
                Row row = sheet.createRow(rowIndexx++);
                for (int colIndex = 0; colIndex < rowData.length; colIndex++) {
                    Cell cell = row.createCell(colIndex);
                    cell.setCellValue(rowData[colIndex]);
                }
            }

	            String[][] data = dataList.toArray(new String[0][]);

        /*****************************************************************/
       // Ajouter les données dans la feuille
          addDataToSheet(sheet, data, borderedCellStyle, yellowCellStyle);
          /****************** ajouter footer   ***************/
          int nbrDerniereRow = addDataToSheet(sheet, data, borderedCellStyle, yellowCellStyle) + 1;

          // Style de la cellule
          CellStyle cellStyle = workbook.createCellStyle();
          Font font = workbook.createFont();
          font.setFontHeightInPoints((short) 12);
          cellStyle.setFont(font);

          // Style pour le texte en gras
          CellStyle boldCellStyle = workbook.createCellStyle();
          Font boldFont = workbook.createFont();
          boldFont.setBold(true);
          boldFont.setFontHeightInPoints((short) 12);
          boldCellStyle.setFont(boldFont);

          // Ajouter le texte
          int rowIndex = nbrDerniereRow;
          Row row = sheet.createRow(rowIndex++);
          Cell cell = row.createCell(2);
          cell.setCellValue("            Chef Service");
          cell.setCellStyle(boldCellStyle);

          Cell cell100 = row.createCell(14);
          cell100.setCellValue("Le S/D Ressources Humaine");
          cell100.setCellStyle(boldCellStyle);

          /******************************************/
          row = sheet.createRow(rowIndex++);
          cell = row.createCell(2);
          cell.setCellValue("Développement RH & Formation");
          cell.setCellStyle(boldCellStyle);
          Cell cell101 = row.createCell(14);
          cell101.setCellValue("& Relations Professionnelles P/I  ");
          cell101.setCellStyle(boldCellStyle);
         /* CellRangeAddress cellRangeAddress = new CellRangeAddress(nbrDerniereRow+1, nbrDerniereRow+1, 14, 17);
          sheet.addMergedRegion(cellRangeAddress);*/
          /********************************************/
          row = sheet.createRow(rowIndex++);
          cell = row.createCell(2);
          cell.setCellValue("            Amani NASR");
          cell.setCellStyle(boldCellStyle);
          Cell cell102 = row.createCell(15);
          cell102.setCellValue("Lotfi ROUIS");
          cell102.setCellStyle(boldCellStyle);
          
          
          /**************************************************************/
          // Convert workbook to byte array
          ByteArrayOutputStream bos = new ByteArrayOutputStream();
          workbook.write(bos);
          workbook.close();
          ByteArrayResource resource = new ByteArrayResource(bos.toByteArray());
          return ResponseEntity.ok()
	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=etat Mensuel_"+dateEffet+".xlsx")
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .body(resource);
	}


private int addDataToSheet(Sheet sheet, String[][] data, CellStyle borderedCellStyle, CellStyle yellowCellStyle) {
	        int rowNum = 6;
	        for (String[] rowData : data) {
	            Row row = sheet.createRow(rowNum++);
	            boolean shouldColorRow = false; 
	            
	            for (int i = 0; i < rowData.length; i++) {
	                Cell cell = row.createCell(i);
	                String cellValue = rowData[i];
	                
	                if (cellValue != null) {
	                    cell.setCellValue(cellValue);
	                }
	                
	                if (shouldColorRow) {
	                    cell.setCellStyle(yellowCellStyle);
	                } else {
	                    CellStyle centerCellStyle = sheet.getWorkbook().createCellStyle();
	                    centerCellStyle.cloneStyleFrom(borderedCellStyle); 
	                    centerCellStyle.setAlignment(HorizontalAlignment.CENTER); 
	                    
	                    cell.setCellStyle(centerCellStyle); 
	                }
	                if ((i == 15 || i == 16) && cellValue != null && !cellValue.isEmpty()) {
	                    shouldColorRow = true; 
	                }
	                if (rowData.length > 14 && "-".equals(rowData[14])) {
	                    shouldColorRow = true;
	                }
	            }
	            
	            if (shouldColorRow) {
	                CellStyle centerRowStyle = sheet.getWorkbook().createCellStyle();
	                centerRowStyle.cloneStyleFrom(yellowCellStyle); 
	                centerRowStyle.setAlignment(HorizontalAlignment.CENTER);
	                for (int i = 0; i < row.getLastCellNum(); i++) {
	                    Cell cell = row.getCell(i);
	                    if (cell != null) {
	                        cell.setCellStyle(centerRowStyle); 
	                    }
	                }
	            }
	        }
	        return rowNum ; 

	    } 
	 


	 public String transformDateToMonthYear(String dateString) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate date = LocalDate.parse(dateString, formatter);
	        
	        String monthName = date.getMonth().getDisplayName(
	            TextStyle.FULL, 
	            Locale.FRENCH
	        );
	        
	        String year = String.valueOf(date.getYear());
	        
	        return "MOIS DE " + monthName.toUpperCase() + " " + year;
	    }
	 
	 private CellStyle createYellowCellStyle(Workbook workbook) {
	        CellStyle yellowCellStyle = workbook.createCellStyle();
	        yellowCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
	        yellowCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	        yellowCellStyle.setBorderBottom(BorderStyle.THIN);
	        yellowCellStyle.setBorderTop(BorderStyle.THIN);
	        yellowCellStyle.setBorderRight(BorderStyle.THIN);
	        yellowCellStyle.setBorderLeft(BorderStyle.THIN);
	        return yellowCellStyle;
	    }
	 private void mergeCells(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		    // Validate the merging range
		    if (firstRow <= lastRow && firstCol <= lastCol) {
		        // Ensure the range covers at least 2 cells
		        if (firstRow < lastRow || firstCol < lastCol) {
		            sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
		        } else {
		            System.out.println("Merge range is invalid as it does not cover multiple cells.");
		        }
		    } else {
		        throw new IllegalArgumentException("Invalid merge range: Rows and columns must be in increasing order.");
		    }
		}


}