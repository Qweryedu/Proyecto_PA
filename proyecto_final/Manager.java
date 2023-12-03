package proyecto_final;

public class Manager {
  private int cantidadWorkers;

  // Constructor
  public Manager(int cantidadWorkers) {
    this.cantidadWorkers = cantidadWorkers;
  }

  // Métodos
  public void promedioConcurrente() {
    Thread[] manager = new Thread[this.cantidadWorkers];
    // Llenamos el manager
    for (int i = 0; i < this.cantidadWorkers; i += 1) {
      manager[i] = new Thread(new Worker("SubArchivo" + i + ".csv", "./"));
      manager[i].start();
    }
    ////// Obtenemos los datos de suma y cantidad
    /// Calcular el último el promedio
  }
}
