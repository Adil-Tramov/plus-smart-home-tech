package ru.yandex.practicum.fallback.shopping.store;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.yandex.practicum.controller.shopping.store.feign.StoreControllerFeign;
import ru.yandex.practicum.dto.ProductCategoryDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.QuantityStateDto;

import java.util.UUID;

@Component
public class StoreControllerFallback implements StoreControllerFeign {

    @Override
    public Page<ProductDto> getListOfProducts(@RequestParam ProductCategoryDto category,
                                              @PageableDefault(size = 20, sort = "productName",
                                                      direction = Sort.Direction.ASC) Pageable pageable) {
        return null;
    }

    @Override
    public ProductDto createNewProduct(@RequestBody ProductDto product) {
        return null;
    }

    @Override
    public ProductDto updateProductInfo(@RequestBody ProductDto product) {
        return null;
    }

    @Override
    public boolean removeProductFromStore(@RequestBody UUID productId) {
        return false;
    }

    @Override
    public ProductDto changeQuantityState(@RequestParam UUID productId,
                                          @RequestParam QuantityStateDto quantityState) {
        return null;
    }

    @Override
    public ProductDto getProductInfo(@PathVariable UUID id) {
        return null;
    }
}
