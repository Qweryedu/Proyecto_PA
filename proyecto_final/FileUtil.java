package proyecto_final;

import java.io.*;

public abstract class FileUtil {
  // Esta clase se va a encargar de lo que esté relacionado a el archivo, ya sea
  // abrirlo, limpiarlo, escribirlo en otro directorio, etc

  // Varibles
  // public String entrada, pathIn;
  // public String salida, pathOut;
  public File archivo, archivoSalida;

  // Constructor
  public FileUtil(String entrada, String pathIn, String salida, String pathOut) {
    // Recibe el nombre del archivo y el path de búsqueda junto con el nombre
    // de salida y el path de salida

    this.archivo = new File(pathIn, entrada);
    this.archivoSalida = new File(pathOut, salida);

    // Aseguramos que el archivo existe
    BuscaDirectorio();
  }

  public FileUtil(String entrada, String pathIn) {

    this.archivo = new File(pathIn, entrada);
    BuscaDirectorio();
  }

  // Métodos
  public void BuscaDirectorio() {
    // Este método verifica que los directorios y el archivo de entrada existan
    try {
      // String path = this.archivo.getAbsolutePath();
      // System.out.println(path);
      // if (!this.archivo.exists()) {
      // System.out.println("El archivo de entrada no existe");
      // System.out.println(this.archivo.exists());
      // throw new Exception();
      // }
    } catch (Exception e) {
      e.getStackTrace();
    }
  }

  public void LeeArchivo() {
    System.out.println("Empezamos a leer el archivo");
    String linea; // Para leer las líneas del archivo
    String[] campos; // Cuando separamos la línea leída

    // Leemos los contenidos del archivo
    try (BufferedReader bufferLectura = new BufferedReader(new FileReader(this.archivo))) {

      System.out.println("Empezamos el ciclo");
      // System.out.println("El header es:");
      // System.out.println(bufferLectura.readLine()); // Se salta la primer línea para evitar el header
      // Leemos, procesamos y escribimos la información relevante
      while ((linea = bufferLectura.readLine()) != null) {
        campos = linea.split(","); // Separamos por coma por ser formato CSV
        // Sucede un proceso
        HazAlgo(campos);

      }
      System.out.println("Termina de hacer cosas");
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    } catch (IOException e) {
      e.getStackTrace();
    }
  }

  public abstract void HazAlgo(String[] campos);

}
