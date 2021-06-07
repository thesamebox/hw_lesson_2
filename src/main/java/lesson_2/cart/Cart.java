package lesson_2.cart;

import lesson_2.product.Product;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class Cart {

    private final List<Product> products= new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public void removeProducts(int id) {
        products.remove(products.stream().filter(it -> it.getId() == id).findFirst().orElse(null));
    }

    public void viewCart() {
        for (Product product : products) {
            System.out.println(product.getId() + " " + product.getName() + " " + product.getCost());
        }
        System.out.println("----------------");
        System.out.println("Всего товаров на сумму " + getTotalAmount() + " денег");
    }

    public int getListLength() {
        return products.size();
    }

    public double getTotalAmount() {
        double total = 0;
        for (Product product : products) {
            total = total + product.getCost();
        }
        return total;
    }
}
