package site.sniffer.jason.selenium;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Hello world!
 *
 */
public class SeleniumTest 
{
    public static void main( String[] args )
    {
        System.setProperty("webdriver.gecko.driver","/home/jason/Downloads/geckodriver");
        WebDriver webDriver = new FirefoxDriver();
    	//System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
        webDriver.get("http://finance.sina.com.cn/stock/usstock/sector.shtml");
        
        String html = webDriver.getPageSource();
        
        System.out.println(html);
        new Thread(new FileSaveRunnable("tmp/page.txt", html))
        .start();
    }
    
    
    static class FileSaveRunnable implements Runnable{
    	private String mFileName;
    	private byte[] mData;
    	
    	
    	public FileSaveRunnable(String fn, String data){
    		
            File file = new File(fn);
            if(!file.exists()){
            	file.mkdirs();
            }
            
            if(!file.canWrite()){
            	file.setWritable(true);
            }
            
            if(data != null){
            	mData = data.getBytes();
            }
    	}

		public void run() {
			
			if(mData != null){
				
				try {
					FileOutputStream fos = new FileOutputStream(mFileName);
					fos.write(mData);
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
    	
    }
}
