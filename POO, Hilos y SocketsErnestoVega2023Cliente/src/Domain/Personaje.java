/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.Disenio;
import Utility.GestionXML;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Ernesto
 */
public class Personaje {

    private final Punto2D posicion;
    private final Rectangle colicion;
    private int direccionX;
    private int direccionY;
    private int filaCasilla;
    private int columnaCasilla;
    private int puntosVida;
    private int diamantes;
    private BufferedImage avatar;
    private String rutaImagen;
    private Casilla casillaPaso;
    private final double velocidadPorDefecto;
    private double velocidadMovimiento;
    private final Espada espada;
    private final Arco arco;
    private boolean usaArco;
    private boolean usaEspada;

    public Personaje(Punto2D posiciones) {
        this.posicion = posiciones;
        this.colicion = new Rectangle((int) this.posicion.x, (int) this.posicion.y, Disenio.PERSONAJE, Disenio.PERSONAJE);
        this.direccionX = 0;
        this.direccionY = 0;
        this.puntosVida = 100;
        this.diamantes = 0;
        this.velocidadPorDefecto=10;
        this.velocidadMovimiento = this.velocidadPorDefecto;
        this.espada = new Espada();
        this.arco = new Arco();
        this.usaArco = false;
        this.usaEspada = false;
    }//constructor

