package pt.ul.fc;

import java.io.PrintStream;
import java.util.List;
import pt.ul.fc.cli.Cli;
import pt.ul.fc.cli.ExitCode;

/** Ponto de entrada da harness. */
public final class App {

  private App() {}

  /**
   * Executa a harness com os argumentos dados.
   *
   * @param args argumentos da linha de comandos
   * @param out saída normal
   * @param err saída de erros
   * @return código de saída
   */
  static int run(String[] args, PrintStream out, PrintStream err) {
    Cli cli = new Cli(List.of());
    return cli.run(List.of(args), out, err);
  }

  /**
   * Corre a harness.
   *
   * @param args argumentos da linha de comandos
   */
  public static void main(String[] args) {
    int code = run(args, System.out, System.err);
    if (code != ExitCode.OK) {
      System.exit(code);
    }
  }
}