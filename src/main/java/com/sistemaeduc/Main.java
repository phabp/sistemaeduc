
package com.sistemaeduc;

import com.sistemaeduc.entities.Usuario;
import com.sistemaeduc.repositories.UsuarioRepository;
import com.sistemaeduc.services.MenuService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MenuService menuService;

    public Main(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder, MenuService menuService) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.menuService = menuService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        Usuario usuarioLogado = menuLogin(scanner);
        menuPrincipal(scanner);
    }

    private Usuario menuLogin(Scanner scanner) {
        Usuario usuarioLogado = null;

        while (usuarioLogado == null) {
            System.out.println("\nBEM VINDO AO SISTEMAEDUC");
            System.out.println("1 - Fazer login");
            System.out.println("2 - Cadastrar novo usuário");
            System.out.println("0 - Sair");
            System.out.print("Digite uma das opções: ");
            String escolha = scanner.nextLine();

            switch (escolha) {
                case "1":
                    usuarioLogado = realizarLogin(scanner);
                    break;
                case "2":
                    cadastrarUsuario(scanner);
                    break;
                case "0":
                    System.out.println("Saindo do sistema. Obrigado!");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida.");
            }
        }
        return usuarioLogado;
    }

    private Usuario realizarLogin(Scanner scanner) {
        System.out.print("CPF: ");
        String cpfLogin = scanner.nextLine();
        System.out.print("Senha: ");
        String senhaLogin = scanner.nextLine();
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCpf(cpfLogin);
        if (usuarioOpt.isPresent() && passwordEncoder.matches(senhaLogin, usuarioOpt.get().getSenha())) {
            System.out.println("Login bem-sucedido. Bem-vindo, " + usuarioOpt.get().getNome());
            return usuarioOpt.get();
        } else {
            System.out.println("CPF ou senha incorretos.");
            return null;
        }
    }

    private void cadastrarUsuario(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        if (usuarioRepository.findByCpf(cpf).isPresent()) {
            System.out.println("Erro: CPF já cadastrado.");
            return;
        }
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Data de nascimento (ano-mm-dd): ");
        String dataNascimento = scanner.nextLine();

        try {
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setCpf(cpf);
            novoUsuario.setSenha(passwordEncoder.encode(senha));
            novoUsuario.setDataNascimento(LocalDate.parse(dataNascimento));
            usuarioRepository.save(novoUsuario);
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar usuário. Verifique os dados inseridos.");
        }
    }

    private void menuPrincipal(Scanner scanner) {
        while (true) {
            System.out.println("\nMENU PRINCIPAL DO SISTEMAEDUC");
            System.out.println("1 - Cadastrar Escola");
            System.out.println("2 - Cadastrar Professor");
            System.out.println("3 - Cadastrar Aluno");
            System.out.println("4 - Listar Escolas");
            System.out.println("5 - Listar Professores");
            System.out.println("6 - Listar Alunos");
            System.out.println("7 - Encontrar dados de aluno por nome");
            System.out.println("8 - Encontrar dados de professor por nome");
            System.out.println("9 - Listar alunos vinculados a um professor específico (por ID)");
            System.out.println("10 - Listar professores vinculados a escola específica (por ID)");
            System.out.println("11 - Listar todos os alunos vinculados a uma escola específica (por ID)");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "0":
                    System.out.println("Encerrando o sistema. Até logo!");
                    System.exit(0);
                case "1":
                    menuService.cadastrarEscola(scanner);
                    break;
                case "2":
                    menuService.cadastrarProfessor(scanner);
                    break;
                case "3":
                    menuService.cadastrarAluno(scanner);
                    break;
                case "4":
                    menuService.listarEscolas();
                    break;
                case "5":
                    menuService.listarProfessores();
                    break;
                case "6":
                    menuService.listarAlunos();
                    break;
                case "7":
                    menuService.buscarAlunoPorNome(scanner);
                    break;
                case "8":
                    menuService.buscarProfessorPorNome(scanner);
                    break;
                case "9":
                    menuService.listarAlunosPorProfessor(scanner);
                    break;
                case "10":
                    menuService.listarProfessoresPorEscola(scanner);
                    break;
                case "11":
                    menuService.listarAlunosPorEscola(scanner);
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção listada.");
            }
        }
    }
}
