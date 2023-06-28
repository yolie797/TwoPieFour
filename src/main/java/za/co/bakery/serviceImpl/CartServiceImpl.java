package za.co.bakery.serviceImpl;

import za.co.bakery.model.CartItem;
import za.co.bakery.model.Product;
import za.co.bakery.service.CartService;

public class CartServiceImpl implements CartService{

    @Override
    public CartItem addItem(Product product, int quantity, double total, double subTotal) {
        return product != null && quantity > 0 ? new CartItem(product, quantity, total, subTotal):null;
    }
    
}
