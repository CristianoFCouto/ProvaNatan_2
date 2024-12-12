	package usuarios;

import java.util.InputMismatchException;
import java.util.Scanner;

import banco.Banco;
import banco.Operacoes;
import banco.Visual;
import contas.Conta;

public class Correntista extends Usuario {
	public Correntista(String usuario, String usuarioPai, String senha) {
		super(usuario,usuarioPai,senha);
	}

	@Override
    public void menu(Banco banco, Usuario usuario) {

        Scanner scanner = new Scanner(System.in);
        
    	Conta titular = banco.getContaByUsuario(usuario);
    	boolean loopUm = true;
    	
    	while(loopUm) {
    		System.out.println("====== Menu Correntista ======");
            System.out.println("1. Ver saldo");
            System.out.println("2. Sacar");
            System.out.println("3. Depositar");
            System.out.println("4. Transferir");
            System.out.println("5. Sair");
            
            int opcao = scanner.nextInt();
            int subOpc = 1;
            
            scanner.nextLine();
            float valor;
            
            switch(opcao){
            case 1:
            	Visual.quebraLinha();
            	Visual.mostraSaldo(titular);
            	break;
            	
            case 2:
            	while(subOpc == 1) {
            		Visual.quebraLinha();
            		Visual.mostraSaldo(titular);
                	System.out.println("Insira o valor a ser sacado:");
                	try{
                		valor = scanner.nextFloat();
                		if(Operacoes.sacar(titular,valor))
                    	{
                			System.out.println("Saque realizado com sucesso!");
                    		System.out.println("Saldo atual:"+titular.getSaldo());
                    		banco.salvarSaldos();
                    		subOpc = 0;
                    	}else {
                    		System.out.println("Saldo insuficiente!");
                    	}
                	}catch (InputMismatchException e) {
                		System.out.println("Insira um valor correto!");
                	}
            	}
            	break;
            	
            case 3:
            	while(subOpc == 1) {
            		Visual.quebraLinha();
            		Visual.mostraSaldo(titular);
                	System.out.println("Insira o valor a ser depositado:");
                	try{
                		valor = scanner.nextFloat();
                		Operacoes.depositar(titular,valor);
                    	System.out.println("Deposito realizado com sucesso!");
                		System.out.println("Saldo atual:"+titular.getSaldo());
                		banco.salvarSaldos();
                		subOpc = 0;
                	}catch(InputMismatchException e) {
                		System.out.println("Insira um valor correto!");
                	}
            	}
            	break;
            	
            case 4:
            	while(subOpc == 1) {
	            	Visual.quebraLinha();
	            	Visual.mostraSaldo(titular);
	                System.out.print("Insira o nome de usuário do destinatário: ");
	                String destinatario = scanner.nextLine();
	                Conta contaDestino = banco.getContaByNome(destinatario);
	
	                if (contaDestino == null) {
	                    System.out.println("Conta do destinatário não encontrada.");
	                }
	
	                System.out.print("Insira o valor a ser transferido: ");
	                try {
	                    valor = scanner.nextFloat();
	                    if (Operacoes.sacar(titular,valor)) {
	                    	Operacoes.depositar(contaDestino,valor);
	                    	
	                        System.out.println("Transferência realizada com sucesso!");
	                        Visual.mostraSaldo(titular);
	                        
	                        banco.salvarSaldos();
	                        subOpc = 0;
	                    } else {
	                        System.out.println("Saldo insuficiente para a transferência.");
	                    }
	                } catch (InputMismatchException e) {
	                    System.out.println("Insira um valor correto!");
	                }
            	}
                break;
            	
            case 5:
                System.out.println("Saindo do menu bancário...");
                loopUm = false;
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

