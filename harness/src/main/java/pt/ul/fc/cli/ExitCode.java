package pt.ul.fc.cli;

/** Códigos de saída. */
public final class ExitCode {

  /** O comando correu com sucesso. */
  public static final int OK = 0;

  /** O comando correu, mas falhou. */
  public static final int FAILURE = 1;

  /** Uso incorreto: comando desconhecido ou argumentos inválidos. */
  public static final int USAGE = 2;

  private ExitCode() {}
}