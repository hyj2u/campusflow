package com.cnco.campusflow.brand;


import com.cnco.campusflow.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Transactional
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public CommonResponse<?> getBrands() {
        return CommonResponse.of(brandService.getBrands());
    }

    @PostMapping
    public ResponseEntity<CommonResponse<?>> addBrand(@RequestBody BrandEntity brandEntity) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(brandService.addBrand(brandEntity)));
    }

    @DeleteMapping("/{brandId}")
    public ResponseEntity<CommonResponse<?>> addBrand(@PathVariable Long brandId) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(CommonResponse.of(null));
    }
}

