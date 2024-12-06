/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

/**
 *
 * @author Ernesto
 */
public class Validacion {

    public static boolean datoValido(String stringVerificar){
        //si el dato se encuntra vacio o es un espacio en blaco se retorna falso
        return !(stringVerificar.isEmpty() && isBlank(stringVerificar));
    }//metodo que verifica que el string no este vacio o sea un espacio en blanco
    
    public static boolean isBlank(String stringVerificar){
        for (int i = 0; i < stringVerificar.length(); i++) {
            if (!stringVerificar.substring(i).equals("")) return false;//verifica si existen valores no vacios
        }//recorre los caracteres de la cadena
        return true;
    }//verifica si el caracter esta formado por espacios vacios

}//class
