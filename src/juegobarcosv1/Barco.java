/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de funcionamiento
 *  Alvaro Perez 870097  Responsabe de calidad
 * 30/01/24
 */
package juegobarcosv1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Barco de Hundir la flota
 * 
 */

public class Barco {
    public static final int LONGITUD_FRAGATA = 2;
    public static final int LONGITUD_PORTAAVION = 5;
    public static final int LONGITUD_CRUCERO = 3;
    public static final int LONGITUD_DESTRUCTOR = 4;

    public static final String ID_FRAGATA = "Fragata";
    public static final String ID_PORTAAVION = "Portaavion";
    public static final String ID_CRUCERO = "Crucero";
    public static final String ID_DESTRUCTOR = "Destructor";

    private List<Casilla> casillas;

    /**
     * Construye un barco
     * 
     */
    public Barco(List<Casilla> casillas) {
        this.casillas = casillas;
    }

    /**
     * Construye un barco desde un fichero
     * 
     */
    public Barco(Scanner scanner) throws IOException {
        casillas = new ArrayList<>();

        for (int i = 0; i < casillas.size(); i++) {
            casillas.add(new Casilla(scanner));
        }
    }

    /**
     * Construye un barco
     * 
     */
    public boolean valida(Casilla casilla) {
        for (Casilla casilla_array : casillas) {
            if (casilla_array.equals(casilla)) {

                return true;

            }
        }
        return false;
    }

    /**
     * devuelve verdadi si la casilla ha sido tocada
     * 
     */
    public boolean tocada(Casilla casilla) {
        for (Casilla casilla_array : casillas) {
            if (casilla_array.equals(casilla) &&
                    casilla_array.tocada()) {

                return true;
            }
        }
        return false;
    }

    /**
     * devuelve verdad si la casilla coincide con alguna del barco
     * 
     */
    public boolean disparar(Casilla casilla) {
        for (Casilla casilla_array : casillas) {
            if (casilla_array.equals(casilla)) {
                casilla_array.disparar(casilla);
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve verdad si todas las posiciones
     * del barco estan tocadas
     * 
     */
    public boolean estaHundido() {
        for (Casilla casilla_array : casillas) {
            if (!casilla_array.tocada()) {

                return false;
            }
        }
        return true;
    }

    /**
     * Devuelve el tamaño del barco
     * 
     */
    public int devolverLongitud() {
        return casillas.size();
    }

    /**
     * Guarda una partida en un fichero
     * 
     */
    public void guardar(PrintWriter pw) throws IOException {

        pw.print(casillas.size() + " ");
        for (Casilla casilla_array : casillas) {
            casilla_array.guardar(pw);
        }
        pw.println();
    }
}