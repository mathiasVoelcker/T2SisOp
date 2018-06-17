import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GerenteDeMemoria {

    public static synchronized void alocarProcesso(MyThread processo, int tamanhoAlocacao) {
        int quantidadeJaAlocada = 0;
        List<Integer> paginasASerAlocadas = new ArrayList<Integer>();
        // para cada página
        for (Pagina pagina: Paginas.getPaginas()) {
            // e se nessa página tiver apenas endereços vazios ou endereços com o mesmo processo alocado
            if(pagina.getEnderecos()
                    .stream()
                    .allMatch(e -> e.getProcessoAlocado() == null
                            || e.getProcessoAlocado().getNome().equals(processo.getNome())
                            )) {
//                    alocar processo nos endereços disponíveis
                for (Endereco endereco: pagina.getEnderecos()) {
                    if(endereco.getProcessoAlocado() == null) {
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

    public static synchronized void adicionarProcessoAEnderecos(List<Integer> paginasASeremAlocadas, int totalEnderecos, MyThread processo) {
        int count = 0;
        int tamanhoPagina = Paginas.getPagina(0).getTamanho();
        for(int numPagina : paginasASeremAlocadas) {
            for(int i = 0; i < tamanhoPagina; i++) {
                if(count == totalEnderecos) {
                    List<Pagina> paginas = Paginas.getPaginas();
                    return;
                }
                Endereco end = Paginas.getPaginas()
                        .get(numPagina)
                        .getEnderecos()
                        .get(i);
                end.setProcessoAlocado(processo);
                end.setEnderecoDoProcesso(count);
                count++;
            }
        }
    }

    public static void acessarEndereco(MyThread processo, int enderecoProcesso) {
        if(processo.getTamanho() >= enderecoProcesso)
            System.out.println("erro de acesso à página");
        else {
            // busca a página em que enderecoProcesso se encontra
            Pagina pagina = Paginas.getPaginas()
                    .stream()
                    .filter( p -> p.getEnderecos()
                            .stream()
                            .anyMatch( e -> e.getEnderecoDoProcesso() == enderecoProcesso))
                    .findFirst()
                    .get();
            System.out.println("Acessar o endereco " + enderecoProcesso + " do " + processo.getNome() + " - página " + pagina.getNumero());
        }
    }
}
