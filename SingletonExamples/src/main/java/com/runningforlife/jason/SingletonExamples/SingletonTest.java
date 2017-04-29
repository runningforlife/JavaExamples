package com.runningforlife.jason.SingletonExamples;

/**
 * Hello world!
 *
 */
public class SingletonTest 
{
    public static void main( String[] args )
    {
        System.out.println( "singleton checking..." );
        
        SimpleThreadFactory factory = new SimpleThreadFactory();
        // start 10 threads
        for(int i = 0; i < 10; ++i){
        	Thread t = factory.newThread(new SingletonChecking());
        	t.start();
        }
    }
    
    
    private static class SingletonChecking implements Runnable{

		public void run() {
			SimpleSingletonUnsafe s1 = SimpleSingletonUnsafe.getInstance();
			checkNull(s1);
			
			SimpleSingletonSafe s2 = SimpleSingletonSafe.getInstance();
			checkNull(s2);
			
			LazySingletonUnsafe s3 = LazySingletonUnsafe.getInstance();
			checkNull(s3);
			
			LazySingletonSafe s4 = LazySingletonSafe.getInstance();
			checkNull(s4);
			
			SingletonHolder s5 = SingletonHolder.getInstance();
			checkNull(s5);
			
		}
    	
		
		private void checkNull(Object o){
			if(o == null){
				System.out.println("Fail to get instance of " + o + " for current thread: " + Thread.currentThread().getName());
			}else{
				System.out.println("Get instance of " + o + " for current thread: " + Thread.currentThread().getName());
			}
		}
    }
}
