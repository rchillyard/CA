package com.phasmidsoftware.ca.util;

import com.phasmidsoftware.ca.modules.MethodsForJava;

import java.util.function.Supplier;

public class JavaClients {

    static public Supplier<Boolean> randomBooleanSupplier() {
        return MethodsForJava.randomSupplier(Boolean.class, 0L);
    }

    public static void main(String[] args) {
        Supplier<Boolean> supplier = JavaClients.randomBooleanSupplier();
        System.out.println(supplier.get());
    }
}
