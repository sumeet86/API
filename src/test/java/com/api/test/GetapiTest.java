package com.api.test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.api.client.RestClient;

public class GetapiTest {

	String tab1url;
	String tab2url;
	int sheet1;

	@SuppressWarnings({ "unchecked", "unused", "resource" })
	@Test
	public void getTest() throws ClientProtocolException, IOException {

		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/TestData.xlsx");
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = workbook.getSheetAt(0);
		int rowcount = sheet1.getLastRowNum();

		for (int i = 1; i < rowcount + 1; i++) {
			String table1 = sheet1.getRow(i).getCell(0).getStringCellValue();
			String table2 = sheet1.getRow(i).getCell(1).getStringCellValue();

			tab1url = table1;
			tab2url = table2;

			RestClient rc = new RestClient();
			rc.get(tab1url);
			RestClient rc1 = new RestClient();
			rc1.get(tab2url);

			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> map = mapper.readValue(rc.responsestring, Map.class);

			ObjectMapper mapper1 = new ObjectMapper();
			Map<String, String> map2 = mapper.readValue(rc1.responsestring, Map.class);

			if (map.entrySet().containsAll(map2.entrySet())) {
				System.out.println(tab1url + " equals " + tab2url);
			} else {
				System.out.println(tab1url + " not equals " + tab2url);
			}

		}
		workbook.close();
	}

}
