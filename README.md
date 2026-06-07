# Simulador de Escalonamento de Processos

Este projeto é um simulador de sistemas operacionais que demonstra o funcionamento de três algoritmos clássicos de escalonamento de processos. O simulador processa uma lista de processos com diferentes características, incluindo prioridades e solicitações de Entrada/Saída (I/O).

## 🚀 Algoritmos Implementados

1.  **First-Come, First-Served (FCFS):** Executa os processos na ordem de chegada (FIFO).
2.  **Shortest-Job-First (SJF):** Algoritmo preemptivo que prioriza processos com o menor tempo de execução restante (Shortest Remaining Time First).
3.  **Round Robin (RR) com Prioridade:** Escalonamento circular onde processos de maior prioridade têm precedência. Em caso de empate na prioridade, utiliza-se a ordem circular.

## 📋 Requisitos do Simulador

O simulador atende aos seguintes requisitos técnicos:
- **Linguagem:** Java.
- **Processos:** 10 instâncias com dados variados.
- **Estrutura do Processo:**
  - Identificador (P1, P2...).
  - NQT (Número de Quantum Total / Tamanho).
  - NQP (Número de Quantum Processados).
  - NQNP (Número de Quantum Não Processados).
  - Estado (Novo, Pronto, Execução, Bloqueado, Encerrado).
  - Prioridade (1 a 5).
  - PIO (Pedidos de I/O contendo o quantum da solicitação e o tempo de espera).
- **Entrada/Saída:** Cada processo realiza pelo menos um pedido de I/O, com alguns realizando até três pedidos.

## 💻 Como Compilar e Executar

Certifique-se de ter o JDK (Java Development Kit) instalado em sua máquina.

### 1. Compilação
Abra o terminal na pasta raiz dos arquivos `.java` e execute:
```bash
javac *.java
```

### 2. Execução
Após a compilação, execute a classe principal:
```bash
java Main
```

## 📊 Visualização da Saída

O simulador imprime o estado de todos os processos a cada ciclo (Quantum). Para facilitar a leitura, o console exibe:
- Cabeçalhos claros para cada algoritmo.
- Uma linha em branco entre cada ciclo.
- Um "Marco de 20 Ciclos" para navegação em logs extensos.
- Rodapé com o tempo total de execução de cada algoritmo.

---
**Nota:** Este projeto foi desenvolvido como parte da disciplina de Sistemas Operacionais.
