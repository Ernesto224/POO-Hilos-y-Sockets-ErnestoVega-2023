/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.Disenio;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ernesto
 */
public class Diamante {

    private int id;
    private final Punto2D posicion;
    private BufferedImage skin;

    public Diamante(int id, Punto2D posicion) {
        this.id=id;
        this.posicion = new Punto2D(0, 0);
        this.posicion.x = posicion.x + 20;
        this.posicion.y = posicion.y + 20;
        try {
            this.skin = ImageIO.read(getClass().getResource("/assets/diamante.png"));
        } catch (IOException ex) {
            Logger.getLogger(Casilla.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("No puedo cargar esta imagen");
        }
    }//constructor

    //metodos de acceso
    public Punto2D getPosicion() {
        return posicion;
    }

    public int getId() {
        return id;
    }
    
    public void actualizar(Punto2D posicion) {
        this.posicion.x = posicion.x + 20;
        this.posicion.y = posicion.y + 20;
    }

    public boolean jugadorTomaDiamante(Personaje personaje) {
        return personaje.getColicion().contains(this.posicion);
    }
    
    public void dibujar(Graphics g) {
        g.drawImage(this.skin, (int) this.posicion.x, (int) this.posicion.y, Disenio.DIAMANTE, Disenio.DIAMANTE, null);
    }//metodo de dibujado

}//class
