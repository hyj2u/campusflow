package com.cnco.campusflow.brand;

import com.cnco.campusflow.common.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
@Transactional
@Tag(name = "Brand", description = "Brand management APIs")
public class BrandController {
    private final BrandService brandService;

    @Operation(summary = "Get brands", description = "Retrieves the list of brands.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Brands retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @GetMapping
    public CommonResponse<?> getBrands() {
        return CommonResponse.of(brandService.getBrands());
    }

    @Operation(summary = "Add brand", description = "Creates a new brand.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Brand added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<CommonResponse<?>> addBrand(
        @Parameter(description = "Brand details") @RequestBody BrandEntity brandEntity
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.of(brandService.addBrand(brandEntity)));
    }

    @Operation(summary = "Delete brand", description = "Deletes a specific brand.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Brand deleted successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @DeleteMapping("/{brandId}")
    public ResponseEntity<CommonResponse<?>> addBrand(
        @Parameter(description = "Brand ID") @PathVariable Long brandId
    ) {
        brandService.deleteBrand(brandId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body(CommonResponse.of(null));
    }
}

