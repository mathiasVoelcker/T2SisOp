import com.sun.tools.corba.se.idl.constExpr.Or;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class GerenteDeMemoria {

    public static synchronized void alocarProcesso(MyThread processo, int tamanhoAlocacao, boolean imprimirInfo) {
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
                        if(imprimirInfo) {
                            imprimirInfo();
                        }
                        return;
                    }

                }
            }
        }
        System.out.println("Neste momento acontece o page fault");
        int numPaginas = tamanhoAlocacao/(Paginas.getPagina(0).getTamanho() + 1) + 1;
        if(Disco.numPaginasVazias() < numPaginas) {
            System.out.println("Não há mais espaco na memória");
        } else {
            if (Algoritmo.algoritmo.equalsIgnoreCase("lru"))
                trocarProcessosMemoriaLRU(numPaginas);
            else
                trocarProcessosMemoriaAleatorio(numPaginas);
            alocarProcesso(processo, tamanhoAlocacao, true);
        }
    }

    private static void imprimirInfo() {
        System.out.println("Páginas Memória");
        for (Pagina pagina: Paginas.getPaginas()) {
            MyThread processo = pagina.getEnderecos().get(0).getProcessoAlocado();
            if( processo != null)
                System.out.print(" - " + processo.getNome());
            else
                System.out.print(" 0 ");
        }
        System.out.println("");
        System.out.println("Páginas Disco");
        for (Pagina pagina: Disco.getPaginas()) {
            MyThread processo = pagina.getEnderecos().get(0).getProcessoAlocado();
            if( processo != null)
                System.out.print(" - " + processo.getNome());
            else
                System.out.print(" - 0 ");
        }
        System.out.println("");
    }

    private synchronized static void trocarProcessosMemoriaLRU(int numPaginas) {
        List<Pagina> paginas = Paginas.getPaginas();
        for (int i = 0; i < numPaginas; i++) {
            Pagina paginaLRU = null;
            for (Pagina pagina: paginas) {
                if (paginaLRU == null || pagina.getOrdemExecucao() < paginaLRU.getOrdemExecucao()) {
                    paginaLRU = pagina;
                }
            }
            System.out.println("Sai a página " + paginaLRU.getNumero());
            alocarPaginaADisco(paginaLRU);
            OrdemExecucao.aumentarOrdemExecucao();
            paginaLRU.setOrdemExecucao(OrdemExecucao.ordemExecucao);
        }
    }

    private synchronized static void trocarProcessosMemoriaAleatorio(int numPaginas) {
        List<Pagina> paginas = Paginas.getPaginas();
        for (int i = 0; i < numPaginas; i++) {
            Pagina paginaAleatoria = null;
            int numPagina = ThreadLocalRandom.current().nextInt(0, Paginas.getPaginas().size() - 1);
            System.out.println("Sai a página " + numPagina);
            alocarPaginaADisco(Paginas.getPagina(numPagina));
            OrdemExecucao.aumentarOrdemExecucao();
            Paginas.getPagina(numPagina).setOrdemExecucao(OrdemExecucao.ordemExecucao);
        }
    }

    public static synchronized void adicionarProcessoAEnderecos(List<Integer> paginasAlocadas, int totalEnderecos, MyThread processo) {
        int tamanhoAnteriorProcesso = processo.getEnderecos().size();
        int count = tamanhoAnteriorProcesso;
        //lista criada para auxiliar a impressão de endereços físicos no final do método
        List<Integer> enderecosFisicosAlocados = new ArrayList<Integer>();
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
            OrdemExecucao.aumentarOrdemExecucao();
            Paginas.getPagina(numPagina).setOrdemExecucao(OrdemExecucao.ordemExecucao);
        }

        System.out.print("Aloca " + paginasAlocadas.size() + " páginas para " + processo.getNome());
        System.out.print(" - endereços de " + tamanhoAnteriorProcesso + " a " + (processo.getEnderecos().size() - 1));
        System.out.print(" - end físicos de " + enderecosFisicosAlocados.get(0) + " a " + enderecosFisicosAlocados.get(enderecosFisicosAlocados.size() - 1));
        System.out.println(" - páginas " + paginasAlocadas.stream().map(p -> p.toString()).collect(Collectors.joining(", ")));
    }

    public static synchronized void acessarEndereco(MyThread processo, int enderecoProcesso) {
        if (processo.getEnderecos().size() <= enderecoProcesso)
            System.out.println("erro de acesso à página p: " + processo.getNome() + " e: " + enderecoProcesso);
        else {
            //encontrar pagina que possui o enderecoDoProcesso e o processo desejado
            List<Pagina> paginas = Paginas.getPaginas()
                    .stream()
                    .filter(p -> p.getEnderecos()
                            .stream()
                            .anyMatch(e -> e.getEnderecoDoProcesso() == enderecoProcesso
                                    && e.getProcessoAlocado() != null
                                    && e.getProcessoAlocado().getNome().equals(processo.getNome()))).collect(Collectors.toList());
            OrdemExecucao.aumentarOrdemExecucao();
            if(paginas.size() == 0) {
                System.out.println("Page fault de acesso - processo " + processo.getNome() + " endereco " + enderecoProcesso);
                buscarPaginaEmDisco(processo, enderecoProcesso);
            } else {
                paginas.get(0).setOrdemExecucao(OrdemExecucao.ordemExecucao);
                System.out.println("Acessar o endereco " + enderecoProcesso + " do " + processo.getNome() + " - página " + paginas.get(0).getNumero());
            }
        }
    }

    private static void buscarPaginaEmDisco(MyThread processo, int enderecoProcesso) {
        //encontrar pagina que possui o enderecoDoProcesso e o processo desejado
        List<Pagina> paginas = Disco.getPaginas()
                .stream()
                .filter(p -> p.getEnderecos()
                        .stream()
                        .anyMatch(e -> e.getEnderecoDoProcesso() == enderecoProcesso
                                && e.getProcessoAlocado() != null
                                && e.getProcessoAlocado().getNome().equals(processo.getNome()))).collect(Collectors.toList());
        OrdemExecucao.aumentarOrdemExecucao();
        if(paginas.size() == 0)
            System.out.println("Processo Não Encontrado");
        else {
            for (Endereco endereco: Disco.getPagina(paginas.get(0).getNumero()).getEnderecos()) {
                endereco.setProcessoAlocado(null);
                endereco.setEnderecoDoProcesso(0);
            }
            trocarProcessosMemoriaLRU(1);
            int tamanhoAlocacao = paginas.get(0).getEnderecos()
                    .stream()
                    .filter(e -> e.getProcessoAlocado() != null)
                    .toArray().length;
            alocarProcesso(processo, tamanhoAlocacao, true);
        }

    }

    public static synchronized void alocarPaginaADisco(Pagina paginaMemoria) {

        // para cada página
        for (Pagina paginaDisco : Disco.getPaginas()) {
            // e se nessa página tiver apenas endereços vazios ou endereços com o mesmo processo alocado
            if (paginaDisco.getEnderecos()
                    .stream()
                    .allMatch(e -> e.getProcessoAlocado() == null)) {
//                    alocar processo nos endereços disponíveis
                List<Endereco> cloneEnderecos = new ArrayList<Endereco>(paginaMemoria.getEnderecos().size());
                for(int i = 0; i < paginaMemoria.getEnderecos().size(); i++) {
                    cloneEnderecos.add(new Endereco(paginaMemoria.getEnderecos().get(i)));
                }
                Disco.getPaginas().get(paginaDisco.getNumero()).setEnderecos(cloneEnderecos);
                List<Pagina> paignas = Disco.getPaginas();
                Paginas.getPagina(paginaMemoria.getNumero()).esvaziar();
                break;
            }
        }
    }
}
