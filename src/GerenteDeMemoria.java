import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenteDeMemoria {

    public static synchronized void alocarProcesso(MyThread processo, int tamanhoAlocacao) {
        int quantidadeJaAlocada = 0;
        List<Integer> paginasASerAlocadas = new ArrayList<Integer>();
        // para cada página
        for (Pagina pagina : Paginas.getPaginas()) {
            // e se nessa página tiver apenas endereços vazios ou endereços com o mesmo processo alocado
            if (pagina.getEnderecos()
                    .stream()
                    .allMatch(e -> e.getProcessoAlocado() == null
                            || e.getProcessoAlocado().getNome().equals(processo.getNome())
                    )) {
//                    alocar processo nos endereços disponíveis
                for (Endereco endereco : pagina.getEnderecos()) {
                    if (endereco.getProcessoAlocado() == null) {
                        paginasASerAlocadas.add(pagina.getNumero());
                        quantidadeJaAlocada++;
                    }
                    if (tamanhoAlocacao == quantidadeJaAlocada) {
                        paginasASerAlocadas = paginasASerAlocadas.stream().distinct().collect(Collectors.toList());
                        adicionarProcessoAEnderecos(paginasASerAlocadas, tamanhoAlocacao, processo);
                        return;
                    }

                }
            }
        }
        System.out.println("Não há espaço na memória");
    }

    public static synchronized void adicionarProcessoAEnderecos(List<Integer> paginasAlocadas, int totalEnderecos, MyThread processo) {
        int tamanhoAnteriorProcesso = processo.getEnderecos().size();
        int count = tamanhoAnteriorProcesso;
        List<Integer> enderecosFisicosAlocados = new ArrayList<Integer>(); //lista criada para auxiliar a impressão de endereços físicos no final do método
        outerbreak:
        for (int numPagina : paginasAlocadas) {
            for (int i = 0; i < Paginas.getPaginas().get(0).getTamanho(); i++) {
                if (count == totalEnderecos + tamanhoAnteriorProcesso) {
                    List<Pagina> paginas = Paginas.getPaginas();
                    break;
                }

                Endereco end = Paginas.getPaginas()
                        .get(numPagina)
                        .getEnderecos()
                        .get(i);
                if(end.getProcessoAlocado() == null) {
                    end.setProcessoAlocado(processo);
                    enderecosFisicosAlocados.add(end.getEnderecoFisico());
                    processo.getEnderecos().add(count);
                    end.setEnderecoDoProcesso(count);
                    count++;
                }
            }
        }
        System.out.print("Aloca " + paginasAlocadas.size() + " páginas para " + processo.getNome());
        System.out.print(" - endereços de " + tamanhoAnteriorProcesso + " a " + (processo.getEnderecos().size() - 1));
        System.out.print(" - end físicos de " + enderecosFisicosAlocados.get(0) + " a " + enderecosFisicosAlocados.get(enderecosFisicosAlocados.size() - 1));

        System.out.println(" - páginas " + paginasAlocadas.stream().map(p -> p.toString()).collect(Collectors.joining(", ")));
    }

    public static synchronized void acessarEndereco(MyThread processo, int enderecoProcesso) {
        if (processo.getTamanho() <= enderecoProcesso)
            System.out.println("erro de acesso à página");
        else {
            //encontrar pagina que possui o enderecoDoProcesso e o processo desejado
            List<Pagina> paginas = Paginas.getPaginas()
                    .stream()
                    .filter(p -> p.getEnderecos()
                            .stream()
                            .anyMatch(e -> e.getEnderecoDoProcesso() == enderecoProcesso
                                    && e.getProcessoAlocado() != null
                                    && e.getProcessoAlocado().getNome().equals(processo.getNome()))).collect(Collectors.toList());
            System.out.println("Acessar o endereco " + enderecoProcesso + " do " + processo.getNome() + " - página " + paginas.get(0).getNumero());
        }
    }
}
