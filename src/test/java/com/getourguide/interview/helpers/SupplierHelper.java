package com.getourguide.interview.helpers;

import com.getourguide.interview.entity.Supplier;

public class SupplierHelper {
    public static Supplier createSupplier(Long id, String name) {
        var supplier = new Supplier();
        supplier.setId(id);
        supplier.setName(name);
        return supplier;
    }
}
