/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package Domain;

import Utility.ConexionInterfaz;
import Utility.GestionXML;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.jdom.Element;
import org.jdom.JDOMException;

/**
 *
 * @author Ernesto
 */
public enum ProtocoloCliente {
    
    REGISTRO_EXITOSO {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ConexionInterfaz.iniciarInterfaz();
        }
    }, INICIO_EXITOSO {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ConexionInterfaz.iniciarInterfaz();
        }
    }, REGISTRO_FALLIDO_IINICIO_FALLIDO {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ConexionInterfaz.errorRegistroInicio();
        }
    }, PUBLICACION_JUGADOR {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ConexionInterfaz.publicar(datos.getChildText("dato"));
        }
    }, CERRAR_SESSION {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ClienteSingleton.getInstance().cerrarFlujo();
            ConexionInterfaz.cerrarInterfaz();
            ClienteSingleton.getInstance().setSessionEjecutando(false);
        }
    }, SOLICITUD_AMISTAD {
        @Override
        public void ejecutarProtocolo(Element datos) {
            int confirmacion = JOptionPane.showConfirmDialog(ConexionInterfaz.jFInterfazUsuario, "solicitud amistad de " + datos.getChildText("dato"));
            if (confirmacion == 0) {
                ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Solicitud.SOLICITUD_ACEPTADA.name(), datos.getChildText("dato"))));
            }//verifca que el jugador acepte la solicitud
        }
    }, SOLICITUD_ACEPTADA {
        @Override
        public void ejecutarProtocolo(Element datos) {
            JOptionPane.showMessageDialog(ConexionInterfaz.jFInterfazUsuario, "Solicitud de amistad a " + datos.getChildText("dato") + " aceptada");
        }
    }, INICIAR_PARTIDA {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ConexionInterfaz.iniciarJuego();
        }
    }, ENVIAR_MAPA_JUEGO {
        @Override
        public void ejecutarProtocolo(Element datos) {
            try {
                Mapa mapaPartida = GestionXML.cargarMapa(GestionXML.stringToXML(datos.getValue()));
                JuegoSingleton.getInstance().setMapa(mapaPartida);
                //despues de resibir el mapa de juego envia la informacion al servidor de el cliente actual
                Element jugadorCliente = GestionXML.enviarInformacionJugador(ClienteSingleton.getInstance().getJugadorCliente());
                ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(
                        GestionXML.agregarAccionSimple(Solicitud.ENVIAR_ESTADO_JUGADOR.name(), jugadorCliente)));
            } catch (JDOMException | IOException ex) {
                Logger.getLogger(ProtocoloCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }, FLECHA_LANZADA {
        @Override
        public void ejecutarProtocolo(Element datos) {
            //metodo reconstruye la flecha para desplgarla en el juego
            JuegoSingleton.getInstance().lazarFlecha(GestionXML.leerFlecha(datos.getChild("flecha")));
        }
    }, ENVIAR_OPONENTES {
        @Override
        public void ejecutarProtocolo(Element datos) {
            //el jugador resibe los opnentes a los que se va a enfrentar antes de que inicia la partida
            JuegoSingleton.getInstance().setOponentes(GestionXML.listaDeOponentes(datos));
            ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.crearMensajeProtocolo(Solicitud.SOLICITAR_DIAMANTES.name())));
        }
    }, ACTUALIZAR_OPONENTE {
        @Override
        public void ejecutarProtocolo(Element datos) {
            //leer la informacion de un opnente que actualizo su informacion
            GestionXML.oponenteActualizado(datos);
        }
    }, LISTA_AMIGOS {
        @Override
        public void ejecutarProtocolo(Element datos) {
            //descompone la lista de amigos en una lista de caracteres para agregar a la interfaz grafica
            String[] amigos = datos.getChildText("amigos").split(",");
            String[] mapas = datos.getChildText("mapas").split(",");
            ConexionInterfaz.solicitudListaAmigos(amigos, mapas);
        }
    }, RANKING_JUGADORES {
        @Override
        public void ejecutarProtocolo(Element datos) {
            String[] ranking = datos.getChildText("dato").split(",");
            ConexionInterfaz.ranking(ranking);
        }
    }, NOTIFICAR_ESPADAZO {
        @Override
        public void ejecutarProtocolo(Element datos) {
            datos=datos.getChild("espadaso");
            JuegoSingleton.getInstance().notificarEspadazo(datos.getAttributeValue("creador")
                    ,Double.valueOf(datos.getChildText("posX")),Double.valueOf(datos.getChildText("posY")));
        }
    }, VERIFICAR_DIAMANTES {
        @Override
        public void ejecutarProtocolo(Element datos) {
            GestionXML.verificarDiamantes(datos, JuegoSingleton.getInstance().getMapa());
        }
    }, ENVIO_DIAMANTES {
        @Override
        public void ejecutarProtocolo(Element datos) {
            GestionXML.colocarDiamantes(datos, JuegoSingleton.getInstance().getMapa());
        }
    }, JUGADOR_PERDIO {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ConexionInterfaz.desplegarJugadorPerdio();
            JuegoSingleton.getInstance().restablecerJuego();
        }
    }, ELIMINAR_JUGADOR {
        @Override
        public void ejecutarProtocolo(Element datos) {
            JuegoSingleton.getInstance().oponenteMurio(datos.getChildText("dato"));
        }
    }, SUMAR_DIAMANTE {
        @Override
        public void ejecutarProtocolo(Element datos) {
            JuegoSingleton.getInstance().getJugadorCliente().getPersonaje().sumarDiamantes(Integer.parseInt(datos.getChildText("dato")));
        }
    }, QUITAR_DIAMANTE {
        @Override
        public void ejecutarProtocolo(Element datos) {
            //elimina un diamante en el mapa de juego
            Element diamante = datos.getChild("diamante");
            JuegoSingleton.getInstance().getMapa().quitarDiamante(Integer.parseInt(diamante.getChildText("fila")),
                    Integer.parseInt(diamante.getChildText("columna")));
        }
    }, INVITAR_PARTIDA {
        @Override
        public void ejecutarProtocolo(Element datos) {
            int confirmacion = JOptionPane.showConfirmDialog(ConexionInterfaz.jFInterfazUsuario, "solicitud partida de " + datos.getChildText("dato"));
            if (confirmacion == 0) {
                ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.agregarAccionSimple(Solicitud.UNIRSE_A_PARTIDA.name(), datos.getChildText("dato"))));
            }//verifca que el jugador acepte la partida
        }
    }, SOLICITUD_GURDADA {
        @Override
        public void ejecutarProtocolo(Element datos) {
            JOptionPane.showMessageDialog(ConexionInterfaz.jFInterfazUsuario, "Solicitud guardada");
        }
    }, USUARIO_NO_EXISTE {
        @Override
        public void ejecutarProtocolo(Element datos) {
            JOptionPane.showMessageDialog(ConexionInterfaz.jFInterfazUsuario, "Usuario no existe");
        }
    }, ENVIAR_SOLICITUDES {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ConexionInterfaz.solicitudListaAmigos(datos.getChildText("Solicitudes").split(","));
        }
    },JUGADOR_GANO {
        @Override
        public void ejecutarProtocolo(Element datos) {
            ConexionInterfaz.desplegarJugadorGano(JuegoSingleton.getInstance().getJugadorCliente(), JuegoSingleton.getInstance().getOponentes());
            JuegoSingleton.getInstance().restablecerJuego();
        }
    };
    
    public abstract void ejecutarProtocolo(Element datos);
    
}
