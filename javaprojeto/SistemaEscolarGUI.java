import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaEscolarGUI {
    private static final String ARQUIVO_ALUNOS = "alunos.txt";
    private static final Map<String, String> contas = new HashMap<>();
    private static final Map<String, Aluno> alunos = new HashMap<>();
    private static String usuarioLogado = "";

    public static void main(String[] args) {
        carregarDados(); // Carregar os dados dos alunos ao iniciar o programa
        criarConta("admin", "admin123"); // Criar uma conta de administrador padrão

        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            if (usuarioLogado.isEmpty()) {
                System.out.println("Bem-vindo! Faça login para continuar.");
                System.out.print("Usuário: ");
                String usuario = scanner.nextLine();
                System.out.print("Senha: ");
                String senha = scanner.nextLine();

                if (fazerLogin(usuario, senha)) {
                    System.out.println("Login bem-sucedido!");
                } else {
                    System.out.println("Usuário ou senha incorretos.");
                }
            } else {
                exibirMenu();
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarAluno(scanner);
                        break;
                    case 2:
                        adicionarNota(scanner);
                        break;
                    case 3:
                        atualizarNota(scanner);
                        break;
                    case 4:
                        exibirAlunos();
                        break;
                    case 5:
                        apagarNota(scanner);
                        break;
                    case 6:
                        salvarDados();
                        break;
                    case 7:
                        fazerLogout();
                        break;
                    case 8:
                        System.out.println("Obrigado por usar o sistema. Até mais!");
                        executando = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n### GESTÃO ESCOLAR ### - Desenvolvedores: Ronald, Gabriel, Guilherme");
        System.out.println("ESCOLHA UMA OPÇÃO: ");

        System.out.println("1. Cadastrar Aluno");
        System.out.println("2. Adicionar Nota");
        System.out.println("3. Atualizar Nota");
        System.out.println("4. Exibir Alunos");
        System.out.println("5. Apagar Nota");
        System.out.println("6. Salvar Dados");
        System.out.println("7. Fazer Logout");
        System.out.println("8. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static boolean fazerLogin(String usuario, String senha) {
        if (contas.containsKey(usuario) && contas.get(usuario).equals(senha)) {
            usuarioLogado = usuario;
            return true;
        }
        return false;
    }

    private static void criarConta(String usuario, String senha) {
        contas.put(usuario, senha);
    }

    private static void fazerLogout() {
        usuarioLogado = "";
    }

    private static void cadastrarAluno(Scanner scanner) {
        System.out.println("\n### CADASTRO DE ALUNO ###");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Matrícula: ");
        String matricula = scanner.nextLine();
        System.out.print("Idade: ");
        int idade = Integer.parseInt(scanner.nextLine());
        System.out.print("Turma: ");
        String turma = scanner.nextLine();

        Aluno aluno = new Aluno(nome, matricula, idade, turma);
        alunos.put(matricula, aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private static void adicionarNota(Scanner scanner) {
        System.out.println("\n### ADIÇÃO DE NOTA ###");
        System.out.print("Matrícula do Aluno: ");
        String matricula = scanner.nextLine();

        if (alunos.containsKey(matricula)) {
            Aluno aluno = alunos.get(matricula);
            System.out.print("Disciplina: ");
            String disciplina = scanner.nextLine();
            System.out.print("Nota: ");
            double nota = Double.parseDouble(scanner.nextLine());
            aluno.adicionarNota(disciplina, nota);
            System.out.println("Nota adicionada com sucesso!");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    private static void atualizarNota(Scanner scanner) {
        System.out.println("\n### ATUALIZAÇÃO DE NOTA ###");
        System.out.print("Matrícula do Aluno: ");
        String matricula = scanner.nextLine();

        if (alunos.containsKey(matricula)) {
            Aluno aluno = alunos.get(matricula);
            System.out.print("Disciplina: ");
            String disciplina = scanner.nextLine();
            System.out.print("Nova Nota: ");
            double novaNota = Double.parseDouble(scanner.nextLine());
            aluno.atualizarNota(disciplina, novaNota);
            System.out.println("Nota atualizada com sucesso!");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    private static void apagarNota(Scanner scanner) {
        System.out.println("\n### REMOÇÃO DE NOTA ###");
        System.out.print("Matrícula do Aluno: ");
        String matricula = scanner.nextLine();

        if (alunos.containsKey(matricula)) {
            Aluno aluno = alunos.get(matricula);
            System.out.print("Disciplina: ");
            String disciplina = scanner.nextLine();
            
            if(aluno.removerNota(disciplina)) {
                System.out.println("Nota removida com sucesso!");
            } else {
                System.out.println("A disciplina especificada não possui nota para remover.");
            }
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    private static void exibirAlunos() {
        System.out.println("\n### ALUNOS CADASTRADOS ###");
        for (Aluno aluno : alunos.values()) {
            System.out.println(aluno);
        }
    }

    private static void salvarDados() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARQUIVO_ALUNOS))) {
            for (Aluno aluno : alunos.values()) {
                writer.write(aluno.toFileString());
                writer.newLine();
            }
            System.out.println("Dados salvos com sucesso em " + ARQUIVO_ALUNOS);
        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private static void carregarDados() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_ALUNOS))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(", ");
                String nome = dados[0];
                String matricula = dados[1];
                int idade = Integer.parseInt(dados[2]);
                String turma = dados[3];
                Aluno aluno = new Aluno(nome, matricula, idade, turma);
                if (dados.length > 4) {
                    for (int i = 4; i < dados.length; i++) {
                        try {
                            String[] nota = dados[i].split(": ");
                            aluno.adicionarNota(nota[0], Double.parseDouble(nota[1]));
                        } catch (NumberFormatException e) {
                            System.out.println("Erro ao ler nota: " + e.getMessage());
                            // Você pode adicionar tratamento adicional aqui, se necessário
                        }
                    }
                }
                alunos.put(matricula, aluno);
            }
            System.out.println("Dados carregados com sucesso de " + ARQUIVO_ALUNOS);
        } catch (IOException e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}

class Aluno {
    private String nome;
    private String matricula;
    private int idade;
    private String turma;
    private Map<String, Double> notas;

    public Aluno(String nome, String matricula, int idade, String turma) {
        this.nome = nome;
        this.matricula = matricula;
        this.idade = idade;
        this.turma = turma;
        this.notas = new HashMap<>();
    }

    public void adicionarNota(String disciplina, double nota) {
        notas.put(disciplina, nota);
    }

    public void atualizarNota(String disciplina, double novaNota) {
        notas.put(disciplina, novaNota);
    }

    public boolean removerNota(String disciplina) {
        if(notas.containsKey(disciplina)) {
            notas.remove(disciplina);
            return true;
        }
        return false;
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append(nome).append(", ");
        sb.append(matricula).append(", ");
        sb.append(idade).append(", ");
        sb.append(turma).append(", ");
        sb.append("Notas: ");
        for (Map.Entry<String, Double> entry : notas.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "Nome: " + nome + ", Matrícula: " + matricula + ", Idade: " + idade + ", Turma: " + turma + ", Notas: " + notas;
    }
}
