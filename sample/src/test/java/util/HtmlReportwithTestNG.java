package util;


	import org.openqa.selenium.By;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.firefox.FirefoxDriver;
	import org.testng.annotations.AfterSuite;
	import org.testng.annotations.AfterTest;
	import org.testng.annotations.BeforeSuite;
	import org.testng.annotations.BeforeTest;
	import org.testng.annotations.DataProvider;
	import org.testng.annotations.Test;

	import com.aventstack.extentreports.ExtentReports;
	import com.aventstack.extentreports.ExtentTest;
	import com.aventstack.extentreports.MediaEntityBuilder;
	import com.aventstack.extentreports.Status;
	import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

	public class HtmlReportwithTestNG {
		static  ExtentHtmlReporter htmlReporter;
		static ExtentReports extent;
	private static 	WebDriver driver;
	static ExtentTest test;

		@BeforeSuite
		public static void setUp()
		{
			
			htmlReporter=new ExtentHtmlReporter("HtmlReport.html");
			//create extend report and attach reporter
			extent=new ExtentReports();
			extent.attachReporter(htmlReporter);
			
		}

		@BeforeTest
		public void setTest()
		{
			String path=System.getProperty("user.dir");
			System.setProperty("webdriver.gecko.driver",path+ "\\drivers\\geckodriver\\geckodriver.exe");
			driver=new FirefoxDriver();
		}

		@Test(dataProvider = "test1")
		public void test1(String username,String password) throws Exception
		{
			System.out.println(username+" "+password);

			ExtentTest test=extent.createTest("1st Test","Sample Description");

			test.log(Status.INFO, "This step shows usages of log(status,details)");
			test.info("Step goes to flipcart login");

			driver.get("https://www.flipkart.com/");

			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[1]/input")).sendKeys(username);
			

			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[2]/input")).sendKeys(password);

			driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[3]/button")).click();
			Thread.sleep(2000);
			//test.fail("details",MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());
			test.pass("details",MediaEntityBuilder.createScreenCaptureFromPath("screenshot.png").build());
			//test with snapshot
			test.addScreenCaptureFromPath("screenshot.png");
		}

		@DataProvider(name= "test1")

		public  Object[][] getData()
		{
			String excelPath="D:\\My_Workspace\\sample\\excel\\Data.xlsx";
			Object data[][]= testData(excelPath,"Sheet1");
			return data;
		}


		public Object[][] testData(String excelPath, String sheetName)
		{
			ExcelUtils excel=new ExcelUtils(excelPath, sheetName);
			int rowCount=	excel.getRowCount();
			int colCount=excel.getColCount();

			Object data[][]=new Object[rowCount-1][colCount];

			for(int i=1;i<rowCount;i++)
			{
				for(int j=0;j<colCount;j++)
				{
					String cellData=excel.getCellDataString(i, j);
					//System.out.println(cellData+" ");
					data[i-1][j]=cellData;
				}
			}
			return data;
		}

		
		@AfterSuite
		public void afterMethod()
		{ 
			extent.flush();
			driver.close();
			driver.quit();
			System.out.println("Test Successful");
		}



		@AfterTest
		public void tearDown()
		{
			extent.flush();
		}
		


	}

