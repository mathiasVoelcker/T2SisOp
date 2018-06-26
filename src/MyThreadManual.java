import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyThreadManual extends MyThread{

	private String acao; 
	
    public MyThreadManual(String acao, String nome, int tamanho) {
    	super(nome, tamanho);
    	this.acao = acao;
    }

    public void run() {
    	switch(this.acao.toUpperCase()) {
    		case "C":
    			GerenteDeMemoria.alocarProcesso(this, this.getTamanho());
    			break;
    		case "A":
    			GerenteDeMemoria.acessarEndereco(this, this.getTamanho());
    			break;
    		case "M":
    			GerenteDeMemoria.alocarProcesso(this, this.getTamanho());
    			break;
    		case "T":
    			break;
    		default:
    			System.err.println("Ação " + this.acao + "inválida!");
    			break;
    	}
    }

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	@Override
	public String toString() {
		return "MyThreadManual [acao=" + acao + ", getNome()=" + getNome() + ", getTamanho()=" + getTamanho() + "]";
	}
	
}
