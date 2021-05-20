package com.hamburger.server.service;

import com.hamburger.server.model.Ingredient;
import com.hamburger.server.model.Product;
import com.hamburger.server.model.PurchaseOrder;
import com.hamburger.server.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<PurchaseOrder> getOrderList(){
        return  orderRepository.getOrderList();
    }

    public void setOrder(PurchaseOrder purchaseOrder){
        orderRepository.setOrder(purchaseOrder);
    }

    public PurchaseOrder getOrderByID(long id){
        return orderRepository.getOrderByID(id);
    }

    public double calculateSaleDiscount(PurchaseOrder purchaseOrder){
        double totalPrice = 0;
        double discount = 0;

        for (Product product : purchaseOrder.getProducts() ){
            double lunchPrice = product.getIngredientList().stream().map(ingredient -> (ingredient.getPrice() * ingredient.getQuantity())).reduce(0F,(ing1, ing2)-> ing1 + ing2 );
            switch (product.getSale()){
                case LIGHT:
                    boolean hasAlface = product.getIngredientList().stream().anyMatch(ingredient -> ingredient.getName() == "Alface");
                    boolean hasBacon = product.getIngredientList().stream().anyMatch(ingredient -> ingredient.getName() == "Bacon");
                    if(hasAlface && !hasBacon){
                        lunchPrice = lunchPrice * 0.10F;
                    }
                    break;
                case MUITA_CARNE:
                    Ingredient meat = product.getIngredientList().stream().filter(ing -> ing.getId() == 2).findAny().orElse(null);
                    discount = Math.floor(meat.getQuantity() / 3);
                    lunchPrice -= discount * meat.getPrice();
                    break;
                case MUITO_QUEIJO:
                    Ingredient cheese = product.getIngredientList().stream().filter(ing -> ing.getId() == 4).findAny().orElse(null);
                    discount = Math.floor(cheese.getQuantity() / 3);
                    lunchPrice -= discount * cheese.getPrice();
                case INFLACAO:
                    break;
                default:
                    break;
            }
            totalPrice += lunchPrice;
        }
        purchaseOrder.setTotalPrice(((double) Math.round(totalPrice * 100) / 100));
        return (double) Math.round(totalPrice * 100) / 100;
    }
}
