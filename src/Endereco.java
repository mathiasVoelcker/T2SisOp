public class Endereco {
    private int enderecoFisico;
    private MyThread processoAlocado;
    private int enderecoDoProcesso;

    public Endereco(int enderecoFisico) {
        this.enderecoFisico = enderecoFisico;
    }

    public int getEnderecoFisico() {
        return enderecoFisico;
    }

    public MyThread getProcessoAlocado() {
        return processoAlocado;
    }

    public int getEnderecoDoProcesso() {
        return enderecoDoProcesso;
    }

    public void setEnderecoFisico(int enderecoFisico) {
        this.enderecoFisico = enderecoFisico;
    }

    public void setProcessoAlocado(MyThread processoAlocado) {
        this.processoAlocado = processoAlocado;
    }

    public void setEnderecoDoProcesso(int enderecoDoProcesso) {
        this.enderecoDoProcesso = enderecoDoProcesso;
    }
}
