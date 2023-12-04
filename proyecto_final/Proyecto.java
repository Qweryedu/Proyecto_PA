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

        //////////////// Limpiamos la DB //////////////////////////////
        try {
            LimpiaBase LimpiaDB = new LimpiaBase(entrada, path, salida, path);
            // LimpiaBase LimpiaDBG = new LimpiaBase(entradaGrande, path, salidaGrande,
            // path);

            System.out.println("Creamos el objeto LimpiaDB");
            LimpiaDB.LeeArchivo();
            // LimpiaDBG.LeeArchivo();
            System.out.println("Ya limpiamos");
            LimpiaDB.CierraBufferWritter();
        } catch (IOException e) {
            e.getStackTrace();
        }

        // Obtenemos el metro
        metro = LimpiaBase.getMetro();
        // System.out.println(metro);

        ////////// Usar un query específico ///////////////////////////
        try {
            Scanner sc = new Scanner(System.in);
            Query querySettings = new Query(sc);
            // querySettings.setTipoDeFecha();
            // querySettings.setLineaEstacion(metro);
            querySettings.setSettings(metro);
        } catch (ParseException e) {
            System.out.println("Error en setSettings");
            e.getStackTrace();
        }

        // Tomamos el tiempo
        // Tiempo inicial
        double tiempoInicioSec = System.nanoTime();

        // Calculamos el promedio de todo
        // Solo para revisar que todo funciona
        System.out.println("Sacamos promedio");
        Promedio prom = new Promedio(salida, path);
        // Promedio promG = new Promedio(salidaGrande, path);

        // Iteramos el archivo
        prom.LeeArchivo();
        // promG.LeeArchivo();

        prom.CierraBufferWritter();
        // Tiempo final
        double tiempoFinalSec = System.nanoTime();
        double tiempoTotalSec = (tiempoFinalSec - tiempoInicioSec) / 1000000;
        System.out.println("Tiempo transcurrido secuencial: " + tiempoTotalSec + "ms");

        ///////////////////////////////// PARALELO ////////////////////////////
        System.out.println("\n\nIniciamos el proceso paralelo\n");

        ////// Despachador
        Despachador despachador = new Despachador(salida, path, 3);
        despachador.LeeArchivo();
        despachador.CierraBufferWritter();
        System.out.println("Se terminaron de hacer los subArchivos");

        System.out.println("Inicia el procesamiento");
        double tiempoInicioPar = System.nanoTime();
        //////// Parte concurrente
        Manager manager = new Manager(despachador.getCantidadArchivos());
        manager.promedioConcurrente();
        manager.juntaHilos();

        double tiempoFinalPar = System.nanoTime();
        despachador.limpiaArchivos();
        double tiempoTotalPar = (tiempoFinalPar - tiempoInicioPar) / 1000000;
        System.out.println("Tiempo transcurrido de manera paralela: " + tiempoTotalPar + "ms");

        // Enseñamos el promedio calculado
        System.out.println("El promedio total es: " + prom.getPromedio());
        System.out.println("Calculando de " + prom.getContador() + " filas");
        // System.out.println("El promedio total es: " + promG.getPromedio());
        // System.out.println("Calculando de " + promG.getContador() + " filas");
        manager.printPromedio();
        System.out.println("El Speed-Up es de: ");
        System.out.printf("%f", tiempoTotalSec / tiempoTotalPar);
        System.out.println(tiempoTotalSec + "/" + tiempoTotalPar);

    }

}