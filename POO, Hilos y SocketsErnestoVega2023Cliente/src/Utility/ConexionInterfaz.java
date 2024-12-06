/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import Domain.Jugador;
import Domain.Oponente;
import GUI.JFInterfazUsuario;
import GUI.JfLoginJugador;
import java.util.ArrayList;

/**
 *
 * @author Ernesto
 */
public class ConexionInterfaz {
    
    public static JfLoginJugador jfLoginJugador;
    public static JFInterfazUsuario jFInterfazUsuario;
    
    public ConexionInterfaz() {
    }
    
    public static void setJfLoginJugador(JfLoginJugador jfLoginJugador) {
        ConexionInterfaz.jfLoginJugador = jfLoginJugador;
    }
    
    public static void setjFInterfazUsuario(JFInterfazUsuario jFInterfazUsuario) {
        ConexionInterfaz.jFInterfazUsuario = jFInterfazUsuario;
    }
    
    public static void iniciarInterfaz() {
        ConexionInterfaz.jfLoginJugador.iniciarInterfaz();
    }//metodo para desplegar la ventana de interfaz

    public static void iniciarJuego() {
        jFInterfazUsuario.abrirAreaDeJuego();
    }//metodo para desplegar la ventana de interfaz

    public static void errorRegistroInicio() {
        ConexionInterfaz.jfLoginJugador.errorRegistroInicio();
    }//metodo para desplegar la ventana de interfaz

    public static void cerrarInterfaz() {
        jFInterfazUsuario.dispose();
    }//cierra la ventana de interfaz

    public static void publicar(String informacion) {
        jFInterfazUsuario.agregarMuro(informacion);
    }//metodo para el despliegue de la informacion

    public static void solicitudListaAmigos(String[] amigos, String[] mapas) {
        jFInterfazUsuario.cargarAmigos(amigos, mapas);
    }//notifica una solicitud de amistad resibida

    public static void ranking(String[] usuariosRanking) {
        jFInterfazUsuario.desplagarRanking(usuariosRanking);
    }//notifica una solicitud de amistad resibida

    public static void desplegarJugadorPerdio() {
        jFInterfazUsuario.desplegarJugadorPerdio();
    }//cierra la ventana de juego y despliega un mensaje de jugador perdio
    
    public static void desplegarJugadorGano(Jugador jugador, ArrayList<Oponente> oponentes) {
        jFInterfazUsuario.desplegarJugadorGano(jugador, oponentes);
    }//abre la ventana de victoria

    public static void solicitudListaAmigos(String[] solicitudes) {
        jFInterfazUsuario.cargarSolicitudes(solicitudes);
    }//carga las solicitudesde amistad

}//class
