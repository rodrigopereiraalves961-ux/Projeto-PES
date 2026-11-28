package pt.ul.fc.cli;

import java.io.PrintStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Interpreta os argumentos e encaminha para o comando certo. */
public final class Cli {

  private static final Set<String> HELP = Set.of("help", "-h", "--help");

  private final Map<String, Contract> commands;

  /**
   * Cria a interface de linha de comandos.
   *
   * @param commands comandos disponíveis
   * @throws IllegalArgumentException se dois comandos tiverem o mesmo nome
   */
  public Cli(List<Contract> commands) {
    Map<String, Contract> byName = new LinkedHashMap<>();
    for (Contract command : commands) {
      if (byName.putIfAbsent(command.name(), command) != null) {
        throw new IllegalArgumentException("Comando repetido: " + command.name());
      }
    }
    this.commands = Collections.unmodifiableMap(byName);
  }

  /**
   * Executa a linha de comandos.
   *
   * @param args argumentos recebidos
   * @param out saída normal
   * @param err saída de erros
   * @return código de saída
   */
  public int run(List<String> args, PrintStream out, PrintStream err) {
    if (args.isEmpty()) {
      printUsage(err);
      return ExitCode.USAGE;
    }
    String name = args.get(0);
    if (HELP.contains(name)) {
      printUsage(out);
      return ExitCode.OK;
    }
    Contract command = commands.get(name);
    if (command == null) {
      err.println("Comando desconhecido: " + name);
      printUsage(err);
      return ExitCode.USAGE;
    }
    return command.run(args.subList(1, args.size()), out, err);
  }

  /** Escreve a ajuda com a lista de comandos disponíveis. */
  private void printUsage(PrintStream stream) {
    stream.println("Utilização: harness <comando> [opções]");
    stream.println();
    stream.println("Comandos:");
    stream.printf("  %-10s %s%n", "help", "Mostra esta ajuda");
    for (Contract command : commands.values()) {
      stream.printf("  %-10s %s%n", command.name(), command.description());
    }
  }
}