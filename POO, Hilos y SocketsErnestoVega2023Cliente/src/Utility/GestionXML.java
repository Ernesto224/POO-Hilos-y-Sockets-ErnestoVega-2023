/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utility;

import Domain.ClienteSingleton;
import Domain.Flecha;
import Domain.JuegoSingleton;
import Domain.Jugador;
import Domain.Mapa;
import Domain.Oponente;
import Domain.Punto2D;
import Domain.Solicitud;
import Domain.Usuario;
import java.io.IOException;
import java.io.StringReader;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 *
 * @author Nelson
 */
public class GestionXML {
    
    public static Element stringToXML(String stringMensaje) throws JDOMException, IOException {
        SAXBuilder saxBuilder = new SAXBuilder();
        StringReader stringReader = new StringReader(stringMensaje);
        Document doc = saxBuilder.build(stringReader);
        return doc.getRootElement();
    } // stringToXML  combierte un string en un xml

    public static String xmlToString(Element element) {
        XMLOutputter outputter = new XMLOutputter(Format.getCompactFormat());
        String xmlStringElement = outputter.outputString(element);
        xmlStringElement = xmlStringElement.replace("\n", "");
        return xmlStringElement;
    } // xmlToString convierte un xml en string

    public static Element crearMensajeProtocolo(String accion) {
        Element eProtocolo = new Element("Protocolo");
        eProtocolo.setAttribute("accion", accion);
        return eProtocolo;
    } // retorna una accion de protocolo sin el dato solo la accion

    public static Element agregarAccionSimple(String accion, Element dato) {
        Element eDato = new Element("dato");
        eDato.addContent(dato);
        Element eProtocolo = crearMensajeProtocolo(accion);
        eProtocolo.addContent(eDato);
        return eProtocolo;
    }//retorna un xml con la accion a ejecutar de protocolo y el dato a enviar
    
