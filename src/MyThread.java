import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyThread extends Thread{
    private int probabilidadeZero = 90;
    private int probabilidadeUm = 5;
    private int probabilidadeDois = 5;

    private String nome;
    private int tamanho;

    public MyThread(String nome, int tamanho) {
        this.nome = nome;
        this.tamanho = tamanho;
    }

    public void run() {
        Random gerador = new Random();
        int s = gerador.nextInt(100);
        int n = 0;
        while(n == 2) {
            int aux = ThreadLocalRandom.current().nextInt(1, 101);
            if(aux < probabilidadeZero) {
                --probabilidadeZero;
                ++probabilidadeDois;
                n = ThreadLocalRandom.current().nextInt(1, s);
            } else if (aux < probabilidadeZero + probabilidadeUm) {
                if(s/5 < 1) n = 2;
                else n = ThreadLocalRandom.current().nextInt(2, s/5);
            } else break;
        }
        System.out.println("thread " + nome + " finalizada");
    }
}
