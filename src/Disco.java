import java.util.ArrayList;
import java.util.List;

public class Disco {

    public static List<Pagina> paginas = new ArrayList<Pagina>();


    public static void criarPaginas(int quantidade, int tamanho ) {
        int endereco = 0;
        for(int i = 0; i < quantidade; i++) {
            List<Endereco> enderecos = new ArrayList<Endereco>();
            while(endereco < i * tamanho) {
                enderecos.add(new Endereco(endereco));
                endereco++;
            }
            paginas.add(new Pagina(i, tamanho, enderecos));

        }
    }

    public static List<Pagina> getPaginas() {
        return paginas;
    }

    public static Pagina getPagina(int index) {
        return paginas.get(index);
    }

    public static int numPaginasVazias() {
        int num = 0;
        for (Pagina pagina: paginas) {
            if (pagina.getEnderecos().stream().allMatch(e -> e.getProcessoAlocado() == null)) {
                num++;
            }
        }
        return num;
    }

}
