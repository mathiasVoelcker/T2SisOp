import javax.print.attribute.standard.MediaSize;
import java.util.ArrayList;
import java.util.List;

public class Pagina {

    private int numero;
    private int tamanho;
    private List<Endereco> enderecos;


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
}
