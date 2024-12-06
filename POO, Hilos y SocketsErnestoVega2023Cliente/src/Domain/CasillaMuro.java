/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ernesto
 */
public class CasillaMuro extends Casilla {

    public CasillaMuro(Punto2D posicion) {
        super(posicion);
        try {
            this.textura = ImageIO.read(getClass().getResource("/assets/textura-muro-piedras.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No puedo cargar esta imagen");
        }//trata de cargar la imagen
    }//constructor

    @Override
    public void aplicarEfecto(Personaje personaje) {
    }//la casilla de muro tiene la propiedad de bloquear el negarAcceso

    @Override
    public boolean negarAcceso(Personaje personaje) {
        //esta casilla siempre niega el acceso al chocar con un jugador
        return this.colicion.intersects(personaje.getColicion().getX() + (5 * personaje.getDireccionX()),
                personaje.getColicion().getY() + (5 * personaje.getDireccionY()),
                personaje.getColicion().getHeight() + (5 * personaje.getDireccionX()),
                personaje.getColicion().getHeight() + (5 * personaje.getDireccionY()));
    }//verifica si existe una interccion con una casilla

    @Override
    public boolean aceptarDiamante() {
        return false;
    }//varifica si esta casilla puede tener un diamante

    @Override
    public int retornarTipo() {
        return ElementoMapaFabrica.MURO_ROCA;
    }

}//class
