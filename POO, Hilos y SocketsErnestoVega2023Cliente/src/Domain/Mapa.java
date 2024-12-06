/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

import Utility.Disenio;
import java.awt.Graphics;

/**
 *
 * @author Ernesto
 */
public class Mapa {
    
    private final int filas;
    private final int columnas;
    private double o;
    private double n;
    private double e;
    private double s;
    private final Casilla[][] casillas;
    
    private final ElementoMapaFabrica fabrica;
    
    public Mapa(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;
        this.casillas = new Casilla[this.filas][this.columnas];
        this.fabrica = new ElementoMapaFabrica();
    }//constructor
    
    //metodo de negarAcceso
    public Casilla[][] getCasillas() {
        return casillas;
    }

    public int getFilas() {
        return filas;
    }

    public int getColumnas() {    
        return columnas;
    }

    public double getO() {
        return o;
    }

    public double getN() {
        return n;
    }

    public double getE() {
        return e;
    }

    public double getS() {
        return s;
    }
    
    public void establecerLimites() {
        //establce los limites del mapa de juego
        this.o = (int) this.casillas[0][0].getPosicion().getX();
        this.n = (int) this.casillas[0][0].getPosicion().getY();
        this.e = (int) (this.casillas[this.filas-1][this.columnas-1].getPosicion().getX() + Disenio.CASILLA);
        this.s = (int) (this.casillas[this.filas-1][this.columnas-1].getPosicion().getY() + Disenio.CASILLA);
    }//establece las coliciones del tablero de juego

    //metodos relacionados con la jugabilidad 
    //este metodo usa un recorrido completo para validar las coliciones del jugador y evitar que este quede bloqueado
    public boolean accederSiguiente(Personaje personaje) {
        for (int fila = 0; fila < this.filas; fila++) {
            for (int columna = 0; columna < this.columnas; columna++) {
                if (this.casillas[fila][columna].negarAcceso(personaje)) {//si una casilla contiene las posiciones seleccionadas la retorna
                    personaje.setDireccionX(0);
                    personaje.setDireccionY(0);
                    return false;
                }//verifica que la posicion enviada coincida con una casilla para retornar 
            }//for para recorrer columnas
        }//for para recorrer filas
        return true;
    }//verifica la colicion de un jugador con una casilla
    
    public void tomarCasilla(Personaje personaje) {
        //utiliza un calculo para optener la fila y la columna del jugador y asi obtener de forma rapida
        if (this.casillas[personaje.getFilaCasilla()][personaje.getColumnaCasilla()].contienePersonaje(personaje)) {//si una casilla contiene las posiciones seleccionadas la retorna
           personaje.setCasillaPaso(this.casillas[personaje.getFilaCasilla()][personaje.getColumnaCasilla()]);
        }//verifica que la posicion enviada coincida con una casilla para retornar 
    }//metodo que coloca a un jugador una casilla a la que se desea el acceso

    //estos metodos son necesarios para optener la posicion precisa de un jugador o una arma respecto a 
    //el mapa de juego mediante sacar la diferencia
    public double retornarDistanciaX(Personaje personaje) {
        return Math.abs(this.o - personaje.getPosicion().getX());
    }//retorna la distacia del jugador respecto al mapa

    public double retornarDistanciaY(Personaje personaje) {
        return Math.abs(this.n - personaje.getPosicion().getY());
    }//retorna la distacia del jugador respecto al mapa

    public double retornarDistanciaXArma(Arma arma) {
        return Math.abs(this.o - arma.getPosicion().getX());
    }//retorna la distacia del arma respecto al mapa

    public double retornarDistanciaYArma(Arma arma) {
        return Math.abs(this.n - arma.getPosicion().getY());
    }//retorna la distacia del arma respecto al mapa

    public void desIzquierda(Personaje personaje) {
        if (this.e - personaje.getVelocidadMovimiento() >= 420 ) {
            for (int fila = 0; fila < this.filas; fila++) {
                for (int columna = 0; columna < this.columnas; columna++) {
                    this.casillas[fila][columna].getPosicion().x -= personaje.getVelocidadMovimiento();//carga las casillas en la carretera
                    this.casillas[fila][columna].actualizarClicion();
                }//for para recorrer columnas
            }//for para recorrer filas
            this.establecerLimites();
        }//valida que el personaje no salga del mapa de juego
    }//metodo para cargar el tablero

    public void desDerecha(Personaje personaje) {
        if (this.o + personaje.getVelocidadMovimiento() <= 380) {
            for (int fila = 0; fila < this.filas; fila++) {
                for (int columna = 0; columna < this.columnas; columna++) {
                    this.casillas[fila][columna].getPosicion().x += personaje.getVelocidadMovimiento();//carga las casillas en la carretera
                    this.casillas[fila][columna].actualizarClicion();
                }//for para recorrer columnas
            }//for para recorrer filas
            this.establecerLimites();
        }//valida que el personaje no salga del mapa de juego
    }//metodo para cargar el tablero

    public void desArriba(Personaje personaje) {
        if ((this.s - personaje.getVelocidadMovimiento()) >= 340) {//valida que el mapa se desplace hasta los limites necesarios
            for (int fila = 0; fila < this.filas; fila++) {
                for (int columna = 0; columna < this.columnas; columna++) {
                    this.casillas[fila][columna].getPosicion().y -= personaje.getVelocidadMovimiento();//carga las casillas en la carretera
                    this.casillas[fila][columna].actualizarClicion();
                }//for para recorrer columnas
            }//for para recorrer filas
            this.establecerLimites();
        }//valida que el personaje no salga del mapa de juego
    }//metodo para cargar el tablero

