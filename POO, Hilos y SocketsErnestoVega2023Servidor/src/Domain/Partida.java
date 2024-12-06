/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.GestionXML;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Ernesto
 */
public class Partida {
    
    private int filasMapa;
    private int columnasMapa;
    private final int cantidadDiamantes;
    private int idDiamantes;
    private ClienteDelServidor anfitrion;
    private final ArrayList<Diamante> diamantes;
    private ArrayList<ClienteDelServidor> clientesEnPartida;
    private boolean tomandoDiamante;
    
    public Partida(ClienteDelServidor credosrPartida) {
        this.cantidadDiamantes = 4;
        this.idDiamantes = 0;
        this.anfitrion = credosrPartida;
        this.diamantes = new ArrayList<>();
        this.clientesEnPartida = new ArrayList<>();
        this.tomandoDiamante = false;
    }//constructor

    //metodos de aacceso
    public ClienteDelServidor getAnfitrion() {
        return anfitrion;
    }

    public void setAnfitrion(ClienteDelServidor anfitrion) {
        this.anfitrion = anfitrion;
    }
    
    public ArrayList<ClienteDelServidor> getClientesEnPartida() {
        return clientesEnPartida;
    }
    
    public void setClientesEnPartida(ArrayList<ClienteDelServidor> clientesEnPartida) {
        this.clientesEnPartida = clientesEnPartida;
    }
    
    public void unirCreadorPartida(ClienteDelServidor clienteDelServidor) {
        this.reiniciarPartida();
        this.clientesEnPartida.add(clienteDelServidor);
        clienteDelServidor.setPartidaJugador(this);
    }//une a la partida al creador de esta

    private void reiniciarPartida() {
        this.filasMapa = 0;
        this.columnasMapa = 0;
        this.idDiamantes = 0;
        this.clientesEnPartida.clear();
        this.diamantes.clear();
    }//reinica los datos de la partida
    
