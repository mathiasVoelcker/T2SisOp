import java.util.ArrayList;
import java.util.List;

public class Paginas {
    public static List<Pagina> paginas = new ArrayList<Pagina>();


    public static void criarPaginas(int quantidade, int tamanho ) {
        int endereco = 0;
        for(int i = 1; i <= quantidade; i++) {
            List<Integer> enderecos = new ArrayList<Integer>();
            while(endereco < i * tamanho) {
                enderecos.add(endereco);
                endereco++;
            }
            paginas.add(new Pagina("Página " + i, tamanho, enderecos));
            enderecos.clear();
        }
    }

    public static List<Pagina> getPaginas() {
        return paginas;
    }

    public static Pagina getPagina(int index) {
        return paginas.get(index);
    }
}
