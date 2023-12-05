package proyecto_final;

import java.io.*;

public abstract class FileUtil {
  // Esta clase se va a encargar de lo que esté relacionado a el archivo, ya sea
  // abrirlo, limpiarlo, escribirlo en otro directorio, etc

  // Varibles
  // public String entrada, pathIn;
  // public String salida, pathOut;
  public File archivo;
  // public File archivoSalida;

  // Constructor
  public FileUtil(String entrada, String pathIn) {

    this.archivo = new File(pathIn, entrada);
    // BuscaDirectorio();
  }

  // Métodos
  public void leeArchivo() {
    String linea; // Para leer las líneas del archivo
    String[] campos; // Cuando separamos la línea leída

    // Leemos los contenidos del archivo
    try (BufferedReader bufferLectura = new BufferedReader(new FileReader(this.archivo))) {
      // Leemos, procesamos y escribimos la información relevante
      while ((linea = bufferLectura.readLine()) != null) {
        campos = linea.split(","); // Separamos por coma por ser formato CSV
        // Sucede un proceso
        hazAlgo(campos);

      }
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    } catch (IOException e) {
      e.getStackTrace();
    }
  }

  public abstract void hazAlgo(String[] campos);
}
