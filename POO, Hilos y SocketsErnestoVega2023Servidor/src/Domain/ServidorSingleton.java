/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Business.MapaBusiness;
import Business.UsuarioBusiness;
import Utility.DatosRed;
import java.io.IOException;
import java.net.ServerSocket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Ernesto
 */
public class ServidorSingleton extends Thread {

    private static ServidorSingleton servidorSingleton;
    private final ServerSocket servidor;
    private final ArrayList<Partida> partidas;
    private final boolean servidorActivo;
    private final UsuarioBusiness usuarioBusines;
    private final MapaBusiness mapaBusiness;

    private ServidorSingleton() throws IOException {
        this.servidor = new ServerSocket(DatosRed.puerto);
        partidas = new ArrayList<>();
        this.servidorActivo = true;
        this.usuarioBusines = new UsuarioBusiness();
        this.mapaBusiness = new MapaBusiness();
    }//constructor

    //metodos de acceso
    public UsuarioBusiness getUsuarioBusines() {
        return usuarioBusines;
    }

    public MapaBusiness getMapaBusiness() {
        return mapaBusiness;
    }

    public static ServidorSingleton getInstance() throws IOException {
        if (servidorSingleton == null) {
            servidorSingleton = new ServidorSingleton();
        }//metodo get intance
        return servidorSingleton;
    }//metodo sigleton

    public void agregarPartida(Partida partida) {
        this.partidas.add(partida);
    }//agrega una nueva partida

    public void quitarPartida(Partida partida) {
        this.partidas.remove(partida);
    }//quita una nueva partida

    public void notificarAlResto(ClienteDelServidor clienteNotificando, Element dato) {
        for (Partida partida : this.partidas) {
            partida.getAnfitrion().notificarACreadorExcluyente(clienteNotificando, dato);
        }//llama a que todas las partidas notifiquen a su creador
    }//este metodo notifica un mensage de un jugador en especifico al resto que estan conectados al servidor

    public boolean enviarSolicitudAmistad(SolicitudAmistad solicitudAmistad) {
        for (Partida partida : partidas) {
            if (partida.getAnfitrion().getUsurioCliente().getNombre().equals(solicitudAmistad.getAddressee())) {
                partida.getAnfitrion().notificarSolicitud(solicitudAmistad); //envia la solicitud  
                return true;
            }//valida que el creador de la partida no resiva un mensage global que el envio
        }//recorre a las partida
        return false;
    }//metodo que busca al jugador al que se le desea solicitar la amistad

    public void notificarAceptacion(SolicitudAmistad solicitudAmistad) {
        for (Partida partida : partidas) {
            if (partida.getAnfitrion().getUsurioCliente().getNombre().equals(solicitudAmistad.getAddressee())) {
                partida.getAnfitrion().notificarAceptacion(solicitudAmistad); //envia la solicitud 
                break;
            }//si este es el remitente agrega la relacion de amistad
        }//recorre a las partida
    }//metodo que busca al jugador al que se le desea solicitar la amistad

    public boolean notificarAceptacionGuardada(SolicitudAmistad solicitudAmistad) {
        for (Partida partida : partidas) {
            if (partida.getAnfitrion().getUsurioCliente().getNombre().equals(solicitudAmistad.getAddressee())) {
                partida.getAnfitrion().notificarAceptacion(solicitudAmistad); //envia la solicitud 
                return true;
            }//si este es el remitente agrega la relacion de amistad
        }//recorre a las partida
        return false;
    }//metodo que busca al jugador al que se le desea solicitar la amistad

    public boolean guardarSolicitudAmistad(SolicitudAmistad solicitudAmistad) {
        Usuario auxUsuario = this.usuarioBusines.recuperarUsurio(solicitudAmistad.getAddressee());
        if (auxUsuario != null) {
            try {
                auxUsuario.agregarSolicitud(solicitudAmistad);//agrega la nueva solicitud
                this.usuarioBusines.actualizar(auxUsuario);
                return true;
            } catch (IOException | NoSuchAlgorithmException ex) {
                Logger.getLogger(ServidorSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//valida que el usuario exista
        return false;
    }//cuando un usurio no esta conectado se gurdan las loicitudes de amistad a este

    public void guardarAmistad(SolicitudAmistad solicitudAmistad) {
        Usuario auxUsuario = this.usuarioBusines.recuperarUsurio(solicitudAmistad.getAddressee());
        if (auxUsuario != null) {
            try {
                auxUsuario.agregarAmigo(new Amigo(solicitudAmistad.getSender()));//agrega la nueva solicitud
                this.usuarioBusines.actualizar(auxUsuario);
            } catch (IOException | NoSuchAlgorithmException ex) {
                Logger.getLogger(ServidorSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//valida que el usuario exista
    }//cuando un usurio no esta conectado se gurdan las loicitudes de amistad a este

    public void invitarPartida(ClienteDelServidor clienteNotificando, String nombre) {
        for (Partida partida : partidas) {
            try {
                if (partida.getAnfitrion().getUsurioCliente().getNombre().equals(nombre)) {
                    partida.invitarPartida(clienteNotificando.getUsurioCliente().getNombre());
                }//agrega un usurio a una partida
            } catch (IOException | ClassNotFoundException | JDOMException ex) {
                Logger.getLogger(ServidorSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//recorre a las partida
    }//metodo que busca al jugador al que se le desea solicitar la amistad

    public void unirPartida(ClienteDelServidor clienteNotificando, String nombre) {
        for (Partida partida : partidas) {
            try {
                if (partida.getAnfitrion().getUsurioCliente().getNombre().equals(nombre)) {
                    partida.agregarClientes(clienteNotificando);
                }//agrega un usurio a una partida
            } catch (IOException | ClassNotFoundException | JDOMException ex) {
                Logger.getLogger(ServidorSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//recorre a las partida
    }//metodo que busca al jugador al que se le desea solicitar la amistad

    public void recuperarPartida(ClienteDelServidor clienteDelServidor) {
        for (Partida partida : partidas) {
            if (partida.getAnfitrion().equals(clienteDelServidor)) {
                clienteDelServidor.setPartidaJugador(partida);
            }//verifica si se refiere al mismo cliente
        }//recorre la lista de partidas
    }//metodo para reasignar a un cliente su partida

    public boolean usuarioEnLinea(Usuario usuarioVerificar) {
        for (Partida partida : partidas) {
            //verifica mediante los nombres de usurio 
            if (partida.getAnfitrion().getUsurioCliente() != null
                    && partida.getAnfitrion().getUsurioCliente().getNombre().equals(usuarioVerificar.getNombre())) {
                return true;
            }
        }
        return false;
    }//verifica que el usuario no se conecte 2 veces

    @Override
    public void run() {
        System.out.println("Servidor iniciado esperando clientes " + this.servidor.getLocalPort());
        while (this.servidorActivo) {
            try {
                ClienteDelServidor clienteDelServidor = new ClienteDelServidor(this.servidor.accept());
                this.partidas.add(clienteDelServidor.getPartidaJugador());
                clienteDelServidor.getPartidaJugador().agregarClientes(clienteDelServidor);
                clienteDelServidor.start();
                System.out.println("Cliente aceptado");
            } catch (IOException | ClassNotFoundException | JDOMException ex) {
                Logger.getLogger(ServidorSingleton.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(Arrays.toString(ex.getStackTrace()));
            }
            //exepccion
        }//esta constantemente esperando clientes
    }//metodo heredado de thread que esta constantemente resiviendo jugadores

}//class
