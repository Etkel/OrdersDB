package orders;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Orders {
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderNumber;

    private String status;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Clients client;
    @ManyToMany(mappedBy = "ordersList", cascade = CascadeType.ALL)
    private Set<Products> productsSet = new HashSet<>();

    public Orders(Status status) {
        this.status = status.getV();
    }

    public Orders() {
    }

    public void addClient(Clients client) {
        this.setClient(client);
        client.getListOfOrders().add(this);
    }

    public void addProduct(Products product) {
        productsSet.add(product);
        product.getOrdersList().add(this);
    }

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Clients getClient() {
        return client;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    public Set<Products> getProductsSet() {
        return productsSet;
    }

    public void setProductsSet(Set<Products> productsSet) {
        this.productsSet = productsSet;
    }

    @Override
    public String toString() {
        return "Orders{" +
                "orderNumber=" + orderNumber +
                ", status=" + status +
                '}';
    }
}
