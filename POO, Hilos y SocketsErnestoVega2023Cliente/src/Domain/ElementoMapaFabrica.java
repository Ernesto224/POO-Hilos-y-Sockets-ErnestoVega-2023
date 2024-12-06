/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Domain;

/**
 *
 * @author Ernesto
 */
public class ElementoMapaFabrica{
  
    public static int AGUA = 1;
    public static int CESPED = 2;
    public static int MURO_ROCA = 4;
    public static int CAMINO = 3;
    public static String FLORES = "flor";
    public static String ROCA = "roca";
    public static String ARBOL = "sgus";

    public ElementoMapaFabrica() {
    }//constructor

    public Casilla crear(float instancia,Punto2D posicion) {
        if (instancia == AGUA) {
            return new CasillaAgua(posicion);
        } else if (instancia == CESPED) {
            return new CasillaCesped(posicion);
        }else if (instancia == MURO_ROCA) {
            return new CasillaMuro(posicion);
        } else if (instancia == CAMINO) {
            return new CasillaCamino(posicion);
        }//retorna un tipo de casilla
        return new CasillaCamino(posicion);
    }//metodo para crear una casilla
    
    public ObjetoDecorativo crear(String instacia, Casilla casilla) {
        if (instacia.equals(ROCA)) {
            return new Roca(casilla.getPosicion());
        } else if (instacia.contentEquals(ARBOL)) {
            return new Arbol(casilla.getPosicion());
        } else {
            return new Flor(casilla.getPosicion());
        }//metodo que retorna un objeto decorativo
    }//metodo para generar un objeto decorativo
    
}//5/21/2023
