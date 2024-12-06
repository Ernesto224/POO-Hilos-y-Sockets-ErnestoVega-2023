/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package Domain;

import Utility.GestionXML;
import java.awt.Graphics;
import java.util.ArrayList;

/**
 *
 * @author Ernesto
 */
public class JuegoSingleton {
    
    private static JuegoSingleton INSTANCE;
    private Jugador jugadorCliente;
    private ArrayList<Oponente> oponentes;
    private final ArrayList<Flecha> flechas;
    private Mapa mapa;
    
    public static JuegoSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new JuegoSingleton();
        }//crea la primera instacia
        return INSTANCE;
    }//metodo get instance

    private JuegoSingleton() {
        this.jugadorCliente = ClienteSingleton.getInstance().getJugadorCliente();
        this.mapa = null;
        this.flechas = new ArrayList<>();
    }//constructor

    //metodos de acceso
    public Jugador getJugadorCliente() {
        return jugadorCliente;
    }
    
    public void setJugadorCliente(Jugador jugadorCliente) {
        this.jugadorCliente = jugadorCliente;
    }
    
    public ArrayList<Oponente> getOponentes() {
        return oponentes;
    }
    
    public void setOponentes(ArrayList<Oponente> oponentes) {
        this.oponentes = oponentes;
    }
    
    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }
    
    public Mapa getMapa() {
        return mapa;
    }
    
    public void restablecerJuego(){
      this.mapa=null;
      this.oponentes=null;
      this.jugadorCliente.getPersonaje().reiniciarDatos();
    }//metodo que reinicia el juego para otra partida
    
    public void notificarVictoria() {
        if (!this.verificarGanador()) {
            //notifica la victoria al jugador
            ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(
                    GestionXML.agregarAccionSimple(Solicitud.NOTIFICAR_VICTORIA.name(),
                     String.valueOf(this.jugadorCliente.getPersonaje().getDiamantes()))));            
        }
    }//envia al servidor una verificacion de que este jugador gano la partida
    
    public void notificarEspadazo(String creador,double disX,double disY) {
        //calcula las posiciones del espadazo respecto al mapa de juego
        double posX=this.mapa.getO()+disX;
        double posY=this.mapa.getN()+disY;
        this.jugadorCliente.getPersonaje().verificarGolpe(creador,new Punto2D(posX, posY));
    }//metodo para notificar la accion de espadazo

    public void lazarFlecha(Flecha flecha) {
        this.flechas.add(flecha);
    }//metodo que lanza una fecha cuando se indica

    //verifica la poscion con todos los elemtos posibles
    public boolean verificarColicion(Personaje personaje) {
        for (Oponente oponente : oponentes) {
            if (oponente.colicion(personaje) && oponente.isVivo()) {
                return false;
            }//si el oponente coliciona no se permite el acceso
        }//verifica si hay un oponente en el camino
        return this.mapa.accederSiguiente(personaje);
    }//verifica la colicion de un personaje
    
    public void tomarCasilla(Personaje personaje){
      this.mapa.tomarCasilla(personaje);
    }//coloca la casilla deseada por el jugador

    public boolean verificarGanador() {
        for (Oponente oponente : oponentes) {
            if (oponente.isVivo()) {
                return true;
            }//verifica si queda algun oponente con vida
        }
        return false;
    }//verifica cuantos oponentes siguen activos

    public void actualizarInformacion() {
        ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Solicitud.ACTUALIZAR_ESTADO.name(),
                GestionXML.actualizarInformacionJugador(this.jugadorCliente, this.mapa))));
    }//metodo para actualizar la informacion del jugador que gano el juego en el achivo

    public void actualizarInformacionOponente(String nombre, double disX, double disY) {
        for (Oponente oponente : oponentes) {
            if (oponente.getNombreUsurio().equals(nombre)) {
                oponente.setDisX(disX);
                oponente.setDisY(disY);
            }//actualiza al oponente en cuestion
        }//recorre la lista de oponentes
    }//metodo para actualizar la informacion del jugador que gano el juego en el achivo

    public void oponenteMurio(String nombreJugadorMuerto) {
        for (Oponente oponente : oponentes) {
            if (oponente.getNombreUsurio().equals(nombreJugadorMuerto)) {
                oponente.setVivo(false);
            }//actualiza al oponente en cuestion
        }//recorre la lista de oponentes
        this.notificarVictoria();
    }//cuando un oponente muere se cambia su estado de vida a falso
    //este desaparecera del campo de juego y ya noresivira danio ni afectara las coliciones
    
    public boolean colicionArmaOponente(Arma arma){
       for (Oponente oponente : oponentes) {
            if (oponente.colcionConArma(arma) && oponente.isVivo()) {
                return true;
            }//si el oponente coliciona no se permite el acceso
        }//verifica si hay un oponente en el camino
       return false;
    }//verifica si una arma coliciona con un oponente 

    public void dibujarJuego(Graphics g) {
        if (this.mapa!=null) {
         this.mapa.dibujar(g);   
        }//dibuja el mapa de juego en cuestion
        if (!this.flechas.isEmpty()) {
            for (int i = 0; i < this.flechas.size(); i++) {
                if (this.flechas.get(i).isEnJuego()) {
                    this.flechas.get(i).dibujar(g);
                } else {
                    this.flechas.remove(i);
                }//si la felcha sale del campo de juego esta es elimnida de la lista   
            }//recorre las flechas para dibujar
        }//verifica si hay flechas que dibujar
        if (this.oponentes != null && !this.oponentes.isEmpty()) {
            for (Oponente oponente : this.oponentes) {
                if (oponente.isVivo()) {
                   oponente.dibujar(g);    
                }//si el oponente sigue en juego lo dibuja sino desparece de la partida     
            }//recorre las flechas para dibujar
        }//verifica si hay flechas que dibujar
        this.jugadorCliente.dibujar(g);
    }//metodo de dibujado del juego

}//class 5/20/2023
