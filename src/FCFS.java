import java.util.ArrayList;
import java.util.List;

public class FCFS implements Escalonador {

    @Override
    public String getNome() {
        return "First-Come, First-Served (FCFS)";
    }

    @Override
    public void executar(List<Processo> processos) {
        System.out.println("==========================================================================");
        System.out.println("               INICIANDO ALGORITMO: " + getNome().toUpperCase());
        System.out.println("==========================================================================\n");

        int tempoGlobal = 1;
        List<Processo> todosProcessos = new ArrayList<>(processos);
        List<Processo> filaProntos = new ArrayList<>(processos);
        for (Processo p : filaProntos) {
            p.setEstado(Estado.PRONTO);
        }

        List<Processo> filaBloqueados = new ArrayList<>();

        while (true) {
            long encerrados = todosProcessos.stream().filter(p -> p.getEstado() == Estado.ENCERRADO).count();
            if (encerrados == todosProcessos.size()) break;

            // 1. Cuidar de quem está voltando do I/O
            List<Processo> liberados = new ArrayList<>();
            for (Processo p : filaBloqueados) {
                p.setTempoEsperaAtual(p.getTempoEsperaAtual() - 1);
                if (p.getTempoEsperaAtual() <= 0) {
                    p.setEstado(Estado.PRONTO);
                    liberados.add(p);
                }
            }
            filaBloqueados.removeAll(liberados);
            filaProntos.addAll(liberados);

            // 2. Executar processos
            if (!filaProntos.isEmpty()) {
                Processo executando = filaProntos.get(0);
                executando.setEstado(Estado.EXECUCAO);
                
                Quantum ioAtendido = null;
                for (Quantum io : executando.getPIO()) {
                    if (io.getQio() == executando.getNQP()) {
                        ioAtendido = io;
                        break;
                    }
                }
                
                if (ioAtendido != null) {
                    executando.setEstado(Estado.BLOQUEADO);
                    executando.setTempoEsperaAtual(ioAtendido.getQqe());
                    executando.getPIO().remove(ioAtendido);
                    filaProntos.remove(executando);
                    filaBloqueados.add(executando);
                } else {
                    executando.processar();
                    if (executando.concluido()) {
                        executando.setEstado(Estado.ENCERRADO);
                        filaProntos.remove(executando);
                    }
                }
            }

            imprimirCiclo(tempoGlobal, todosProcessos);
            tempoGlobal++;
            if (tempoGlobal > 1000) break;
        }

        System.out.println("==========================================================================");
        System.out.println("               FIM DO ALGORITMO: " + getNome().toUpperCase());
        System.out.println("               Tempo total executado: " + (tempoGlobal - 1) + " quantums");
        System.out.println("==========================================================================\n\n");
    }
}
