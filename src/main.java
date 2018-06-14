import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class main {

    public static void main(String[] args) {

        Paginas.criarPaginas(8, 8);
        List<Pagina> paginas = Paginas.getPaginas();
        executarThreads(10);
    }

    private static void executarThreads(int n) {
        Random gerador = new Random();
        int s = gerador.nextInt(20);
        for(int i = 0; i < n; i++) {
            MyThread thread = new MyThread("num" + i, s);
            thread.run();
        }
    }
}
