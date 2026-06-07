import java.util.List;
import java.util.ArrayList;

public class Processo{
    private int identificador;
    private int NQT;
    private int NQP;
    private int prioridade;
    private Estado estado;
    private List<Quantum> PIO;
    private int tempoEsperaAtual;

    public Processo(int id, int nqt, int nqp, int prioridade, List<Quantum> pio){
       this.identificador = id;
       this.NQT = nqt;
       this.NQP = nqp;
       this.prioridade = prioridade;
       this.PIO = pio != null ? new ArrayList<>(pio) : new ArrayList<>();
       this.estado = Estado.NOVO;
       this.tempoEsperaAtual = 0;
    }
    public int getIdentificador(){return this.identificador;}
    public int getNQT(){return this.NQT;}
    public int getNQP(){return this.NQP;}
    public int getPrioridade(){return this.prioridade;}
    public Estado getEstado(){return estado;}
    public List<Quantum> getPIO() {return PIO;}
    public int getTempoEsperaAtual() { return tempoEsperaAtual; }



    public void setIdentificador(int id){this.identificador = id;}
    public void setNQT(int nqt){this.NQT = nqt;}
    public void setNQP(int nqp){this.NQP = nqp;}
    public void setPrioridade(int prioridade){this.prioridade = prioridade;}
    public void setEstado(Estado estado) { this.estado = estado; }
    public void setTempoEsperaAtual(int tempo) { this.tempoEsperaAtual = tempo; }

    public Processo copiar() {
        List<Quantum> pioCopia = new ArrayList<>();
        for (Quantum q : this.PIO) {
            pioCopia.add(new Quantum(q.getQio(), q.getQqe()));
        }
        return new Processo(this.identificador, this.NQT, this.NQP, this.prioridade, pioCopia);
    }

    @Override
    public String toString() {
        int nqnp = this.NQT - this.NQP;
        return String.format("P%d [Estado: %s | Total: %d quantum | Processado: %d | Restante: %d]",
            this.identificador,
            this.estado.toString().toLowerCase(), 
            this.NQT, 
            this.NQP, 
            nqnp);
    }

    public boolean concluido() {
        return this.NQP >= this.NQT;
    }

    public void processar(){
        if(!concluido()){
            this.NQP++;
        }
    }
}