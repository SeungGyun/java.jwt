
package com.ilhsk.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
/*
 * https://github.com/jwtk/jjwt
 */
public class JjwtSample {

	public static String createToken(SecretKey key) {

		Map<String, Object> headers = new HashMap<>();
		headers.put("typ", "JWT");
		headers.put("alg", "HS256");

		Map<String, Object> payloads = new HashMap<>();
		Long expiredTime = 1000 * 60l; // 만료기간 1분
		Date now = new Date();
		now.setTime(now.getTime() + expiredTime);
		payloads.put("exp", now);
		payloads.put("data", "hello world!");

		String jwt = Jwts.builder().setHeader(headers).setClaims(payloads).signWith(key).compact();

		return jwt;
	}

	public static void getTokenFromJwtString(String jwtTokenString,SecretKey key) throws InterruptedException {
	    Claims claims =  Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwtTokenString).getBody();
	    
	    Date expiration = claims.get("exp", Date.class);
	    System.out.println(expiration);
	    String data = claims.get("data", String.class);
	    System.out.println(data);
	}
	
	public static void main(String[] args) throws InterruptedException {
		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		String jwtString = createToken(key);
		System.out.println(jwtString);
		getTokenFromJwtString(jwtString, key);

	}

}
