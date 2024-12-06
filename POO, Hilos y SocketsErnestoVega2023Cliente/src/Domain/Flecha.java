/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.Disenio;
import Utility.GestionXML;
import java.awt.Graphics;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ernesto
 */
public class Flecha extends Arma {

    private final String creador;
    private final int direccionX;
    private final int direccionY;
    private double disX;
    private double disY;
    private final double velocidad;
    private boolean enJuego;

    public Flecha(String creador, Punto2D posicion, int direccionX, int direccionY,double disX,double disY) {
        this.creador = creador;
        this.posicion = posicion;
        this.danio = 5;
        this.direccionX = direccionX;
        this.direccionY = direccionY;
        this.disX=disX;
        this.disY=disY;
        this.velocidad = 0.5;
        this.enJuego = true;
        this.skinSegunDireccion();
    }//constructor

    private void skinSegunDireccion() {
        try {
            //carga la imagen corecpondiente segun la direccion
            if (this.direccionX == -1) {
                this.skin = ImageIO.read(getClass().getResource("/assets/flechaIzqui.png"));
            } else if (this.direccionX == 1) {
                this.skin = ImageIO.read(getClass().getResource("/assets/flechaDer.png"));
            } else if (this.direccionY == -1) {
                this.skin = ImageIO.read(getClass().getResource("/assets/flechaArriba.png"));
            } else if (this.direccionY == 1) {
                this.skin = ImageIO.read(getClass().getResource("/assets/flechaAbajo.png"));
            }
        } catch (IOException ex) {
            Logger.getLogger(Flecha.class.getName()).log(Level.SEVERE, null, ex);
        }//trata de cargar la imagen
    }//muestra la skin segun la direccion

    //metodos de acceso
    public boolean isEnJuego() {
        return enJuego;
    }

    public void setEnJuego(boolean enJuego) {
        this.enJuego = enJuego;
    }

    public int getDireccionX() {
        return direccionX;
    }

    public int getDireccionY() {
        return direccionY;
    }

    public String getCreador() {
        return creador;
    }

    public double getDisX() {
        return disX;
    }

    public double getDisY() {
        return disY;
    }

    private void mover() {
        //calcula la distancia de la flecha respecto al mapa
        this.disX += (this.velocidad * this.direccionX);
        this.disY += (this.velocidad * this.direccionY);
        //calcula la casilla por la que el jugador quiere pasar
        int inColumna = Math.floorDiv((int) this.disX, Disenio.CASILLA);
        int inFila = Math.floorDiv((int) this.disY, Disenio.CASILLA);
        //calcula como tal la nueva posicion de la flecha
        double posXAux = JuegoSingleton.getInstance().getMapa().getO() + this.disX;
        double posYAux = JuegoSingleton.getInstance().getMapa().getN() + this.disY;
        //validaciones necesarias
        if (inColumna == JuegoSingleton.getInstance().getMapa().getColumnas()) {
            inColumna--;
        } else if (inFila == JuegoSingleton.getInstance().getMapa().getFilas()) {
            inFila--;
        }//verifica que la flecha no solicite una casilla invalida
        if (inColumna < 0) {
            inColumna++;
        } else if (inFila < 0) {
            inFila++;
        }//verifica que la flecha no solicite una casilla invalida
        //establece las nuevas posciones
        this.posicion.x = posXAux;
        this.posicion.y = posYAux;
        if (!JuegoSingleton.getInstance().getMapa().getCasillas()[inFila][inColumna].aceptarDiamante()) {
            this.enJuego = false;
        }//verifica si la casilla de paso actual es accesible para la flecha 
    }//metodo de movimiento de la fecha

    private void verificarLimtes() {
        if (this.posicion.getX() < JuegoSingleton.getInstance().getMapa().getO()) {
            this.enJuego = false;
        } else if (this.posicion.getY() < JuegoSingleton.getInstance().getMapa().getN()) {
            this.enJuego = false;
        }else if (this.posicion.getX() > JuegoSingleton.getInstance().getMapa().getE()) {
            this.enJuego = false;
        } else if (this.posicion.getY() > JuegoSingleton.getInstance().getMapa().getS()) {
            this.enJuego = false;
        }//valida que la flecha no abandone el campo de juego
    }//verifica que la flecha este dentro del mapa

    @Override
    public void realizarDanio(Personaje personaje) {//verifica si esta flecha choco con el jugador
        //pregunta si el jugador del clente resibe el danio o un oponente para desparecer la flecha
        if (personaje.getColicion().contains(this.posicion)
                && !JuegoSingleton.getInstance().getJugadorCliente().getNombreUsuario().equals(this.creador)) {

            personaje.resivirDanio(this);
            ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.robarDiamantes(Solicitud.ROBAR_DIAMANTES.name(),
                    this.creador, personaje.robarDiamantes(1))));
            this.enJuego = false;
        } else if (JuegoSingleton.getInstance().colicionArmaOponente(this)) {//valida que la fecha desaparezca al chocar con un opnente
            this.enJuego = false;
        }
    }//causa danio a un personaje

    @Override
    public void dibujar(Graphics g) {
        this.verificarLimtes();
        this.mover();
        g.drawImage(this.skin, (int) this.posicion.getX(), (int) this.posicion.getY(), null);
        this.realizarDanio(JuegoSingleton.getInstance().getJugadorCliente().getPersonaje());
    }//metodo de dibujado

}//class
