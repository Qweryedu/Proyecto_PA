package proyecto_final;

public class Worker implements Runnable {
  // Variables
  private Promedio promedio;

  // Constructor
  public Worker(String fileName, String pathIn) {
    // Instanciamos un objeto de Promedio
    promedio = new Promedio(fileName, pathIn);

  }

  @Override
  public void run() {
    // Leemos el archivo
    this.promedio.leeArchivo();
  }

  public double[] getValores() {
    double valores[] = new double[2];
    valores[0] = this.promedio.getSuma();
    valores[1] = this.promedio.getContador();

    return valores;
  }
}
