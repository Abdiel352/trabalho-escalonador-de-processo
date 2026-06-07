import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RR implements Escalonador {

    @Override
    public String getNome() {
        return "Round Robin (RR) com Prioridade";
    }

    @Override
    public void executar(List<Processo> processos) {
        System.out.println("==========================================================================");
        System.out.println("               INICIANDO ALGORITMO: " + getNome().toUpperCase());
        System.out.println("==========================================================================\n");

        int tempoGlobal = 1;
        List<Processo> todosProcessos = new ArrayList<>(processos);
        List<Processo> filaProntos = new ArrayList<>();
        List<Processo> filaBloqueados = new ArrayList<>();

        // Inicializa todos como PRONTO
        for (Processo p : todosProcessos) {
            p.setEstado(Estado.PRONTO);
            filaProntos.add(p);
        }

        while (true) {
            // Condição de parada: todos encerrados
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

            // 2. Ordena fila de prontos por Prioridade (5 a 1)
            // Processos de mesma prioridade seguem ordem de chegada na lista (Round Robin)
            filaProntos.sort(Comparator.comparingInt(Processo::getPrioridade).reversed());

            // 3. Execução
            if (!filaProntos.isEmpty()) {
                Processo executando = filaProntos.get(0);
                executando.setEstado(Estado.EXECUCAO);

                // Checa I/O
                Quantum ioPedido = null;
                for (Quantum io : executando.getPIO()) {
                    if (io.getQio() == executando.getNQP()) {
                        ioPedido = io;
                        break;
                    }
                }

                if (ioPedido != null) {
                    executando.setEstado(Estado.BLOQUEADO);
                    executando.setTempoEsperaAtual(ioPedido.getQqe());
                    executando.getPIO().remove(ioPedido);
                    
                    filaProntos.remove(executando);
                    filaBloqueados.add(executando);
                } else {
                    executando.processar();
                    
                    if (executando.concluido()) {
                        executando.setEstado(Estado.ENCERRADO);
                        filaProntos.remove(executando);
                    } else {
                        // Preempção Round Robin: volta para o fim da fila de sua prioridade
                        executando.setEstado(Estado.PRONTO);
                        filaProntos.remove(executando);
                        filaProntos.add(executando);
                    }
                }
            }

            // 4. Imprime Status do Ciclo
            imprimirCiclo(tempoGlobal, todosProcessos);
            
            tempoGlobal++;
            
            // Segurança para evitar loop infinito em erros de lógica
            if (tempoGlobal > 1000) break;
        }

        System.out.println("==========================================================================");
        System.out.println("               FIM DO ALGORITMO: " + getNome().toUpperCase());
        System.out.println("               Tempo total executado: " + (tempoGlobal - 1) + " quantums");
        System.out.println("==========================================================================\n\n");
    }
}
