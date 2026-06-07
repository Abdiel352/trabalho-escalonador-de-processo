import java.util.List;

public interface Escalonador{
    void executar(List<Processo> processos);
    String getNome();

    default void imprimirCiclo(int ciclo, List<Processo> todosProcessos) {
        System.out.println("Ciclo/Quantum-" + ciclo + ":");
        for (Processo p : todosProcessos) {
            System.out.println(p.toString());
        }
        System.out.println(); // Linha em branco para facilitar a leitura

        if (ciclo % 20 == 0) {
            System.out.println("--------------------------------------------------");
            System.out.println("             MARCO DE 20 CICLOS (" + ciclo + ")             ");
            System.out.println("--------------------------------------------------\n");
        }
    }
}