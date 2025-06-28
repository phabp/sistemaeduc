package com.sistemaeduc.services;
import com.sistemaeduc.entities.Aluno;
import com.sistemaeduc.entities.Escola;
import com.sistemaeduc.entities.Professor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Scanner;

@Service
public class MenuService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String BASE_URL = "http://localhost:8082/api";

    public void cadastrarEscola(Scanner scanner) {
        try {
            System.out.print("Nome da escola: ");
            String nome = scanner.nextLine();
            System.out.print("Cidade: ");
            String cidade = scanner.nextLine();

            Escola escola = new Escola();
            escola.setNome(nome);
            escola.setCidade(cidade);

            ResponseEntity<Escola> response = restTemplate.postForEntity(BASE_URL + "/escolas", escola, Escola.class);
            System.out.println(response.getStatusCode().is2xxSuccessful()
                    ? "Escola cadastrada: " + response.getBody()
                    : "Erro ao cadastrar escola.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarProfessor(Scanner scanner) {
        try {
            System.out.print("Nome do professor: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Senha: ");
            String senha = scanner.nextLine();
            System.out.print("Data de nascimento (yyyy-mm-dd): ");
            String data = scanner.nextLine();
            System.out.print("ID da escola vinculada: ");
            Long escolaId = Long.parseLong(scanner.nextLine());

            Professor prof = new Professor();
            prof.setNome(nome);
            prof.setCpf(cpf);
            prof.setSenha(senha);
            prof.setDataNascimento(LocalDate.parse(data));
            prof.setEscola(new Escola(escolaId));

            ResponseEntity<Professor> response = restTemplate.postForEntity(BASE_URL + "/professores", prof, Professor.class);
            System.out.println(response.getStatusCode().is2xxSuccessful()
                    ? "Professor cadastrado: " + response.getBody()
                    : "Erro ao cadastrar professor.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void cadastrarAluno(Scanner scanner) {
        try {
            System.out.print("Nome do aluno: ");
            String nome = scanner.nextLine();
            System.out.print("CPF: ");
            String cpf = scanner.nextLine();
            System.out.print("Data de nascimento (yyyy-mm-dd): ");
            String data = scanner.nextLine();
            System.out.print("ID do professor vinculado: ");
            Long profId = Long.parseLong(scanner.nextLine());

            Aluno aluno = new Aluno();
            aluno.setNome(nome);
            aluno.setCpf(cpf);
            aluno.setDatadNascimento(LocalDate.parse(data));
            aluno.setProfessor(new Professor(profId));

            ResponseEntity<Aluno> response = restTemplate.postForEntity(BASE_URL + "/alunos", aluno, Aluno.class);
            System.out.println(response.getStatusCode().is2xxSuccessful()
                    ? "Aluno cadastrado: " + response.getBody()
                    : "Erro ao cadastrar aluno.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarEscolas() {
        try {
            ResponseEntity<Escola[]> response = restTemplate.getForEntity(BASE_URL + "/escolas", Escola[].class);
            Escola[] escolas = response.getBody();
            if (escolas == null || escolas.length == 0) {
                System.out.println("Nenhuma escola encontrada.");
            } else {
                for (Escola e : escolas) {
                    System.out.println("ID: " + e.getId());
                    System.out.println("Nome: " + e.getNome());
                    System.out.println("Cidade: " + e.getCidade());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarProfessores() {
        try {
            ResponseEntity<Professor[]> response = restTemplate.getForEntity(BASE_URL + "/professores", Professor[].class);
            Professor[] professores = response.getBody();
            if (professores == null || professores.length == 0) {
                System.out.println("Nenhum professor encontrado.");
            } else {
                for (Professor p : professores) {
                    System.out.println("ID: " + p.getId());
                    System.out.println("Nome: " + p.getNome());
                    System.out.println("CPF: " + p.getCpf());
                    System.out.println("Escola: " + (p.getEscola() != null ? p.getEscola().getNome() : "N/A"));
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarAlunos() {
        try {
            ResponseEntity<Aluno[]> response = restTemplate.getForEntity(BASE_URL + "/alunos", Aluno[].class);
            Aluno[] alunos = response.getBody();
            if (alunos == null || alunos.length == 0) {
                System.out.println("Nenhum aluno encontrado.");
            } else {
                for (Aluno a : alunos) {
                    System.out.println("ID: " + a.getId());
                    System.out.println("Nome: " + a.getNome());
                    System.out.println("CPF: " + a.getCpf());
                    System.out.println("Nascimento: " + a.getDatadNascimento());
                    System.out.println("Professor: " + (a.getProfessor() != null ? a.getProfessor().getNome() : "N/A"));
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void buscarAlunoPorNome(Scanner scanner) {
        System.out.print("Parte do nome: ");
        String nome = scanner.nextLine();
        try {
            ResponseEntity<Aluno[]> response = restTemplate.getForEntity(BASE_URL + "/alunos/nome/" + nome, Aluno[].class);
            Aluno[] alunos = response.getBody();
            if (alunos == null || alunos.length == 0) {
                System.out.println("Nenhum aluno encontrado.");
            } else {
                for (Aluno a : alunos) {
                    System.out.println("ID: " + a.getId());
                    System.out.println("Nome: " + a.getNome());
                    System.out.println("CPF: " + a.getCpf());
                    System.out.println("Nascimento: " + a.getDatadNascimento());

                    if (a.getProfessor() != null) {
                        System.out.println("Professor: " + a.getProfessor().getNome());
                        if (a.getProfessor().getEscola() != null) {
                            System.out.println("Escola: " + a.getProfessor().getEscola().getNome());
                        } else {
                            System.out.println("Escola: N/A");
                        }
                    } else {
                        System.out.println("Professor: N/A");
                        System.out.println("Escola: N/A");
                    }

                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


    public void buscarProfessorPorNome(Scanner scanner) {
        System.out.print("Parte do nome: ");
        String nome = scanner.nextLine();
        try {
            ResponseEntity<Professor[]> response = restTemplate.getForEntity(BASE_URL + "/professores/nome/" + nome, Professor[].class);
            Professor[] professores = response.getBody();
            if (professores == null || professores.length == 0) {
                System.out.println("Nenhum professor encontrado.");
            } else {
                for (Professor p : professores) {
                    System.out.println("ID: " + p.getId());
                    System.out.println("Nome: " + p.getNome());
                    System.out.println("CPF: " + p.getCpf());
                    System.out.println("Nascimento: " + p.getDataNascimento());
                    
                    if (p.getEscola() != null) {
                        System.out.println("Escola: " + p.getEscola().getNome());
                    } else {
                        System.out.println("Escola: N/A");
                    }
                    
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


    public void listarAlunosPorProfessor(Scanner scanner) {
        System.out.print("ID do professor: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            ResponseEntity<Aluno[]> response = restTemplate.getForEntity(BASE_URL + "/alunos/professor/" + id, Aluno[].class);
            Aluno[] alunos = response.getBody();
            if (alunos == null || alunos.length == 0) {
                System.out.println("Nenhum aluno vinculado a este professor.");
            } else {
                for (Aluno a : alunos) {
                    System.out.println("ID: " + a.getId());
                    System.out.println("Nome: " + a.getNome());
                    System.out.println("CPF: " + a.getCpf());
                    System.out.println("Nascimento: " + a.getDatadNascimento());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarProfessoresPorEscola(Scanner scanner) {
        System.out.print("ID da escola: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            ResponseEntity<Professor[]> response = restTemplate.getForEntity(BASE_URL + "/professores/escola/" + id, Professor[].class);
            Professor[] professores = response.getBody();
            if (professores == null || professores.length == 0) {
                System.out.println("Nenhum professor vinculado a esta escola.");
            } else {
                for (Professor p : professores) {
                    System.out.println("ID: " + p.getId());
                    System.out.println("Nome: " + p.getNome());
                    System.out.println("CPF: " + p.getCpf());
                    System.out.println("Nascimento: " + p.getDataNascimento());
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void listarAlunosPorEscola(Scanner scanner) {
        System.out.print("ID da escola: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            ResponseEntity<Aluno[]> response = restTemplate.getForEntity(BASE_URL + "/alunos/escola/" + id, Aluno[].class);
            Aluno[] alunos = response.getBody();
            if (alunos == null || alunos.length == 0) {
                System.out.println("Nenhum aluno vinculado a esta escola.");
            } else {
                for (Aluno a : alunos) {
                    System.out.println("ID: " + a.getId());
                    System.out.println("Nome: " + a.getNome());
                    System.out.println("CPF: " + a.getCpf());
                    System.out.println("Nascimento: " + a.getDatadNascimento());
                    System.out.println("Professor: " + (a.getProfessor() != null ? a.getProfessor().getNome() : "N/A"));
                    System.out.println();
                }
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
