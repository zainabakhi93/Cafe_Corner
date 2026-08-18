package Backend;

public class CartItem {

    MenuItem item;
    int quantity;

    public CartItem(MenuItem item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    public MenuItem getItem(){
        return item;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public double getSubtotal(){
        return item.getPrice() * quantity;
    }

}