    public static Element robarDiamantes(String accion, String creador, int dimantes) {
        Element eDato = new Element("creador");
        eDato.addContent(creador);
        Element eDato2 = new Element("cantidad");
        eDato2.addContent(String.valueOf(dimantes));
        Element eProtocolo = crearMensajeProtocolo(accion);
        eProtocolo.addContent(eDato);
        eProtocolo.addContent(eDato2);
        return eProtocolo;
    }//retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element enviarMapa(String accion, Element dato) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        eProtocolo.addContent(dato);
        return eProtocolo;
    }//retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static Element agregarAccionSimple(String accion, String dato) {
        Element eDato = new Element("dato");
        eDato.addContent(dato);
        Element eProtocolo = crearMensajeProtocolo(accion);
        eProtocolo.addContent(eDato);
        return eProtocolo;
    }//retorna un xml con la accion a ejecutar de protocolo y el destinatario a enviar

    public static Element enviarUsurio(Usuario usuario) throws NoSuchAlgorithmException {
        //prepara a este oponente para ser enviado 
        Element usurio = new Element("Usurio");
        //agrega el nombre y la contrasenia como atributos
        usurio.setAttribute("Nombre", usuario.getNombreUsuario());
        usurio.setAttribute("Contrasenia", usuario.getContrasenia());
        return usurio;
    }//retorna un xml con la accion a ejecutar de protocolo y el dato a enviar

    public static void oponenteActualizado(Element datos) {

        Element oponente = datos.getChild("oponente");
        JuegoSingleton.getInstance().actualizarInformacionOponente(oponente.getChildText("nombre"),
                 Double.valueOf(oponente.getChildText("posX")), Double.valueOf(oponente.getChildText("posY")));

    }//metodo lee datos de los oponentes

    public static ArrayList<Oponente> listaDeOponentes(Element datos) {
        
        ArrayList<Oponente> oponentes = new ArrayList<>();
        List listaJugadores = datos.getChild("oponentes").getChildren();
        for (Object listaOponentes : listaJugadores) {
            Element oponente = (Element) listaOponentes;
            Oponente oponenteNuevo = new Oponente(oponente.getChildText("nombre"),
                    new Punto2D(Double.valueOf(oponente.getChildText("posX")), Double.valueOf(oponente.getChildText("posY"))));
            oponenteNuevo.setAvatar(oponente.getAttributeValue("personaje"));
            oponentes.add(oponenteNuevo);
        }//for que recorre la lista de jugadores
        return oponentes;
        
    }//metodo lee lista de oponentes

    public static String ranking(Element listaUsuarios) {
        
        String ranking = "Record de jugadores";//carga el ranking de juego en un carracter
        List listaJugadores = listaUsuarios.getChildren("Jugador");
        int indice = 1;
        //lee a todos los usuarios para cargar ql ranking el cual 
        //ya biene en  orde
        for (Object listaJugadore : listaJugadores) {
            Element usurio = (Element) listaJugadore;
            //agrega al jugador al ranking
            ranking += indice + " -> " + usurio.getAttributeValue("Nombre")
                    + " diamantes reunidos = " + usurio.getChildText("Diamantes");
            indice++;
        }//for que recorre la lista de jugadores
        return ranking;
        
    }//metodo que retorna string con el ranking

    public static Element enviarFlecha(String accion, Flecha flecha) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        Element flechaEnviar = new Element("flecha");
        flechaEnviar.setAttribute("creador", flecha.getCreador());
        Element direccionX = new Element("direccionX");
        direccionX.addContent(String.valueOf(flecha.getDireccionX()));
        flechaEnviar.addContent(direccionX);
        Element direccionY = new Element("direccionY");
        direccionY.addContent(String.valueOf(flecha.getDireccionY()));
        flechaEnviar.addContent(direccionY);
        Element posicionX = new Element("posicionX");
        posicionX.addContent(String.valueOf(flecha.getDisX()));
        flechaEnviar.addContent(posicionX);
        Element posicionY = new Element("posicionY");
        posicionY.addContent(String.valueOf(flecha.getDisY()));
        flechaEnviar.addContent(posicionY);
        eProtocolo.addContent(flechaEnviar);
        return eProtocolo;
        
    }//prepara una flecha para ser enviada

    public static Element enviarEspadaso(String accion,String creador,double posX, double posY) {
        Element eProtocolo = crearMensajeProtocolo(accion);
        Element espadazo = new Element("espadaso");
        espadazo.setAttribute("creador", creador);
        Element posicionX = new Element("posX");
        posicionX.addContent(String.valueOf(posX));
        espadazo.addContent(posicionX);
        Element posicionY = new Element("posY");
        posicionY.addContent(String.valueOf(posY));
        espadazo.addContent(posicionY);
        eProtocolo.addContent(espadazo);
        return eProtocolo;
    }//prepara una flecha para ser enviada

    public static Element enviarInformacionJugador(Jugador jugador) {
        
        Element jugadorEnviar = new Element("jugador");
        jugadorEnviar.setAttribute("personaje", jugador.getPersonaje().getRutaImagen());
        Element posX = new Element("posX");
        posX.addContent(String.valueOf(jugador.getPersonaje().getPosicion().x));
        jugadorEnviar.addContent(posX);
        Element posY = new Element("posY");
        posY.addContent(String.valueOf(jugador.getPersonaje().getPosicion().y));
        jugadorEnviar.addContent(posY);
        
        return jugadorEnviar;
    }//metodo crea un elemento con la informacion del jugador para enviarlo al servidor
    
    public static void verificarDiamantes(Element dato, Mapa mapa) {

        List dimantes = dato.getChild("diamantes").getChildren();
        int posicion = 0;
        for (Object dimante : dimantes) {
            Element diamante = (Element) dimante;
            if (!mapa.diamantePermitido(Integer.parseInt(diamante.getChildText("fila")),
                    Integer.parseInt(diamante.getChildText("columna")))) {
                break;
            }//verifica si el diamnte es accesible a esa carta  
            posicion++;
        }//recorre la informacion de losdiamantes

        if (dimantes.size() == posicion) {//notifica que lis diamantes estan listos
            ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Solicitud.DISTRIBUCION_CORRECTA.name())));
        } else {
            ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Solicitud.CAMBIAR_CASILLA_DIAMANTE.name(),
                    String.valueOf(posicion))));//notifica un error de coliciones
        }//verifica si pudo resibir todos los diamante se confirman las posciones 
    }//metodo para lerr los diamntes y verificar si estos tienen posiciones correctas

    public static void colocarDiamantes(Element dato, Mapa mapa) {

        List dimantes = dato.getChild("diamantes").getChildren();
        for (Object dimante : dimantes) {
            Element diamante = (Element) dimante;

            mapa.colocarDiamante(Integer.parseInt(diamante.getAttributeValue("id")), Integer.parseInt(diamante.getChildText("fila")),
                    Integer.parseInt(diamante.getChildText("columna")));
        }//recorre la informacion de losdiamantes

    }//coloca diamantes 

    public static Element actualizarInformacionJugador(Jugador jugador, Mapa mapa) {
        
        Element jugadorEnviar = new Element("jugador");
        jugadorEnviar.setAttribute("puntosVida", String.valueOf(jugador.getPersonaje().getPuntosVida()));
        Element posX = new Element("posX");
        posX.addContent(String.valueOf(mapa.retornarDistanciaX(jugador.getPersonaje())));
        jugadorEnviar.addContent(posX);
        Element posY = new Element("posY");
        posY.addContent(String.valueOf(mapa.retornarDistanciaY(jugador.getPersonaje())));
        jugadorEnviar.addContent(posY);
        
        return jugadorEnviar;
    }//metodo crea un elemento con la informacion del jugador para enviarlo al servidor

    public static Flecha leerFlecha(Element dato) {
        
        int direccionX = Integer.parseInt(dato.getChildText("direccionX"));
        int direccionY = Integer.parseInt(dato.getChildText("direccionY"));
        double disX=Double.parseDouble(dato.getChildText("posicionX"));
        double disY=Double.parseDouble(dato.getChildText("posicionY"));
        double posX =JuegoSingleton.getInstance().getMapa().getO()+disX;
        double posY =JuegoSingleton.getInstance().getMapa().getN()+disY;
        return new Flecha(dato.getAttributeValue("creador")
                ,new Punto2D(posX, posY)
                , direccionX, direccionY
                ,disX,disY);
        
    }//prepara una flecha para ser enviada

    public static Element empaquetarMapa(String nombreUsurio, String nombreMapa, Mapa mapaNuevo) {
        
        Element mapa = new Element("Mapa");
        mapa.setAttribute("creador", nombreUsurio);
        mapa.setAttribute("nombre", nombreMapa);
        mapa.setAttribute("filas", String.valueOf(mapaNuevo.getFilas()));
        mapa.setAttribute("columnas", String.valueOf(mapaNuevo.getColumnas()));
        
        Element casillas = new Element("casillas");
        for (int i = 0; i < mapaNuevo.getFilas(); i++) {
            for (int j = 0; j < mapaNuevo.getColumnas(); j++) {
                Element casilla = new Element("casilla");
                casilla.setAttribute("fila", String.valueOf(i));
                casilla.setAttribute("columna", String.valueOf(j));
                casilla.setAttribute("tipo", String.valueOf(mapaNuevo.getCasillas()[i][j].retornarTipo()));
                if (mapaNuevo.getCasillas()[i][j].getObjetoDecorativo() != null) {
                    casilla.setAttribute("objetoDecorativo", String.valueOf(mapaNuevo.getCasillas()[i][j].getObjetoDecorativo().retornarTipo()));
                }//verifica si la casilla tiene un objeto decorativo que se pueda almacenar
                casillas.addContent(casilla);
            }//recorre las columnas del mapa
        }//recorre la filas del mapa

        mapa.addContent(casillas);
        
        return mapa;
        
    }//conviente un mapa a un elemeto para transferir

    public static Mapa cargarMapa(Element mapa) {
        
        Mapa mapaJuego = new Mapa(Integer.parseInt(mapa.getAttributeValue("filas")), Integer.parseInt(mapa.getAttributeValue("columnas")));
        
        List casillas = mapa.getChild("casillas").getChildren();
        
        for (Object casilla : casillas) {
            Element casillaCargar = (Element) casilla;

            //carga cada casilla del mapa
            mapaJuego.cargarCasilla(Integer.parseInt(casillaCargar.getAttributeValue("fila")),
                    Integer.parseInt(casillaCargar.getAttributeValue("columna")),
                    Integer.parseInt(casillaCargar.getAttributeValue("tipo")));
            
            if (casillaCargar.getAttribute("objetoDecorativo") != null) {
                mapaJuego.colocarObjeto(Integer.parseInt(casillaCargar.getAttributeValue("fila")),
                        Integer.parseInt(casillaCargar.getAttributeValue("columna")),
                        casillaCargar.getAttributeValue("objetoDecorativo"));
            }//verifica si hay un objeto decorativo que crear

        }//recorre todas la casillas del mapa de juago

        return mapaJuego;
        
    }//metodo que convierte un elemeto a un mapa

}//class
