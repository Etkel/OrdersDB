package orders;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productCode;
    private String productName;
    private Integer quantity;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "order_products",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Orders> ordersList = new ArrayList<>();

    public Products() {
    }

    public Products(String productName, Integer quantity) {
        this.productName = productName;
        this.quantity = quantity;
    }

    public void addOrder(Orders order) {
        ordersList.add(order);
        order.getProductsSet().add(this);
    }
    public Long getProductCode() {
        return productCode;
    }

    public void setProductCode(Long productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Products products = (Products) o;

        if (!Objects.equals(productCode, products.productCode))
            return false;
        if (!Objects.equals(productName, products.productName))
            return false;
        return Objects.equals(quantity, products.quantity);
    }

    @Override
    public int hashCode() {
        int result = productCode != null ? productCode.hashCode() : 0;
        result = 31 * result + (productName != null ? productName.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Products{" +
                "productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

