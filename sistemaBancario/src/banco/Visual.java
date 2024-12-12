package banco;

import contas.Conta;

public class Visual {
	
	public static void quebraLinha(){
		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
	}
	
	public static void mostraSaldo(Conta Titular) {
		System.out.println("Saldo atual:"+Titular.getSaldo());
	}
	
}
