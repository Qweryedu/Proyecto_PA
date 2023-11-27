package proyecto_final;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Query extends FileUtil {
  // Esta clase pregunta en consola y recibe los parámetros que
  // el usuario quiere buscar, para así segmentar por archivos más
  // pequeños
  private int fechaInicio = 20100101, fechaFinal = 20231231;
  private String tipoFecha;
  private String[] lineas;

  // Constructor
  public Query(String entrada, String pathIn) {
    super(entrada, pathIn);
  }

  // Métodos
  @Override
  public void HazAlgo(String[] campos) {

  }

  public void setTipoDeFecha() {
    // Método que pregunta por los parámetros de búsqueda de las fechas
    System.out.println("Qué tipo de búsqueda desea realizar? Ingrese el dígito de la opción que desea: ");
    System.out.println("1) Histórica \n2) Por periodo");
    while (true) {
      try (Scanner sc = new Scanner(System.in)) {
        int opcion = sc.nextInt();
        if (opcion == 1) {
          this.tipoFecha = "historico";
          break;
        } else if (opcion == 2) {
          this.tipoFecha = "periodo";
          break;
        } else {
          // Igual se podría solamente imprimir dentro de este else el mensaje
          // Pero para vernos fancy estamos lanzando una Excepción
          throw new InputMismatchException();
        }
      } catch (InputMismatchException e) {
        System.out.println("El opción ingresada fue incorrecta, solo ingrese el número de la opción deseada.\n");
      } catch (Exception e) {
        System.out.println("Error en setTipoDeFecha");
      }
    }

    // Solo si es 'periodo' cambiamos las fechas, de otro modo dejamos las
    // fechas como ya están predefinidas
    if (this.tipoFecha == "periodo") {
      // Preguntamos por las fechas de inicio y de final
      System.out.println("Usar el formato AAAAMMDD para ingresar la fecha");
      System.out.println("Ingrese la fecha de inicio");
      while (true) {
        try (Scanner sc = new Scanner(System.in)) {
          this.fechaInicio = sc.nextInt();
          break;
        } catch (InputMismatchException e) {
          System.out.println("Solo escriba el entero de la fecha con el formato adecuado");
        } catch (Exception e) {
          System.out.println("Error en Fecha de Inicio");
        }
      }

      System.out.println("Ingrese la fecha de final");
      while (true) {
        try (Scanner sc = new Scanner(System.in)) {
          this.fechaFinal = sc.nextInt();
          break;
        } catch (InputMismatchException e) {
          System.out.println("Solo escriba el entreo de la fecha en el formato adecuado");
        } catch (Exception e) {
          System.out.println("Error en la Fecha de Final");
        }
      }
    }

  }

  public void setLineaEstacion(Map<String, ArrayList<String>> metro) {
    // Para definir las estaciones que se desean verificar
    // Vamos a necesitar el uso de Regex probablemente para parcear el todo
    // Mostramos las líneas disponibles
    System.out.println("Las líneas del metro son:\n");
    for (String linea : metro.keySet()) {
      System.out.println(linea);
    }
    System.out.println("Ingrese el número (o letra) de la(s) línea(s) que desea revisar (separados por coma)");
    System.out.println("En caso de querer revisar todas, solo ingresar el caracter *");
    try (Scanner sc = new Scanner(System.in)) {
      String str = sc.nextLine();
      if (str != "*") {
        // Se usan las líneas indicadas
        this.lineas = str.split(",");
      } else {
        // Se usan todas las líneas
        this.lineas = (String[]) metro.keySet().toArray();
      }
    } catch (Exception e) {
      System.out.println("Error al ingresar las líneas a revisar");
    }

    // Ahora preguntamos por las estaciones de las líneas que eligieron

  }

}
