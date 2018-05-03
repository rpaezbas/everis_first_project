package utils;


import com.auth0.client.auth.AuthAPI;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import Logger.Log;

public class AuthUtil {

	static final String clientKey = "7q8Owacjr2xKCOE7YwRnUBPwG7LJmRP31abbFPR-3oS7FAJOCSddJEybtjHK6Wcf";
	static final String domain = "rpaezbas.auth0.com";
	static final String clientId = "x6mEq1xqWkr730EKMD43N7gY227CZmpe";
	public final static AuthAPI auth = new AuthAPI(domain, clientId, clientKey);
	public final static String requiredIssuer = "https://rpaezbas.auth0.com/";
	public final static String metadataClaim = "https://rp.com/user_metadata";
	
	
	//This is the core function of this Class, it uses every other function in the class
	public static boolean verifyRoleInToken(String rawHeader, String requiredRole) {
		
		Log.logger.info("Enters AuthUtil.verifyRoleInToken");

		boolean authorized = false;
		String idToken = extractTokenFromHeader(rawHeader);

		try {
			// If the token doesnt verify, this will throw an exception, and authorized will
			// be false
			String rol = getTokenPermissions(idToken);

			switch (requiredRole) {
			case "user":
				if (rol.equals("user") || rol.equals("admin")) {
					authorized = true;
				}
				break;
			case "admin":
				if (rol.equals("admin")) {
					authorized = true;
				}
				break;
			}

		} catch (Exception e) {
			Log.logger.info(e.getMessage());
		}
		
		Log.logger.info("Exits AuthUtil.verifyRoleInToken");

		return authorized;
	}
	

	// Since the token comes preceded by the word Bearer it has to be divided
	public static String extractTokenFromHeader(String rawHeader) {
		
		Log.logger.info("Enters AuthUtil.extractTokenFromHeader");
		
		String idToken = null;
		if (rawHeader != null && rawHeader.length() > 0) {
			String[] dividedHeader = rawHeader.split(" ");
			idToken = dividedHeader[1];
		}
		
		Log.logger.info("Exits AuthUtil.extractTokenFromHeader");
		
		return idToken;
	}

	// Decode the token and verify that the signature is right
	public static DecodedJWT getDecodedJWT(String idToken) throws Exception {
		
		Log.logger.info("Enters AuthUtil.getDecodedJWT");
		
		Algorithm algorithm = Algorithm.HMAC256(AuthUtil.clientKey);
		JWTVerifier verifier = JWT.require(algorithm).withIssuer(requiredIssuer).build();
		
		Log.logger.info("Exits AuthUtil.getDecodedJWT");
		
		return verifier.verify(idToken);
	}

	// Extract the metadata from the token
	public static String getTokenPermissions(final String idToken) throws Exception {
		
		Log.logger.info("Enters AuthUtil.getTokenPermissions");
		
		String rol = null;
		if (idToken != null) {
			DecodedJWT decodedJwt = getDecodedJWT(idToken);
			rol = decodedJwt.getClaims().get(metadataClaim).asMap().get("rol").toString(); 
		}
		
		Log.logger.info("Exits AuthUtil.getTokenPermissions");
		
		return rol;
	}
}
