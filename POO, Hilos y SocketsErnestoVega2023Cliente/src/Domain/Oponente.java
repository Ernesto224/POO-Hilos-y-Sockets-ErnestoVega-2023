/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.Disenio;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ernesto
 */
public class Oponente {

    private String nombreUsurio;
    private Punto2D posicion;
    private double disX;
    private double disY;
    private final Rectangle colicion;
    private BufferedImage avatar;
    private boolean vivo;

    public Oponente(String nombreUsurio, Punto2D posiciones) {
        this.nombreUsurio = nombreUsurio;
        this.posicion = posiciones;
        this.colicion = new Rectangle((int) this.posicion.x, (int) this.posicion.y, Disenio.PERSONAJE, Disenio.PERSONAJE);
        try {
            this.avatar = ImageIO.read(getClass().getResource("/assets/personaje1.png"));
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No puedo cargar esta imagen");
        }
        this.vivo = true;
    }//constructor

    //metodos de acceso
    public String getNombreUsurio() {
        return nombreUsurio;
    }

    public void setNombreUsurio(String nombreUsurio) {
        this.nombreUsurio = nombreUsurio;
    }

    public Punto2D getPosicion() {
        return posicion;
    }

    public void setPosicion(Punto2D posicion) {
        this.posicion = posicion;
    }

    public double getDisX() {
        return disX;
    }

    public void setDisX(double disX) {
        this.disX = disX;
    }

    public double getDisY() {
        return disY;
    }

    public void setDisY(double disY) {
        this.disY = disY;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

    public boolean isVivo() {
        return vivo;
    }
    

    public void setAvatar(String ruta) {
        try {
            this.avatar = ImageIO.read(getClass().getResource(ruta));
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No puedo cargar esta imagen");
        }
    }

    public boolean colicion(Personaje personaje) {
        //niega el acceso al chocar con un oponente
        return this.colicion.intersects(personaje.getColicion().getX() + (5 * personaje.getDireccionX()),
                personaje.getColicion().getY() + (5 * personaje.getDireccionY()),
                personaje.getColicion().getHeight() + (5 * personaje.getDireccionX()),
                personaje.getColicion().getHeight() + (5 * personaje.getDireccionY()));
    }//verifica si habra colicion con un oponete
    
    public boolean colcionConArma(Arma arma){
      return this.colicion.contains(arma.getPosicion());
    }//verifica la colicion con un arma

    private void recualcularPosicion() {
        Mapa mapa = JuegoSingleton.getInstance().getMapa();
        this.posicion.x = mapa.getO() + this.disX;
        this.posicion.y = mapa.getN() + this.disY;
        this.colicion.setLocation((int) this.posicion.getX(), (int) this.posicion.getY());
    }//recalcula la poscion del opnente segun la distancia de este

    public void dibujar(Graphics g) {
        this.recualcularPosicion();
        g.drawImage(this.avatar, (int) this.posicion.x, (int) this.posicion.y, null);
    }//metodo de dibujado

}//class
