/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de funcionamiento
 *  Alvaro Perez 870097  Responsabe de calidad
 * 30/01/24
 */
package juegobarcosv1;

import java.util.Random;
import java.io.PrintWriter;
import java.util.Scanner;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Partida de Hundir la flota
 * 
 */

public class Partida {
    Random random = new Random();

    public enum ResultadoDisparo {
        DISPARO_INVALIDO, BARCO_TOCADO,
        DISPARO_FALLADO, ADYACENTE
    };

    public static final int MAX_INTENTOS = 15;
    public static final char INICIO_COORDENADAS = 'A';
    public static final char SIMBOLO_BARCO = '*';
    public static final char SIMBOLO_AGUA = 'X';

    private Tablero miTablero;
    private List<Casilla> jugadas;

    /**
     * Construye una partida
     * 
     */
    public Partida(int filas, int columnas) {
        miTablero = new Tablero(filas, columnas);
        this.jugadas = new ArrayList<Casilla>();
    }

    /**
     * Construye una partida de un fichero
     * 
     */
    public Partida(Scanner scanner) throws IOException {
        jugadas = new ArrayList<>();
        int tamanio = scanner.nextInt();

        for (int i = 0; i < tamanio; i++) {
            jugadas.add(new Casilla(scanner));
        }

        miTablero = new Tablero(scanner);
    }

    /**
     * Colocar un barco en el tablero
     * 
     */
    public void colocarBarco(String ID) {
        miTablero.colocarBarco(ID);
    }

    /**
     * Dispara a una posicion del tablero
     * 
     */
    public ResultadoDisparo disparar(Casilla casilla) {
        ResultadoDisparo resultado;

        if (!casilla.valida(miTablero.devolverFilas(),
                miTablero.devolverColumnas())) {
            return ResultadoDisparo.DISPARO_INVALIDO;
        }

        resultado = miTablero.disparar(casilla);
        jugadas.add(casilla);

        if (miTablero.esAdyacente(casilla)) {
            return ResultadoDisparo.ADYACENTE;
        }
        return resultado;
    }

    /**
     * toString()
     * 
     */
    public String toString() {
        String s = "";
        s = s + " " + " ";

        for (int k = 0; k < miTablero.devolverColumnas(); k++) {
            s = s + (char) (INICIO_COORDENADAS + k) + " ";
        }

        for (int i = 0; i < miTablero.devolverFilas(); i++) {
            s = s + '\n' + (char) (INICIO_COORDENADAS + i) + " ";
            for (int j = 0; j < miTablero.devolverColumnas(); j++) {

                boolean esBarco = false;
                boolean esJugada = false;

                for (Casilla jugada : jugadas) {
                    if (jugada.equals(new Casilla(i, j,
                            false))) {
                        esBarco = miTablero.esBarco(jugada);
                        esJugada = true;
                        break;
                    }
                }

                if (esJugada && esBarco) {
                    s = s + SIMBOLO_BARCO + " ";
                } else if (esJugada && !esBarco) {
                    s = s + SIMBOLO_AGUA + " ";
                } else {
                    s = s + "  " + " ";
                }
            }
        }
        s = s + '\n' + miTablero.barcosRestantes();
        return s;
    }

    /**
     * Guardar el estado de la partida en el fichero
     * 
     */
    public void guardar(PrintWriter pw) throws IOException {
        pw.println(jugadas.size() + " ");
        for (Casilla jugada : jugadas) {
            jugada.guardar(pw);
        }
        pw.println();
        miTablero.guardar(pw);
        pw.println();
    }

}
