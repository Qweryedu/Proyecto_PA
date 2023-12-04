package proyecto_final;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Filtra extends FileUtilWriter {
  // Clase para filtrar con el Query el archivo limpio

  // variables
  private BufferedWriter escritor;
  private String entrada, pathOut;
  private String salida;

  // Constructor
  public Filtra(String entrada, String pathOut) {
    super("ArchivoLimpioTmp.csv", "./");
    this.entrada = entrada;
    this.pathOut = pathOut;

    ////// Determinamos el nombre del archivo de salida
    // Definimos el formateo correcto
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmm");
    LocalDateTime ahora = LocalDateTime.now();

    this.salida = this.entrada.replaceAll(".csv", "") + "_filtered(" + dtf.format(ahora).toString() + ").csv";

    System.out.println("El archivo de salida es: " + this.salida);

    try {
      // Instanciamos el Buffre de escritura
      this.escritor = new BufferedWriter(new FileWriter(new File(this.pathOut, this.salida)));
    } catch (IOException e) {
      System.out.println("Error de IO al crear el escritor en Filtra");
    }
  }

  // Métodos
  @Override
  public void hazAlgo(String[] campos) {
    try {
      // Si cumple con el Query, escribimos el valor en el archivo de salida
      if (Query.checaQuery(campos)) {
        this.escritor.write(campos[0] + "," + campos[1] + "," + campos[2] + "," + campos[3] + "\n");
        this.escritor.flush();
      }
    } catch (IOException e) {
      System.out.println("Error de IO en filtra-hazAlgo");
    }
  }

  @Override
  public void cierraBufferWriter() {
    try {
      this.escritor.close();
    } catch (IOException e) {
      System.out.println("Error al intentar cerrar el Writer de Filtra");
    }
  }

  public String getArchivoFinal() {
    return this.salida;
  }

  public void eliminaArchivo() {
    Path path = Paths.get(this.pathOut, this.salida);
    try {
      Files.delete(path);
    } catch (IOException e) {
      System.out.println("No se pudo eliminar el archivo filtrado de salida");
    }
  }
}
