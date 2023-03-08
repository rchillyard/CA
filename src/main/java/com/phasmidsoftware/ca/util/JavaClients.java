package com.phasmidsoftware.ca.util;

import com.google.common.collect.ImmutableList;
import com.phasmidsoftware.ca.modules.MethodsForJava;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class JavaClients {

    static public Supplier<Boolean> randomBooleanSupplier() {
        return MethodsForJava.randomSupplier(Boolean.class, 0L);
    }

    public static void main(String[] args) {
        Supplier<Boolean> supplier = JavaClients.randomBooleanSupplier();
        System.out.println(supplier.get());
        ImmutableList<Integer> list = ImmutableList.of(1, 2, 3);
        List<Integer> arrayList = new ArrayList<>(list);
        System.out.println(MethodsForJava.reverse(arrayList));
    }
}
