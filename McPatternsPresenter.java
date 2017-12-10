import java.util.*;

class McPatternsPresenter {
    //This is the class that will handle the model <-> UI communication.
    MenuModel model;
    McPatternsGUI view;
    private static final int FIRST_ORDER_NUMBER = 101;
    private static final double SALES_TAX = 8.75;
    private String[] menuFileNames;
    private ArrayList<OrderItem> currentOrder = new ArrayList<OrderItem>();
    private int orderNumber;
    private float orderTotal = 0;
    
    private class OrderItem {
        String name;
        Integer count;

        private OrderItem(String name, Integer count) {
            this.name = name;
            this.count = count;
        }
    }
    
    private McPatternsPresenter() {}
    
    McPatternsPresenter(String[] args) {
        menuFileNames = args;
        orderNumber = FIRST_ORDER_NUMBER;
    }
    
    

    void loadMenuItems() {
        // TODO: Add code to read a file and load the menu items.
    	try {
            model = new MenuModel(menuFileNames);
            menuFileNames = null;
        }
        catch (MenuModel.MenuModelException e) {
            view.error(e.getMessage());
        }
    } 

    void attachView(McPatternsGUI view) {
        this.view = view;
    }
    // Add functions to return the menu items. 
    
    Set<String> getItems() {
        return model.getItemsList();
    }
    
    void addToOrder(String itemName) {
        for (OrderItem oi : currentOrder)
            if (oi.name.equals(itemName)) {
                oi.count++;
                orderTotal += model.getPrice(oi.name);
                printOrder();
                return;
            }
      
        currentOrder.add(new OrderItem(itemName, 1));
        orderTotal += model.getPrice(itemName);
      
        printOrder();
    }
    
    void submitOrder(CreditCard card) {
        card.charge(orderTotal*0.01);

        StringBuilder itemsList = new StringBuilder(currentOrder.size());
        for (OrderItem oi : currentOrder)
            itemsList.append(String.format("%4dx %-20s\n", oi.count, oi.name));
        String itemsString = itemsList.toString();
      
        System.out.println("----------------------");
        System.out.println("   ORDER NUMBER " + orderNumber);
        System.out.println("----------------------");
        System.out.print(itemsString);
        System.out.println("----------------------");

        System.out.println("\n\n----------------------");
        System.out.println(" WRITE IN PROGRAM LOG ");
        System.out.println("----------------------");
        System.out.println(" Order number: " + orderNumber);
        System.out.println(" CC: " + card.getNumber());
        System.out.println(" Date: " + (new Date()).toString());
        System.out.print(itemsString);
        System.out.println("----------------------");

        orderNumber++;
        cancelOrder();
    }
    
    void cancelOrder() {
        currentOrder.clear();
        orderTotal = 0;
    }
    
    int getOrderItemCount() {
        return currentOrder.size();
    }
    
    void printOrder() {
        StringBuilder output = new StringBuilder();
        output.append(String.format("\n    ORDER NUMBER: %4d           \n\n", orderNumber));

        for (OrderItem oi : currentOrder) {
            float price = model.getPrice(oi.name) * oi.count;
            output.append(String.format("%4dx %-20s%8.2f\n", oi.count, oi.name, price));
        }

        output.append("\n\n-----------------------------------\n");
        output.append(String.format(" Subtotal: \t\t%8.2f\n", orderTotal));
        output.append(String.format("       Tax: \t\t%8.2f\n", orderTotal*0.01*SALES_TAX));
        output.append(String.format("     Total: \t\t%8.2f\n", orderTotal*(1 + 0.01*SALES_TAX)));

        view.printOrder(output.toString());
    }
}