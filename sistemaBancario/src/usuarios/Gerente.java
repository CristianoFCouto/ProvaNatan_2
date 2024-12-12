package usuarios;

import java.util.InputMismatchException;
import java.util.Scanner;

import banco.Banco;
import banco.Visual;
import contas.Conta;

public class Gerente extends Usuario {

    Scanner scanner = new Scanner(System.in);
    
    public Gerente(String usuario, String senha) {
        super(usuario, senha);
    }

    @Override
    public void menu(Banco banco, Usuario usuario) {
        boolean loopUm = true;
    	boolean loopDois = true;
        double limite = 0;
    	while(loopUm) {
    		Visual.quebraLinha();
    		System.out.println("====== Menu Gerente ======");
    		System.out.println("1. Ver Usuarios");
            System.out.println("2. Cadastrar");
            System.out.println("3. Alterar Limites");
            System.out.println("4. Sair");
            
            System.out.println("Opção:");
            int opcao = scanner.nextInt();
            
            Visual.quebraLinha();
            
            switch(opcao){
            case 1:
            	System.out.println("Usuarios cadastrados:");
            	for (Usuario usuarios : banco.getUsuarios()) {
            	    System.out.println("Nome de usuário: " + usuarios.getUsuario()+"\n");
            	}
            	System.out.println("Precione Qualquer Tecla para Continuar...");
            	scanner.nextInt();
            break;
            	
            case 2:
            	while(loopDois) {
	            	System.out.println("Tipo do novo cliente");
	            	System.out.println("1. Bancario");
	                System.out.println("2. Correntista");
	                System.out.println("3. Cancelar/Finalizar");
	                
	                System.out.println("Opção:");
	                int tipo = scanner.nextInt();
	               
	            	System.out.println("Nome do Usuário: ");
	            	String nome = scanner.next();
	                
	                System.out.println("Senha do Usuário: ");
	                String senha = scanner.next();
	                
	                
	                if(tipo == 1) {
	                	banco.adicionarContaBancario(nome,senha);
	                }else if(tipo == 2) {
	                	System.out.println("Tipo de Conta");
		            	System.out.println("1. Principal");
		                System.out.println("2. Adicional");
		                System.out.println("3. Poupanca");
		                System.out.println("4. Cancelar Operação.");
		                
		                System.out.println("Opção:");
		                tipo = scanner.nextInt();
		                
		                switch(tipo) {
			                case 1:
			                	double especial = 0;
			                	
			                	while(loopDois) {
					                try {
					                	System.out.println("Limite:");
					                	limite = scanner.nextDouble();
					                	
					                	loopDois = false;
					                }catch(InputMismatchException e) {
				                		System.out.println("Insira um valor correto!");
				                		Visual.quebraLinha();
				                		
				                		loopDois = true;
				                	}
				                }
			                	loopDois = true;

			                	while(loopDois) {
					                try {
					                	System.out.println("Cheque Especial:");
					                	especial = scanner.nextDouble();
					                	loopDois = false;
					                }catch(InputMismatchException e) {
				                		System.out.println("Insira um valor correto!");
				                		Visual.quebraLinha();
				                		loopDois = true;
				                	}
				                }
			                	
			                	banco.adicionarContaPrincipal(nome,senha,0,limite,especial);
			                	System.out.println("Conta criada com sucesso.");
			                	loopDois = true;
			                	break;
			                	
			                case 2:
			                	Conta contaPai = null;
			                    String contaPrincipal = null;
			                	while(loopDois) {
			                		System.out.println("Conta Principal:");
				                	contaPrincipal = scanner.next();
				                	
					                contaPai = banco.getContaByNome(contaPrincipal);
					                if(contaPai !=null) {
					                	loopDois = true;
					                }else {
					                	System.out.println("Usuario não encontrado!");
				                		Visual.quebraLinha();
				                		loopDois = false;
					                }
				                	
				                	
				                }
			                	
			                	loopDois = true;
			                	while(loopDois) {
					                try {
					                	System.out.println("Limite:");
					                	limite = scanner.nextDouble();
					                	loopDois = false;
					                }catch(InputMismatchException e) {
				                		System.out.println("Insira um valor correto!");
				                		Visual.quebraLinha();
				                		loopDois = true;
				                	}
				                }
			                	banco.adicionarContaAdicional(nome,contaPai.getTitular(),senha,0,limite);
			                	System.out.println("Conta criada com sucesso.");
			                	loopDois = true;
			                	
			                	break;
			                case 3:
			                	banco.adicionarContaPoupanca(nome,senha,0);
			                	System.out.println("Conta criada com sucesso.");
			                	break;
			                	
			                case 4:
				                System.out.println("Cancelando Operação.");
							try {
								Thread.sleep(1400);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
								
			                	break;
			                default:

				                System.out.println("Opção Invalida!");
			                	break;
		                }
		                banco.salvarTudo();
	                }else if (tipo == 3){
	                	System.out.println("Cancelando Operação.");
	                	loopDois = false;
	                }else {
	                	System.out.println("Opção Invalida!");
	                }
	                
            	}
            case 3:
//            	System.out.println("Saindo do menu bancário...");
                loopUm = false;
                break;
            case 4:
            	System.out.println("Saindo do menu bancário...");
                loopUm = false;
                break;
                
            default:
                System.out.println("Opção inválida. Por favor, escolha novamente.");
                break;
            }
            try {
				Thread.sleep(2400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    	scanner.close();
    }
}
