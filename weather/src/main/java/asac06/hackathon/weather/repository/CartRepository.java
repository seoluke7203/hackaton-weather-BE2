package asac06.hackathon.weather.repository;

import asac06.hackathon.weather.model.Cart;
import asac06.hackathon.weather.model.Product;
import asac06.hackathon.weather.model.Size;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    List<Cart> findByProductId(Integer id);

    @Query("select c from Cart c where c.size = :size and c.product.id = :id")
    Cart findByProductIdAndSize(@Param("id") Integer id, @Param("size") Size size);


}
