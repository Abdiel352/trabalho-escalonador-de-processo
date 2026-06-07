import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        List<Processo> processosBase = criarProcessosDeTeste();

        executarSimulacao(new FCFS(), processosBase);
        executarSimulacao(new SJF(), processosBase);
        executarSimulacao(new RR(), processosBase);
    }

    private static void executarSimulacao(Escalonador escalonador, List<Processo> base) {
        List<Processo> copia = new ArrayList<>();
        for (Processo p : base) {
            copia.add(p.copiar());
        }
        escalonador.executar(copia);
    }

    private static List<Processo> criarProcessosDeTeste() {
        List<Processo> processos = new ArrayList<>();

        processos.add(new Processo(1, 10, 0, 3, Arrays.asList(new Quantum(4, 2))));
        processos.add(new Processo(2, 8, 0, 1, Arrays.asList(new Quantum(3, 3))));
        processos.add(new Processo(3, 12, 0, 5, Arrays.asList(new Quantum(6, 1))));
        processos.add(new Processo(4, 4, 0, 2, Arrays.asList(new Quantum(2, 4))));
        processos.add(new Processo(5, 15, 0, 4, Arrays.asList(new Quantum(7, 2))));
        processos.add(new Processo(6, 6, 0, 3, Arrays.asList(new Quantum(3, 2))));

        processos.add(new Processo(7,20,0,2, Arrays.asList(
                new Quantum(5,2),
                new Quantum(10,3)
        )));
        processos.add(new Processo(8,14,0,4, Arrays.asList(
                new Quantum(4,1),
                new Quantum(10,2)
        )));
        processos.add(new Processo(9,25,0,5, Arrays.asList(
                new Quantum(6,2),
                new Quantum(15,3),
                new Quantum(25,2)
        )));
        processos.add(new Processo(10,30,0,1, Arrays.asList(
                new Quantum(8,3),
                new Quantum(15,3),
                new Quantum(25,2)
        )));
        return processos;
    }
}