package Util;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import cn.cqupt.entity.UserToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import net.sf.json.JSONObject;
public class JavaWebToken {
	private String sunyushan;
	public SecretKey generalKey(){
        String stringKey = sunyushan+Constant.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
public  String createJWT(String id, String subject, long ttlMillis) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey key = generalKey();
        JwtBuilder builder = Jwts.builder()
            .setId(id)
            .setIssuedAt(now)
            .setSubject(subject)
            .signWith(signatureAlgorithm, key);
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }
    
    /**
     * 解密jwt
     * @param jwt
     * @return
     * @throws Exception
     */
    public Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()         
           .setSigningKey(key)
           .parseClaimsJws(jwt).getBody();
        return claims;
    }
    
    /**
     * 生成subject信息
     * @param user
     * @return
     */
    public static String generalSubject(UserToken user){
        JSONObject jo = new JSONObject();
        jo.put("accountId", user.getAccountId());
        jo.put("userName", user.getUserName());
        jo.put("accountName",user.getAccountName());
        jo.put("accountPwd",user.getAccountPwd());
        jo.put("userEmail", user.getUserEmail());
        jo.put("userTel",user.getUserTel());
        jo.put("roleName",user.getRoleName());
        jo.put("roleId",user.getRoleId());
        jo.put("rolePerm",user.getRolePerm());
        jo.put("depId",user.getDepId());
        jo.put("depName", user.getDepName());
        return jo.toString();
    }	
}
