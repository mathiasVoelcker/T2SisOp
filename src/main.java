import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.w3c.dom.NamedNodeMap;

public class main {

    public static void main(String[] args) {
    	
     	Scanner in = new Scanner(System.in);
    	
    	System.out.println("Modo de execuução:");
//    	String modo = in.nextLine();

    	arquivo("e0");
    	//aleatorio();

    	/*
    	switch(modo.toUpperCase()) {
    		case "ALEATORIO":
    			aleatorio();
    			break;
    		case "MANUAL":
    			System.out.println("Escolha o arquivo:");
    	    	String arquivo = in.nextLine();
    			arquivo(arquivo);
    			break;
    		default:
    			System.out.println("Opção inválida!");
    			break;
    	}
    	*/

    }

    private static void aleatorio() {
    	int TAMANHO_PAGINAS = 8;
        Paginas.criarPaginas(8, TAMANHO_PAGINAS);
        Disco.criarPaginas(2, TAMANHO_PAGINAS);
        List<Pagina> paginas = Paginas.getPaginas();
        executarThreads(3);
    }
    
    private static void arquivo(String arquivo) {
    	try {
    		int count = 0;
    		boolean isPagCriadas = false;
    		String modo = "";
    		String algoritmo = "";
    		int tamPag = 0;
    		int tamMem = 0;
    		int tamArmaz = 0;
    		
    		List<MyThreadManual> processos = new ArrayList<>(); 
    		
            File f = new File("./in/" + arquivo + ".txt");
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine = "";
            while ((readLine = b.readLine()) != null) {
            	System.out.println(readLine);
            	if(count > 4) {
            		if(!isPagCriadas) {
	            		Paginas.criarPaginas(tamMem/tamPag, tamPag);
	            		Disco.criarPaginas(tamArmaz/tamPag, tamPag);
	            		isPagCriadas = true;
            		}
            		
            		String[] lineContent = readLine.split(" ");
            		
            		if("C".equalsIgnoreCase(lineContent[0])) {
            			MyThreadManual processo = new MyThreadManual(lineContent[1], Integer.valueOf(lineContent[2]));
            			processos.add(processo);
            		} else {
            			MyThreadManual processo = procuraThread(lineContent[1], processos);
            			if(processo != null) {
            				processo.addAcao(readLine);
            			} else {
            				System.err.println("Erro ao procurar processo para a linha: " + readLine);
            			}
            		}
            		
            		
            	} else if (count == 0) {
            		modo = readLine.trim();
            	} else if (count == 1) {
            		algoritmo = readLine.trim();
            	} else if (count == 2) {
                	tamPag = Integer.valueOf(readLine.trim());
            	} else if (count == 3) {
            		tamMem = Integer.valueOf(readLine.trim());
            	} else if (count == 4) {
            		tamArmaz = Integer.valueOf(readLine.trim());
            	}
            	count++;
            }
            
            executarThreadsManual(processos);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static MyThreadManual procuraThread(String nome, List<MyThreadManual> lista) {
    	for (MyThreadManual thread : lista) {
			if(nome.equalsIgnoreCase(thread.getNome())) {
				return thread;
			}
		}
    	return null;
    }
    
    private static void executarThreads(int n) {
        Random gerador = new Random();
        for(int i = 0; i < n; i++) {
            int s = ThreadLocalRandom.current().nextInt(10, 20);
            MyThread thread = new MyThread("num" + i, s);
            thread.start();
        }
    }
    
    private static void executarThreadsManual(List<MyThreadManual> processos) {
    	for (MyThreadManual thread : processos) {
    		System.out.println(thread);
    		thread.start();
		}
    }
}
