package com.sparta.hotbody.diet.service;

import com.sparta.hotbody.diet.dto.FoodResponseDto;
import java.io.File;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

import org.apache.poi.util.XMLHelper;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;

import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;

import org.springframework.stereotype.Service;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

@Service
public class FoodServiceImpl implements FoodService {


  private File file;
  private static String searchFoodText;
  private static String foodType;
  private static List<FoodResponseDto> foodResponseDtoList = new ArrayList<FoodResponseDto>();

  @Override
  public List<FoodResponseDto> searchFood(String foodType, String searchFoodText) throws Exception {
    switch (foodType) {
      case ("농축산물"):
        file = new File(
            "src/main/resources/foodData/agriculturalAndLivestockProducts.xlsx"); // 농산물 DB
        break;
      case ("수산물"):
        file = new File(
            "src/main/resources/foodData/aquaticProducts.xlsx"); // 수산물 DB
        break;
      case ("음식"):
        file = new File(
            "src/main/resources/foodData/food.xlsx"); // 음식 DB
        break;
      case ("가공음식"):
        file = new File(
            "src/main/resources/foodData/processedFood.xlsx"); // 가공식품 DB
        break;
    }
    this.searchFoodText = searchFoodText;
    this.foodType = foodType;

    processAllSheets(file);

    return foodResponseDtoList;
  }


  public void processAllSheets(File filename) throws Exception {

    OPCPackage pkg = OPCPackage.open(filename, PackageAccess.READ);

    XSSFReader xssfReader = new XSSFReader(pkg);

    StylesTable stylesTable = xssfReader.getStylesTable();

    // 성능 향상을 위해 쓰인다. https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/model/SharedStringsTable.html
    ReadOnlySharedStringsTable stringsTable = new ReadOnlySharedStringsTable(pkg);

    XMLReader parser = fetchSheetParser(stringsTable, stylesTable);

    Iterator<InputStream> sheets = xssfReader.getSheetsData();
    while (sheets.hasNext()) {
      InputStream sheet = sheets.next();
      InputSource sheetSource = new InputSource(sheet);
      parser.parse(sheetSource);
      sheet.close();
    }
  }

  public XMLReader fetchSheetParser(ReadOnlySharedStringsTable strings, StylesTable styles)
      throws SAXException, ParserConfigurationException {
    XMLReader parser = XMLHelper.newXMLReader();
    ContentHandler handler = new XSSFSheetXMLHandler(styles, strings, new SheetHandler(), false);
    parser.setContentHandler(handler);
    return parser;
  }

  /**
   * See org.xml.sax.helpers.DefaultHandler javadocs
   */
  private static class SheetHandler implements SheetContentsHandler {

    private List<String[]> rows = new ArrayList<String[]>();
    private String[] row;
    private int currColNum;

    @Override
    public void startRow(int rowNum) {
      if (!foodResponseDtoList.isEmpty()) {
        foodResponseDtoList.clear();
      }
      this.row = new String[20];
      currColNum = 0;
    }

    @Override
    public void cell(String cellReference, String formattedValue, XSSFComment comment) {
      if (currColNum < 20) {
        row[currColNum++] = formattedValue == null ? searchFoodText : formattedValue;
      } else {
        return;
      }

    }

    @Override
    public void endRow(int rowNum) {
      if (row[5].contains(searchFoodText)) {
        rows.add(row);
      }
    }

    @Override
    public void endSheet() {
      for (String[] row : rows) {
        foodResponseDtoList.add(new FoodResponseDto(foodType, row));
      }
    }
  }

}
