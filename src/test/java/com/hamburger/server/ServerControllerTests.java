package com.hamburger.server;

import com.hamburger.server.controller.ServerController;
import com.hamburger.server.model.Ingredient;
import com.hamburger.server.model.Product;
import com.hamburger.server.model.PurchaseOrder;
import com.hamburger.server.service.IngredientService;
import com.hamburger.server.service.OrderService;
import com.hamburger.server.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServerController.class)
public class ServerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private OrderService orderService;

    List<Ingredient> ingredientListMock = Arrays.asList(new Ingredient[]{
            new Ingredient(0, "Alface" , 0.4F, 1),
            new Ingredient(1, "Bacon" , 2.0F, 1),});

    List<Product> productListMock = new ArrayList<>();

    @BeforeEach
    public void setup(){
        productListMock.add(new Product(0, "X-Bacon", ingredientListMock, Product.Sale.LIGHT, 1));
        productListMock.add(new Product(1, "X-Burger", ingredientListMock, Product.Sale.MUITA_CARNE, 1));
    }

    // Testing the get on /api/ingredients
    @Test
    public void testGetIngredientsList() throws Exception {
        String expectedResult = "[{\"id\":0,\"name\":\"Alface\",\"price\":0.4,\"quantity\":1},{\"id\":1,\"name\":\"Bacon\",\"price\":2.0,\"quantity\":1}]";

        when(ingredientService.getIngredients()).thenReturn(ingredientListMock);

        this.mockMvc.perform(get("/api/ingredients"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    // Testing the get on /api/lunchs
    @Test
    public void testGetProductsList() throws Exception {
        String expectedResult = "[{\"sale\":\"LIGHT\",\"id\":0,\"name\":\"X-Bacon\",\"count\":1,\"ingredientList\":[{\"id\":0,\"name\":\"Alface\",\"price\":0.4,\"quantity\":1},{\"id\":1,\"name\":\"Bacon\",\"price\":2.0,\"quantity\":1}]},{\"sale\":\"MUITA_CARNE\",\"id\":1,\"name\":\"X-Burger\",\"count\":1,\"ingredientList\":[{\"id\":0,\"name\":\"Alface\",\"price\":0.4,\"quantity\":1},{\"id\":1,\"name\":\"Bacon\",\"price\":2.0,\"quantity\":1}]}]";

        when(productService.getProductList()).thenReturn(productListMock);

        this.mockMvc.perform(get("/api/lunchs"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    // Testing the get on /api/orders
    @Test
    public void testGetOrdersList() throws Exception{
        List<PurchaseOrder> purchaseOrderList = new ArrayList<>();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(0);
        purchaseOrder.setProducts(productListMock);
        purchaseOrder.setTotalPrice(2.40F);
        purchaseOrderList.add(purchaseOrder);

        String expectedResult = "[{\"id\":0,\"totalPrice\":2.4000000953674316,\"products\":[{\"sale\":\"LIGHT\",\"id\":0,\"name\":\"X-Bacon\",\"count\":1,\"ingredientList\":[{\"id\":0,\"name\":\"Alface\",\"price\":0.4,\"quantity\":1},{\"id\":1,\"name\":\"Bacon\",\"price\":2.0,\"quantity\":1}]},{\"sale\":\"MUITA_CARNE\",\"id\":1,\"name\":\"X-Burger\",\"count\":1,\"ingredientList\":[{\"id\":0,\"name\":\"Alface\",\"price\":0.4,\"quantity\":1},{\"id\":1,\"name\":\"Bacon\",\"price\":2.0,\"quantity\":1}]}]}]";

        when(orderService.getOrderList()).thenReturn(purchaseOrderList);

        this.mockMvc.perform(get("/api/orders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResult));
    }

    // Testing the post on /api/orders
    @Test
    public void testCreateOrder() throws Exception {
        String order = "{\"id\":1000,\"totalPrice\":2.4,\"products\":[{\"sale\":\"LIGHT\",\"id\":0,\"name\":\"X-Bacon\",\"count\":1,\"ingredientList\":[{\"id\":0,\"name\":\"Alface\",\"price\":0.4,\"quantity\":1},{\"id\":1,\"name\":\"Bacon\",\"price\":2.0,\"quantity\":1}]},{\"sale\":\"MUITA_CARNE\",\"id\":1,\"name\":\"X-Burger\",\"count\":1,\"ingredientList\":[{\"id\":0,\"name\":\"Alface\",\"price\":0.4,\"quantity\":1},{\"id\":1,\"name\":\"Bacon\",\"price\":2.0,\"quantity\":1}]}]}";

        long orderId = 1000;
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setId(orderId);
        purchaseOrder.setTotalPrice(2.4);
        purchaseOrder.setProducts(productListMock);

        when(orderService.getOrderByID(orderId)).thenReturn(purchaseOrder);

        this.mockMvc.perform(post("/api/orders")
                .content(order)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(order));
    }

}
