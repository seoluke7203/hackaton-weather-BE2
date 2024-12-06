package asac06.hackathon.weather.service;

import asac06.hackathon.weather.dto.CartDto;
import asac06.hackathon.weather.model.Cart;
import asac06.hackathon.weather.model.Product;
import asac06.hackathon.weather.model.Size;
import asac06.hackathon.weather.repository.CartRepository;
import asac06.hackathon.weather.repository.ProductRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addProductByProductId(Integer productId, Size size) {
        Cart findCart = cartRepository.findByProductIdAndSize(productId, size);

        if (findCart == null) {
            Product findProduct = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("ㅗ"));
            Cart newCart = new Cart(findProduct, size);
            newCart.setCount(1);
            cartRepository.save(newCart);
        } else {
            findCart.setCount(findCart.getCount() + 1);
        }
    }

    @Transactional
    public List<CartDto> findAllCart() {
        List<Cart> carts = cartRepository.findAll();
        List<CartDto> cartDtos = new ArrayList<>();
        for (Cart cart : carts) {
            cartDtos.add(CartDto.from(cart));
        }
        return cartDtos;
    }

    @Transactional
    public String deleteCartById(Integer id) {
        Cart findCart = cartRepository.findById(id).orElseThrow(() -> new RuntimeException("NOT FOUND Cart"));

        if (findCart.getCount() >= 1) findCart.setCount(findCart.getCount() - 1);
        if (findCart.getCount() == 0) {
            cartRepository.deleteById(id);
            return id + " 번 product가 삭제되었습니다.";
        }
        return id + " 번 producdt가 하나 줄었습니다.";
    }
}
