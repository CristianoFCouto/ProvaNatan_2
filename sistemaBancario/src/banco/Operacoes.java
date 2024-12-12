package banco;

import contas.Conta;

public class Operacoes {
	
	public static void depositar(Conta conta, double valor) {
		conta.setSaldo(conta.getSaldo() + valor);
    }

    public static boolean sacar(Conta conta, double valor) {
    	double creditoTotal = conta.getSaldo() + conta.getChequeEspecial();
		if (valor <= creditoTotal) {
			conta.setSaldo(conta.getSaldo() - valor);
		    return true;
		}
		return false;
    }
    
    public static boolean transferir(Conta origem, Conta destino, double valor) {
		if(sacar(origem,valor)) {
			depositar(destino,valor);
			return true;
		}
		return false;
    }
    
}
