/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de funcionamiento
 *  Alvaro Perez 870097  Responsabe de calidad
 * 30/01/24
 */

package juegobarcosv1;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Juego Hundir la Flota
 * 
 */
public class HundirLaFlota {
    public static final int COLUMNAS = 9;
    public static final int FILAS = 9;
    public static final int BARCOS_MAX = 4;
    public static final String VERSION = "Hundir la flota 1.0";
    private static String ARG_DEBUG = "-d";
    private static boolean modoDebug = false;

    private Partida partida;

    public HundirLaFlota() {

    }

    /**
     * Crear partida
     * 
     */
    public void crearPartida(int filas, int columnas) {
        partida = new Partida(filas, columnas);
    }

    /**
     * Colocar Barcos de la Partida
     * 
     */
    public void colocarBarcos() {
        partida.colocarBarco(TipoBarco.ID_CRUCERO);
        partida.colocarBarco(TipoBarco.ID_DESTRUCTOR);
        partida.colocarBarco(TipoBarco.ID_FRAGATA);
        partida.colocarBarco(TipoBarco.ID_PORTAAVION);
    }

    /**
     * toString()
     *
     */
    @Override
    public String toString() {
        return partida.toString() + "\n";
    }

    /**
     * Devuelve el resultado del disparo
     *
     */
    public Partida.ResultadoDisparo disparar(Casilla casilla) {
        return partida.disparar(casilla);

    }

    /**
     * Devuelve verdadero si aplicación está en modo debug
     * o falso en caso contrario
     * 
     */
    public static boolean esModoDebug() {
        return modoDebug;
    }

    /**
     * Procesa argumentos de main
     * 
     */
    public static void procesarArgsMain(String[] args) {
        List<String> argumentos = new ArrayList<>(Arrays.asList(args));

        if (argumentos.contains(ARG_DEBUG)) {
            modoDebug = true;
        }
    }

    /**
     * Guarda partida actual
     * 
     */
    public void guardarPartida(String nombreArchivo) throws IOException {
        PrintWriter pw = new PrintWriter(new File(nombreArchivo));

        partida.guardar(pw);
        pw.close();

    }

    /**
     * Recupera una Partida de un Fichero
     * 
     */
    public void cargarPartida(String nombreArchivo) throws IOException {
        Scanner scanner = new Scanner(new FileInputStream(nombreArchivo));

        this.partida = new Partida(scanner);
        scanner.close();

    }
}