    public void desAbajo(Personaje personaje) {
        if ((this.n + personaje.getVelocidadMovimiento()) <= 300) {//valida que el mapa se desplace hasta los limites necesarios
            for (int fila = 0; fila < this.filas; fila++) {
                for (int columna = 0; columna < this.columnas; columna++) {
                    this.casillas[fila][columna].getPosicion().y += personaje.getVelocidadMovimiento();//carga las casillas en la carretera
                    this.casillas[fila][columna].actualizarClicion();
                }//for para recorrer columnas
            }//for para recorrer filas
            this.establecerLimites();
        }//valida que el personaje no salga del mapa de juego
    }//metodo para cargar el tablero

    //metodos relacionados con la construccion del mapa 
    //y su carga al ser resibido
    public void cargarPorDefecto() {
        for (int fila = 0; fila < this.filas; fila++) {
            for (int columna = 0; columna < this.columnas; columna++) {
                this.casillas[fila][columna] = new CasillaCamino(new Punto2D(columna * Disenio.CASILLA, fila * Disenio.CASILLA));//carga las casillas en la carretera
            }//for para recorrer columnas
        }//for para recorrer filas
        this.establecerLimites();
    }//metodo para cargar el tablero

    public void cargarCasilla(int fila, int columna, int tipo) {
        this.casillas[fila][columna] = this.fabrica.crear(tipo, new Punto2D(columna * Disenio.CASILLA, fila * Disenio.CASILLA));
    }//crea una nueva casilla en la poscion resivida

    public void colocarObjeto(int fila, int columna, String tipo) {
        this.casillas[fila][columna].setObjetoDecorativo(this.fabrica.crear(tipo, this.casillas[fila][columna]));
    }//crea una nueva casilla en la poscion resivida

    public Casilla obtenerCasilla(Punto2D punto) {
        for (int fila = 0; fila < this.filas; fila++) {
            for (int columna = 0; columna < this.columnas; columna++) {
                if (this.casillas[fila][columna].esSeleccionada(punto)) {//si una casilla contiene las posiciones seleccionadas la retorna
                    return this.casillas[fila][columna];
                }//verifica que la posicion enviada coincida con una casilla para retornar 
            }//for para recorrer columnas
        }//for para recorrer filas
        return null;
    }//verifica si fue selecionada una casilla

    public void colocarCasilla(int instancia, Punto2D punto) {
        for (int fila = 0; fila < this.filas; fila++) {
            for (int columna = 0; columna < this.columnas; columna++) {
                if (this.casillas[fila][columna] != null
                        && this.casillas[fila][columna].esSeleccionada(punto)) {//si una casilla contiene las posiciones seleccionadas la retorna

                    this.casillas[fila][columna] = this.fabrica.crear(instancia, this.casillas[fila][columna].getPosicion());//cambia la casilla del trablero
                }//verifica que la posicion enviada coincida con una casilla para retornar 
            }//for para recorrer columnas
        }//for para recorrer filas
    }//verifica si fue selecionada una casilla

    public void colocarDecoracion(String instancia, Casilla casilla) {
        if (casilla.getObjetoDecorativo() == null) {
            casilla.setObjetoDecorativo(this.fabrica.crear(instancia, casilla));
        }//valida no colocar un objeto decorativo a una casilla que ya lo tien
    }//coloca un onjeto decorativo en una nueva casilla

    public void horizontal(int direccion) {
        for (int fila = 0; fila < this.filas; fila++) {
            for (int columna = 0; columna < this.columnas; columna++) {
                this.casillas[fila][columna].getPosicion().x += (10 * direccion);//carga las casillas en la carretera
                this.casillas[fila][columna].actualizarClicion();
            }//for para recorrer columnas
        }//for para recorrer filas
    }//metodo para cargar el tablero

    public void vertical(int direccion) {
        for (int fila = 0; fila < this.filas; fila++) {
            for (int columna = 0; columna < this.columnas; columna++) {
                this.casillas[fila][columna].getPosicion().y += (10 * direccion);//carga las casillas en la carretera
                this.casillas[fila][columna].actualizarClicion();
            }//for para recorrer columnas
        }//for para recorrer filas
    }//metodo para cargar el tablero

    public boolean diamantePermitido(int fila, int columna) {
        return this.casillas[fila][columna].aceptarDiamante();
    }//verifica si esa poscion para el diamante es valida

    public void colocarDiamante(int id, int fila, int columna) {
        this.casillas[fila][columna].setDiamante(id);
    }//coloca un diamante en una casilla
    
    public void quitarDiamante(int fila, int columna) {
        this.casillas[fila][columna].quitar();
    }//quita un diamante cuando este es tomado

    public void dibujar(Graphics g) {
        for (int fila = 0; fila < this.filas; fila++) {
            for (int columna = 0; columna < this.columnas; columna++) {
                if (this.casillas[fila][columna] != null) {
                    this.casillas[fila][columna].dibujar(g);
                }//se asegura que existen casillas
            }//for para recorrer columnas
        }//for para recorrer filas
    }//metodo de dibujado del objeto

}//class 5/20/2023
