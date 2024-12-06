/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.awt.Graphics;

/**
 *
 * @author Ernesto
 */
public class Jugador {

    private final String nombreUsuario;
    private Personaje personaje;

    public Jugador(String nombreUsuario) {
        this.nombreUsuario=nombreUsuario;
        this.personaje = new Personaje(new Punto2D(380, 300));
    }//constructor

    //metodos de acceso
    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void dibujar(Graphics g) {
        this.personaje.dibujar(g);
    }//metodo de dibujado

}//class
