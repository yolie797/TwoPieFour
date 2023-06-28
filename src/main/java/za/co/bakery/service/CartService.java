package za.co.bakery.service;

import za.co.bakery.model.CartItem;
import za.co.bakery.model.Product;

public interface CartService {
    CartItem addItem(Product product, int quantity, double total, double subTotal);
}
