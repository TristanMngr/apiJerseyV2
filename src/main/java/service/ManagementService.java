package service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Clase para funciones genéricas que se usen en varios lugares
 */
public class ManagementService {

    public List<String> getPostParams(String params) {
        List<String> listaAux = Arrays.asList(params.split("&", -1));

        List<String> parametros = listaAux.stream().map(elem -> this.getNValueFromSplit(elem, "=", 1)).collect(Collectors.toList());
        for (String s : parametros) {
//            System.out.println(getNValueFromSplit(s, "=", 0) + ": " + getNValueFromSplit(s, "=", 1));
            System.out.println(s);
        }
//        System.out.println(parametros);
//
//        System.out.println(parametros.get(0));
//        System.out.println(parametros.get(1));
//        System.out.println(parametros.get(2));
//        System.out.println(parametros.get(3));
//        System.out.println(parametros.get(4));
        return listaAux;
    }

    public String getNValueFromSplit(String sentencia, String separador, Integer posicion) {
        List<String> lista = Arrays.asList(sentencia.split(separador, -1));
        return lista.get(posicion);
    }

}
