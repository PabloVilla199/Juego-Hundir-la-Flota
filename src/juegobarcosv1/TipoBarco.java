package juegobarcosv1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TipoBarco {
    public static final int LONGITUD_FRAGATA = 2;
    public static final int LONGITUD_PORTAAVION = 5;
    public static final int LONGITUD_CRUCERO = 3;
    public static final int LONGITUD_DESTRUCTOR = 4;

    public static final String ID_FRAGATA = "Fragata";
    public static final String ID_PORTAAVION = "Portaavion";
    public static final String ID_CRUCERO = "Crucero";
    public static final String ID_DESTRUCTOR = "Destructor";

    static Map<String, List<Casilla>> forma = new HashMap<>();

    public TipoBarco() {
        this.forma = forma;

        forma.put(TipoBarco.ID_CRUCERO, new ArrayList<>());
        forma.put(TipoBarco.ID_DESTRUCTOR, new ArrayList<>());
        forma.put(TipoBarco.ID_FRAGATA, new ArrayList<>());
        forma.put(TipoBarco.ID_PORTAAVION, new ArrayList<>());

        forma.get(TipoBarco.ID_CRUCERO).addAll(generarCasillas(TipoBarco.LONGITUD_CRUCERO));
        forma.get(TipoBarco.ID_DESTRUCTOR).addAll(generarCasillas(TipoBarco.LONGITUD_DESTRUCTOR));
        forma.get(TipoBarco.ID_FRAGATA).addAll(generarCasillas(TipoBarco.LONGITUD_FRAGATA));
        forma.get(TipoBarco.ID_PORTAAVION).addAll(generarCasillas(TipoBarco.LONGITUD_PORTAAVION));

    }

    private List<Casilla> generarCasillas(int longitud) {
        List<Casilla> casillas = new ArrayList<>();
        for (int i = 0; i < longitud; i++) {
            casillas.add(new Casilla(0, 0, false));
        }
        return casillas;
    }

}