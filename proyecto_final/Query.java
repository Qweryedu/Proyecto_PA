package proyecto_final;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Query extends FileUtil {
  // Esta clase pregunta en consola y recibe los parámetros que
  // el usuario quiere buscar, para así segmentar por archivos más
  // pequeños
  // Definimos las fechas
  // TEST
  public static SimpleDateFormat sdf;
  private static Date fechaInicio;
  private static Date fechaFinal;
  // public static DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  // private static LocalDateTime fechaInicio = LocalDateTime.parse("2010-01-01", f);
  // private static LocalDateTime fechaFinal  = LocalDateTime.parse("2023-12-31", f);
  private static String tipoFecha;
  private String[] lineas;
  // Metro que se va a usar
  public Map<String, ArrayList<String>> metroMuestra;

  //Scanner
  private Scanner sc;

  // Path donde se van a mandar los archivos a revisar
  private static String pathOut = "./";

  // Declaramos el FileBufferWriter
  private BufferedWriter archivoSalida;

  // Constructor
  public Query(String entrada, String pathIn, Scanner sc) throws ParseException {
    super(entrada, pathIn);
    this.sc = sc;
  }

  public Query(Scanner sc) throws ParseException {
    this.sc = sc;
    // Definimos el formato de fecha y las fechas de inicio y fin
    sdf = new SimpleDateFormat("yyyy-MM-dd");
    fechaInicio = sdf.parse("2010-01-01");
    fechaFinal  = sdf.parse("2023-12-31");
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
    if (tipoFecha == "periodo") {
      // Preguntamos por las fechas de inicio y de final
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
    System.out.println("En caso de querer revisar todas, solo dar enter");
    
    // boolean tieneLineas = false;
    while (true) {
      try {
        String str = this.sc.nextLine(); 
        System.out.println("str: " + str);
        // Quitamos todos los espacios del string
        str = str.replaceAll("\\s", "");

        // Verificamos en caso de no ser todas las estaciones
        if (!str.equals("*")) {
          // Se usan las líneas indicadas
          this.lineas = str.split(",");
          System.out.println("Despues de partir: " + this.lineas);
          System.out.println("Las líneas elegidas son:");
          for(String l : this.lineas) {
            if (metro.containsKey("Línea " + l)) {
              System.out.println("Línea " + l);
              metroMuestra.put("Línea " + l, new ArrayList<>());
            } else {
              System.out.println("La línea " + l + " no existe");
            }
          }
          // tieneLineas = true;
          break;
        // Se usan todas las líneas
        } else {
          System.out.println("Se usan todas las líneas");
          System.out.println( metro.keySet().toArray());
          this.lineas = (String[]) metro.keySet().toArray();
          for (String linea : this.lineas) {
            metroMuestra.put("Línea " + linea, new ArrayList<>());
          }
          System.out.println(metroMuestra);
          // System.out.println("Se eligieron todas las líneas");
          // tieneLineas = true;
          break;
        }
      } catch (InputMismatchException e) {
        sc.nextLine(); // Limpiamos el buffer
        System.out.println("Favor de solo ingresar elementos disponibles");
      // } catch (Exception e) {
      //   System.out.println("Error al ingresar las líneas a revisar");
      //   e.getStackTrace();
      }
      
    }
    System.out.println(metroMuestra);
    /////// Averiguamos las estaciones
    System.out.println("Averiguamos las estaciones");
    // while (true) {
    //   for (String linea : metroMuestra.keySet()) {
    //     for (String estaciones : metro.get(linea)) {
    //       System.out.println(estaciones);

    //       }
    //     }
    //     break;
    //   }
      
    }
    // System.out.println("Ingrese el asdf");

  public void setFileNameOut(String filename) {
    //Definimos el BufferWriter
    try {
      this.archivoSalida = new BufferedWriter(new FileWriter(new File(pathOut, filename)));
    } catch (IOException e) {
      e.getStackTrace();
    }
    
  }

  @Override
  public void HazAlgo(String[] campos) {

    try {
      // Definimos los campos 
      String linea = campos[1];
      String estacion = campos[2];
      String afluencia = campos[3];

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
      if( nuevaFecha.after(fechaInicio) &&  nuevaFecha.before(fechaFinal) && linea.equals("Línea 3")) {
        // System.out.println("Entra dato");
        this.archivoSalida.write(fecha+","+linea+","+estacion+","+afluencia+"\n");
      }
    } catch (ParseException e) {
      System.out.println("Error del Parse en HazAlgo");
      // e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Error de IO");
      e.getStackTrace();
    } catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Index fuera del límite en Query-HazAlgo");
    }
    
  }
}
