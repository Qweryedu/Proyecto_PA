package proyecto_final;

import java.io.*;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class LimpiaBase extends FileUtil {
  // Esta clase se usa de manera inicial para limpiar la DB
  BufferedWriter bufferEscritura;

  // Constructor
  public LimpiaBase(String entrada, String pathIn, String salida, String pathOut) throws IOException {
    super(entrada, pathIn, salida, pathOut);
    this.bufferEscritura = new BufferedWriter(new FileWriter(new File(pathOut, salida)));
  }

  // Métodos
  @Override
  public void HazAlgo(String[] campos) {
    // Se escribe el archivo
    try {
      this.bufferEscritura.write(campos[0] + "," + campos[3] + "," + campos[4] + "," + campos[5] + "\n");
    } catch (FileNotFoundException e) {
      e.getStackTrace();
    } catch (IOException e) {
      e.getStackTrace();
    }

  }
}
