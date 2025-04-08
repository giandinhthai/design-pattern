package Mediator;

import java.util.UUID;

enum OrderEvent {
    ORDER_CREATED
}
class Order {
    private String name;
    private UUID id;
    Order(String name){
        this.name=name;
        this.id= UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
class OrderService {
    OrderMediator orderMediator;
    public OrderService(OrderMediator mediator) {
        this.orderMediator=mediator;
    }

    public void createOrder(Order order) {
        System.out.println("Creating order: " + order.getId());

        orderMediator.notify(OrderEvent.ORDER_CREATED, order);
    }

}
interface OrderMediator{
    void notify(OrderEvent event,Order order);
}
interface EmailService{
    public void sendOrderConfirmation(Order order);
}
class EmailServiceImpl implements EmailService{
    public void sendOrderConfirmation(Order order) {
        System.out.println("Sending confirmation email for order " + order.getId());
    }
}
interface InventoryService{
    public void reserveItems(Order order);
}
class InventoryServiceImpl implements  InventoryService{
    public void reserveItems(Order order) {
        System.out.println("Reserving items for order " + order.getId());
    }
}
interface LogService{
    public void log(String message);
}
class LogServiceImpl implements  LogService {
    public void log(String message) {
        System.out.println("[LOG] " + message);
    }
}
class OrderMediatorImpl implements OrderMediator{
    private InventoryService inventoryService;
    private EmailService emailService;
    private LogService logService;

    public OrderMediatorImpl(EmailService emailService, InventoryService inventoryService, LogService logService) {
        this.emailService=emailService;
        this.inventoryService=inventoryService;
        this.logService=logService;
    }

    @Override
    public void notify(OrderEvent event, Order order) {
        switch (event) {
            case OrderEvent.ORDER_CREATED:
                emailService.sendOrderConfirmation(order);
                inventoryService.reserveItems(order);
                logService.log("Order with id" + order.getId() + "has created");
                break;
        }
    }
}
public class Main {
    public static void main(String[] args) {
        EmailService emailService = new EmailServiceImpl();
        InventoryService inventoryService = new InventoryServiceImpl();
        LogService logService = new LogServiceImpl();

        OrderMediator mediator = new OrderMediatorImpl(emailService, inventoryService, logService);
        OrderService orderService = new OrderService(mediator);

        Order order = new Order("ORDER123");
        orderService.createOrder(order);

    }
}