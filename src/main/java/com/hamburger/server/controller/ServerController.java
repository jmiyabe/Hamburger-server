package com.hamburger.server.controller;

import com.hamburger.server.model.Ingredient;
import com.hamburger.server.model.Product;
import com.hamburger.server.model.PurchaseOrder;
import com.hamburger.server.service.IngredientService;
import com.hamburger.server.service.ProductService;
import com.hamburger.server.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "controller", description = "API of Products")
@CrossOrigin(origins = "http://localhost:3000")
public class ServerController {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    ProductService productService;

    @Autowired
    OrderService orderService;

    @Operation(summary = "Return all ingredients", description = "Return all ingredients from the database")
    @RequestMapping(method = RequestMethod.GET, value = "/ingredients")
    public ResponseEntity<?> getIngredientList(){
        return new ResponseEntity<>(ingredientService.getIngredients(), HttpStatus.OK);
    }

    @Operation(summary = "Create an ingredient", description = "Save an ingredient in the database")
    @RequestMapping(method = RequestMethod.POST, value = "/ingredient")
    public ResponseEntity<?> createIngredient(@RequestBody Ingredient ingredient){
        ingredientService.setIngredient(ingredient);
        return new ResponseEntity<>("Ingredient created", HttpStatus.OK);
    }

    @Operation(summary = "Return all products", description = "Return all products from the database")
    @RequestMapping(method = RequestMethod.GET, value = "/lunchs")
    public ResponseEntity<?> getLunchList(){
        return new ResponseEntity<>(productService.getProductList(), HttpStatus.OK);
    }

    @Operation(summary = "Create a product", description = "Save a product in the database")
    @RequestMapping(method = RequestMethod.POST, value="/product")
    public ResponseEntity<?> createProduct(@RequestBody Product product){
        productService.setProduct(product);
        return new ResponseEntity<>("Product created", HttpStatus.OK);
    }

    @Operation(summary = "Create an order", description = "Save a purchase order in the database and return the purchase")
    @RequestMapping(method = RequestMethod.POST, value = "/orders")
    public ResponseEntity<?> createOrder(@RequestBody PurchaseOrder payload) {
        orderService.setOrder(payload);
        return new ResponseEntity<>(orderService.getOrderByID(payload.getId()), HttpStatus.OK);
    }

    @Operation(summary = "Return an order", description = "Return all purchase orders from the database")
    @RequestMapping(method = RequestMethod.GET, value = "/orders")
    public ResponseEntity<?> getAllOrders() {
        return new ResponseEntity<>(orderService.getOrderList(), HttpStatus.OK);
    }
}
