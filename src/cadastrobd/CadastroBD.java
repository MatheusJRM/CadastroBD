package cadastrobd;

import cadastro.model.PessoaFisicaDAO;
import cadastro.model.PessoaJuridicaDAO;
import cadastrobd.model.PessoaFisica;
import cadastrobd.model.PessoaJuridica;
import cadastro.model.util.ConectorBD;
import cadastrobd.model.Endereco;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CadastroBD {

    public static void main(String[] args) throws SQLException {
        try (Scanner scanner = new Scanner(System.in)) {
            ConectorBD conectorBD = new ConectorBD();
            PessoaFisicaDAO pessoaFisicaDAO = new PessoaFisicaDAO(conectorBD);
            PessoaJuridicaDAO pessoaJuridicaDAO = new PessoaJuridicaDAO(conectorBD);
            int option;

            do {
                System.out.println("==============================");
                System.out.println("1 - Incluir Pessoa");
                System.out.println("2 - Alterar Pessoa");
                System.out.println("3 - Excluir Pessoa");
                System.out.println("4 - Buscar pelo Id");
                System.out.println("5 - Exibir Todos");
                System.out.println("0 - Finalizar Programa");
                System.out.println("==============================");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 ->
                        incluirPessoa(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 2 ->
                        alterarPessoa(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 3 ->
                        excluirPessoa(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 4 ->
                        buscarPorId(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 5 ->
                        exibirTodos(scanner, pessoaFisicaDAO, pessoaJuridicaDAO);
                    case 0 ->
                        System.out.println("Finalizando o programa...");
                    default ->
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } while (option != 0);
        }
    }

    private static void incluirPessoa(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();
        try {
            if (!tipo.equals("F")) {
                if (tipo.equals("J")) {
                    System.out.println("Nome: ");
                    String nome = scanner.nextLine();
                    if (nome.isEmpty()) {
                        System.out.println("Nome é obrigatório.");
                        return;
                    }
                    System.out.println("CNPJ(APENAS DIGITOS): ");
                    String cnpj = scanner.nextLine();
                    if (cnpj.isEmpty()) {
                        System.out.println("CNPJ é obrigatório.");
                        return;
                    }
                    if (!cnpj.matches("\\d{14}")) {
                        System.out.println("CNPJ invalido! Deve conter apenas 14 digitos.");
                        return;
                    }
                    System.out.println("Telefone: ");
                    String telefone = scanner.nextLine();

                    if (telefone.isEmpty()) {
                        System.out.println("Telefone é obrigatório.");
                        return;
                    }
                    System.out.println("CEP(APENAS DIGITOS): ");
                    String cep = scanner.nextLine();
                    if (cep.isEmpty()) {
                        System.out.println("CEP é obrigatório.");
                        return;
                    }
                    if (!cep.matches("\\d{8}")) {
                        System.out.println("CEP invalido! Deve conter apenas 8 digitos.");
                        return;
                    }
                    System.out.println("Logradouro: ");
                    String logradouro = scanner.nextLine();
                    if (logradouro.isEmpty()) {
                        System.out.println("Logradouro é obrigatório.");
                        return;
                    }
                    System.out.println("Bairro: ");
                    String bairro = scanner.nextLine();
                    if (bairro.isEmpty()) {
                        System.out.println("Bairro é obrigatório.");
                        return;
                    }
                    System.out.println("Número: ");
                    String numero = scanner.nextLine();
                    if (numero.isEmpty()) {
                        System.out.println("Número é obrigatório.");
                        return;
                    }
                    System.out.println("Cidade: ");
                    String cidade = scanner.nextLine();
                    if (cidade.isEmpty()) {
                        System.out.println("Cidade é obrigatório.");
                        return;
                    }
                    System.out.println("UF: ");
                    String uf = scanner.nextLine();
                    if (uf.isEmpty()) {
                        System.out.println("UF é obrigatório.");
                        return;
                    }
                    if (!uf.matches("[a-zA-Z]{2}")) {
                        System.out.println("UF invalido! Deve conter apenas 2 letras.");
                        return;
                    }
                    System.out.println("Complemento: ");
                    String complemento = scanner.nextLine();
                    Endereco endereco = new Endereco();
                    endereco.setCep(cep);
                    endereco.setLogradouro(logradouro);
                    endereco.setBairro(bairro);
                    endereco.setNumero(numero);
                    endereco.setCidade(cidade);
                    endereco.setEstado(uf);
                    endereco.setComplemento(complemento);
                    PessoaJuridica pj = new PessoaJuridica();
                    pj.setNome(nome);
                    pj.setTelefone(telefone);
                    pj.setEndereco(endereco);
                    pj.setCnpj(cnpj);
                    pjDAO.incluirPessoaJuridica(pj);
                } else {
                    System.out.println("Tipo inválido.");
                }
            } else {
                System.out.println("Nome: ");
                String nome = scanner.nextLine();
                if (nome.isEmpty()) {
                    System.out.println("Nome é obrigatório.");
                    return;
                }
                if (!nome.matches("[A-Za-z\\s\\p{Punct}]+")) {
                    System.out.println("Nome invalido!");
                    return;
                }
                System.out.println("CPF(APENAS DIGITOS): ");
                String cpf = scanner.nextLine();
                if (cpf.isEmpty()) {
                    System.out.println("CPF invalido! Deve ser uma String nao vazia.");
                    return;
                }
                if (!cpf.matches("\\d{11}")) {
                    System.out.println("CPF invalido! Deve conter apenas 11 digitos.");
                    return;
                }
                System.out.println("Telefone: ");
                String telefone = scanner.nextLine();
                if (telefone.isEmpty()) {
                    System.out.println("Telefone é obrigatório.");
                    return;
                }
                System.out.println("CEP(APENAS DIGITOS): ");
                String cep = scanner.nextLine();
                if (cep.isEmpty()) {
                    System.out.println("CEP é obrigatório.");
                    return;
                }
                if (!cep.matches("\\d{8}")) {
                    System.out.println("CEP invalido! Deve conter apenas 8 digitos.");
                    return;
                }
                System.out.println("Logradouro: ");
                String logradouro = scanner.nextLine();
                if (logradouro.isEmpty()) {
                    System.out.println("Logradouro é obrigatório.");
                    return;
                }
                System.out.println("Bairro: ");
                String bairro = scanner.nextLine();
                if (bairro.isEmpty()) {
                    System.out.println("Bairro é obrigatório.");
                    return;
                }
                System.out.println("Número: ");
                String numero = scanner.nextLine();
                if (numero.isEmpty()) {
                    System.out.println("Número é obrigatório.");
                    return;
                }
                System.out.println("Cidade: ");
                String cidade = scanner.nextLine();
                if (cidade.isEmpty()) {
                    System.out.println("Cidade é obrigatório.");
                    return;
                }
                System.out.println("UF: ");
                String uf = scanner.nextLine();
                if (uf.isEmpty()) {
                    System.out.println("UF é obrigatório.");
                    return;
                }
                if (!uf.matches("[a-zA-Z]{2}")) {
                    System.out.println("UF invalido! Deve conter apenas 2 letras.");
                    return;
                }
                System.out.println("Complemento: ");
                String complemento = scanner.nextLine();
                Endereco endereco = new Endereco();
                endereco.setCep(cep);
                endereco.setLogradouro(logradouro);
                endereco.setBairro(bairro);
                endereco.setNumero(numero);
                endereco.setCidade(cidade);
                endereco.setEstado(uf);
                endereco.setComplemento(complemento);
                PessoaFisica pf = new PessoaFisica();
                pf.setNome(nome);
                pf.setTelefone(telefone);
                pf.setEndereco(endereco);
                pf.setCpf(cpf);
                pfDAO.incluirPessoaFisica(pf);
            }
        } catch (Exception e) {
            System.out.println("Erro ao incluir pessoa: " + e.getMessage());
        }
    }

    private static void alterarPessoa(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            if (!tipo.equals("F")) {
                if (tipo.equals("J")) {
                    System.out.println("Informe o ID da Pessoa Jurídica que deseja alterar: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    PessoaJuridica pj = pjDAO.getPessoaJuridica(id);
                    if (pj != null) {
                        System.out.println("Dados atuais:");
                        System.out.println("Nome: " + pj.getNome());
                        System.out.println("CNPJ: " + pj.getCnpj());
                        System.out.println("Telefone: " + pj.getTelefone());
                        System.out.println("CEP: " + pj.getEndereco().getCep());
                        System.out.println("Logradouro: " + pj.getEndereco().getLogradouro());
                        System.out.println("Bairro: " + pj.getEndereco().getBairro());
                        System.out.println("Número: " + pj.getEndereco().getNumero());
                        System.out.println("Cidade: " + pj.getEndereco().getCidade());
                        System.out.println("UF: " + pj.getEndereco().getEstado());
                        System.out.println("Complemento: " + pj.getEndereco().getComplemento());

                        System.out.println("Informe o novo nome (Ou aperte ENTER para manter): ");
                        String nome = scanner.nextLine();
                        if (!nome.isEmpty()) {
                            pj.setNome(nome);
                        }
                        System.out.println("Informe o novo CNPJ com APENAS DIGITOS (Ou aperte ENTER para manter): ");
                        String cnpj = scanner.nextLine();
                        if (!cnpj.isEmpty() && !cnpj.matches("\\d{14}")) {
                            System.out.println("CNPJ invalido! Deve conter apenas 14 digitos.");
                            return;
                        }
                        if (!cnpj.isEmpty()) {
                            pj.setCnpj(cnpj);
                        }
                        System.out.println("Informe o novo telefone (Ou aperte ENTER para manter): ");
                        String telefone = scanner.nextLine();
                        if (!telefone.isEmpty()) {
                            pj.setTelefone(telefone);
                        }
                        System.out.println("Informe o novo CEP (Ou aperte ENTER para manter)(APENAS DIGITOS): ");
                        String cep = scanner.nextLine();
                        if (!cep.isEmpty() && !cep.matches("\\d{8}")) {
                            System.out.println("CEP invalido! Deve conter apenas 8 digitos.");
                            return;
                        }
                        if (!cep.isEmpty()) {
                            pj.getEndereco().setCep(cep);
                        }
                        System.out.println("Informe o novo logradouro (Ou aperte ENTER para manter): ");
                        String logradouro = scanner.nextLine();
                        if (!logradouro.isEmpty()) {
                            pj.getEndereco().setLogradouro(logradouro);
                        }
                        System.out.println("Informe o novo bairro (Ou aperte ENTER para manter): ");
                        String bairro = scanner.nextLine();
                        if (!bairro.isEmpty()) {
                            pj.getEndereco().setBairro(bairro);
                        }
                        System.out.println("Informe o novo número (Ou aperte ENTER para manter): ");
                        String numero = scanner.nextLine();
                        if (!numero.isEmpty()) {
                            pj.getEndereco().setNumero(numero);
                        }
                        System.out.println("Informe o novo cidade (Ou aperte ENTER para manter): ");
                        String cidade = scanner.nextLine();
                        if (!cidade.isEmpty()) {
                            pj.getEndereco().setCidade(cidade);
                        }
                        System.out.println("Informe a nova UF (Ou aperte ENTER para manter): ");
                        String uf = scanner.nextLine();
                        if (!uf.isEmpty() && !uf.matches("[a-zA-Z]{2}")) {
                            System.out.println("UF invalido! Deve conter apenas 2 letras.");
                            return;
                        }
                        if (!uf.isEmpty()) {
                            pj.getEndereco().setEstado(uf);
                        }
                        System.out.println("Informe o novo complemento (Ou aperte ENTER para manter): ");
                        String complemento = scanner.nextLine();
                        if (!complemento.isEmpty()) {
                            pj.getEndereco().setComplemento(complemento);
                        }
                        pjDAO.alterarPessoaJuridica(pj);
                        System.out.println("Pessoa Jurídica atualizada com sucesso!");
                    } else {
                        System.out.println("Nenhuma pessoa jurídica com o ID informado foi encontrada.");
                    }
                } else {
                    System.out.println("Tipo inválido.");
                }
            } else {
                System.out.println("Informe o ID da Pessoa Física que deseja alterar: ");
                int id = Integer.parseInt(scanner.nextLine());

                PessoaFisica pf = pfDAO.getPessoaFisica(id);
                if (pf != null) {
                    System.out.println("Dados atuais: ");
                    System.out.println("Nome: " + pf.getNome());
                    System.out.println("CPF: " + pf.getCpf());
                    System.out.println("Telefone: " + pf.getTelefone());
                    System.out.println("CEP: " + pf.getEndereco().getCep());
                    System.out.println("Logradouro: " + pf.getEndereco().getLogradouro());
                    System.out.println("Bairro: " + pf.getEndereco().getBairro());
                    System.out.println("Número: " + pf.getEndereco().getNumero());
                    System.out.println("Cidade: " + pf.getEndereco().getCidade());
                    System.out.println("UF: " + pf.getEndereco().getEstado());
                    System.out.println("Complemento: " + pf.getEndereco().getComplemento());

                    System.out.println("Informe o novo nome (Ou aperte ENTER para manter): ");
                    String nome = scanner.nextLine();
                    if (!nome.matches("[A-Za-z\\s\\p{Punct}]+")) {
                        System.out.println("Nome invalido!");
                        return;
                    }
                    if (!nome.isEmpty()) {
                        pf.setNome(nome);
                    }
                    System.out.println("Informe o novo CPF com APENAS DIGITOS (Ou aperte ENTER para manter): ");
                    String cpf = scanner.nextLine();
                    if (!cpf.isEmpty() && !cpf.matches("\\d{11}")) {
                        System.out.println("CPF invalido! Deve conter apenas 8 digitos.");
                        return;
                    }
                    if (!cpf.isEmpty()) {
                        pf.setCpf(cpf);
                    }
                    System.out.println("Informe o novo telefone (Ou aperte ENTER para manter): ");
                    String telefone = scanner.nextLine();
                    if (!telefone.isEmpty()) {
                        pf.setTelefone(telefone);
                    }
                    System.out.println("Informe o novo CEP (Ou aperte ENTER para manter)(APENAS DIGITOS): ");
                    String cep = scanner.nextLine();
                    if (!cep.isEmpty() && !cep.matches("\\d{8}")) {
                        System.out.println("CEP invalido! Deve conter apenas 8 digitos.");
                        return;
                    }
                    if (!cep.isEmpty()) {
                        pf.getEndereco().setCep(cep);
                    }
                    System.out.println("Informe o novo logradouro (Ou aperte ENTER para manter): ");
                    String logradouro = scanner.nextLine();
                    if (!logradouro.isEmpty()) {
                        pf.getEndereco().setLogradouro(logradouro);
                    }
                    System.out.println("Informe o novo bairro (Ou aperte ENTER para manter): ");
                    String bairro = scanner.nextLine();
                    if (!bairro.isEmpty()) {
                        pf.getEndereco().setBairro(bairro);
                    }
                    System.out.println("Informe o novo número (Ou aperte ENTER para manter): ");
                    String numero = scanner.nextLine();
                    if (!numero.isEmpty()) {
                        pf.getEndereco().setNumero(numero);
                    }
                    System.out.println("Informe o novo cidade (Ou aperte ENTER para manter): ");
                    String cidade = scanner.nextLine();
                    if (!cidade.isEmpty()) {
                        pf.getEndereco().setCidade(cidade);
                    }
                    System.out.println("Informe a nova UF (Ou aperte ENTER para manter): ");
                    String uf = scanner.nextLine();
                    if (!uf.isEmpty() && !uf.matches("[a-zA-Z]{2}")) {
                        System.out.println("UF invalido! Deve conter apenas 2 letras.");
                        return;
                    }
                    if (!uf.isEmpty()) {
                        pf.getEndereco().setEstado(uf);
                    }
                    System.out.println("Informe o novo complemento (Ou aperte ENTER para manter): ");
                    String complemento = scanner.nextLine();
                    if (!complemento.isEmpty()) {
                        pf.getEndereco().setComplemento(complemento);
                    }
                    pfDAO.alterarPessoaFisica(pf);
                    System.out.println("Pessoa Física atualizada com sucesso!");

                } else {
                    System.out.println("Nenhuma pessoa física com o ID informado foi encontrada.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, informe um número.");
        } catch (Exception e) {
            System.out.println("Erro ao alterar pessoa: " + e.getMessage());
        }
    }

    private static void excluirPessoa(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        try {
            if (!tipo.equals("F")) {
                if (tipo.equals("J")) {
                    System.out.println("Informe o ID da Pessoa Jurídica que deseja excluir: ");
                    int id = Integer.parseInt(scanner.nextLine());

                    PessoaJuridica pessoa = pjDAO.getPessoaJuridica(id);
                    if (pessoa != null) {
                        System.out.println("Tem certeza que deseja excluir a Pessoa Jurídica com ID " + id + "? (S/N)");
                        String confirmacao = scanner.nextLine().toUpperCase();
                        if (confirmacao.equals("S")) {
                            pjDAO.excluirPessoaJuridica(id);
                            System.out.println("Pessoa Jurídica excluída com sucesso!");
                        } else {
                            System.out.println("Operação de exclusão cancelada.");
                        }
                    } else {
                        System.out.println("Nenhuma pessoa jurídica com o ID informado foi encontrada.");
                    }
                } else {
                    System.out.println("Tipo inválido.");
                }
            } else {
                System.out.println("Informe o ID da Pessoa Física que deseja excluir: ");
                int id = Integer.parseInt(scanner.nextLine());

                PessoaFisica pessoa = pfDAO.getPessoaFisica(id);
                if (pessoa != null) {
                    System.out.println("Tem certeza que deseja excluir a Pessoa Física com ID " + id + "? (S/N)");
                    String confirmacao = scanner.nextLine().toUpperCase();
                    if (confirmacao.equals("S")) {
                        pfDAO.excluirPessoaFisica(id);
                        System.out.println("Pessoa Física excluída com sucesso!");
                    } else {
                        System.out.println("Operação de exclusão cancelada.");
                    }
                } else {
                    System.out.println("Nenhuma pessoa física com o ID informado foi encontrada.");
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("ID inválido. Por favor, informe um número.");
        } catch (Exception e) {
            System.out.println("Erro ao excluir pessoa: " + e.getMessage());
        }
    }

    private static void buscarPorId(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws SQLException {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        if (!"F".equals(tipo)) {
            if ("J".equals(tipo)) {
                System.out.println("Digite o ID da pessoa que deseja buscar:");
                int id = scanner.nextInt();
                scanner.nextLine();
                PessoaJuridica pj = pjDAO.getPessoaJuridica(id);
                if (pj != null) {
                    System.out.println("Exibindo dados de Pessoa Jurídica...");
                    System.out.println("=========================");
                    System.out.println("Id: " + pj.getId());
                    System.out.println("Nome: " + pj.getNome());
                    System.out.println("CNPJ: " + pj.getCnpj());
                    System.out.println("Telefone: " + pj.getTelefone());
                    System.out.println("Logradouro: " + pj.getEndereco().getLogradouro());
                    System.out.println("Bairro: " + pj.getEndereco().getBairro());
                    System.out.println("Numero: " + pj.getEndereco().getNumero());
                    System.out.println("Cidade: " + pj.getEndereco().getCidade());
                    System.out.println("Estado: " + pj.getEndereco().getEstado());
                    System.out.println("Complemento: " + pj.getEndereco().getComplemento());
                    System.out.println("CEP: " + pj.getEndereco().getCep());
                } else {
                    System.out.println("Pessoa Jurídica com ID " + id + " não encontrada.");
                }
            } else {
                System.out.println("Tipo inválido. Por favor, digite 'F' para Pessoa Física ou 'J' para Pessoa Jurídica.");
            }
        } else {
            System.out.println("Digite o ID da pessoa que deseja buscar:");
            int id = scanner.nextInt();
            scanner.nextLine();
            PessoaFisica pf = pfDAO.getPessoaFisica(id);
            if (pf != null) {
                System.out.println("Exibindo dados de Pessoa Física...");
                System.out.println("=========================");
                System.out.println("Id: " + pf.getId());
                System.out.println("Nome: " + pf.getNome());
                System.out.println("CPF: " + pf.getCpf());
                System.out.println("Telefone: " + pf.getTelefone());
                System.out.println("Logradouro: " + pf.getEndereco().getLogradouro());
                System.out.println("Bairro: " + pf.getEndereco().getBairro());
                System.out.println("Numero: " + pf.getEndereco().getNumero());
                System.out.println("Cidade: " + pf.getEndereco().getCidade());
                System.out.println("Estado: " + pf.getEndereco().getEstado());
                System.out.println("Complemento: " + pf.getEndereco().getComplemento());
                System.out.println("CEP: " + pf.getEndereco().getCep());
            } else {
                System.out.println("Pessoa Física com ID " + id + " não encontrada.");
            }
        }
    }

    private static void exibirTodos(Scanner scanner, PessoaFisicaDAO pfDAO, PessoaJuridicaDAO pjDAO) throws SQLException {
        System.out.println("F - Pessoa Física | J - Pessoa Jurídica");
        String tipo = scanner.nextLine().toUpperCase();

        if (!"F".equals(tipo)) {
            if ("J".equals(tipo)) {
                List<PessoaJuridica> pessoasJuridicas = pjDAO.getPessoaJuridicas();
                if (pessoasJuridicas.isEmpty()) {
                    System.out.println("Nenhuma Pessoa Jurídica encontrada.");
                } else {
                    System.out.println("Exibindo todas as Pessoas Jurídicas...");
                    System.out.println("=========================");
                    for (PessoaJuridica pj : pessoasJuridicas) {
                        System.out.println("Id: " + pj.getId());
                        System.out.println("Nome: " + pj.getNome());
                        System.out.println("CNPJ: " + pj.getCnpj());
                        System.out.println("Telefone: " + pj.getTelefone());
                        System.out.println("CEP: " + pj.getEndereco().getCep());
                        System.out.println("Logradouro: " + pj.getEndereco().getLogradouro());
                        System.out.println("Bairro: " + pj.getEndereco().getBairro());
                        System.out.println("Numero: " + pj.getEndereco().getNumero());
                        System.out.println("Cidade: " + pj.getEndereco().getCidade());
                        System.out.println("Estado: " + pj.getEndereco().getEstado());
                        System.out.println("Complemento: " + pj.getEndereco().getComplemento());
                        System.out.println("-------------------------");
                    }
                }
            } else {
                System.out.println("Tipo inválido.");
            }
        } else {
            List<PessoaFisica> pessoasFisicas = pfDAO.getPessoasFisicas();
            if (pessoasFisicas.isEmpty()) {
                System.out.println("Nenhuma Pessoa Física encontrada.");
            } else {
                System.out.println("Exibindo todas as Pessoas Físicas...");
                System.out.println("=========================");
                for (PessoaFisica pf : pessoasFisicas) {
                    System.out.println("Id: " + pf.getId());
                    System.out.println("Nome: " + pf.getNome());
                    System.out.println("CPF: " + pf.getCpf());
                    System.out.println("Telefone: " + pf.getTelefone());
                    System.out.println("CEP: " + pf.getEndereco().getCep());
                    System.out.println("Logradouro: " + pf.getEndereco().getLogradouro());
                    System.out.println("Bairro: " + pf.getEndereco().getBairro());
                    System.out.println("Numero: " + pf.getEndereco().getNumero());
                    System.out.println("Cidade: " + pf.getEndereco().getCidade());
                    System.out.println("Estado: " + pf.getEndereco().getEstado());
                    System.out.println("Complemento: " + pf.getEndereco().getComplemento());
                    System.out.println("-------------------------");
                }
            }
        }
    }
}
