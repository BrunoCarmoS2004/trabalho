package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	 private  static String URL = "jdbc:mysql://localhost:3306/banco";
	    private  static String USER = "root";
	    private  static String PASSWORD = "";
	public static void main( String[] args )
    {
   


        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Scanner scanner = new Scanner(System.in)) {


            System.out.println("Clientes disponíveis:");
            listarClientes(conn);


            System.out.print("\nEscolha o ID do cliente que deseja alterar: ");
            int idCliente = scanner.nextInt();


            System.out.println("\nCarregando dados do cliente...");
            PreparedStatement selectStmt = conn.prepareStatement("SELECT * FROM cliente WHERE idcliente = ?");
            selectStmt.setInt(1, idCliente);
            ResultSet rs = selectStmt.executeQuery();


            if (rs.next()) {
                String nomeAtual = rs.getString("nome");
                String emailAtual = rs.getString("email");
                double limiteAtual = rs.getDouble("limite");


                System.out.printf("Nome: %s, Email: %s, Limite: %.2f%n", nomeAtual, emailAtual, limiteAtual);


                System.out.println("\nIniciando transação...");
                conn.setAutoCommit(false);


                System.out.print("\nNovo limite para o cliente: ");
                double novoLimite = scanner.nextDouble();


                // Revalidar os dados do cliente
                PreparedStatement recheckStmt = conn.prepareStatement("SELECT * FROM cliente WHERE idcliente = ?");
                recheckStmt.setInt(1, idCliente);
                ResultSet recheckRs = recheckStmt.executeQuery();


                if (recheckRs.next() && recheckRs.getDouble("limite") == limiteAtual) {
                    PreparedStatement updateStmt = conn.prepareStatement("UPDATE cliente SET limite = ? WHERE idcliente = ?");
                    updateStmt.setDouble(1, novoLimite);
                    updateStmt.setInt(2, idCliente);


                    try {
                        int rowsAffected = updateStmt.executeUpdate();
                        if (rowsAffected > 0) {
                            System.out.println("\nAtualização feita com sucesso. Confirma (s/n)?");
                            String confirmacao = scanner.next();


                            if (confirmacao.equalsIgnoreCase("s")) {
                                conn.commit();
                                System.out.println("Transação confirmada.");
                            } else {
                                conn.rollback();
                                System.out.println("Transação cancelada.");
                            }
                        }
                    } catch (SQLException e) {
                        conn.rollback();
                        System.err.println("Erro na atualização: " + e.getMessage());
                    }
                } else {
                    System.out.println("Os dados do cliente foram alterados por outra transação.");
                    conn.rollback();
                }


            } else {
                System.out.println("Cliente não encontrado.");
            }


        } catch (SQLException e) {
            System.err.println("Erro de conexão: " + e.getMessage());
        }
   


   
    }
    private static void listarClientes(Connection conn) throws SQLException {
        String sql = "SELECT idcliente, nome, limite FROM cliente";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.printf("ID: %d, Nome: %s, Limite: %.2f%n",
                                  rs.getInt("idcliente"),
                                  rs.getString("nome"),
                                  rs.getDouble("limite"));
            }
        }
    }

}