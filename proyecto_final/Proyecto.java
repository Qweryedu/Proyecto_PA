package proyecto_final;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Proyecto {
    // Variables de clase
    public static String entrada = "afluenciastc_simple_08_2023.csv"; // Base de datos
    public static String entradaGrande = "Datos10aniosMas.csv"; // Base de datos extendida
    public static String salida = "BaseLimpia.csv"; // Archivo de salida
    // public static String salidaGrande = "BaseLimpiaGrande.csv";
    public static String path = "./"; // Directorio
    public static String pathSalida = "./";
    public static Map<String, ArrayList<String>> metro;
    public static boolean conservarArchivo = true;

    public static void main(String[] args) {
        // Instanciamos el Scanner
        Scanner sc = new Scanner(System.in);

        // Pedimos el path/directorio
        System.out.println("Escriba el path de búsqueda del directorio");
        while (true) {
            path = sc.nextLine();
            // Checamos que exista
            File dir = new File(path);
            if (!dir.exists()) {
                System.out.println("El directorio no existe");
            } else {
                // El directorio es correcto y mostramos los archivos
                System.out.printf("Los archivos dentro del directorio %s son: ", path);
                String[] fileList = dir.list();
                for (String fileName : fileList) {
                    System.out.println(fileName);
                }
                break;
            }
        }

        // Pedimos el nombre del archivo csv
        System.out.println("Escriba el nombre del archivo");
        while (true) {
            entrada = sc.nextLine();
            // Checamos que exista el archivo
            File file = new File(path, entrada);
            if (!file.exists()) {
                System.out.println("El archivo no existe en el path: ");
                System.out.println(path + entrada);
            } else {
                System.out.println("Se usará el archivo " + path + entrada);
                break;
            }
        }

        // Pedimos el path para el archivo de salida
        System.out.println("Escribe el path para el archivo de salida");
        while (true) {
            pathSalida = sc.nextLine();
            File dirSalida = new File(pathSalida);
            if (!dirSalida.exists()) {
                System.out.println("El path de salida no existe, se intentará crear");
                try {
                    Files.createDirectories(Paths.get(pathSalida));
                    break;
                } catch (Exception e) {
                    e.getStackTrace();
                }
            } else {
                System.out.println("El path de salida es correcto");
                break;
            }
        }
        //////////////// Limpiamos la DB //////////////////////////////
        try {
            LimpiaBase LimpiaDB = new LimpiaBase(entrada, path, pathSalida);
            // LimpiaBase LimpiaDBG = new LimpiaBase(entradaGrande, path, salidaGrande,
            // path);

            System.out.println("Creamos el objeto LimpiaDB");
            LimpiaDB.leeArchivo();
            // LimpiaDBG.LeeArchivo();
            System.out.println("Ya limpiamos");
            LimpiaDB.cierraBufferWriter();
        } catch (IOException e) {
            e.getStackTrace();
        }

        // Obtenemos el metro
        metro = LimpiaBase.getMetro();
        // System.out.println(metro);

        ////////// Usar un query específico ///////////////////////////
        try {
            // Scanner sc = new Scanner(System.in);
            Query querySettings = new Query(sc);
            // querySettings.setTipoDeFecha();
            // querySettings.setLineaEstacion(metro);
            querySettings.setSettings(metro);
        } catch (ParseException e) {
            System.out.println("Error en setSettings");
            e.getStackTrace();
        } finally {
            sc.close();
        }

        Filtra filtro = new Filtra(entrada, pathSalida);
        filtro.leeArchivo();
        // Tomamos el tiempo
        // Tiempo inicial
        double tiempoInicioSec = System.nanoTime();

        // Calculamos el promedio de todo
        // Solo para revisar que todo funciona
        System.out.println("Sacamos promedio");
        Promedio prom = new Promedio(filtro.getArchivoFinal(), pathSalida);
        // Promedio promG = new Promedio(salidaGrande, path);

        // Iteramos el archivo
        prom.leeArchivo();
        // promG.LeeArchivo();

        // Tiempo final
        double tiempoFinalSec = System.nanoTime();
        double tiempoTotalSec = (tiempoFinalSec - tiempoInicioSec) / 1000000;
        System.out.println("Tiempo transcurrido secuencial: " + tiempoTotalSec + "ms");

        ///////////////////////////////// PARALELO ////////////////////////////
        System.out.println("\n\nIniciamos el proceso paralelo\n");
        System.out.println("Cuántos hilos desea crear?");
        int numHilos;
        while (true) {
            try {
                numHilos = sc.nextInt();
                if (numHilos <= 0) {
                    System.out.println("Solo ingresar enteros mayores a 0");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Solo ingresar un entero para los hilos");
            }

        }
        ////// Despachador
        Despachador despachador = new Despachador(filtro.getArchivoFinal(), path, numHilos);
        despachador.leeArchivo();
        despachador.cierraBufferWriter();
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
        // System.out.println("El Speed-Up es de: ");
        System.out.printf("El Speed-Up es de: %f", tiempoTotalSec / tiempoTotalPar);
        System.out.println("\n" + tiempoTotalSec + "/" + tiempoTotalPar);
    }
}