package site.sniffer.jason.DataFormatExamples;

import java.text.DecimalFormat;
import java.text.ParseException;

/**
 * Hello world!
 *
 */
public class DataFormat 
{
    public static void main( String[] args )
    {
    	DecimalFormat df = new DecimalFormat("+0.02;-0.02");
    	
    	DecimalFormat df1 = new DecimalFormat("0.02");
    	
    	float f1 = (float) -23.323;
    	
    	String f2 = "+43";
    	
    	float f3 = (float) 120.12;
    	
        System.out.println(df.format(f1));
        
		System.out.println(df.format(Float.parseFloat(f2)));
		
		System.out.println(df.format(f3));
		
		System.out.println(df1.format(f3));
		
		// string replacing
		String str = "this word has been fucked";
		String re = str.replace("t", "$");
		System.out.println(re);
		
		System.out.println(Float.parseFloat("0.00"));
    }
}
