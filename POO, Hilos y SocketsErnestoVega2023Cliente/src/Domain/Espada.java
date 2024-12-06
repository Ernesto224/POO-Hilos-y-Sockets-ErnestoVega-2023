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
public class Espada extends Arma {

    public Espada() {
        this.danio = 10;
        try {
            this.skin = ImageIO.read(getClass().getResource("/assets/espada.png"));
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No puedo cargar esta imagen");
        }//trata de cargar la imagen
    }//constructor

    public void espadaso(Personaje personaje) {
        //envia los datos necesarios para notificar el espadazo principalmente el 
        //punto de golpe 
        double posX = JuegoSingleton.getInstance().getMapa().retornarDistanciaXArma(this);
        double posY = JuegoSingleton.getInstance().getMapa().retornarDistanciaYArma(this);

        //envia al servidor la informacion
        ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.enviarEspadaso(Solicitud.NOTIFICAR_ESPADAZO.name(),
                 JuegoSingleton.getInstance().getJugadorCliente().getNombreUsuario(), posX, posY)));
    }//metodo para saber si una espada golpea a otro jugador

    @Override
    public void realizarDanio(Personaje personaje) {
        personaje.resivirDanio(this);
    }//metodo para relizar danio a un jugador

}//class
