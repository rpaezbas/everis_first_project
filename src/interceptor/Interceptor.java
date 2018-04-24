package interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class Interceptor {

	@AroundInvoke
	public Object Greetings(InvocationContext ctx) throws Exception {
		//ctx.getMethod().getName();
		//Before return verify
		//System.out.println(ctx.getParameters()[0]);
		//System.out.println(ctx.getParameters()[1]);
		return ctx.proceed();
	}

}
