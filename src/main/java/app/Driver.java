package app;

import app.mappers.JPAUtil;

public class Driver {
    public static void main(String[] args) {
        Console console = new Console();
        console.run();
        JPAUtil.close();
    }
}
