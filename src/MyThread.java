import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyThread extends Thread{

    // Probabilidade em % de cada acao
    private int probabilidadeAcesso = 70;
    private int probabilidadeAlocacao = 25;
    private int probabilidadeFinalizar = 5;

    private String nome;
    private int tamanho;
    private List<Integer> enderecos;

    /*
     * Cada processo possui um tamanho, que é o espaço que ele ocupa na memória, ou o número de enderecos das páginas que ele ocupa
     * Cada processo possui uma lista de endereços, que são inicializados uma vez que este é alocado a memória.
     * Ao alocar 15 endereços na memória, e a listagem de enderecos do processo estiver vazia,
     * os endereços serão listados de 0 a 14, independente da página em que foram alocados.
     */

    public MyThread(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
        enderecos = new ArrayList<Integer>();
    }

    public void run() {
        Random gerador = new Random();
        int s = gerador.nextInt(100);
        int n = 0;
        GerenteDeMemoria.alocarProcesso(this, this.tamanho);
        System.out.println("C " + this.nome + " " + this.tamanho);
        List<Pagina> paginas = Paginas.getPaginas();
        while(n != 2) {
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int aux = ThreadLocalRandom.current().nextInt(1, 101);
            if(aux < probabilidadeAcesso) {
                --probabilidadeAcesso;
                ++probabilidadeFinalizar;
                n = ThreadLocalRandom.current().nextInt(0, s);
                int enderecoAcessar = ThreadLocalRandom.current().nextInt(0, 20);
                GerenteDeMemoria.acessarEndereco(this, enderecoAcessar);
            } else if (aux < probabilidadeAcesso + probabilidadeAlocacao) {
                int tamanhoNovaMemoria = ThreadLocalRandom.current().nextInt(5, 10);
                System.out.println("M " + this.nome + " " + tamanhoNovaMemoria);
                System.out.println("tentar alocar mais " + tamanhoNovaMemoria + " a " + this.nome);
                GerenteDeMemoria.alocarProcesso(this, tamanhoNovaMemoria);
                if(s/5 < 1) n = 2;
                else n = ThreadLocalRandom.current().nextInt(0, s/5);
            } else break;
        }
        System.out.println("thread " + nome + " finalizada");
        List<Pagina> paginas1 = Paginas.getPaginas();
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho() {
        return tamanho;
    }

    public List<Integer> getEnderecos() {
        return enderecos;
    }
}
