package proyecto_final;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Manager {
  private int cantidadWorkers;
  private double totalSuma, totalContador; // Se inician en 0
  private Thread[] objetosHilo;
  private Worker[] objetosWorker;
  // Instanciamos el candado
  private static final Lock candado = new ReentrantLock();

  // Constructor
  public Manager(int cantidadWorkers) {
    this.cantidadWorkers = cantidadWorkers;
  }

  // Métodos
  public void promedioConcurrente() {
    // Para almacenar hilos
    this.objetosHilo = new Thread[this.cantidadWorkers];
    // Para almacenar los Workers (Runnables)
    this.objetosWorker = new Worker[this.cantidadWorkers];

    // Llenamos el manager
    for (int i = 0; i < this.cantidadWorkers; i += 1) {
      // objetosRunnable[i] = new Worker("SubArchivo" + i + ".csv", "./");
      // objetosRunnable[i].start();
      objetosWorker[i] = new Worker("SubArchivo" + i + ".csv", "./");
      objetosHilo[i] = new Thread(objetosWorker[i]);
      objetosHilo[i].start();
    }
    // Checamos si han acabado los hilos para sumar los valores respectivos
    int hilosTerminados = 0;
    int index = 0;
    while (hilosTerminados < this.cantidadWorkers) {
      Thread hilo = objetosHilo[index % this.cantidadWorkers];
      Worker worker = objetosWorker[index % this.cantidadWorkers];

      // Checamos si ya terminó el hilo
      if (!hilo.isAlive()) {
        this.sumaPromedios(worker.getValores());
        // Actualizamos la cantidad de hilosTerminados
        hilosTerminados += 1;
      }
      // Aumentamos el contador
      index += 1;
    }
    ////// Obtenemos los datos de suma y cantidad
    /// Calcular el último el promedio
  }

  public void sumaPromedios(double[] valores) {
    // Obtiene los valores de la suma de afluencia y la cantidad de
    // elementos revisados
    // Cerramos el candado
    candado.lock();
    try {
      this.totalSuma += valores[0];
      this.totalContador += valores[1];
    } finally {
      // Abrimos el candado
      candado.unlock();
    }
  }

  public void juntaHilos() {
    for (int i = 0; i < this.cantidadWorkers; i += 1) {
      try {
        this.objetosHilo[i].join();
      } catch (InterruptedException e) {
        System.out.println("Error al juntar hilos");
        e.printStackTrace();
      }
    }
  }

  public void printPromedio() {
    double prom = this.totalSuma / this.totalContador;
    System.out.println("El promedio es: " + prom);
  }
}
