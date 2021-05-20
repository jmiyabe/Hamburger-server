package com.hamburger.server.repository;

import com.hamburger.server.model.PurchaseOrder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {
    List<PurchaseOrder> purchaseOrderList = new ArrayList<>();

    public List<PurchaseOrder> getOrderList(){
        return purchaseOrderList;
    }

    public void setOrder(PurchaseOrder purchaseOrder) {
        purchaseOrderList.add(purchaseOrder);
    }

    public PurchaseOrder getOrderByID(long id){
        return purchaseOrderList.stream().filter(purchaseOrder -> id == purchaseOrder.getId()).findAny().orElse(null);
    }
}
