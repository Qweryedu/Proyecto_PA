package proyecto_final;

import java.io.*;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LimpiaBase extends FileUtil {
  // Esta clase se usa de manera inicial para limpiar la DB
  // Y para obtener los strings de las diferentes líneas y estaciones

  // Archivo de salida
  BufferedWriter bufferEscritura;
  // Diccionario con las lineas y estaciones
  private static Map<String, ArrayList<String>> metro = new HashMap<String, ArrayList<String>>();

  // Constructor
  public LimpiaBase(
      String entrada, String pathIn,
      String salida, String pathOut) throws IOException {
    super(entrada, pathIn, salida, pathOut);
    this.bufferEscritura = new BufferedWriter(new FileWriter(new File(pathOut, salida)));
  }

  // Métodos
  @Override
  public void HazAlgo(String[] campos) {
    // Se escribe el archivo
    try {
      ////////// Descomponemos los campos en variables ////////////

      // Descomponemos el campo 0 de la fecha
      String fecha = campos[0];
      // // Le damos un nuevo formato a la fecha de YYYYMMDD
      // String nuevaFecha = fecha.substring(0, 4) + fecha.substring(5, 7)
      //     + fecha.substring(8, fecha.length());

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
        // Si no está la línea, se agrega junto con su estación
        metro.put(linea, new ArrayList<String>());
        metro.get(linea).add(estacion);
      }

      // System.out.println(metro);
      ////////// Escribimos el archivo ////////////////////////////
      // Escribimos en el nuevo archivo
      this.bufferEscritura.write(fecha + "," + linea + "," + estacion + "," + afluencia + "\n");
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    } catch (IOException e) {
      e.getStackTrace();
    } catch (StringIndexOutOfBoundsException e) {
      e.getStackTrace();
    }

  }

  public static Map<String, ArrayList<String>> getMetro() {
    return metro;
  }
}
