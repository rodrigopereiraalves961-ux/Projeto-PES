package pt.ul.fc.cli;

import java.io.PrintStream;
import java.util.List;

/** Um subcomando da harness, por exemplo {@code new} ou {@code doctor}. */
public interface Contract {

  /**
   * Nome usado na linha de comandos.
   *
   * @return nome do comando
   */
  String name();

  /**
   * Descrição curta, mostrada na ajuda.
   *
   * @return descrição do comando
   */
  String description();

  /**
   * Executa o comando.
   *
   * @param args argumentos depois do nome do comando
   * @param out saída normal
   * @param err saída de erros
   * @return código de saída
   */
  int run(List<String> args, PrintStream out, PrintStream err);
}