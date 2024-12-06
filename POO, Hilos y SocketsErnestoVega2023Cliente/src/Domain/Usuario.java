/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.io.Serializable;

/**
 *
 * @author Ernesto
 */
public class Usuario implements Serializable{
   
    private String nombreUsuario;
    private String contrasenia;

    public Usuario() {
        this.nombreUsuario = "";
        this.contrasenia = "";
    }//constructor

    //mrtodo de acceso
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

}//class
