import java.util.ArrayList;
import java.util.List;

public class Paginas {
    public static List<Pagina> paginas = new ArrayList<Pagina>();

    public static int numTotalEnderecos;
    public static int quantidadePaginas;
    public static int tamanhoPagina;

    public static void criarPaginas(int quantidadePaginas, int tamanhoPagina ) {
        int endereco = 0;
        for(int i = 0; i < quantidadePaginas; i++) {
            List<Endereco> enderecos = new ArrayList<Endereco>();
            while(endereco < (i + 1) * tamanhoPagina) {
                enderecos.add(new Endereco(endereco));
                endereco++;
            }
            paginas.add(new Pagina(i, tamanhoPagina, enderecos));
        }
        quantidadePaginas = quantidadePaginas;
        tamanhoPagina = tamanhoPagina;
        numTotalEnderecos = quantidadePaginas * tamanhoPagina;
    }

    public static List<Pagina> getPaginas() {
        return paginas;
    }

    public static Pagina getPagina(int index) {
        return paginas.get(index);
    }

    public static int getNumTotalEnderecos() {
        return numTotalEnderecos;
    }

    public static int getQuantidadePaginas() {
        return quantidadePaginas;
    }

    public static int getTamanhoPagina() {
        return tamanhoPagina;
    }
}
