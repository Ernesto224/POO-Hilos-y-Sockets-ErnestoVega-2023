/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author Ernesto
 */
public class Jugador {

    private String personaje;
    private String posX;
    private String posY;
    private int puntosVida;
    private int diamantes;

    public Jugador(String personaje, String posX, String posY) {
        this.personaje = personaje;
        this.posX = posX;
        this.posY = posY;
        this.puntosVida = 100;
        this.diamantes = 0;
    }//constructor

    public Jugador(String posX, String posY, int puntosVida) {
        this.posX = posX;
        this.posY = posY;
        this.puntosVida = puntosVida;
    }//constructor 2

    //metodos de acceso
    public String getPersonaje() {
        return personaje;
    }

    public void setPersonaje(String personaje) {
        this.personaje = personaje;
    }

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }

    public int getDiamantes() {
        return diamantes;
    }

    public void setDiamantes(int diamantes) {
        this.diamantes = diamantes;
    }
    
    public void sumarDiamante() {
        this.diamantes++;
    }

    public void restarDiamante(int cantidad) {
        this.diamantes -= cantidad;
        if (this.diamantes == 0) {
            this.diamantes = 0;
        }//resta los diamantes al jugador
    }

}//class
