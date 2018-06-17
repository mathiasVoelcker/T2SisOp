import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class main {

    public static void main(String[] args) {
        int TAMANHO_PAGINAS = 8;
        Paginas.criarPaginas(8, TAMANHO_PAGINAS);
        Disco.criarPaginas(2, TAMANHO_PAGINAS);
        List<Pagina> paginas = Paginas.getPaginas();
        executarThreads(3);
    }

    private static void executarThreads(int n) {
        Random gerador = new Random();
        for(int i = 0; i < n; i++) {
            int s = ThreadLocalRandom.current().nextInt(10, 20);
            MyThread thread = new MyThread("num" + i, s);
            thread.start();
        }
    }
}
