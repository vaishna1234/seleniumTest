package util;

public class ExcelDemo {
	
	public static void main(String[] args) 
		{
			String path=System.getProperty("user.dir");
			ExcelUtils ex=new ExcelUtils(path+"\\excel\\Data.xlsx", "Sheet1" );
			
		ex.getRowCount();
		ex.getCellDataString(0, 0);
		ex.getCellDataString1(1, 1);
		
		
		
		}

	}
