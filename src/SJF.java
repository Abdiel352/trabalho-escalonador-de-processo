import java.util.ArrayList;
import java.util.List;

public class SJF implements Escalonador {

    @Override
    public String getNome() {
        return "Shortest-Job-First (SJF)";
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
        Processo executandoAnterior = null;

        while (true) {
            long encerrados = todosProcessos.stream().filter(p -> p.getEstado() == Estado.ENCERRADO).count();
            if (encerrados == todosProcessos.size()) break;

            // 1. Atualiza bloqueados
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

            // 2. Ordena pela menor quantidade de tempo restante
            final Processo lastExec = executandoAnterior;
            filaProntos.sort((p1, p2) -> {
                int tr1 = p1.getNQT() - p1.getNQP();
                int tr2 = p2.getNQT() - p2.getNQP();
                if (tr1 == tr2) {
                    if (p1 == lastExec) return -1;
                    if (p2 == lastExec) return 1;
                    return Integer.compare(p1.getIdentificador(), p2.getIdentificador());
                }
                return Integer.compare(tr1, tr2);
            });

            // 3. Seleciona processo
            if (!filaProntos.isEmpty()) {
                Processo executandoAtual = filaProntos.get(0);
                
                if (executandoAnterior != null && executandoAnterior != executandoAtual && executandoAnterior.getEstado() == Estado.EXECUCAO) {
                    executandoAnterior.setEstado(Estado.PRONTO);
                }

                executandoAtual.setEstado(Estado.EXECUCAO);
                executandoAnterior = executandoAtual;

                Quantum ioAtendido = null;
                for (Quantum io : executandoAtual.getPIO()) {
                    if (io.getQio() == executandoAtual.getNQP()) {
                        ioAtendido = io;
                        break;
                    }
                }

                if (ioAtendido != null) {
                    executandoAtual.setEstado(Estado.BLOQUEADO);
                    executandoAtual.setTempoEsperaAtual(ioAtendido.getQqe());
                    executandoAtual.getPIO().remove(ioAtendido);
                    filaProntos.remove(executandoAtual);
                    filaBloqueados.add(executandoAtual);
                    executandoAnterior = null;
                } else {
                    executandoAtual.processar();
                    if (executandoAtual.concluido()) {
                        executandoAtual.setEstado(Estado.ENCERRADO);
                        filaProntos.remove(executandoAtual);
                        executandoAnterior = null;
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
