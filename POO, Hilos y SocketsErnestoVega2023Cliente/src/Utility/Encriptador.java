/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Nelson
 */
public class Encriptador {
    
    public static String MD2="MD2";
    public static String MD5="MD5";
    public static String SHA1="SHA-1";
    public static String SHA256="SHA-256";
    public static String SHA384="SHA-384";
    public static String SHA512="SHA-512";
    
    
    private static String convertirAHexadecimal(byte[] digest){
        String hash="";
        // foreach
        for(byte aux:digest){
            int b=aux&0xff;
            if(Integer.toHexString(b).length()==1){
                hash+="0";
            }
            hash+=Integer.toHexString(b);
        } // foreach
        
        return hash;
    } // convertirAHexadecimal
    
    public static String obtenerContraseniaCifrada(String mensaje, String algoritmo) throws NoSuchAlgorithmException{
        byte[] digest=null;
        
        byte[] buffer=mensaje.getBytes();
        MessageDigest messageDigest=MessageDigest.getInstance(algoritmo);
        messageDigest.reset();
        messageDigest.update(buffer);
        digest=messageDigest.digest();
        return convertirAHexadecimal(digest);
        
    } // obtenerContraseniaSifrada
    
} // fin clase
