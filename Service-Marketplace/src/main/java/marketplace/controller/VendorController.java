package marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import marketplace.dto.request.VendorRequestDto;
import marketplace.dto.response.VendorResponseDto;
import marketplace.service.VendorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Vendor", description = "Vendor management APIs")
@RestController
@RequestMapping("api/v1/")
public class VendorController
{
    private final Logger log = LoggerFactory.getLogger(VendorController.class);

    @Autowired
    private VendorService vendorService;

    @Operation(summary = "Create a new vendor", description = "This endpoint accepts vendor request dto and returns a vendor response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Vendor created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendorResponseDto.class))
            ),
                    @ApiResponse(responseCode = "400",description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PostMapping("addVendor")
    public ResponseEntity<?> createVendor(@Valid @RequestBody VendorRequestDto vendorRequestDto)
    {
        log.info("Request to create a vendor");
        return new ResponseEntity<>(vendorService.addVendor(vendorRequestDto), HttpStatus.CREATED);
    }


    @Operation(summary = "Fetch all vendors", description = "This endpoint returns a list of vendor response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "All Vendors fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendorResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Vendor Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getAllVendors")
    public ResponseEntity<?> getAllVendors()
    {
        log.info("Request to get all vendors");
        return new ResponseEntity<>(vendorService.getAllVendors(), HttpStatus.OK);
    }


    @Operation(summary = "Fetch a vendor by vendor id", description = "This endpoint accepts vendor id and returns a vendor response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Vendor fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendorResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Vendor Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getVendorById/{vendorId}")
    public ResponseEntity<?> getVendorById(@PathVariable Long vendorId)
    {
        log.info("Request to get vendor by id: {}",vendorId);
        return new ResponseEntity<>(vendorService.getVendorById(vendorId), HttpStatus.OK);
    }


    @Operation(summary = "Update vendor details", description = "This endpoint accepts vendor request dto and vendorId and returns an updated vendor response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Vendor updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = VendorResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Vendor Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PutMapping("updateVendor/{vendorId}")
    public ResponseEntity<?> updateVendor(@PathVariable Long vendorId, @RequestBody VendorRequestDto vendorRequestDto)
    {
        log.info("Request to update vendor by id: {}",vendorId);
        return new ResponseEntity<>(vendorService.updateVendor(vendorId, vendorRequestDto), HttpStatus.OK);
    }
}
