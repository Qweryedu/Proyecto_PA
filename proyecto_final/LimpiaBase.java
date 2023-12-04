package proyecto_final;

// import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LimpiaBase extends FileUtilWriter {
  // Esta clase se usa de manera inicial para limpiar la DB
  // Y para obtener los strings de las diferentes líneas y estaciones
  public int contador = 0;

  // Archivo de salida
  private BufferedWriter bufferEscritura;
  // Diccionario con las lineas y estaciones
  private static Map<String, ArrayList<String>> metro = new HashMap<String, ArrayList<String>>();

  // Constructor
  public LimpiaBase(
      String entrada, String pathIn) throws IOException {
    super(entrada, pathIn);

    this.bufferEscritura = new BufferedWriter(new FileWriter(new File("./ArchivoLimpioTmp.csv")));
  }

  // Métodos
  @Override
  public void hazAlgo(String[] campos) {
    // Se escribe el archivo
    try {
      ////////// Descomponemos los campos en variables ////////////

      // Descomponemos el campo 0 de la fecha
      String fecha = campos[0];
      // Descomponemos el campo 3 en la linea
      String linea = campos[3];
      // Descomponemos el campo 4 en la estacion
      String estacion = campos[4];
      // Descomponemos el campo 5 en la afluencia
      String afluencia = campos[5];

      ////////// Cheamos el diccionario del metro /////////////////

      // Checamos la línea y la estación y se agregan
      if (metro.containsKey(linea)) {
        // Si la línea está registrada, checamos si no está la estación
        if (!metro.get(linea).contains(estacion)) {
          // En caso de que no esté, agregamos la estación
          metro.get(linea).add(estacion);
        }

      } else {
        // Checamos que no sea el header
        if (!linea.equals("linea")) {
          // Si no está la línea, se agrega junto con su estación
          metro.put(linea, new ArrayList<String>());
          metro.get(linea).add(estacion);
        }
      }

      ////////// Escribimos el archivo ////////////////////////////
      // Escribimos en el nuevo archivo
      this.bufferEscritura.write(fecha + "," + linea + "," + estacion + "," + afluencia + "\n");
      // Después de escribir el dato, acumulamos
      this.contador += 1;
      // Limpiamos el buffer
      this.bufferEscritura.flush();

    } catch (FileNotFoundException e) {
      e.getStackTrace();
    } catch (IOException e) {
      e.getStackTrace();
    } catch (StringIndexOutOfBoundsException e) {
      e.getStackTrace();
    }

  }

  @Override
  public void cierraBufferWriter() {
    try {
      this.bufferEscritura.close();
    } catch (IOException e) {
      System.out.println("No sepudo cerrar el buffer en LimpiaBase");
    }
  }

  public static Map<String, ArrayList<String>> getMetro() {
    return metro;
  }

  public void eliminaArchivoTmp() {
    Path path = Paths.get("./ArchivoLimpioTmp.csv");
    try {
      Files.delete(path);
    } catch (IOException e) {
      System.out.println("No se pudo eliminar ArchivoLimpioTmp.csv");
    }
  }
}
