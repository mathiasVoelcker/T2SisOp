import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyThread extends Thread{
    private int probabilidadeZero = 90;
    private int probabilidadeUm = 5;
    private int probabilidadeDois = 5;

    private String nome;
    private int tamanho;
    private List<Integer> enderecos;

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
        List<Pagina> paginas = Paginas.getPaginas();
        while(n != 2) {
            int aux = ThreadLocalRandom.current().nextInt(1, 101);
            if(aux < probabilidadeZero) {
                --probabilidadeZero;
                ++probabilidadeDois;
                n = ThreadLocalRandom.current().nextInt(1, s);

                int enderecoAcessar = ThreadLocalRandom.current().nextInt(0, Paginas.numTotalEnderecos);
//                System.out.println("thread " + nome + " deu 0");
            } else if (aux < probabilidadeZero + probabilidadeUm) {
                if(s/5 < 1) n = 2;
                else n = ThreadLocalRandom.current().nextInt(2, s/5);
//                System.out.println("thread " + nome + " deu 1");
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
