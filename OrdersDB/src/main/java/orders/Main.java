package orders;

import javax.persistence.*;

public class Main {
    static EntityManagerFactory emf;
    static EntityManager em;

    public static void main(String[] args) {
        try {
            emf = Persistence.createEntityManagerFactory("Orders");
            em = emf.createEntityManager();
            try {
                em.getTransaction().begin();
//Create client1
                Products products1 = new Products("Candle", 25);
                Products products2 = new Products("Phone", 3);
                em.persist(products1);
                em.persist(products2);

                Orders orders1 = new Orders(Status.ACTIVE);
                Orders orders2 = new Orders(Status.CANCELLED);
                em.persist(orders1);
                em.persist(orders2);

                Clients client1 = new Clients("sad", 11111111111l);
                em.persist(client1);

                orders1.addProduct(products1);
                orders1.addProduct(products2);
                orders2.addProduct(products2);

                client1.addOrder(orders1);
                client1.addOrder(orders2);
                em.getTransaction().commit();
//Create client2
                em.getTransaction().begin();
                Clients client2 = new Clients("mad", 22222222222l);
                em.persist(client2);
                Orders orders3 = new Orders();
                orders3.addProduct(products1);
                client2.addOrder(orders3);
                em.getTransaction().commit();

                //clear context
                em.clear();

                em.getTransaction().begin();
                TypedQuery<Object> query = em.createQuery("SELECT o.productsSet FROM Orders o " +
                        "WHERE o.client.id = 1", Object.class);
                var res = query.getResultList();
                System.out.println("------------------Products from Orders whose Client has id = 1----------------");
                System.out.println(res);
                System.out.println();

                TypedQuery<Object> query2 = em.createQuery("SELECT o.ordersList FROM Products o " +
                        "WHERE o.id = 2", Object.class);
                var res2 = query2.getResultList();
                System.out.println("------------------Orders from Products which id = 2----------------");
                System.out.println(res2);
                System.out.println();

                em.getTransaction().commit();

            } catch (Exception e) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
            emf.close();
        }

    }
}