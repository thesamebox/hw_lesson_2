package lesson_2.product;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> products = new ArrayList<>();
    private int idCounter = 1;

    public Product findById(int id) {
        return products.stream().filter(it -> it.getId() == id).findFirst().orElse(null);
    }

    public List<Product> showAll() {
        return products;
    }
    @PostConstruct
    public void init() {

        products.add(new Product(idCounter++, "Булочки из маны", 30.5));
        products.add(new Product(idCounter++, "Кишка великана Николая", 110));
        products.add(new Product(idCounter++, "Крендель судьбы", 59));
        products.add(new Product(idCounter++, "Загадочный пирог", 87.87));
        products.add(new Product(idCounter++, "Стиранная пастила", 87.87));
    }
}
