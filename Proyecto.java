import java.io.*; //Importamos todas las clases del paquete java.io
// import java.io.BufferedReader;
// import java.io.BufferedWriter;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.FileWriter;
// import java.nio.file.FileSystem;
// import java.io.IOException;

public class Proyecto {
    // Variables de clase
    public static String entrada = "afluenciastc_simple_08_2023.csv"; // Base de datos
    public static String salida = "TestSalida.csv"; // Archivo de salida
    public static String path = "/home/edu/Documentos/UNAM/Programación Avanzada/Proyecto/"; // Directorio

    public static void main(String[] args) {
        // Instanciamos al objeto
        Proyecto P = new Proyecto();
        System.out.println("Creamos el objeto Proyecto");

        // Tomamos el tiempo
        // Tiempo inicial
        long startTime = System.nanoTime();
        P.AbreArchivo();
        System.out.println("Corrimos AbreArchivo");
        // Tiempo final
        long endTime = System.nanoTime();
        long totalTime = (endTime - startTime) / 1000000;
        System.out.println("Tiempo transcurrido:" + totalTime + "ms");
    }

    public void AbreArchivo() {
        // Variables del método
        String linea;
        String[] campos;

        // Definimos el archivo usando el path
        File archivo = new File(path, entrada);
        File archivoListo = new File(path, salida);
        // Leemos los contenidos del archivo
        try {
            BufferedReader archivoOrigen = new BufferedReader(new FileReader(archivo));
            BufferedWriter archivoSalida = new BufferedWriter(new FileWriter(archivoListo));

            // Leemos, procesamos y escribimos la información relevante
            while ((linea = archivoOrigen.readLine()) != null) {
                campos = linea.split(","); // Separamos por coma por ser formato CSV
                archivoSalida.write(campos[0] + "," + campos[3] + ","
                        + campos[4] + "," + campos[5] + "\n");
            }
            // Cerramos los Buffers
            archivoOrigen.close();
            archivoSalida.close();

            System.out.println("Archivo de salida: " + salida);
        }

        catch (FileNotFoundException e) {
            e.getStackTrace();
        }

        catch (IOException e) {
            e.getStackTrace();
        } catch (Exception e1) {
            System.out.println("Error");
        }
    }

}