    //metodos de accesoCasilla
    public Punto2D getPosicion() {
        return posicion;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public int getDiamantes() {
        return diamantes;
    }

    public void setDiamantes(int diamantes) {
        this.diamantes = diamantes;
    }

    public void setVelocidadMovimiento(double velocidadMovimiento) {
        this.velocidadMovimiento = velocidadMovimiento;
    }

    public int getDireccionX() {
        return direccionX;
    }

    public int getDireccionY() {
        return direccionY;
    }

    public void setDireccionX(int direccionX) {
        this.direccionX = direccionX;
    }

    public void setDireccionY(int direccionY) {
        this.direccionY = direccionY;
    }

    public Casilla getCasillaPaso() {
        return casillaPaso;
    }

    public void setCasillaPaso(Casilla casillaPaso) {
        this.casillaPaso = casillaPaso;
    }

    public Rectangle getColicion() {
        return colicion;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public double getVelocidadPorDefecto() {
        return velocidadPorDefecto;
    }

    public double getVelocidadMovimiento() {
        return velocidadMovimiento;
    }
    
    public void sumarDiamantes(int cantidad) {
        this.diamantes += cantidad;
    }

    public int getFilaCasilla() {
        return filaCasilla;
    }

    public int getColumnaCasilla() {
        return columnaCasilla;
    }

    public void reiniciarDatos(){
      this.casillaPaso=null;
      this.puntosVida=100;
      this.diamantes=0;
      this.velocidadMovimiento=this.velocidadPorDefecto;
    }//reinicia los datos del jugador para una nueva partida

    public int robarDiamantes(int cantidad) {
        this.diamantes -= cantidad;
        if (this.diamantes < 0) {
            this.diamantes = 0;
            return 0;
        }//resta los diamantes al jugador
        return cantidad;
    }

    public void setAvatar(String ruta) {
        try {
            this.avatar = ImageIO.read(getClass().getResource(ruta));
            this.rutaImagen = ruta;
        } catch (IOException ex) {
            Logger.getLogger(Personaje.class.getName()).log(Level.SEVERE, null, ex);
        }//trata de cargar la imagen de personage
    }
    
    public void dispararArco() {
        this.usaArco = true;
        this.arco.setPosiciones(this);
        this.arco.lazarFlecha(this);
    }//metodo para dispara una flecha

    public void usarEspada() {
        this.usaEspada = true;
        this.espada.setPosiciones(this);
        this.espada.espadaso(this);
    }//metodo para dispara una flecha

    public void ocultarArmas() {
        this.usaArco = false;
        this.usaEspada = false;
    }//oculta ambas armas

    public void movimientoIzquierda() {
        this.direccionX = -1;
        this.direccionY = 0;
        this.izquierda();
    }

    public void movimientoDerecha() {
        this.direccionX = 1;
        this.direccionY = 0;
        this.derecha();
    }

    public void movimientoArriba() {
        this.direccionX = 0;
        this.direccionY = -1;
        this.arriba();
    }

    public void movimientoAbajo() {
        this.direccionX = 0;
        this.direccionY = 1;
        this.abajo();
    }

    public void derecha() {//desplaza el mapa hacia derecha
        if (JuegoSingleton.getInstance().verificarColicion(this)) {
            //valida que el jugador sea el que se mueva pos si mismo luego el 
            //scroll ejecuta el movimiento 
            JuegoSingleton.getInstance().getMapa().desIzquierda(this);
            this.actualizarColiciones();
        }
    }//cuando el jugador no esta en el margen de movimiento este se mueve por su cuenta de forma
    //horizontal

    public void izquierda() {//desplaza el mapa hacia izquierda
        if (JuegoSingleton.getInstance().verificarColicion(this)) {
            //valida que el jugador sea el que se mueva pos si mismo luego el 
            //scroll ejecuta el movimiento 
            JuegoSingleton.getInstance().getMapa().desDerecha(this);
            this.actualizarColiciones();
        }
    }//cuando el jugador no esta en el margen de movimiento este se mueve por su cuenta de forma
    //horizontal

    private void arriba() {//desplaza el mapa hacia arriba
        if (JuegoSingleton.getInstance().verificarColicion(this)) {
            //valida que el jugador sea el que se mueva pos si mismo luego el 
            //scroll ejecuta el movimiento 
            JuegoSingleton.getInstance().getMapa().desAbajo(this);
            this.actualizarColiciones();
        }
    }//cuando el jugador no esta en el margen de movimiento este se mueve por su cuenta de forma
    //horizontal

    public void abajo() {//desplaza el mapa hacia abajo
        if (JuegoSingleton.getInstance().verificarColicion(this)) {
            //valida que el jugador sea el que se mueva pos si mismo luego el 
            //scroll ejecuta el movimiento 
            JuegoSingleton.getInstance().getMapa().desArriba(this);
            this.actualizarColiciones();
        }
    }//cuando el jugador no esta en el margen de movimiento este se mueve por su cuenta de forma
    //horizontal

    private void actualizarColiciones() {
        //optenemos la distancia del jugador respecto al mapa
        double disX = JuegoSingleton.getInstance().getMapa().retornarDistanciaX(this);
        double disY = JuegoSingleton.getInstance().getMapa().retornarDistanciaY(this);
        
        //establecemos la fila y la columna deseada
        this.columnaCasilla = Math.floorDiv((int) disX, Disenio.CASILLA);
        this.filaCasilla = Math.floorDiv((int) disY, Disenio.CASILLA);
        
        JuegoSingleton.getInstance().tomarCasilla(this);//el jugador adquiere una casilla
        this.casillaPaso.aplicarEfecto(this);//se aplica el efecto de la casilla sobre el jugador
        this.casillaPaso.tomarDiamante(this);//toma diamantes si los hay
        
        //actualiza las coliciones del jugador respecto al mapa de juego
        double posX = JuegoSingleton.getInstance().getMapa().getO() + disX;
        double posY = JuegoSingleton.getInstance().getMapa().getN() + disY;
        this.colicion.setLocation((int) posX, (int) posY);
        JuegoSingleton.getInstance().actualizarInformacion();//envia una actualizacion de informacion
    }//actualiza la posicion de las colicion

    public void verificarGolpe(String creador, Punto2D posicionGolpe) {
        //verifica el golpe de la espada con el jugador
        if (this.colicion.contains(posicionGolpe) || this.colicion.intersects(posicionGolpe.getX(), posicionGolpe.getY(), 40, 40)) {
            this.puntosVida -= 10;
            JuegoSingleton.getInstance().actualizarInformacion();
            ClienteSingleton.getInstance().enviarDatos(GestionXML.xmlToString(GestionXML.robarDiamantes(Solicitud.ROBAR_DIAMANTES.name(),
                    creador, this.robarDiamantes(2))));
        }//verifica si existe el golpe
    }//verifia si el jugador resive un golpe de algunaarma

    public void resivirDanio(Arma arma) {
        this.puntosVida -= arma.getDanio();//se rebaja el danio corespondiente al jugador
        JuegoSingleton.getInstance().actualizarInformacion();
    }//metodo por el cual un jugador resibe danio de un arma

    public void adquirirDiamante(Diamante diamante) {
        if (this.colicion.contains(diamante.getPosicion())) {
            this.diamantes++;
        }//varifica si el personaje esta en la posicion de un diamante
    }//metodo para que un usurio pueda adquirir el diamante

    public void dibujar(Graphics g) {
        g.drawImage(this.avatar, (int) this.posicion.x, (int) this.posicion.y, null);
        if (this.usaArco) {
            this.arco.dibujar(g);
        } else if (this.usaEspada) {
            this.espada.dibujar(g);
        }//dependiendo del arma dibuja la skin
        g.setColor(Color.RED);
        g.setFont(Disenio.FORMATO_GENERAL);
        g.drawString("Vida " + this.puntosVida, (int) this.posicion.x-5, (int) (this.posicion.y - 20));//dibuja el funcionamiento del objeto
        g.setColor(Color.BLUE);
        g.drawString("Diamantes " + this.diamantes, (int) this.posicion.x-5, (int) (this.posicion.y - 5));//dibuja los diamantes del objeto
    }//metodo de dibujado

}//class
