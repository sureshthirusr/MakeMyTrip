package com.makemytrip.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.makemytrip.baseclass.BaseClass;
import com.makemytrip.pom.AllFlightInfoPage;

public class UtilFunctions extends BaseClass {

	/*
	 * 1. ExpWaitForWebelement() 2. getExcelData() 3. getCurrentAndReturnDates() 4.
	 * customXpath() 5. scrollToBottom() 6. ScrollToTop() 7. scrollToView() 8.
	 * getScreenshot()
	 * 
	 */
	public static void ExpWaitForWebelement(WebElement element) {
		WebDriverWait expWait = new WebDriverWait(driver, 40);
		expWait.until(ExpectedConditions.elementToBeClickable(element));

	}

	public static String EXCELDATA_SHEET_PATH = prop.getProperty("excelSheetPath");

	static Workbook book;
	static Sheet sheet;

	public static Object[][] getExcelData(String excelSheetName) throws InvalidFormatException {
		FileInputStream file = null;
		try {
			file = new FileInputStream(EXCELDATA_SHEET_PATH);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(excelSheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
				data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
			}
		}
		return data;
	}

	public static String departureDate;
	public static String returnDate;

	public static UtilFunctions getCurrentAndReturnDates() throws Exception {
		UtilFunctions date = new UtilFunctions();
		Calendar cal = Calendar.getInstance();

		cal.add(Calendar.DATE, 1); // please delete this line. added for test the tomorrow date.

		String[] dateArr = cal.getTime().toString().split(" ");
		UtilFunctions.departureDate = dateArr[0] + " " + dateArr[1] + " " + dateArr[2] + " " + dateArr[5];
		Thread.sleep(2000);
		cal.add(Calendar.DATE, Integer.parseInt(prop.getProperty("NoOfdays")));
		dateArr = cal.getTime().toString().split(" ");
		UtilFunctions.returnDate = dateArr[0] + " " + dateArr[1] + " " + dateArr[2] + " " + dateArr[5];
		return date;
	}

	public static By customXpath(String xpath, String param) {
		String rawPath = xpath.replaceAll("%replace%", param);
		return By.xpath(rawPath);
	}

	public static void scrollToBottom() throws InterruptedException {
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;

		try {
			long currentHeight = Long.parseLong(jsDriver.executeScript("return document.body.scrollHeight").toString());

			while (true) {
				jsDriver.executeScript("window.scrollTo(0,document.body.scrollHeight)");
				Thread.sleep(300);

				long newHeight = Long.parseLong(jsDriver.executeScript("return document.body.scrollHeight").toString());

				if (currentHeight == newHeight)
					break;
				currentHeight = newHeight;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void ScrollToTop() {
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		jsDriver.executeScript("window.scrollTo(document.body.scrollHeight,0)");
	}

	public static void scrollToView(WebElement element) {
		JavascriptExecutor jsDriver = (JavascriptExecutor) driver;
		jsDriver.executeScript("arguments[0].scrollIntoView(true);", element);
	}

	public static String getScreenshot(String imageName) {

		String currentDate = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		String destination = System.getProperty("user.dir") + "/Screenshots/" + imageName + currentDate + ".png";
		try {
			FileUtils.copyFile(source, new File(destination));
		} catch (IOException e) {
			System.out.println("Failed to capture the screen " + e.getMessage());
		}

		return destination;
	}

	public static boolean isVisble(WebElement element) {
		boolean flag = false;
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		try {
			if (element.isDisplayed()) {
				flag = true;
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return flag;

	}

	public static String readFlightNameFromData(int lowest_Price, int mininimum_Duration) throws Throwable {
		Map<Integer, Integer> priceMap = new HashMap<>();
		Map<Integer, Integer> durationMap = new HashMap<>();
		int flight_Time = 0;
		int Flight_rowNum = 1;
		String FlightName;

		File f = new File(System.getProperty("user.dir")
				+ "\\src\\test\\resource\\com\\makemytrip\\flightdetails\\Flight Details.xlsx");
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fis);
		Sheet sheet = wb.getSheetAt(0);

		//System.out.println(sheet.getPhysicalNumberOfRows());
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {

				if (j == 3) {
					Cell cell = row.getCell(j);
					String value = cell.getStringCellValue().replace("₹ ", "").replace(",", "");
					//System.out.println(value);
					int amount = Integer.parseInt(value);
					if (lowest_Price == amount) {
						int rowNum = row.getRowNum();
						priceMap.put(rowNum, amount);
					}
				}
				if (j == 2) {
					Cell cell = row.getCell(j);
					String value = cell.getStringCellValue().replace(" h ", "").replace(" h", "")
							.replace(" m", "").trim();
				//	System.out.println(value);

					int duration = Integer.parseInt(value);

					if (mininimum_Duration == duration) {
						int rowNum = row.getRowNum();
						durationMap.put(rowNum, duration);
					}
				}
			}
		}
	//	System.out.println(priceMap.size());
	//	System.out.println(durationMap.size());
		if (priceMap.size() > 1 && durationMap.size() > 1) {

			Set<Integer> rowNums = priceMap.keySet();
			for (int rowNum : rowNums) {
				if (durationMap.containsKey(rowNum)) {
					String stringCellValue = sheet.getRow(rowNum).getCell(1).getStringCellValue().replace(":", "");
					int time = Integer.parseInt(stringCellValue);
					if (time > flight_Time) {
						flight_Time = time;
						Flight_rowNum = rowNum;
					}
				}
			}
		}
		FlightName = sheet.getRow(Flight_rowNum).getCell(0).getStringCellValue();
		return FlightName;
	}

	public static void getFlightInfo() throws Throwable {
		AllFlightInfoPage e = new AllFlightInfoPage(driver);

		//System.out.println("Number of Filtered Flights" + e.getFlightName().size());
		int rowNum = 1;
		for (int i = 0; i < e.getFlightName().size(); i++) {
			String flightName = e.getFlightName().get(i).getText();
			String deptTime = e.getDeptTime().get(i).getText();
			String duration = e.getDuration().get(i).getText();
			String amount = e.getAmount().get(i).getText();
			UtilFunctions.writeData(flightName, deptTime, duration, amount, rowNum);
			rowNum++;
		}
	}

	public static void writeData(String flightName, String deptTime, String duration, String amount, int rowCount)
			throws Throwable {
		File f = new File(System.getProperty("user.dir")
				+ "\\src\\test\\resource\\com\\makemytrip\\flightdetails\\Flight Details.xlsx");
		FileInputStream fis = new FileInputStream(f);
		Workbook wb = new XSSFWorkbook(fis);

		Sheet sheetAt = wb.getSheet("Flight_Details");
		Row createRow = sheetAt.createRow(rowCount);

		Cell c0 = createRow.createCell(0);
		c0.setCellValue(flightName);
		Cell c1 = createRow.createCell(1);
		c1.setCellValue(deptTime);
		Cell c2 = createRow.createCell(2);
		c2.setCellValue(duration);
		Cell c3 = createRow.createCell(3);
		c3.setCellValue(amount);

		FileOutputStream fos = new FileOutputStream(f);
		wb.write(fos);
		wb.close();
	}

}
