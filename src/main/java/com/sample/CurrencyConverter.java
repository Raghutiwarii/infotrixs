package com.sample;
import java.net.URI;
import java.net.http.*;
import java.util.*;

public class CurrencyConverter {
    static List<String> favCurrList = new ArrayList<>();

    static void addFavCurr(Scanner scanner) {
        System.out.print("Enter the Favorite Currency to add (e.g., USD): ");
        String favoriteCurrency = scanner.nextLine();
        if (!favCurrList.contains(favoriteCurrency)) {
        favCurrList.add(favoriteCurrency);
        System.out.println("Favorite currency added.");
        }else {
        	System.out.println("The currency is already present in the Favorite Currencies");
        }
    }

    static void showFavList(Scanner scanner) {
        if(favCurrList.size()==0) {
        	System.out.println("You don't have any Favorite Currencies. Enter 3 to Manage Favorite Currencies");
        }
        else {
        	 System.out.println("Favorite Currencies:");
        for (String favoriteCurrency : favCurrList) {
            System.out.println(favoriteCurrency);
        }
        }
    }
    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            while (true) {
            	System.out.println("");
                System.out.println("Main Menu:");
                System.out.println("1. Convert Currency");
                System.out.println("2. Manage Favorite Currenciess");
                System.out.println("3. Show Favorite Currencies");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        currConverter(scanner);
                        break;
                    case 2:
                        manageFavCurr(scanner);
                        break;
                    case 3:
                    	showFavList(scanner);
                        break;
                    case 4:
                    	System.out.println("Thanks for using Currency Converter.");
                        System.out.println("Exiting the application.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static String convertCurr(String fromCurrency, String toCurrency, double amount, String apiKey) throws Exception {
        String host = "currency-conversion-and-exchange-rates.p.rapidapi.com";
        String endpoint = "convert";
        String url = String.format("https://%s/%s?from=%s&to=%s&amount=%.2f", host, endpoint, fromCurrency, toCurrency, amount);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("X-RapidAPI-Key", apiKey)
                .header("X-RapidAPI-Host", host)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new Exception("Currency conversion request failed with status code: " + response.statusCode());
        }
    }

    static <JSONObject> void currConverter(Scanner scanner) {
        try {
            // Supported currencies ANG,TND,XCD,CAD,MVR,HRK,AUD,MWK,XAG,MAD,PHP,NAD,GNF,KES,MZN,BTN,MGA,AZN,XAU,RON,INR etc
            System.out.print("Enter the source currency (e.g., USD): ");
            String fromCurrency = scanner.nextLine();

            System.out.print("Enter the target currency (e.g., EUR): ");
            String toCurrency = scanner.nextLine();

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            String apiKey = "1bbaf9a444mshb042728e4acfc7ep183a83jsnc23135c606d7"; // API key
            String result = convertCurr(fromCurrency, toCurrency, amount, apiKey);
            
            System.out.println(result.substring(result.indexOf("result")+8,result.length()-1));      
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void manageFavCurr(Scanner scanner) {
        while (true) {
        	System.out.println("");
            System.out.println("Favorite Currency Menu:");
            System.out.println("1. Add Favorite Currency");
            System.out.println("2. Update Favorite Currency");
            System.out.println("3. Delete Favorite Currency");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addFavCurr(scanner);
                    break;
                case 2:
                    updateFavCurr(scanner);
                    break;
                case 3:
                	deleteFavCurr(scanner);
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    static void deleteFavCurr(Scanner scanner) {
	 System.out.print("Enter the Currency which you want to delete from Favorite Currencies (e.g., USD): ");
	 String deleteCurrency = scanner.nextLine();
	 if (favCurrList.contains(deleteCurrency)) {
	        favCurrList.remove(deleteCurrency);
	        System.out.println(deleteCurrency+" deleted from the Favorite Currencies");
	        }else {
	        	System.out.println("The currency is not present in the Favorite Currencies");
	        }
    }

    static void updateFavCurr(Scanner scanner) {
        System.out.print("Enter the currency code you want to update: ");
        String currencyCodeToUpdate = scanner.nextLine();

        boolean isCurrPresent = false;
        for (int i = 0; i < favCurrList.size(); i++) {
            String currency = favCurrList.get(i);
            if (currency.startsWith(currencyCodeToUpdate)) {
                System.out.print("Enter the new Favorite Currency (e.g., USD): ");
                String newCurr = scanner.nextLine();
                favCurrList.set(i, newCurr);
                isCurrPresent = true;
                System.out.println("Favorite currency List updated.");
                break;
            }
        }

        if (!isCurrPresent) {
            System.out.println("Currency code is not present in favorites.");
        }
    }
}