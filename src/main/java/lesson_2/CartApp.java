package lesson_2;

import lesson_2.cart.Cart;
import lesson_2.cart.CartFactory;
import lesson_2.configuration.AppConfig;
import lesson_2.product.Product;
import lesson_2.product.ProductRepository;
import org.springframework.aop.scope.ScopedProxyUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class CartApp {
    private final ProductRepository productRepository;
    private final CartFactory cartFactory;

    public CartApp(ProductRepository productRepository, CartFactory cartFactory) {
        this.productRepository = productRepository;
        this.cartFactory = cartFactory;
    }
    @PostConstruct
    public void start() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        cartFactory.create();
        final List<Product> products = productRepository.showAll();
        products.forEach(it -> System.out.println(it.getId() + " " + it.getName() + " " + it.getCost()));
        final Cart cart = cartFactory.create();
        System.out.println("    В корзине " + cart.getListLength() + " товаров на сумму " + cart.getTotalAmount() + " денег.");
        System.out.println("    Напишите .все для выхода");
        System.out.print("Выберите товар по его номеру: ");
        String message = reader.readLine();
        try {
            while (!(message.equals(".все"))) {
                if (checkForNumber(message)) {
                    if (Integer.parseInt(message) > 0 && Integer.parseInt(message) <= 5) {
                        clearScreen();
                        System.out.println(productRepository.findById(Integer.parseInt(message)).getId()
                                + " " + productRepository.findById(Integer.parseInt(message)).getName()
                                + " " + productRepository.findById(Integer.parseInt(message)).getCost());
                        System.out.println("1. Положить в карзину");
                        System.out.println("2. Убрать из карзины");
                        System.out.println("777. Показать всю карзину");
                        System.out.print("Выберите действие по номеру: ");
                        String choose = reader.readLine();
                        if (checkForNumber(message)) {
                            if (Integer.parseInt(choose) == 1) {
                                System.out.print("Укажите количество: ");
                                int amount = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < amount; i++) {
                                    cart.addProduct(new Product(
                                            productRepository.findById(Integer.parseInt(message)).getId(),
                                            productRepository.findById(Integer.parseInt(message)).getName(),
                                            productRepository.findById(Integer.parseInt(message)).getCost())
                                    );
                                }
                                clearScreen();
                                System.out.println("В корзину было успешно добавлено " + amount + "шт " + productRepository.findById(Integer.parseInt(message)).getName());
                            }
                            if (Integer.parseInt(choose) == 2) {
                                System.out.print("Укажите количество: ");
                                int amount = Integer.parseInt(reader.readLine());
                                for (int i = 0; i < amount; i++) {
                                    cart.removeProducts(Integer.parseInt(message));
                                }
                                clearScreen();
                                System.out.println("Из корзины было успешно удалены " + amount + "шт " + productRepository.findById(Integer.parseInt(message)).getName());
                            }
                            if (Integer.parseInt(choose) == 3) {
                                clearScreen();
                                cart.viewCart();
                            }
                        } else {
                            System.out.println("Команда неправильного формата");
                        }
                    } else {
                        System.out.println("Такого товара не найдено");
                    }
                    if (Integer.parseInt(message) == 777) {
                        clearScreen();
                        cart.viewCart();
                    }
                } else {
                    System.out.println("Команда неправильного формата");
                }

                System.out.println("--------------");
                products.forEach(it -> System.out.println(it.getId() + " " + it.getName() + " " + it.getCost()));
                System.out.println("    В корзине " + cart.getListLength() + " товаров на сумму " + cart.getTotalAmount() + " денег.");
                System.out.println("    Напишите .все для выхода");
                System.out.println("    Напишите 777 для просмотра всей корзины");
                System.out.print("Выберите товар по его номеру: ");

                message = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }

    }

    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(AppConfig.class);
    }

    public boolean checkForNumber(String message) {
        try {
            Integer.parseInt(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println("");
      }
    }


}
