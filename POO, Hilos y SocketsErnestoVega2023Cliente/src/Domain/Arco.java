/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.GestionXML;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ernesto
 */
public class Arco extends Arma {

    public Arco() {
        try {
            this.skin = ImageIO.read(getClass().getResource("/assets/arco.png"));
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No puedo cargar esta imagen");
        }//trata de cargar la imagen
    }//constructor

    public void lazarFlecha(Personaje personaje) {
        //notifica al servidor que se lanzo una flecha
        double disX = JuegoSingleton.getInstance().getMapa().retornarDistanciaXArma(this);
        double disY = JuegoSingleton.getInstance().getMapa().retornarDistanciaYArma(this);
        Flecha flecha = new Flecha(JuegoSingleton.getInstance().getJugadorCliente().getNombreUsuario(),
                new Punto2D(this.posicion.getX(), this.getPosicion().getY()),
                personaje.getDireccionX(), personaje.getDireccionY(),
                 disX, disY);
        JuegoSingleton.getInstance().lazarFlecha(flecha);//agrega al juego del cleinte la flecha 
        ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.enviarFlecha(Solicitud.FLECHA_LANZADA.name()
                , flecha)));//establece la doireccion de la flecha
        //crea una nueva flecha
    }//metodo que lanza una fecha cuando se indica

    @Override
    public void realizarDanio(Personaje personaje) {
    }

}//class
