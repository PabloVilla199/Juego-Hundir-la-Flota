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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Tablero de Hundir la flota
 * 
 */
public class Tablero {
    public static final int MAX_INTENTOS = 15;
    public static final char INICIO_COORDENADAS = 'A';
    public static final char SIMBOLO_BARCO = '*';
    public static final char SIMBOLO_AGUA = 'X';
    public static final int INCREMENTO_FILAS_COLUMNAS = 1;
    public static final int BARCO_TOCADO = 1;
    public static final int BARCO_ADYACENTE = 2;
    public static final int AGUA = 0;

    private int filas;
    private int columnas;
    private Map<String, List<Barco>> barcos;

    /**
     * Construye el tablero de juego
     * 
     */
    public Tablero(int columnas, int filas) {
        this.columnas = columnas;
        this.filas = filas;
        this.barcos = new HashMap<>();

        barcos.put(TipoBarco.ID_CRUCERO, new ArrayList<>());
        barcos.put(TipoBarco.ID_DESTRUCTOR, new ArrayList<>());
        barcos.put(TipoBarco.ID_FRAGATA, new ArrayList<>());
        barcos.put(TipoBarco.ID_PORTAAVION, new ArrayList<>());

    }

    /**
     * Construye el tablero de juego de un fichero
     * 
     */
    public Tablero(Scanner scanner) throws IOException {
        this.filas = scanner.nextInt();
        this.columnas = scanner.nextInt();
        this.barcos = new HashMap<>();

        int cantidadDeBarcos = scanner.nextInt();

        for (int i = 0; i < cantidadDeBarcos; i++) {
            String IDBarco = scanner.next();
            int numCasillas = scanner.nextInt();
            List<Casilla> casillas = new ArrayList<>();

            for (int j = 0; j < numCasillas; j++) {
                int fila = scanner.nextInt();
                int columna = scanner.nextInt();
                casillas.add(new Casilla(fila, columna, false));
            }
            if (!barcos.containsKey(IDBarco)) {
                barcos.put(IDBarco, new ArrayList<>());
            }
            TipoBarco tipoBarco = new TipoBarco(IDBarco);
            barcos.get(IDBarco).add(new Barco(casillas, tipoBarco, IDBarco));
        }
    }

    /**
     * Devuelve VERDAD si alguna casilla de un posible barco
     * es la misma que la de un barco actual
     * en caso contrario devuelve FALSO
     * 
     */
    public boolean solapamiento(List<Casilla> casillas) {
        for (int j = 0; j < casillas.size(); j++) {
            for (List<Barco> listaBarcos : barcos.values()) {
                for (Barco barco : listaBarcos) {
                    if (barco.valida(casillas.get(j))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Devuelve FALSO si alguna posicion de un posible
     * barco se encuentra fuera del rango del tablero
     * en caso contrario devuelve VERDAD
     *
     */
    public boolean barcoValido(List<Casilla> casillas) {
        for (Casilla casillas_array : casillas) {
            if (!valida(casillas_array)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Coloca un Barco de una clave especifica de manera aleatoria
     * 
     */
    public boolean colocarBarco(String ID) {
        TipoBarco tipoBarco = new TipoBarco(ID);
        List<Casilla> casillasBarco = new ArrayList<>();
        boolean colocado = false;
        int intentos = 0;
        Barco barco = new Barco(casillasBarco, tipoBarco, ID);

        do {
            casillasBarco = barco.generarCasillas(ID);

            if (barcoValido(casillasBarco) && !solapamiento(casillasBarco)) {
                colocado = true;
            }
            intentos++;

        } while (!colocado && intentos < MAX_INTENTOS);

        if (colocado) {
            barcos.get(ID).add(new Barco(casillasBarco, tipoBarco, ID));
            return true;
        }
        return false;
    }

    /**
     * Devuelve VERDAD si la casilla forma parte de un barco
     * 
     */
    public boolean esBarco(Casilla casilla) {
        for (List<Barco> listaBarcos : barcos.values()) {
            for (Barco barco : listaBarcos) {
                if (barco.tocada(casilla)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * String que muestra los barcos que aun no han sido hundidos
     * 
     */
    public String barcosRestantes() {
        String tipoBarco = "";
        String s = "Barcos restantes:\n";

        for (Map.Entry<String, List<Barco>> entry : barcos.entrySet()) {
            tipoBarco = entry.getKey();
            contarBarcosHundidos(tipoBarco);
            s += tipoBarco + ":" + contarBarcosHundidos(tipoBarco) + " ";
        }
        return s;
    }

    public int contarBarcosHundidos(String ID) {
        int barcosHundidos = 0;

        if (barcos.containsKey(ID)) {
            for (Barco barco : barcos.get(ID)) {
                if (barco.estaHundido()) {
                    barcosHundidos++;
                }
            }
        }

        return barcosHundidos;
    }

    /**
     * Devuelve verdad si la casilla esta dentro del tablero
     * 
     */
    public boolean valida(Casilla casilla) {
        return casilla.valida(filas, columnas);
    }

    /**
     * Disparo en el tablero
     * 
     */
    public Partida.ResultadoDisparo disparar(Casilla casilla) {
        for (List<Barco> listaBarcos : barcos.values()) {
            for (Barco barco : listaBarcos) {
                if (barco.disparar(casilla)) {
                    return Partida.ResultadoDisparo.BARCO_TOCADO;
                }
            }
        }
        return Partida.ResultadoDisparo.DISPARO_FALLADO;
    }

    /**
     * devuelve VERDAD si una casilla es adyacente a un Barco
     * 
     */
    public boolean esAdyacente(Casilla casilla) {
        int[][] casillasAdyacentes = { { 0, 1 }, { 1, 0 }, { 1, 1 },
                { 0, -1 }, { -1, 0 }, { -1, 1 },
                { 1, -1 }, { -1, -1 } };

        for (int i = 0; i < casillasAdyacentes.length; i++) {

            int[] casillaAdyacente = casillasAdyacentes[i];
            Casilla adyacente = new Casilla(filas + casillaAdyacente[0],
                    columnas + casillaAdyacente[1],
                    false);

            if (adyacente.equals(casilla) && !casilla.tocada()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve las columnas del tablero
     * 
     */
    public int devolverColumnas() {
        return columnas;
    }

    /**
     * Devuelve las columnas del tablero
     * 
     */
    public int devolverFilas() {
        return filas;
    }

    /**
     * Guarda el tablero en un fichero
     * 
     */
    public void guardar(PrintWriter pw) throws IOException {
        pw.print(filas + " ");
        pw.println(columnas);
        pw.println(barcos.size());

        for (List<Barco> listaBarcos : barcos.values()) {
            for (Barco barco : listaBarcos) {
                // pw.print( guardar id del barco)
                barco.guardar(pw);
            }
        }
    }
}