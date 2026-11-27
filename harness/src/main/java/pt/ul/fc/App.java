package pt.ul.fc;

/** Ponto de entrada da harness. */
public final class App {

  private App() {}

  /**
   * Devolve a mensagem de boas-vindas.
   *
   * @return mensagem de boas-vindas
   */
  static String greeting() {
    return "Hello World!";
  }

  /**
   * Arranca a aplicação.
   *
   * @param args argumentos da linha de comandos
   */
  public static void main(String[] args) {
    System.out.println(greeting());
  }
}