package proyecto_final;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;

public class Proyecto {
    // Variables de clase
    public static String entrada = "afluenciastc_simple_08_2023.csv"; // Base de datos
    public static String salida = "TestSalida.csv"; // Archivo de salida
    public static String path = "./"; // Directorio
    public static Map<String, ArrayList<String>> metro;

    public static void main(String[] args) {
        // Tomamos el tiempo
        // Tiempo inicial
        long startTime = System.nanoTime();

        // Limpiamos la DB
        try {
            LimpiaBase LimpiaDB = new LimpiaBase(entrada, path, salida, path);
            System.out.println("Creamos el objeto LimpiaDB");
            LimpiaDB.LeeArchivo();
            System.out.println("Corrimos LeeArchivo");
        } catch (IOException e) {
            e.getStackTrace();
        }

        // Calculamos el promedio de todo
        // Solo para revisar que todo funciona
        System.out.println("Sacamos promedio");
        Promedio prom = new Promedio(salida, path);
        prom.LeeArchivo();
        System.out.println("El promedio total es: " + prom.getPromedio());
        System.out.println("Calculando de " + prom.getContador() + " filas");

        ///// Usar un query específico

        // Tiempo final
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime) / 1000000;
        System.out.println("Tiempo transcurrido:" + totalTime + "ms");

    }

}