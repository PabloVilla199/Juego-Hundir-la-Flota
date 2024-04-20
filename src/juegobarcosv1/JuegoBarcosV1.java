/**
 * Juego de los barcos 
 * Pablo villa 874773 Responsable de funcionamiento
 *  Alvaro Perez 870097  Responsabe de calidad
 * 30/01/24
 */

package juegobarcosv1;

/**
 * Test de Hundir la flota
 * 
 */

public class JuegoBarcosV1 {
    /**
     * Método main
     * 
     */

    public static void main(String[] args) {
        String nombreArchivo = "Alvaro.txt";
        System.out.println(HundirLaFlota.VERSION);

        HundirLaFlota.procesarArgsMain(args);
        if (HundirLaFlota.esModoDebug()) {
            System.out.println("*** Modo debug *** ");
        }

        HundirLaFlota hundirLaFlota = new HundirLaFlota();
        hundirLaFlota.crearPartida(HundirLaFlota.FILAS, HundirLaFlota.COLUMNAS);
        hundirLaFlota.colocarBarcos();

        for (int i = 0; i < HundirLaFlota.FILAS; i++) {
            for (int j = 0; j < HundirLaFlota.COLUMNAS; j++) {
                System.out.println(hundirLaFlota.disparar(new Casilla(i, j, false)));
                System.out.println(hundirLaFlota.toString());

            }
        }

        try {

            hundirLaFlota.guardarPartida(nombreArchivo);
            System.out.println("Partida guardada exitosamente");
            System.out.println();
        } catch (Exception e) {

            System.err.println("Error al guardar  la partida: ");
            if (HundirLaFlota.esModoDebug()) {
                e.printStackTrace();

            }
        }
        try {

            hundirLaFlota.cargarPartida(nombreArchivo);
            System.out.println("Partida cargada exitosamente");
            System.out.println();

        } catch (Exception e) {

            System.err.println("Error al cargar  la partida: ");
            if (HundirLaFlota.esModoDebug()) {
                e.printStackTrace();

            }
        }
        System.out.println(hundirLaFlota.toString());

    }
}
