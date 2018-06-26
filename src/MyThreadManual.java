import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MyThreadManual extends MyThread{

	private List<String> acoes; 
	
    public MyThreadManual(String nome, int tamanho) {
    	super(nome, tamanho);
    	this.acoes = new ArrayList<>();
    }

    public void run() {
    	GerenteDeMemoria.alocarProcesso(this, this.getTamanho(), false);
    	
    	for (String acao : acoes) {
			realizaAcao(acao);
		}
    }
    
    private void realizaAcao(String acao) {
    	
    	String[] actionContent = acao.split(" ");
    	
    	switch(actionContent[0]) {
			case "A":
				GerenteDeMemoria.acessarEndereco(this, Integer.valueOf(actionContent[2].trim()));
				break;
			case "M":
				GerenteDeMemoria.alocarProcesso(this, Integer.valueOf(actionContent[2].trim()),false);
				break;
			case "T":
				System.out.println("Terminou o processo " + this.getName());
				return;
			default:
				System.err.println("Ação " + actionContent[0] + "inválida!");
				break;
    	}
    }
    
	public List<String> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<String> acoes) {
		this.acoes = acoes;
	}
	
	public void addAcao(String acao) {
		this.acoes.add(acao);
	}

	@Override
	public String toString() {
		return "MyThreadManual [ getNome()=" + getNome() + ", getTamanho()=" + getTamanho() + "]";
	}
	
}
