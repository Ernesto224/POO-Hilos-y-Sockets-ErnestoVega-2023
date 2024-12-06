/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.GestionXML;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Ernesto
 */
public class ClienteDelServidor extends Thread {

    private final Socket conexioSocket;
    private final BufferedReader entrada;
    private final PrintStream salida;
    private Usuario usurioCliente;
    private Jugador jugadorCliente;
    private Partida partidaJugador;
    private volatile boolean sessionEjecutando;

    public ClienteDelServidor(Socket conexioSocket) throws IOException {
        this.conexioSocket = conexioSocket;
        this.entrada = new BufferedReader(new InputStreamReader(this.conexioSocket.getInputStream()));
        this.salida = new PrintStream(this.conexioSocket.getOutputStream());
        this.partidaJugador = new Partida(this);
        this.sessionEjecutando = true;
    }//constructor

    //metodos de acceso
    public Usuario getUsurioCliente() {
        return usurioCliente;
    }

    public void setUsurioCliente(Usuario usurioCliente) {
        this.usurioCliente = usurioCliente;
    }

    public Jugador getJugadorCliente() {
        return jugadorCliente;
    }

    public void setJugadorCliente(Jugador jugadorCliente) {
        this.jugadorCliente = jugadorCliente;
    }

    public Partida getPartidaJugador() {
        return partidaJugador;
    }

    public void setPartidaJugador(Partida partidaJugador) {
        this.partidaJugador = partidaJugador;
    }
    
    public void partidaNuva(){
        try {
            this.partidaJugador=new Partida(this);
            ServidorSingleton.getInstance().agregarPartida(this.partidaJugador);
        } catch (IOException ex) {
            Logger.getLogger(ClienteDelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//crea una nueva partida para el jugador
    
    public void terminarConexion() {
        this.sessionEjecutando = false;
    }//termina el subproceso

    public void cerrarFlujo() {
        try {
            this.entrada.close();
            this.salida.close();
            this.conexioSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteDelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }//trata de cerrar el flujo

    } //metodo para cerrar la conexion con el cliente

    public void actualizarUsurio() {
        try {
            ServidorSingleton.getInstance().getUsuarioBusines().actualizar(this.usurioCliente);
        } catch (IOException | NoSuchAlgorithmException ex) {
            Logger.getLogger(ClienteDelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }//captura cualqioer error
    }//actualiza la informacion de este usuario

    public void actualizarJugadorCliente(Jugador jugador) {
        this.jugadorCliente.setPuntosVida(jugador.getPuntosVida());
        this.jugadorCliente.setPosX(jugador.getPosX());
        this.jugadorCliente.setPosY(jugador.getPosY());
    }//actualiza la informacion del jugador

    public void enviar(String instruccion) {
        this.salida.println(instruccion);
    }//metodo que envia informacion al cliente del juego

    public String resivir() throws IOException {
        return this.entrada.readLine();
    }//metodo para resivir informacion desde el servidor
    
    public void actualizarDiamantes(){
        System.out.println(this.usurioCliente.getDiamantes()+"-"+this.jugadorCliente.getDiamantes());
     this.usurioCliente.setDiamantes(this.usurioCliente.getDiamantes()+this.jugadorCliente.getDiamantes());
     this.actualizarUsurio();
    }//actualiza los diamantes del jugador en el servidor y gurda la informacion

    public void notificarACreadorExcluyente(ClienteDelServidor clienteNotificando, Element dato) {
        if (!this.equals(clienteNotificando)) {
            this.enviar(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.PUBLICACION_JUGADOR.name()
                    , dato.getValue())));//envia la publicacion al cliente
        }//valida que el creador de la partida no resiva un mensage global que el envio
    }//este metodo notifica un mensage al cliente creador de la partida

    public void notificarSolicitud(SolicitudAmistad solicitudAmistad) {
         this.enviar(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.SOLICITUD_AMISTAD.name()
                 , solicitudAmistad.getSender())));//envia la publicacion al cliente
    }//este metodo notifica un mensage al cliente creador de la partida

    public void unircePartida(ClienteDelServidor clienteNotificando, Element dato) {
        if (this.usurioCliente.getNombre().equals(dato.getChild("destinatario").getValue())) {
            Element solicitudAmistad = GestionXML.agregarAccionSimple(Respuesta.SOLICITUD_AMISTAD.name()
                    , dato.getAttributeValue("remitente"));
            this.enviar(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.PUBLICACION_JUGADOR.name()
                    , solicitudAmistad)));//envia la publicacion al cliente
        }//valida que el creador de la partida no resiva un mensage global que el envio
    }//este metodo notifica un mensage al cliente creador de la partida

    public void aceptarSolicitud(Amigo amigo) throws IOException {
        this.usurioCliente.agregarAmigo(amigo);
        this.actualizarUsurio();
        ServidorSingleton.getInstance().notificarAceptacion(new SolicitudAmistad(amigo.getUserName(), this.usurioCliente.getNombre()));
        //verifica que se pueda agregar la solicitud
    }//acepta a un nuevo amigo 

    public void aceptarSolicitudGuardada(Amigo amigo) throws IOException {
        this.usurioCliente.agregarAmigo(amigo);
        this.usurioCliente.quitarSolicitud(amigo.getUserName());
        this.actualizarUsurio();
        //verifica si el usuario esta conectado para actualizar la informacion a nivel de memoriia 
        //y a nivel de archivo
        SolicitudAmistad solicitudParaRemitente=new SolicitudAmistad(amigo.getUserName(),this.usurioCliente.getNombre());
        if(!ServidorSingleton.getInstance().notificarAceptacionGuardada(solicitudParaRemitente)){
          ServidorSingleton.getInstance().guardarAmistad(solicitudParaRemitente);
        }//verifica que se pueda agregar la solicitud
    }//acepta a un nuevo amigo 
    
    public void quitarSolicitud(String nombreRemitente){
       this.usurioCliente.quitarSolicitud(nombreRemitente);
       this.actualizarUsurio();
    }//elimina una solicitud de amistad
    
    public void notificarAceptacion(SolicitudAmistad solicitudAmistad) {
        this.usurioCliente.agregarAmigo(new Amigo(solicitudAmistad.getSender()));
        this.actualizarUsurio();
        this.enviar(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.SOLICITUD_ACEPTADA.name(),solicitudAmistad.getSender())));
    }//acepta a un nuevo amigo 

    public void recargarPartida() {
        try {
            ServidorSingleton.getInstance().recuperarPartida(this);
        } catch (IOException ex) {
            Logger.getLogger(ClienteDelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//metodo que crea una partida
    
    @Override
    public void run() {
        while (this.sessionEjecutando) {
            try {
                Element peticion = GestionXML.stringToXML(this.resivir());
                System.out.println(GestionXML.xmlToString(peticion));
                String accion = peticion.getAttributeValue("accion");
                ProtocoloCliente.valueOf(accion).ejecutarProtocolo(this, peticion);
            } catch (IOException ex) {
                Logger.getLogger(ClienteDelServidor.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println(ex.fillInStackTrace());
            } catch (JDOMException ex) {
                Logger.getLogger(ClienteDelServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//resive datos y los envia aprotocolo
        try {
            //quita la partida de la lista del servidor
            ServidorSingleton.getInstance().quitarPartida(this.partidaJugador);
        } catch (IOException ex) {
            Logger.getLogger(ClienteDelServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//metodo heredado

}//class
