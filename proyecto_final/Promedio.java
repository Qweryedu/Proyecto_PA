package proyecto_final;

// import java.io.*;

public class Promedio extends FileUtil {
  // Esta clase es la que se encarga de calcular el promedio

  // Variables
  private double promedio = 0; // Se inicializa solo en 0
  private int contador = 0; // Variable que contiene cuantos valores se han sumado

  // Recibe el nombre del archivo que tiene que revisar
  public Promedio(String entrada, String pathIn) {
    super(entrada, pathIn);

  }

  // Métodos
  @Override
  public void HazAlgo(String[] campos) {
    // Magia sucede dentro
    // Método que calcula el promedio
    // Si los campos cumplen con el Query, entonces se toman en cuenta
    if (Query.checaQuery(campos)); {
      try {
        this.promedio += Double.parseDouble(campos[campos.length - 1]);
        // Usamos el último index  porque la afluencia queda en ese index
        this.contador += 1;
      } catch (Exception e) {
        System.out.println("Un valor está chueco en el iterador " + this.contador);
      }
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
