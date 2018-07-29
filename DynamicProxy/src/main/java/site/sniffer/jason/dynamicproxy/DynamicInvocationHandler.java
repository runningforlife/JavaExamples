package site.sniffer.jason.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicInvocationHandler implements InvocationHandler {
	private Object mTarget;
	//private Map<String, Method> mMethods;
	
	public DynamicInvocationHandler(Object target) {
		this.mTarget = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = method.invoke(mTarget, args);
		System.out.println("method: " + method.getName() + " is invoked");
		System.out.println("method type:" + result.getClass());
	
		return result;
	}
}
