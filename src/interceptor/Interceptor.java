package interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

public class Interceptor {

	@AroundInvoke
	public Object Greetings(InvocationContext ctx) throws Exception {
		return ctx.proceed();
	}

}
