package main;
import java.util.Scanner;

import banco.Banco;
import usuarios.Usuario;

public class Main {
    public static void main(String[] args) throws InterruptedException {
    	
        boolean menuPrinc = true;
        Scanner scanner = new Scanner(System.in);
        
        while(menuPrinc) {
        	Banco banco = new Banco();
        	System.out.println("============ Sistema Bancário ============");
       
            System.out.print("Usuário: ");
            String usuario = scanner.nextLine();
            
            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            Usuario user = banco.autenticar(usuario, senha);

            if (user != null) {
                user.menu(banco, user);
            } else {
                System.out.println("Autenticação falhou.");
                
            }
    		System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        }
        scanner.close();
    }
}
