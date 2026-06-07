public class Quantum{
    private int qio;
    private int qqe;
    public Quantum(int qio, int qqe)
    {
        this.qio = qio;
        this.qqe = qqe;
    }

    public int getQio(){
        return this.qio;
    }
    public int getQqe(){
        return this.qqe;
    }
    public void setQio(int qio){
        this.qio = qio;
    }
    public void setQqe(int qqe) {
        this.qqe = qqe;
    }
}