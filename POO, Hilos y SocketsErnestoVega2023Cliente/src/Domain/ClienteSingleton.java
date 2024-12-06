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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Laboratorios
 */
public class ClienteSingleton extends Thread {
    
    private static ClienteSingleton clienteSingleton;
    private Socket conexion;
    private PrintStream escritorPaquetes;
    private BufferedReader leectorPaquetes;
    private Usuario usurioSession;
    private Jugador jugadorCliente;
    private volatile boolean sessionEjecutando;
    
    private ClienteSingleton() {
        try {
            this.conexion = new Socket("localhost", 5000);
            this.escritorPaquetes = new PrintStream(this.conexion.getOutputStream());
            this.leectorPaquetes = new BufferedReader(new InputStreamReader(this.conexion.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(ClienteSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }//trate de cargar estos elemetos
        this.sessionEjecutando = true;
    }//constructor

    public static ClienteSingleton getInstance() {
        if (clienteSingleton == null) {
            clienteSingleton = new ClienteSingleton();
        }//metodo get intance
        return clienteSingleton;
    }//metodo sigleton

    //metodos de acceso
    public Usuario getUsurioSession() {
        return usurioSession;
    }
    
    public void setUsurioSession(Usuario usurioSession) {
        this.usurioSession = usurioSession;
        this.jugadorCliente=new Jugador(this.usurioSession.getNombreUsuario());
    }
    
    public Jugador getJugadorCliente() {
        return jugadorCliente;
    }
    
    public void setSessionEjecutando(boolean sessionEjecutando) {
        this.sessionEjecutando = sessionEjecutando;
    }
    
    public void enviarDatos(String solicitud) {
        this.escritorPaquetes.println(solicitud);
    } //envia una accion a realizar y la informacion den cuestion

    public String resivirDatos() throws IOException {
        return this.leectorPaquetes.readLine();
    }//resive los datos que le envia el servidor 

    public void cerrarFlujo() {
        try {
            this.escritorPaquetes.close();
            this.leectorPaquetes.close();
            this.conexion.close();
        } catch (IOException ex) {
            Logger.getLogger(ClienteSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//cierra todos los objetos del flujo de datos

    @Override
    public void run() {
        while (this.sessionEjecutando) {
            try {
                Element respuesta = GestionXML.stringToXML(this.resivirDatos());
                System.out.println(GestionXML.xmlToString(respuesta));
                String accion = respuesta.getAttributeValue("accion");
                ProtocoloCliente.valueOf(accion).ejecutarProtocolo(respuesta);
            } catch (IOException ex) {
                Logger.getLogger(ClienteSingleton.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println(ex.fillInStackTrace());
            } catch (JDOMException ex) {
                Logger.getLogger(ClienteSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }
            //exepcion
        }//matiene la comunicacion avierta
        System.exit(0);
    }//metodo heredado

}//class
