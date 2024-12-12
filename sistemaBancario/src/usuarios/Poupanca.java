package usuarios;

import java.util.InputMismatchException;
import java.util.Scanner;
import banco.Banco;
import banco.Operacoes;
import banco.Visual;
import contas.Conta;

public class Poupanca extends Usuario {
    public Poupanca(String usuario, String senha) {
    	super(usuario,senha);
	}

    @Override
    public void menu(Banco banco, Usuario usuario) {

        Scanner scanner = new Scanner(System.in);
        
    	Conta titular = banco.getContaByUsuario(usuario);
    	boolean menuPrinc = true;
    	
    	while(menuPrinc) {
    		System.out.println("====== Menu Correntista ======");
            System.out.println("1. Ver saldo");
            System.out.println("2. Sacar");
            System.out.println("3. Depositar");
            System.out.println("4. Sair");
            
            int opcao = scanner.nextInt();
            int subOpc = 1;
            
            scanner.nextLine();
            float valor;
            
            switch(opcao){
            case 1:
            	Visual.quebraLinha();
            	System.out.println("Saldo atual:"+titular.getSaldo());
            	break;
            	
            case 2:
            	while(subOpc == 1) {
            		Visual.quebraLinha();
            		System.out.println("Saldo atual:"+titular.getSaldo());
                	System.out.println("Insira o valor a ser sacado:");
                	try{
                		valor = scanner.nextFloat();
                		if(Operacoes.sacar(titular,valor) == false)
                    	{
                    		System.out.println("Saldo insuficiente!");
                    	}else {
                    		System.out.println("Saque realizado com sucesso!");
                    		System.out.println("Saldo atual:"+titular.getSaldo());
                    		banco.salvarSaldos();
                    		subOpc = 0;
                    	}
                	}catch (InputMismatchException e) {
                		System.out.println("Insira um valor correto!");
                	}
            	}
            	break;
            	
            case 3:
            	while(subOpc == 1) {
            		Visual.quebraLinha();
            		System.out.println("Saldo atual:"+titular.getSaldo());
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

