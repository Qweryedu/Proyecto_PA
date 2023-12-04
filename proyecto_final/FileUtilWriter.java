package proyecto_final;

public abstract class FileUtilWriter extends FileUtil {

  // Constructores
  public FileUtilWriter(String entrada, String pathIn) {
    super(entrada, pathIn);
  }

  public abstract void hazAlgo(String[] campos);

  public abstract void cierraBufferWriter();
}