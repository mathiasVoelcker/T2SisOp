import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;

public class Pagina {

    private int numero;
    private int tamanho;
    private List<Endereco> enderecos;
    private int ordemExecucao;

    public Pagina(int numero, int tamanho, List<Endereco> enderecos) {
        this.numero = numero;
        this.tamanho = tamanho;
        this.enderecos = new ArrayList<Endereco>();
        for (Endereco end: enderecos) {
            this.enderecos.add(end);
        }
    }

    public int getNumero() {
        return numero;
    }

    public int getTamanho() {
        return tamanho;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public void setTamanho(int tamanho) {
        this.tamanho = tamanho;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    public int getOrdemExecucao() {
        return ordemExecucao;
    }

    public void setOrdemExecucao(int ordemExecucao) {
        this.ordemExecucao = ordemExecucao;
    }

    public void esvaziar() {
        for (int i = 0; i < enderecos.size(); i++) {
            enderecos.get(i).setProcessoAlocado(null);
            enderecos.get(i).setEnderecoDoProcesso(0);
        }
    }

    public void atualizarEnderecos(List<Endereco> enderecos) {
        for (int i = 0; i < enderecos.size(); i++) {

            this.enderecos.get(i).setEnderecoDoProcesso(enderecos.get(i).getEnderecoDoProcesso());
            this.enderecos.get(i).setProcessoAlocado(enderecos.get(i).getProcessoAlocado());
        }
    }
}