    public void invitarPartida(String creadorPartida) throws IOException, FileNotFoundException, ClassNotFoundException, JDOMException {
        this.anfitrion.enviar(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.INVITAR_PARTIDA.name(), creadorPartida)));
    }//metodo que resive un cliente del servidor para agregarlo a la partida

    public void agregarClientes(ClienteDelServidor clienteDelServidor) throws IOException, FileNotFoundException, ClassNotFoundException, JDOMException {
        this.clientesEnPartida.add(clienteDelServidor);
        clienteDelServidor.setPartidaJugador(this);
    }//metodo que resive un cliente del servidor para agregarlo a la partida

    public void enviarOponentes(ClienteDelServidor exclir) {
        try {
            Element oponenestes = GestionXML.enviarOponentes(Respuesta.ENVIAR_OPONENTES.name(), exclir, this.clientesEnPartida);
            exclir.enviar(GestionXML.xmlToString(oponenestes));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
        }//envia los opnentes
    }//envia todos sus oponentes a cada jugador

    public void enviarMapaJugadores(String nombreMap) {
        try {
            Mapa mapaRecuperado = ServidorSingleton.getInstance().getMapaBusiness().recuperarMapa(this.anfitrion.getUsurioCliente().getNombre(), nombreMap);
            if (mapaRecuperado != null) {
                this.filasMapa = mapaRecuperado.getFilas();
                this.columnasMapa = mapaRecuperado.getColumnasa();
                this.generarDimantes();
                this.notificarTodos(GestionXML.xmlToString(GestionXML.enviarMapa(Respuesta.ENVIAR_MAPA_JUEGO.name(), mapaRecuperado.getMapa())));
            }//verifica que el mapa exista para compartirlo con los jugadores
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//metodo envia el mapa de juego a todos los jugadores de la partida

    public void iniciarPartida(String mensaje) {
        if (this.clientesEnPartida.size() >= 2 && this.filasMapa != 0) {
            this.notificarTodos(mensaje);
        }//verifica la cantidad de jugadore minima
    }//inicia la partida

    public void notificarTodos(String instruccion) {
        for (ClienteDelServidor clienteDelServidor : clientesEnPartida) {
            clienteDelServidor.enviar(instruccion);
        }//recorre a los clientesEnPartida de la partida y les envia la instruccion
    }//metodo que notifica a todos los clientesEnPartida de la partida

    public void notificarJugador(String nombre, String instruccion) {
        for (ClienteDelServidor clienteDelServidor : clientesEnPartida) {
            if (clienteDelServidor.getUsurioCliente().getNombre().equals(nombre)) {
                clienteDelServidor.enviar(instruccion);
            }//envia una instruccion especifica a un jugador
        }//recorre a los clientesEnPartida de la partida y les envia la instruccion
    }//metodo que notifica a todos los clientesEnPartida de la partida

    public void usuarioNotificaTodos(ClienteDelServidor excluido, String instruccion) {
        for (ClienteDelServidor clienteDelServidor : clientesEnPartida) {
            if (!clienteDelServidor.equals(excluido)) {
                clienteDelServidor.enviar(instruccion);
            }//notifica a los clientesEnPartida que no sean el que envia la informacion
        }//recorre a los clientesEnPartida de la partida y les envia la instruccion
    }//metodo que notifica a todos los clientesEnPartida de la partida

    public void generarDimantes() {
        for (int i = 0; i < this.cantidadDiamantes; i++) {
            this.idDiamantes++;
            Diamante diamante = new Diamante(this.idDiamantes);
            diamante.generarPosicion(this.filasMapa, this.columnasMapa);
            this.diamantes.add(diamante);
        }//genra diamantes hasta que haya 5
    }//metodo que genera diamantes para el mapa de los jugadores

    public void recargarPoscion(int posicion) {
        this.diamantes.get(posicion).generarPosicion(this.filasMapa, this.columnasMapa);
        this.anfitrion.enviar(GestionXML.xmlToString(GestionXML.enviarDiamantes(Respuesta.VERIFICAR_DIAMANTES.name(), this.diamantes)));
    }//recarga un diamante cuya posicion no es valida

    public void enviarDiamantesVerificar(ClienteDelServidor clienteDelServidor) {
        if (this.anfitrion.equals(clienteDelServidor)) {
            this.anfitrion.enviar(GestionXML.xmlToString(GestionXML.enviarDiamantes(Respuesta.VERIFICAR_DIAMANTES.name(), this.diamantes)));
        }//verifica que primero solo se verifiquen los diamantes mediante el jugador principal
    }//metodo que verifica las posciones de los diamantes

    public void enviarDiamantes() {
        this.notificarTodos(GestionXML.xmlToString(GestionXML.enviarDiamantes(Respuesta.ENVIO_DIAMANTES.name(), this.diamantes)));
    }//metodo que verifica las posciones de los diamantes

    public synchronized void tomarDimante(ClienteDelServidor clienteDelServidor, int id) {
        
        while (this.tomandoDiamante) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
            }
        }//bloque los hilos que tratan de tomar un diamante
        this.tomandoDiamante = true;
        for (int i = 0; i < this.diamantes.size(); i++) {
            if (this.diamantes.get(i).getId() == id) {
                //primero a nivel de servidor de actualiza el diamante luego a nivel de cliente
                clienteDelServidor.getJugadorCliente().sumarDiamante();
                clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.SUMAR_DIAMANTE.name(), String.valueOf(1))));
                //se notifica atodos los cliente que ya no pueden acceder a ese diamante 
                String mensajeParaJugadores = GestionXML.xmlToString(GestionXML.eliminarDiamante(Respuesta.QUITAR_DIAMANTE.name(), this.diamantes.remove(i)));
                this.usuarioNotificaTodos(clienteDelServidor, mensajeParaJugadores);
                break;
            }//suma los diamantes necesarios
        }//recorre los diamantes para verificar si existe el buscado
        if (this.diamantes.isEmpty()) {//regeneara los diamantes
            this.generarDimantes();
            this.enviarDiamantesVerificar(this.anfitrion);
        }//verifica si puede tomar eldiamante
        this.tomandoDiamante = false;
        notifyAll();
        
    }//metodo para que un jugador pueda tomar una casilla

    public boolean determinarPerdedor(ClienteDelServidor clienteDelServidor) {
        if (clienteDelServidor.equals(this.anfitrion) && clienteDelServidor.getJugadorCliente().getPuntosVida() <= 0) {
            
            try {
                this.procesoEliminacion(clienteDelServidor);
                this.anfitrion=this.clientesEnPartida.get(0);
                clienteDelServidor.partidaNuva();
                ServidorSingleton.getInstance().quitarPartida(this);
                return true;
            } catch (IOException ex) {
                Logger.getLogger(Partida.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (clienteDelServidor.getJugadorCliente().getPuntosVida() <= 0) {
            
            this.procesoEliminacion(clienteDelServidor); 
            return true;
        }//pregunta si los puntos de vida de ese jugador llegaron 0
        return false;
    }//metodo para determinar al ganador del juego
    
    private void procesoEliminacion(ClienteDelServidor clienteDelServidor) {
        
        this.usuarioNotificaTodos(clienteDelServidor, GestionXML.xmlToString(GestionXML.agregarAccionSimple(Respuesta.ELIMINAR_JUGADOR.name(),
                clienteDelServidor.getUsurioCliente().getNombre())));
        //segun la cantida de clientesEnPartida que perdieron se retorna la continuacion o el final de la partida
        clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.JUGADOR_PERDIO.name())));
        clienteDelServidor.recargarPartida();
        this.clientesEnPartida.remove(clienteDelServidor);
    }//este metodo consiste en los pasos genericos para eliminar a un jugador de la partida
    
    public void determinarGanador(ClienteDelServidor clienteDelServidor) {
        clienteDelServidor.enviar(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Respuesta.JUGADOR_GANO.name())));
        clienteDelServidor.actualizarDiamantes();
        clienteDelServidor.recargarPartida();
        this.clientesEnPartida.remove(clienteDelServidor);
    }//metodo para determinar al ganador del juego

}//class
