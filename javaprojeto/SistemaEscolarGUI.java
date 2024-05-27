import java.io.BufferedReader; // Importação para leitura de arquivos de texto
import java.io.BufferedWriter; // Importação para escrita em arquivos de texto
import java.io.FileReader; // Importação para leitura de arquivos de texto
import java.io.FileWriter; // Importação para escrita em arquivos de texto
import java.io.IOException; // Importação para tratamento de exceções de entrada/saída
import java.util.HashMap; // Importação para utilizar a estrutura de dados Map
import java.util.Map; // Importação para utilizar a estrutura de dados Map
import java.util.Scanner; // Importação para leitura de entrada do usuário


public class SistemaEscolarGUI {
    // Declaração de constantes para o nome do arquivo e mapas para armazenar dados
    private static final String ARQUIVO_ALUNOS = "alunos.txt";
    private static final Map<String, String> contas = new HashMap<>();
    private static final Map<String, Aluno> alunos = new HashMap<>();
    private static String usuarioLogado = ""; // Variável para controlar o usuário logado

    public static void main(String[] args) {
        carregarDados(); // Carregar dados ao iniciar
        criarConta("admin", "admin123"); // Criar uma conta de administrador padrão

        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        // Loop principal do programa
        while (executando) {
            // Verificar se não há nenhum usuário logado
            if (usuarioLogado.isEmpty()) {
                System.out.println("Bem-vindo! Faça login para continuar.");
                System.out.print("Usuário: ");
                String usuario = scanner.nextLine();
                System.out.print("Senha: ");
                String senha = scanner.nextLine();

                // Verificar credenciais de login
                if (fazerLogin(usuario, senha)) {
                    System.out.println("Login bem-sucedido!");
                } else {
                    System.out.println("Usuário ou senha incorretos.");
                }
            } else {
                exibirMenu(); // Exibir menu quando um usuário está logado
                int opcao = Integer.parseInt(scanner.nextLine());

                // Lidar com a escolha do usuário
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
                        salvarDados(); // Salvar dados no arquivo
                        break;
                    case 7:
                        fazerLogout(); // Fazer logout do usuário
                        break;
                    case 8:
                        System.out.println("Obrigado por usar o sistema. Até mais!");
                        executando = false; // Encerrar o loop e sair do programa
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            }
        }

        scanner.close(); // Fechar o scanner
    }

    // Método para exibir o menu principal
    private static void exibirMenu() {
        System.out.println("\n### GESTÃO ESCOLAR ### - Desenvolvedores: Ronald, Gabriel, Guilherme");
        System.out.println("ESCOLHA UMA OPÇÃO: ");

        // Opções do menu
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

    // Método para fazer login
    private static boolean fazerLogin(String usuario, String senha) {
        // Verificar se as credenciais são válidas
        if (contas.containsKey(usuario) && contas.get(usuario).equals(senha)) {
            usuarioLogado = usuario; // Definir o usuário como logado
            return true;
        }
        return false;
    }

    // Método para criar uma conta
    private static void criarConta(String usuario, String senha) {
        contas.put(usuario, senha); // Adicionar conta ao mapa de contas
    }

    // Método para fazer logout
    private static void fazerLogout() {
        usuarioLogado = ""; // Limpar o usuário logado
    }

    // Método para cadastrar um novo aluno
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
        alunos.put(matricula, aluno); // Adicionar aluno ao mapa de alunos
        System.out.println("Aluno cadastrado com sucesso!");
    }

    // Método para adicionar nota a um aluno
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
            aluno.adicionarNota(disciplina, nota); // Adicionar nota ao aluno
            System.out.println("Nota adicionada com sucesso!");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    // Método para atualizar a nota de um aluno
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
            aluno.atualizarNota(disciplina, novaNota); // Atualizar nota do aluno
            System.out.println("Nota atualizada com sucesso!");
        } else {
            System.out.println("Aluno não encontrado.");
        }
    }

    // Método para apagar nota de um aluno
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

    // Método para exibir os alunos cadastrados
    private static void exibirAlunos() {
        System.out.println("\n### ALUNOS CADASTRADOS ###");
        for (Aluno aluno : alunos.values()) {
            System.out.println(aluno); // Exibir os dados de todos os alunos
        }
    }

    // Método para salvar os dados dos alunos no arquivo
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

    // Método para carregar os dados dos alunos do arquivo
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

// Classe Aluno para representar os dados de um aluno
class Aluno {
    private String nome;
    private String matricula;
    private int idade;
    private String turma;
    private Map<String, Double> notas;

    // Construtor da classe Aluno
    public Aluno(String nome, String matricula, int idade, String turma) {
        this.nome = nome;
        this.matricula = matricula;
        this.idade = idade;
        this.turma = turma;
        this.notas = new HashMap<>();
    }

    // Método para adicionar nota a um aluno
    public void adicionarNota(String disciplina, double nota) {
        notas.put(disciplina, nota);
    }

    // Método para atualizar nota de um aluno
    public void atualizarNota(String disciplina, double novaNota) {
        notas.put(disciplina, novaNota);
    }

    // Método para remover nota de um aluno
    public boolean removerNota(String disciplina) {
        if(notas.containsKey(disciplina)) {
            notas.remove(disciplina);
            return true;
        }
        return false;
    }

    // Método para formatar os dados do aluno para salvar no arquivo
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

    // Método para representação em String do objeto Aluno
    @Override
    public String toString() {
        return "Nome: " + nome + ", Matrícula: " + matricula + ", Idade: " + idade + ", Turma: " + turma + ", Notas: " + notas;
    }
}
