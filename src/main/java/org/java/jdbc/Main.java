package org.java.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    static final String url = "jdbc:mysql://localhost:3306/db_nations";
    static final String user = "root";
    static final String password = "";

    public static void main(String[] args) {
    	
    	Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connessione stabilita correttamente");

            
            displayNationsData(conn);

             
            
            String paramLocation = null;
            
            while (paramLocation == null) {
                System.out.print("Vuoi filtrare questa lista? (si / no)");
                String searchQuery = scanner.nextLine();
                
                if (searchQuery.toLowerCase().equals("si")) {
                    System.out.println("Puoi filtrare per: ");
                    System.out.println("Seleziona il numero");
                    System.out.println("1) Nazione");
                    System.out.println("2) Regione");
                    System.out.println("3) Continente");
                    int searchFilter = Integer.parseInt(scanner.nextLine());
                    
                    
                    String paramInput = null;
                    switch (searchFilter) {
                        case 1:
                            paramLocation = "nazione";
                            System.out.println("Seleziona la " + paramLocation + ": ");
                            paramInput = scanner.nextLine();
                            setFilterLocation(conn, paramLocation, paramInput);
                            break;
                        case 2:
                            paramLocation = "regione";
                            System.out.println("Seleziona la " + paramLocation + ": ");
                            paramInput = scanner.nextLine();
                            setFilterLocation(conn, paramLocation, paramInput);
                            break;
                        case 3:
                            paramLocation = "continente";
                            System.out.println("Seleziona la " + paramLocation + ": ");
                            paramInput = scanner.nextLine();
                            setFilterLocation(conn, paramLocation, paramInput);
                            break;
                        default:
                            System.out.println("Selezione non valida");
                            break;
                    }
                } else if (searchQuery.toLowerCase().equals("no")) {
                    displayNationsData(conn);
                    
                    
                } else {
                    System.out.println("Valore non valido");
                }
            }

          
            
            
            
            int countryId = scanner.nextInt();
            scanner.nextLine();
            displayLanguagesAndStats(conn, countryId);

        } catch (Exception e) {
            System.out.println("Errore di connessione: " + e.getMessage());
        }

        System.out.println("\n----------------------------------\n");
        System.out.println("The end");
    }

    private static void displayNationsData(Connection conn) {
        final String sql = "SELECT c.country_id, c.name AS country_name, r.name AS region_name, c2.name AS continent_name " +
                "FROM countries c " +
                "JOIN regions r ON c.region_id = r.region_id " +
                "JOIN continents c2 ON r.continent_id = c2.continent_id " +
                "ORDER BY c.name ASC";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            System.out.println("\nLista di tutte le nazioni:");
            while (rs.next()) {
                int id = rs.getInt("country_id");
                String countryName = rs.getString("country_name");
                String regionName = rs.getString("region_name");
                String continentName = rs.getString("continent_name");

                System.out.println("ID: " + id);
                System.out.println("Nome Nazione: " + countryName);
                System.out.println("Nome Regione: " + regionName);
                System.out.println("Nome Continente: " + continentName);
                System.out.println("\n------------------------------\n");
            }
        } catch (Exception e) {
            System.out.println("Errore di lettura:" + e.getMessage());
        }
    }

    
    private static void setFilterLocation(Connection conn, String inputSearch, String input) {
    	
    	
    	String columnName = null;
    	
    	if(inputSearch.toLowerCase().equals("nazione")) {
    		columnName = "c.name";
    	} else if(inputSearch.toLowerCase().equals("regione")) {
    		columnName = "r.name";
    	}else if(inputSearch.toLowerCase().equals("continente")) {
    		columnName = "c2.name";
    	}
    	
    	
    	
    	
    	String sql = "SELECT c.country_id, c.name AS country_name, r.name AS region_name, c2.name AS continent_name " +
                "FROM countries c " +
                "JOIN regions r ON c.region_id = r.region_id " +
                "JOIN continents c2 ON r.continent_id = c2.continent_id " +
                "WHERE " + columnName + " LIKE ? " +
                "ORDER BY c.name ASC";
        

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + input + "%");
            ResultSet rs = ps.executeQuery();

            System.out.println("\nRisultati della ricerca per '" + inputSearch + "':");
            while (rs.next()) {
                int id = rs.getInt("country_id");
                String countryName = rs.getString("country_name");
                String regionName = rs.getString("region_name");
                String continentName = rs.getString("continent_name");

                System.out.println("ID: " + id);
                System.out.println("Nome Nazione: " + countryName);
                System.out.println("Nome Regione: " + regionName);
                System.out.println("Nome Continente: " + continentName);
                System.out.println("\n------------------------------\n");
            }
        } catch (Exception e) {
            System.out.println("Errore di lettura:" + e.getMessage());
        }
    }

    private static void displayLanguagesAndStats(Connection conn, int countryId) {
    	
        
    }
}
