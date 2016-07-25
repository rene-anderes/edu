package org.anderes.edu.oop;

public class Starter {

    public static void main(String[] args) {
        Kuh elsa = new Kuh();
        elsa.setName("Elsa");
        elsa.setFarbe("braun");
        int milch = elsa.getMilch();
        
        Kuh roesli = new Kuh();
        roesli.setName("Rösli");
        roesli.setFarbe("weiss");

        System.out.println("Objekt elsa: " + elsa);
        System.out.println("Objekt roesli: " + roesli);
    }

}
