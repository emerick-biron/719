package fr.ebiron.septunneuf.shop.dto;

public class GenerateEggsRequest {
    private int quantity;

    public GenerateEggsRequest(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
