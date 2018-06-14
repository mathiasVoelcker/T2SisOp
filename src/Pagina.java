import java.util.ArrayList;
import java.util.List;

public class Pagina {

    private String nome;
    private int tamanho;
    private List<Integer> enderecos;
    private boolean ocupada;

    public Pagina(String nome, int tamanho, List<Integer> enderecos) {
        this.nome = nome;
        this.tamanho = tamanho;
        this.enderecos = new ArrayList<Integer>();
        this.ocupada = false;
        for (int end: enderecos) {
            this.enderecos.add(end);
        }
    }
}
