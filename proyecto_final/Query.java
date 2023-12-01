package proyecto_final;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;
import java.util.HashMap;
// import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Query {
  // Esta clase pregunta en consola y recibe los parámetros que
  // el usuario quiere buscar, para así confirmar si el query es válido

  // Definimos las fechas
  public static SimpleDateFormat sdf; // Formato de las fechas
  private static Date fechaInicio;
  private static Date fechaFinal;
  private static String tipoFecha; // Entre Histórico o por periodos
  // private String[] lineas; // líneas que se desean consultar

  // Metro que se va a usar
  public static Map<String, ArrayList<String>> metroMuestra;

  // Scanner para la consulta de los datos
  private Scanner sc;

  // Constructor
  // public Query(String entrada, String pathIn, Scanner sc) throws ParseException
  // {
  // this.sc = sc;
  // }

  public Query(Scanner sc) throws ParseException {
    this.sc = sc;
    // Definimos el formato de fecha y las fechas de inicio y fin
    sdf = new SimpleDateFormat("yyyy-MM-dd");
    sdf.setLenient(false);
    fechaInicio = sdf.parse("2010-01-01");
    fechaFinal = sdf.parse("2023-12-31");
    System.out.println("\nmetroMuestra: ");
    metroMuestra = new HashMap<>();
    System.out.println(metroMuestra);
  }

  // Métodos
  public void setTipoDeFecha() {
    // Método que pregunta por los parámetros de búsqueda de las fechas
    System.out.println("Qué tipo de búsqueda desea realizar? Ingrese el dígito de la opción que desea: ");
    System.out.println("1) Histórica \n2) Por periodo");
    // Iniciamos el scanner
    // Scanner sc = new Scanner(System.in);
    while (true) {
      try {
        int opcion = this.sc.nextInt();
        if (opcion == 1) {
          tipoFecha = "historico";
          break;
        } else if (opcion == 2) {
          tipoFecha = "periodo";
          break;
        } else {
          // Igual se podría solamente imprimir dentro de este else el mensaje
          // Pero para vernos fancy estamos lanzando una Excepción
          throw new InputMismatchException();
        }
      } catch (InputMismatchException e) {
        this.sc.nextLine(); // limpiamos el buffer
        System.out.println("El opción ingresada fue incorrecta, solo ingrese el número de la opción deseada.");
      } catch (Exception e) {
        System.out.println("Error en setTipoDeFecha");
      }
    }

    // Solo si es 'periodo' cambiamos las fechas, de otro modo dejamos las
    // fechas como ya están predefinidas
    if (tipoFecha.equals("periodo")) {
      /////////// Preguntamos por las fechas de inicio y de final ////////////////
      System.out.println("Usar el formato AAAA-MM-DD para ingresar la fecha");
      System.out.println("Ingrese la fecha de inicio");
      while (true) {
        try {
          // Se lee la fecha
          String inicio = this.sc.nextLine();
          // Se convierte a LocalDateTime
          fechaInicio = sdf.parse(inicio);
          break;
        } catch (InputMismatchException e) {
          this.sc.nextLine();
          System.out.println("Solo escriba el entero de la fecha con el formato adecuado");
        } catch (Exception e) {
          System.out.println("Error en Fecha de Inicio");
        }
      }

      System.out.println("Ingrese la fecha de final");
      while (true) {
        try {
          // Se lee la fecha
          String fFinal = this.sc.nextLine();
          // Se convierte a LocalDateTime
          fechaFinal = sdf.parse(fFinal);
          break;
        } catch (InputMismatchException e) {
          this.sc.nextLine();
          System.out.println("Solo escriba el entreo de la fecha en el formato adecuado");
        } catch (Exception e) {
          System.out.println("Error en la Fecha de Final");
        }
      }
    }
    //// No tiene else porque en el constructor las fechas se están definiendo entre
    //// los intervalos máximos

  }

  public void setLineaEstacion(Map<String, ArrayList<String>> metro) {
    // Para definir las estaciones que se desean verificar
    // Vamos a necesitar el uso de Regex probablemente para parcear el todo
    // Mostramos las líneas disponibles
    System.out.println("Las líneas del metro son:");
    for (String linea : metro.keySet()) {
      System.out.println(linea);
    }
    System.out.println("Ingrese uno por uno el número (o letra) de la(s) línea(s) que desea revisar");
    System.out.println("Ingrese 'exit' para finalizar");
    System.out.println("En caso de querer revisar todas ingresar '*' ");

    while (true) {
      try {
        // Obtenemos la linea
        String str = this.sc.nextLine();
        System.out.println("str: " + str);
        // Quitamos todos los espacios del string
        str = str.replaceAll("\\s", "");

        // Preguntamos si es *
        if (str.equals("*")) {
          System.out.println("Se usan todas las líneas");
          // Limpiamos el mapa por si tenía valores ya dentro
          // metroMuestra.clear();
          // Iteramos todas las líneas y las metemos a metroMuestra
          for (String linea : metro.keySet()) {
            metroMuestra.putIfAbsent(linea, new ArrayList<String>());
          }
          break; // Paramos el while

        } else if (str.equals("exit")) {
          System.out.println("Se usarán las siguientes líneas: ");
          for (String linea : metroMuestra.keySet()) {
            System.out.println(linea);
          }
          break; // Paramos el while

        } else {
          // Checamos si es una entrada válida y la ingresamos si no está
          if (metro.containsKey("Línea " + str)) {
            System.out.println("Intentamos agregar la línea " + str);
            metroMuestra.putIfAbsent("Línea " + str, new ArrayList<String>());
          } else {
            System.out.println(str + "No es una línea");
          }
        }
      } catch (InputMismatchException e) {
        sc.nextLine(); // Limpiamos el buffer
        System.out.println("Favor de solo ingresar elementos disponibles");
      } catch (NullPointerException e) {
        System.out.println("Error al intentar ingresar la linea");
        System.out.println(metroMuestra);
        e.getStackTrace();
        // } catch (Exception e) {
        // System.out.println("Error al ingresar las líneas a revisar");
        // e.getStackTrace();
      }
    }
    // System.out.println(metroMuestra);

    /////// Averiguamos las estaciones
    System.out.println("Ahora las estaciones por línea");
    System.out.println("Favor de escribirlas como se muestran");
    System.out.println("En caso de querer todas las estaciones, escribir '*'");
    // Iteramos por las líneas sí ingresadas
    for (String linea : metroMuestra.keySet()) {
      System.out.println("Las estaciones de la línea son:");
      System.out.println(metro.get(linea));
      while (true) {
        try {
          // Obtenemos la estación
          String str = this.sc.nextLine();
          // Si quiere todas las estaciones
          if (str.equals("*")) {
            // Recorremos las estaciones de la línea
            for (String estacion : metro.get(linea)) {
              // Si no la contiene, la agregamos
              if (!metroMuestra.get(linea).contains(estacion)) {
                metroMuestra.get(linea).add(estacion);
              }
            }
            break;
            // Si quiere salir
          } else if (str.equals("exit")) {
            // Cuando quiere salir
            System.out.println("Las estaciones a checar son:");
            System.out.println(metroMuestra.get(linea));
            break;
            // Si ingresó un valor válido
          } else {
            // Verificamos la linea y la ingresamos
            if (metro.get(linea).contains(str)) {
              // En caso que no se encuentre ya dentro
              if (!metroMuestra.get(linea).contains(str)) {
                metroMuestra.get(linea).add(str);
              }
            } else {
              System.out
                  .println("La estación ingresada no se encuentra en esta línea o no fue escrita incorrectamente");
            }
          }
        } catch (InputMismatchException e) {
          System.out.println("Error en el ingreso de la estación");
        }
      }
    }
  }

  public static boolean checaQuery(String[] campos) {
    try {
      // Definimos los campos
      String linea = campos[1];
      String estacion = campos[2];
      // String afluencia = campos[3];

      // System.out.println(estacion.equals("Línea 3"));
      // Formateamos la fecha
      String fecha = campos[0];
      // System.out.println(fecha);
      // Cambiamos la fecha a Date

      Date nuevaFecha = sdf.parse(fecha);
      // System.out.println(nuevaFecha);
      // Checamos si el dato entra
      // System.out.println(linea);
      // System.out.println(linea.equals("Línea 3"));
      if (nuevaFecha.after(fechaInicio) && nuevaFecha.before(fechaFinal)) {
        // Checamos las líneas
        if (metroMuestra.containsKey(linea)) {
          // Checamos las estaciones
          if (metroMuestra.get(linea).contains(estacion)) {
            return true;
          }
        }
        // System.out.println("Entra dato");

      } else {
        return false;
      }

    } catch (ParseException e) {
      System.out.println("Error del Parse en HazAlgo");
      // e.printStackTrace();
    }
    return false;
  }

}
