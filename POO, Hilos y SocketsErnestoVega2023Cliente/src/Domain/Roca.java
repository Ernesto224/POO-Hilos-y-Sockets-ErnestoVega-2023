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
public class Roca extends ObjetoDecorativo {

    public Roca(Punto2D posicion) {
        super(posicion);
        try {
            this.textura = ImageIO.read(getClass().getResource("/assets/roca.png"));
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No puedo cargar esta imagen");
        }//trata de cargar la imagen
    }//constructor

    @Override
    public String retornarTipo() {
        return ElementoMapaFabrica.ROCA;
    }

}//class
