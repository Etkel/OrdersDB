package orders;

import javax.persistence.*;
import java.util.Scanner;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try {
            emf = Persistence.createEntityManagerFactory("Orders");
            em = emf.createEntityManager();
            try {

                em.getTransaction().begin();
                //Create client1
                Products products1=new Products("Candle",25);
                Products products2=new Products("Phone",3);
                em.persist(products1);
                em.persist(products2);

                Orders orders1=new Orders(Status.ACTIVE);
                Orders orders2=new Orders(Status.CANCELLED);
                em.persist(orders1);
                em.persist(orders2);

                Clients client1=new Clients("sad",11111111111l);
                em.persist(client1);

                orders1.addProduct(products1);
                orders1.addProduct(products2);
                orders2.addProduct(products2);

                client1.addOrder(orders1);
                client1.addOrder(orders2);
                em.getTransaction().commit();
                //Create client2
                em.getTransaction().begin();
                Clients client2=new Clients("mad",22222222222l);
                em.persist(client2);
                Orders orders3=new Orders();
                orders3.addProduct(products1);
                client2.addOrder(orders3);
                em.getTransaction().commit();

                //clear context
                em.clear();

                em.getTransaction().begin();
                TypedQuery<Object> query=em.createQuery("SELECT o.productsSet FROM Orders o "+
                        "WHERE o.client.id = 1",Object.class);
                var res=query.getResultList();
                System.out.println("------------------Products from Orders whose Client has id = 1----------------");
                System.out.println(res);
                System.out.println();

                TypedQuery<Object> query2=em.createQuery("SELECT o.ordersList FROM Products o "+
                        "WHERE o.id = 2",Object.class);
                var res2=query2.getResultList();
                System.out.println("------------------Orders from Products which id = 2----------------");
                System.out.println(res2);
                System.out.println();

                em.getTransaction().commit();

                while (true) {
                    System.out.println("1: add client");
                    System.out.println("2: add order");
                    System.out.println("3: add product");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1" -> addClient(sc);
                        case "2" -> addOrder(sc);
                        case "3" -> addProduct(sc);
                        default -> {
                            return;
                        }
                    }
                }
            } finally {
                sc.close();
                em.close();
                emf.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private static void addClient(Scanner sc) {
        System.out.print("Enter client name: ");
        String name = sc.nextLine();
        System.out.print("Enter client phone: ");
        String sPhone = sc.nextLine();
        Long phone = Long.parseLong(sPhone);

        em.getTransaction().begin();
        try {
            Clients client = new Clients(name, phone);
            em.persist(client);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void addOrder(Scanner sc) {
        System.out.print("Enter order status: ");
        System.out.print("1. Done ");
        System.out.print("2. Active ");
        System.out.print("3. Cancelled ");
        String sStatus = sc.nextLine();
        Status status;
        if (sStatus.equals("1")) {
            status = Status.DONE;
        } else if (sStatus.equals("2")) {
            status = Status.ACTIVE;
        } else status = Status.CANCELLED;

        em.getTransaction().begin();
        try {
            Orders orders = new Orders(status);
            em.persist(orders);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

    private static void addProduct(Scanner sc) {
        System.out.print("Enter product name: ");
        String name = sc.nextLine();
        System.out.print("Enter product quantity: ");
        String sQuantity = sc.nextLine();
        Integer quantity = Integer.parseInt(sQuantity);

        em.getTransaction().begin();
        try {
            Products products = new Products(name, quantity);
            em.persist(products);
            em.getTransaction().commit();
        } catch (Exception ex) {
            em.getTransaction().rollback();
        }
    }

}














