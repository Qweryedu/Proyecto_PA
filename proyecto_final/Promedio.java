package proyecto_final;

// import java.io.*;

public class Promedio extends FileUtil {
  // Esta clase es la que se encarga de calcular el promedio

  // Variables
  public double promedio = 0; // Se inicializa solo en 0
  public int contador = 0; // Variable que contiene cuantos valores se han sumado

  // Recibe el nombre del archivo que tiene que revisar
  public Promedio(String entrada, String pathIn) {
    super(entrada, pathIn);

  }

  // Métodos
  @Override
  public void HazAlgo(String[] campos) {
    // Magia sucede dentro
    // Método que calcula el promedio
    try {
      this.promedio += Double.parseDouble(campos[3]);
      // Usamos el index 3 porque después de ser limpiada la DB, la afluencia
      // queda en ese index
      this.contador += 1;
    } catch (Exception e) {
      System.out.println("Un valor está chueco en el iterador " + this.contador);
    }

  }

  public double getSuma() {
    return this.promedio;
  }

  public double getPromedio() {
    return this.promedio / this.contador;
  }

  public int getContador() {
    return this.contador;
  }
}
