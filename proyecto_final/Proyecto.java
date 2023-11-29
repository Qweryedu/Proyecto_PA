package proyecto_final;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class Proyecto {
    // Variables de clase
    public static String entrada = "afluenciastc_simple_08_2023.csv"; // Base de datos
    public static String entradaGrande = "DB_extra.csv"; // Base de datos extendida
    public static String salida = "BaseLimpia.csv"; // Archivo de salida
    public static String salidaGrande = "BaseLimpiaGrande.csv";
    public static String path = "./"; // Directorio
    public static Map<String, ArrayList<String>> metro;

    public static void main(String[] args) {
        

        // Limpiamos la DB
        try {
            LimpiaBase LimpiaDB = new LimpiaBase(entrada, path, salida, path);
            LimpiaBase LimpiaDBG = new LimpiaBase(entradaGrande, path, salidaGrande, path);

            System.out.println("Creamos el objeto LimpiaDB");
            LimpiaDB.LeeArchivo();
            LimpiaDBG.LeeArchivo();
            System.out.println("Ya corrimos LeeArchivo");
        } catch (IOException e) {
            e.getStackTrace();
        }
        // metro = LimpiaBase.getMetro();

        /// Usar un query específico
        try {
            Scanner sc = new Scanner(System.in);
            Query querySettings = new Query(sc);
            querySettings.setTipoDeFecha();
            // querySettings.setLineaEstacion(metro);
            // querySettings.setFileNameOut();
            querySettings.LeeArchivo();
        } catch (ParseException e) {
            System.out.println("Error en preguntas.setTipoDeFecha");
            e.getStackTrace();
        }
        
        // Tomamos el tiempo
        // Tiempo inicial
        long tiempoInicioSec = System.nanoTime();

        // Calculamos el promedio de todo
        // Solo para revisar que todo funciona
        System.out.println("Sacamos promedio");
        Promedio prom = new Promedio(salida, path);
        Promedio promG = new Promedio(salidaGrande, path);

        prom.LeeArchivo();
        promG.LeeArchivo();
        System.out.println("El promedio total es: " + prom.getPromedio());
        System.out.println("Calculando de " + prom.getContador() + " filas");
        System.out.println("El promedio total es: " + promG.getPromedio());
        System.out.println("Calculando de " + promG.getContador() + " filas");


        ////// Despachador 
        Despachador despachador = new Despachador(salidaGrande, path, 3);
        despachador.LeeArchivo();
        System.out.println("Se terminaron de hacer los subArchivos");
        // Tiempo final
        long tiempoFinalSec = System.nanoTime();
        long tiempoTotalSec = (tiempoFinalSec  - tiempoInicioSec) / 1000000;
        System.out.println("Tiempo transcurrido:" + tiempoTotalSec + "ms");

    }

}