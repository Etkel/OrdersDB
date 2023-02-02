package orders;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Clients {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long clientNumber;
    private String name;
    private Long phone;
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Orders> listOfOrders = new ArrayList<>();

    public Clients() {
    }

    public Clients(String name, Long phone) {
        this.name = name;
        this.phone = phone;
    }

    public void addOrder(Orders orders) {
        listOfOrders.add(orders);
        orders.setClient(this);
    }
    public Long getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(Long clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public List<Orders> getListOfOrders() {
        return listOfOrders;
    }

    public void setListOfOrders(List<Orders> listOfOrders) {
        this.listOfOrders = listOfOrders;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "clientNumber=" + clientNumber +
                ", name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
