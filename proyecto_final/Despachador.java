package proyecto_final;

import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Despachador extends FileUtilWriter {
    // Variables
    private int contador;
    private int cantidadArchivos;
    private static ArrayList<BufferedWriter> BufferSalida = new ArrayList<>();

    // Constructor
    public Despachador(String entrada, String pathIn, int cantidadA) {
        // Recibe el archivo a revisar y la cantidad de sub archivos a crear
        super(entrada, pathIn);
        System.out.println("Checando el path " + pathIn + entrada);
        this.cantidadArchivos = cantidadA;

        // Checamos los CPU's para definir la cantidad de hilos
        int cpu = Runtime.getRuntime().availableProcessors();
        if (this.cantidadArchivos < 4 * cpu) {
            System.out.println("La cantidad de sub archivos es menor a la cantidad de CPU's disponibles");
            System.out.println("Por lo que se sobre escribe a la cantidad de CPU's");
            this.cantidadArchivos = cpu * 4;
        }

        // Hacemos las instancias de sub archivos
        for (int i = 0; i < this.cantidadArchivos; i += 1) {
            try {
                BufferSalida.add(new BufferedWriter(new FileWriter(new File("./", "SubArchivo" + i + ".csv"))));
            } catch (IOException e) {
                System.out.println("Error al crear los buffers de sub archivos");
            }
        }
    }

    @Override
    public void hazAlgo(String[] campos) {
        // Recibimos los parámetros y los dividimos entre los archivos
        int index = this.contador % this.cantidadArchivos;
        try {
            BufferSalida.get(index).write(campos[0] + "," + campos[1] + "," + campos[2] + "," + campos[3] + "\n");
            this.contador += 1;

            BufferSalida.get(index).flush();
        } catch (IOException e) {
            System.out.println("Error en la sub escritura del archivo");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Esta linea no está completa");
        }

    }

    @Override
    public void cierraBufferWriter() {
        for (int i = 0; i < this.cantidadArchivos; i += 1) {
            try {
                BufferSalida.get(i).close();
            } catch (IOException e) {
                System.out.println("No se pudo cerrar en BuffreSalida en Despachador");
            }
        }
    }

    public void limpiaArchivos() {
        for (int i = 0; i < this.cantidadArchivos; i += 1) {
            Path path = Paths.get("./SubArchivo" + i + ".csv");
            try {
                Files.delete(path);
            } catch (IOException e) {
                System.out.printf("No se pudo eliminar el SubArchivo%d", i);
            }
        }
    }

    public int getCantidadArchivos() {
        return this.cantidadArchivos;
    }
}
