public class Endereco {

    /*
     * Esta classe é usada para representar o endereço da página
     * considerando como exemplo 8 páginas com 8 endereços:
        * enderecoFisico vai de 0 a 7 na pagina 0
        * enderecoFisico vai de 8 a 15 na página 1
        * e assim continua até o enderecoFísico 63 na página 7
        *
        * enderecoDoProcesso é definido pelo processo que foi alocado para este endereço
        * cada processo possui uma lista de endereços que são enumerados de 0 a n
        * este número é salvo na variável enderecoDoProcesso
     */

    private int enderecoFisico;
    private MyThread processoAlocado;
    private int enderecoDoProcesso;

    public Endereco(int enderecoFisico) {
        this.enderecoFisico = enderecoFisico;
    }

    public Endereco(Endereco endereco) {
        this.enderecoFisico = endereco.getEnderecoFisico();
        this.processoAlocado = endereco.getProcessoAlocado();
        this.enderecoDoProcesso = endereco.getEnderecoDoProcesso();
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
