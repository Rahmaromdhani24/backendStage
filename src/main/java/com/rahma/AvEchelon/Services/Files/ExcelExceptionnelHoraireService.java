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
import com.rahma.AvEchelon.Services.Personnel.MesPersonnels;
import com.rahma.AvEchelon.Services.Personnel.ServicePersonnel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import org.apache.poi.ss.util.CellRangeAddress;
import java.time.ZoneId;
import java.util.Optional;

@Service
public class ExcelExceptionnelHoraireService {

    @Autowired private ServiceHistorique service;
    @Autowired private ServiceAvancement serviceAvancement;
    @Autowired private ServicePersonnel servicePersonnel ; 

    public ResponseEntity<ByteArrayResource> generateExcelHoraire57ans(String dateEffet) throws IOException, ParseException {
        List<HistoriqueAv> avancements = service.recupereLesHistoriquePersonnelsHoraire(dateEffet); 
	    avancements.sort(Comparator.comparingInt(av -> Integer.parseInt(av.getMle())));
	    /********************************************************************************/
	    XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("etat ExceptionnelHoraire_" + dateEffet);

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
        cell8.setCellValue("ETAT D'AVANCEMENT EXCEPTIONNEL / PROMOTION EXCEPTIONNELLE DE 57 ANS");
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
        cell10.setCellValue(transformDateToMonthYear(dateEffet));
        cell10.setCellStyle(boldDoubleUnderlineStyle);

        // Create the table with specified headers
        String[][] headers = {
            { "Mle", "Nom & Prénom", "ANCIENNE SITUATION", "", "", "", "", "NOUVELLE SITUATION", "", "", "", "", "MOY\nNOTE", "SANCTION\n1er DEGRE", "SANCTION\n2eme DEGRE", "REPORT\n3 MOIS", "REPORT\n6 MOIS" },
            { "", "", "CAT", "ECH", "SB/TH", "IND-DIFF", "D.EFFET", "CAT", "ECH", "SB/TH", "IND-DIFF", "D.EFFET", "", "", "", "", "" }
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
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 6)); // ANCIENNE SITUATION
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 7, 11)); // NOUVELLE SITUATION
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 12, 12)); // MOY NOTE
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 13, 13)); // SANCTION 1er DEGRE
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 14, 14)); // SANCTION 2eme DEGRE
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 15, 15)); // REPORT 3 MOIS
        sheet.addMergedRegion(new CellRangeAddress(4, 5, 16, 16)); // REPORT 6 MOIS

        // Adjust column width for "Nom & Prénom"
        sheet.setColumnWidth(1, 6000); // Adjust the width as needed (6000 = 6000/256 characters)
        
        // Adjust column width for the last columns
        sheet.setColumnWidth(13, 3000); // Adjust the width for "SANCTION 1er DEGRE"
        sheet.setColumnWidth(14, 3200); // Adjust the width for "SANCTION 2eme DEGRE"
        sheet.setColumnWidth(15, 2500); // Adjust the width for "REPORT 3 MOIS"
        sheet.setColumnWidth(16, 2500); // Adjust the width for "REPORT 6 MOIS"
        
        sheet.setColumnWidth(12, 1800); // Adjust the width for "MOY NOTE"
        sheet.setColumnWidth(2, 1400); // Adjust the width for "CAT"
        sheet.setColumnWidth(3, 1400); // Adjust the width for "ECH"
        sheet.setColumnWidth(7, 1400); // Adjust the width for "CAT"
        sheet.setColumnWidth(8, 1400); // Adjust the width for "ECH"
        
        sheet.setColumnWidth(4, 3000); // Adjust the width for "SB/TH"
        sheet.setColumnWidth(5, 3000); // Adjust the width for "IND-DIFF"
        sheet.setColumnWidth(6, 3000); // Adjust the width for "D.EFFET"
        
        sheet.setColumnWidth(9, 3000); // Adjust the width for "SB/TH"
        sheet.setColumnWidth(10, 3000); // Adjust the width for "IND-DIFF"
        sheet.setColumnWidth(11, 3000); // Adjust the width for "D.EFFET"
