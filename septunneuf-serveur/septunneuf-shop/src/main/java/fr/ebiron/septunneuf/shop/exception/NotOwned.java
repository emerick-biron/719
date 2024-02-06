package fr.ebiron.septunneuf.shop.exception;

public class NotOwned extends Exception{
    public NotOwned(String message) {
        super(message);
    }
}
