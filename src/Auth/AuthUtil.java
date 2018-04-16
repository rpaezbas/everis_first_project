package Auth;

import com.auth0.client.auth.AuthAPI;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class AuthUtil {

	static final String clientKey = "7q8Owacjr2xKCOE7YwRnUBPwG7LJmRP31abbFPR-3oS7FAJOCSddJEybtjHK6Wcf";
	static final String domain = "rpaezbas.auth0.com";
	static final String clientId = "x6mEq1xqWkr730EKMD43N7gY227CZmpe";
	public final static AuthAPI auth = new AuthAPI(domain, clientId, clientKey);

	public static boolean verifyTokenInHeader(final String rawHeader) {

		boolean Authorized;

		if (rawHeader != null && rawHeader.length() > 0 ) {
			// Split the header content, example: Bearer 7s64jhsdifu8324h2kfjsdfjojlskdjflkjsdlfkjsdflsdkjf....
			String[] dividedHeader = rawHeader.split(" ");
			// Get the token
			final String idToken = dividedHeader[1];

			try {
				Algorithm algorithm = Algorithm.HMAC256(AuthUtil.clientKey);
				JWTVerifier verifier = JWT.require(algorithm).withIssuer("https://rpaezbas.auth0.com/").build();
				DecodedJWT jwt = verifier.verify(idToken);
				Authorized = true;
			} catch (Exception exception) {
				exception.printStackTrace();
				Authorized = false;
			}

			// If header is null there is not idToken
		} else {
			Authorized = false;
		}

		return Authorized;
	}
}
