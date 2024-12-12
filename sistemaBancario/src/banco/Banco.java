package banco;

import java.io.*;
import java.util.*;

import contas.Conta;
import contas.ContaAdicional;
import contas.ContaCorrentePrincipal;
import contas.ContaPoupanca;
import usuarios.Adicional;
import usuarios.Bancario;
import usuarios.Correntista;
import usuarios.Gerente;
import usuarios.Poupanca;
import usuarios.Usuario;

public class Banco {
    private List<Usuario> usuarios;
    private List<Conta> contas;
    private String diretorioAtual = System.getProperty("user.dir");
    private String usuariosDb = diretorioAtual + File.separator + "usuarios.txt";
    private String saldosDb = diretorioAtual + File.separator + "saldos.txt";

    public Banco() {
        usuarios = new ArrayList<>();
        contas = new ArrayList<>();
        carregarUsuarios();
        carregarSaldos();
    }

    public List<Usuario> getUsuarios() { // Corrigido o nome do método
        return usuarios;
    }

    public void adicionarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void adicionarConta(Conta conta) {
        contas.add(conta);
    }

    public Conta getContaByUsuario(Usuario usuario) {
        for (Conta conta : contas) {
            if (conta.getTitular().equals(usuario.getUsuario())) { // Corrigido para retornar diretamente
                return conta;
            }
        }
        return null;
    }

    public Conta getContaByNome(String usuario) {
        for (Conta conta : contas) {
            if (conta.getTitular().equals(usuario)) { // Corrigido para retornar diretamente
                return conta;
            }
        }
        return null;
    }

    public void carregarUsuarios() {
        try (BufferedReader reader = new BufferedReader(new FileReader(usuariosDb))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 4) {
                    String tipo = partes[0];
                    String usuario = partes[1];
                    String usuarioPai = partes[2];
                    String senha = partes[3];

                    switch (tipo.toLowerCase()) { // Substituído if-else por switch
                        case "gerente":
                            usuarios.add(new Gerente(usuario, senha));
                            break;
                        case "bancario":
                            usuarios.add(new Bancario(usuario, senha));
                            break;
                        case "poupanca":
                            usuarios.add(new Poupanca(usuario, senha));
                            break;
                        case "adicional":
                            usuarios.add(new Adicional(usuario, usuarioPai, senha));
                            break;
                        default:
                            System.err.println("Tipo de usuário desconhecido: " + tipo);
                    }
                } else {
                    System.err.println("Linha inválida em usuários.txt: " + linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    public void salvarUsuarios() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(usuariosDb, true))) { // Corrigido para evitar leitura desnecessária
            Set<String> usuariosExistentes = new HashSet<>();

            for (Usuario u : usuarios) {
                if (!usuariosExistentes.contains(u.getUsuario())) {
                    String tipo;

                    if (u instanceof Gerente) {
                        tipo = "gerente";
                    } else if (u instanceof Bancario) {
                        tipo = "bancario";
                    } else if (u instanceof Poupanca) {
                        tipo = "poupanca";
                    } else if (u instanceof Adicional) {
                        tipo = "adicional";
                    } else {
                        continue;
                    }

                    writer.write(tipo + "," + u.getUsuario() + "," + (u.getUsuarioPai() != null ? u.getUsuarioPai() : "") + "," + u.getSenha()); // Corrigido para evitar null
                    writer.newLine();
                }
            }

            System.out.println("Informações salvas ou mantidas.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    public void carregarSaldos() {
        try (BufferedReader reader = new BufferedReader(new FileReader(saldosDb))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(",");
                if (partes.length == 4) {
                    String login = partes[0];
                    double saldo = Double.parseDouble(partes[1]);
                    double limite = Double.parseDouble(partes[2]);
                    double especial = Double.parseDouble(partes[3]);

                    Usuario titular = usuarios.stream()
                            .filter(u -> u.getUsuario().equals(login))
                            .findFirst()
                            .orElse(null);

                    if (titular != null) {
                        Conta conta = new ContaCorrentePrincipal(titular.getUsuario(), saldo, limite, especial);
                        contas.add(conta); // Removida chamada desnecessária a Operacoes.depositar
                    } else {
                        System.err.println("Usuário não encontrado para o login: " + login);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar saldos: " + e.getMessage());
        }
    }

    public void salvarSaldos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saldosDb))) {
            for (Conta conta : contas) {
                writer.write(conta.getTitular() + "," + conta.getSaldo() + "," + conta.getLimite() + "," + conta.getChequeEspecial());
                writer.newLine();
            }
            System.out.println("Informações salvas ou mantidas.");
        } catch (IOException e) {
            System.err.println("Erro ao salvar contas: " + e.getMessage());
        }
    }

    public void salvarTudo() {
        salvarSaldos();
        salvarUsuarios();
    }

    public void adicionarContaPrincipal(String usuario, String senha, double saldo, double limite, double especial) {
        adicionarUsuario(new Correntista(usuario, usuario, senha));
        adicionarConta(new ContaCorrentePrincipal(usuario, saldo, limite, especial));
    }

    public void adicionarContaAdicional(String usuario, String usuarioPai, String senha, double saldo, double limite) {
        adicionarUsuario(new Adicional(usuario, usuarioPai, senha));
        adicionarConta(new ContaAdicional(usuario, saldo, limite));
    }

    public void adicionarContaBancario(String usuario, String senha) {
        adicionarUsuario(new Bancario(usuario, senha));
    }

    public void adicionarContaPoupanca(String usuario, String senha, double saldo) {
        adicionarUsuario(new Poupanca(usuario, senha));
        adicionarConta(new ContaPoupanca(usuario, saldo));
    }

    public Usuario autenticar(String usuario, String senha) {
        return usuarios.stream()
                .filter(u -> u.getUsuario().equals(usuario) && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null); // Substituído for loop por stream para maior clareza
    }
}
