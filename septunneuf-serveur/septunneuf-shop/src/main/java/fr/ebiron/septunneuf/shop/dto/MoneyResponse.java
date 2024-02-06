package fr.ebiron.septunneuf.shop.dto;

public class MoneyResponse {
    private long money;

    public MoneyResponse(long money) {
        this.money = money;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
