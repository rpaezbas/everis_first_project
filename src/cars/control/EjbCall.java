package cars.control;

import javax.ws.rs.core.Response;

//This interface is the definition for a lambda expression. In the class Rest_v2, the method verifyAndCallEJB 
//takes as last argument a lambda expression whose type is defined by this interface. In this class that function is defined anonymously 
//like in Javascript
public interface EjbCall {
	Response call();
}
