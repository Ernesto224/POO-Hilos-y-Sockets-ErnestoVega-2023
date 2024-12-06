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
public class CasillaCesped extends Casilla {

    public CasillaCesped(Punto2D posicion) {
        super(posicion);
        this.limitacionMovimiento = 0.75;
        try {
            this.textura = ImageIO.read(getClass().getResource("/assets/textura-hierba-cesped.jpg"));
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No puedo cargar esta imagen");
        }//trata de cargar la imagen
    }//constructor

    @Override
    public void aplicarEfecto(Personaje personaje) {
        personaje.setVelocidadMovimiento(personaje.getVelocidadPorDefecto()*this.limitacionMovimiento);
    }//el efecto del agua es reducir en un 50% el movimiento
    
    @Override
    public int retornarTipo() {
       return ElementoMapaFabrica.CESPED;
    }

}//class
