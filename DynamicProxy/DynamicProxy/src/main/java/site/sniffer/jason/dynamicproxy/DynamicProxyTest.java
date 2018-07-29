package site.sniffer.jason.dynamicproxy;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * dynamic proxy examples
 * the proxyed class should be an interface!!!
 */
public class DynamicProxyTest 
{
    public static void main( String[] args )
    {	
    	Set setProxy = (Set) Proxy.newProxyInstance(DynamicProxyTest.class.getClassLoader(), 
    			new Class[]{Set.class}, new DynamicInvocationHandler(new HashSet<String>()));
    	
    	setProxy.add("hello world");
    	System.out.println("current item" + setProxy.size());
    	
    	CharSequence strProxy = (CharSequence) Proxy.newProxyInstance(DynamicProxyTest.class.getClassLoader(), 
    				new Class[]{CharSequence.class}, new DynamicInvocationHandler("hi, world"));
    	
    	System.out.println("string length=" + strProxy.length());
    }
}
