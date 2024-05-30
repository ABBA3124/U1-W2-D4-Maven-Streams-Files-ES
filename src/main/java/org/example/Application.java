package org.example;

import org.example.entities.Customer;
import org.example.entities.Order;
import org.example.entities.Product;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
//        System.out.println("ciao funziono!");
//
//        Faker faker = new Faker();
//        System.out.println(faker.job().title());

        // Clienti
        List<Customer> clienti = Arrays.asList(
                new Customer("Mario Rossi", 1),
                new Customer("Luigi Bianchi", 2),
                new Customer("Alessio Vulpinari", 3)
        );

        // Prodotti
        List<Product> prodotti = Arrays.asList(
                new Product("Laptop", "Electronics", 1200.00),
                new Product("s 22 ultra 5g", "Electronics", 800.00),
                new Product("iphone 15 pro max", "Electronics", 129.99),
                new Product("Lenovo legion go", "Electronics", 799.99)
        );

        // Ordini
        List<Order> orders = Arrays.asList(
                new Order("Spedito", LocalDate.now().minusDays(7), LocalDate.now().plusDays(6), Arrays.asList(prodotti.get(0), prodotti.get(1)), clienti.get(1)),
                new Order("In Elaborazione", LocalDate.now().minusDays(3), LocalDate.now().plusDays(8), Arrays.asList(prodotti.get(2), prodotti.get(3)), clienti.get(0)),
                new Order("Consegnato", LocalDate.now().minusDays(10), LocalDate.now().minusDays(2), Arrays.asList(prodotti.get(1), prodotti.get(2)), clienti.get(2)),
                new Order("Spedito", LocalDate.now().minusDays(5), LocalDate.now().plusDays(3), Arrays.asList(prodotti.get(0), prodotti.get(3)), clienti.get(1))
        );


        // --------------------------------------------esercizio 1 --------------------------------------------------
        System.out.println("--------------------------------------------esercizio 1 --------------------------------------------------");
        //ordini per cliente
        Map<Customer, List<Order>> ordiniPerCliente = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));


//        ordiniPerCliente.forEach((cliente, ordini) -> System.out.println("cliente: " + cliente.getName() + ordini));
        ordiniPerCliente.forEach((cliente, ordini) -> {
            System.out.println("\t\nCliente: " + cliente.getName());
            ordini.forEach(order -> System.out.println("-- ID ordine: " + order.getId() + " - " + "Stato: " + order.getStatus() + " - " + "Ordinato il: " + order.getOrderDate() + " - " + "Data di consegna: " + order.getDeliveryDate() + " \n\t- " + "Prodotti ordinato/i: " + order.getProducts()));
        });

        // --------------------------------------------esercizio 2 --------------------------------------------------
        System.out.println("--------------------------------------------esercizio 2 --------------------------------------------------");

        //totale delle vendite per ogni cliente
        Map<Customer, Double> totaleVenditePerCliente = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer, Collectors.summingDouble(order ->
                        order.getProducts().stream().mapToDouble(Product::getPrice).sum()
                )));


        totaleVenditePerCliente.forEach((cliente, totaleVendite) -> {
            System.out.println("Cliente: " + cliente.getName() + " - Totale vendite: " + totaleVendite + "€");
        });

        // --------------------------------------------esercizio 3 --------------------------------------------------
        System.out.println("--------------------------------------------esercizio 3 --------------------------------------------------");

        //prodotto più costoso
        Optional<Product> prodottoPiuCostoso = prodotti.stream()
                .max((prezzo1, prezzo2) -> prezzo1.getPrice().compareTo(prezzo2.getPrice()));

        prodottoPiuCostoso.ifPresent(prodotto -> {
            System.out.println("Prodotto più costoso: " + prodotto.getName() + " - Prezzo: " + prodotto.getPrice() + "€");
        });

        // --------------------------------------------esercizio 4 --------------------------------------------------
        System.out.println("--------------------------------------------esercizio 4 --------------------------------------------------");

        //media degli importi degli ordini
        DoubleSummaryStatistics statistics = orders.stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.summarizingDouble(Product::getPrice));

        double mediaImportiOrdini = statistics.getAverage();


        System.out.println("Media degli importi degli ordini: " + mediaImportiOrdini + "€");
    }
}
