package usuarios;

import java.util.InputMismatchException;
import java.util.Scanner;

import banco.Banco;
import banco.Operacoes;
import banco.Visual;
import contas.Conta;

public class Bancario extends Usuario {
    public Bancario(String usuario,String senha) {
    	super(usuario, senha);
	}

	@Override
    public void menu(Banco banco, Usuario usuario) {

        Scanner scanner = new Scanner(System.in);
    	boolean menuPrinc = true;
    	
    	while(menuPrinc) {
    		System.out.println("====== Menu Bancário ======");
            System.out.println("1. Transferir");
            System.out.println("2. Sair");
            
            int opcao = scanner.nextInt();
            int subOpc = 1;
            
            scanner.nextLine();
            float valor;
            
            switch(opcao){
	            case 1:
	            	while(subOpc == 1) {
	            		Visual.quebraLinha();
		                System.out.print("Insira o nome de usuário do remetente: ");
		                String remetente = scanner.nextLine();
		                Conta contaOrigem = banco.getContaByNome(remetente);
		                
		                if (contaOrigem == null) {
		                    System.out.println("Conta do destinatário não encontrada.");
		                }
		                
		                System.out.print("Insira o nome de usuário do destinatário: ");
		                String destinatario = scanner.nextLine();
		                Conta contaDestino = banco.getContaByNome(destinatario);
		
		                if (contaDestino == null) {
		                    System.out.println("Conta do destinatário não encontrada.");
		                }
		
		                System.out.print("Insira o valor a ser transferido: ");
		                try {
		                    valor = scanner.nextFloat();
		                    if(Operacoes.transferir(contaOrigem,contaDestino,valor)) {
		                    	 banco.salvarSaldos();
				                    System.out.println("Transferência realizada com sucesso!\n");
				                       
			                        System.out.println("Saldo atual do Remetente "
			                        		+ contaOrigem.getTitular()+": "
			                        		+ contaOrigem.getSaldo()+"R$");
			                        
			                        System.out.println("Saldo atual do Destinatario" 
			                        		+contaDestino.getTitular()+": "
			                        		+contaDestino.getSaldo()+"R$");
		                    }else {
		                    	System.out.println("Saldo Insuficiente\n");
		                    	
		                    }
		                
	                        subOpc = 0;
		            
		                } catch (InputMismatchException e) {
		                    System.out.println("Insira um valor correto!");
		                }
	            	}
	                break;
	            	
	            case 2:
	                System.out.println("Saindo do menu bancário...");
	                menuPrinc = false;
	                break;
	
	            default:
	                System.out.println("Opção inválida. Por favor, escolha novamente.");
	                break; 
            }
    	}
    	banco.salvarSaldos();
    	scanner.close();
    }
}

