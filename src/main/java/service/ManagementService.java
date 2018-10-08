package service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase para funciones genéricas que se usen en varios lugares
 */
public class ManagementService {

    public Map<String, String> getPostParams(String params) {
        List<String> listaAux = Arrays.asList(params.split("&", -1));
        Map<String, String> parametros = new HashMap<String, String>();
        listaAux.stream().map(elem -> parametros.put(this.getNValueFromSplit(elem, "=", 0), this.getNValueFromSplit(elem, "=", 1))).collect(Collectors.toList());
        return parametros;
    }

    public String getNValueFromSplit(String sentencia, String separador, Integer posicion) {
        List<String> lista = Arrays.asList(sentencia.split(separador, -1));
        return lista.get(posicion);
    }

    
}