/**********************************************************************************************************************/
	    
        List<String[]> dataList = new ArrayList<>();
        int currentRowIndex = 6; // Start with row index 6

        for (int i = 0; i < avancements.size(); i++) {
            HistoriqueAv avancement = avancements.get(i);
            String indDiffA = avancement.getIndDiffA() != null ? avancement.getIndDiffA() : "-";
            String indDiffN = avancement.getIndDiffN() != null ? avancement.getIndDiffN() : "-";
            String notePersonnel = avancement.getNote() != 0 ? String.format("%.2f", avancement.getNote()) : "-";
            String mle = avancement.getMle();
            int age = calculerDifferenceDateEffetEtDateNaissance(mle , dateEffet) ; 
            if(age == 57)
            {
            	AvancementProjection test = serviceAvancement.recupereLesAvancementsPersonnelMensuelByPeriode(mle, dateEffet);
           
            
            if (test == null) {
                dataList.add(new String[]{
                    avancement.getMle(),
                    avancement.getNom(),
                    avancement.getCatA(),
                    avancement.getEchA(),
                    avancement.getSbaseA(),
                    indDiffA,
                    Optional.ofNullable(avancement.getDEffetA())
                    .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
                    .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
                    .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
                    .orElse(""),
                    avancement.getCatN(),
                    avancement.getEchN(),
                    avancement.getSbaseN(),
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
                    test.getEch(),
                    test.getSbase(),
                    test.getIndDiff()!= null ?  test.getIndDiff() : "-",
                    Optional.ofNullable(test.getdEffet())
  		            .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
  		            .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
  		            .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
  		            .orElse("") + "",
                    avancement.getCatN(),
                    avancement.getEchN(),
                    avancement.getSbaseN(),
                    indDiffN,
                    Optional.ofNullable(avancement.getDEffetN())
  		            .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
  		            .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
  		            .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
  		            .orElse("") + "",
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
                    avancement.getEchA(),
                    avancement.getSbaseA(),
                    indDiffA,
                    Optional.ofNullable(avancement.getDEffetA())
  		            .map(d -> new java.util.Date(d.getTime())) // Convert java.sql.Date to java.util.Date
  		            .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()) // Convert to LocalDate
  		            .map(localDate -> localDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) // Format LocalDate
  		            .orElse("") + "",
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

                // Merge cells dynamically based on the current row index
                int currentRowIndexForMerge = currentRowIndex - 2; // Adjust for the two added rows

                if (currentRowIndexForMerge < currentRowIndex - 1) {
                	 mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 0, 0); // Adjust column indexes as needed
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 1, 1);               
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 7, 7); // Column 7 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 8, 8); // Column 8 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 9, 9); // Column 9 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 10, 10); // Column 10 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 11, 11); // Column 11 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 12, 12); // Column 12 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 13, 13); // Column 13 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 14, 14); // Column 14 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 15, 15); // Column 15 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 16, 16); // Column 16 for merged cells
                     mergeCells(sheet, currentRowIndexForMerge, currentRowIndexForMerge + 1, 17, 17); // Column 17 for merged cells

                }
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
        Cell cell = row.createCell(1);
        cell.setCellValue("            Chef Service");
        cell.setCellStyle(boldCellStyle);

        Cell cell100 = row.createCell(12);
        cell100.setCellValue("Le S/D Ressources Humaine");
        cell100.setCellStyle(boldCellStyle);

        /******************************************/
        row = sheet.createRow(rowIndex++);
        cell = row.createCell(1);
        cell.setCellValue("Développement RH & Formation");
        cell.setCellStyle(boldCellStyle);
        Cell cell101 = row.createCell(12);
        cell101.setCellValue("& Relations Professionnelles P/I   ");
        cell101.setCellStyle(boldCellStyle);
        

        /********************************************/
        row = sheet.createRow(rowIndex++);
        cell = row.createCell(1);
        cell.setCellValue("            Amani NASR");
        cell.setCellStyle(boldCellStyle);
        Cell cell102 = row.createCell(12);
        cell102.setCellValue("                  Lotfi ROUIS");
        cell102.setCellStyle(boldCellStyle);
        
        
        /**************************************************************/
        // Convert workbook to byte array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        workbook.write(bos);
        workbook.close();
        ByteArrayResource resource = new ByteArrayResource(bos.toByteArray());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=etat Mensuel_" + dateEffet + ".xlsx")
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
                if ((i == 13 || i == 14) && cellValue != null && !cellValue.isEmpty()) {
                    shouldColorRow = true; 
                }
                if (rowData.length > 12 && "-".equals(rowData[12])) {
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

	    public int calculerDifferenceDateEffetEtDateNaissance(String mle, String dateEffetStr) throws ParseException {
	        MesPersonnels personnel = servicePersonnel.recuperePersonnelParMle(mle);
	        if (personnel == null) {
	            throw new IllegalArgumentException("Personnel not found with mle: " + mle);
	        }

	        Date dateNaissance = personnel.getDate_N();
	        LocalDate dateNaissanceLocal = convertToLocalDate(dateNaissance);

	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        LocalDate dateEffet = LocalDate.parse(dateEffetStr, formatter);

	        return Period.between(dateNaissanceLocal, dateEffet).getYears();
	    }

	    private LocalDate convertToLocalDate(Date date) {
	        if (date instanceof java.sql.Date) {
	            return ((java.sql.Date) date).toLocalDate();
	        } else {
	            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	        }
	    }
	    
	}