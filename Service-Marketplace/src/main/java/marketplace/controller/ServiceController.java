package marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import marketplace.dto.request.ServiceRequestDto;
import marketplace.dto.response.ServiceResponseDto;
import marketplace.service.ServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Service", description = "Service management APIs")
@RestController
@RequestMapping("api/v1/service/")
public class ServiceController
{
    private final Logger log = LoggerFactory.getLogger(ServiceController.class);

    @Autowired
    private ServicesService serviceService;

    /*
        To add a new service
     */
    @Operation(summary = "Create a new service", description = "This endpoint accepts service request dto and returns a service response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Service created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceResponseDto.class))
            ),
                    @ApiResponse(responseCode = "400",description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PostMapping("addService")
    public ResponseEntity<?> createService(@Valid @RequestBody ServiceRequestDto serviceRequestDto)
    {
        log.info("Request to create a service");
        return new ResponseEntity<>(serviceService.addService(serviceRequestDto), HttpStatus.CREATED);
    }


    /*
        To fetch all services
     */
    @Operation(summary = "Fetch all services", description = "This endpoint returns a list of service response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "All Services fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Service Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getAllServices")
    public ResponseEntity<?> getAllServices()
    {
        log.info("Request to get all services");
        return new ResponseEntity<>(serviceService.getAllServices(), HttpStatus.OK);
    }

    /*
        To fetch a service by title
     */
    @Operation(summary = "Fetch a service by service title", description = "This endpoint accepts service title and returns a service response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Service fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Service Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getServiceByTitle")
    public ResponseEntity<?> getServiceByTitle(@RequestParam("serviceTitle") String serviceTitle)
    {
        log.info("Request to get service by title: {}",serviceTitle);
        return new ResponseEntity<>(serviceService.getServiceByTitle(serviceTitle), HttpStatus.OK);
    }

    /*
        to fetch a service by id
     */
    @Operation(summary = "Fetch a service by service id", description = "This endpoint accepts service id and returns a service response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Service fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Service Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getServiceById/{serviceId}")
    public ResponseEntity<?> getServiceById(@PathVariable Long serviceId)
    {
        log.info("Request to get service by id: {}",serviceId);
        return new ResponseEntity<>(serviceService.getServiceById(serviceId), HttpStatus.OK);
    }
    /*
        approve service - for admin
    */
    @Operation(summary = "approve service", description = "This endpoint accepts service id and returns an updated service response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Service approved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Service Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PutMapping("approveService/{serviceId}")
    public ResponseEntity<?> approveService(@PathVariable Long serviceId)
    {
        log.info("Request to approve service by id: {}",serviceId);
        return new ResponseEntity<>(serviceService.approveService(serviceId), HttpStatus.OK);
    }

    /*
       To fetch all services
    */
    @Operation(summary = "Fetch all vendor services", description = "This endpoint takes a vendor id and returns a list of service response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "All Services fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServiceResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Service Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getVendorAllServices/{vendorId}")
    public ResponseEntity<?> getVendorAllServices(@PathVariable Long vendorId)
    {
        log.info("Request to get all vendor services");
        return new ResponseEntity<>(serviceService.getVendorAllServices(vendorId), HttpStatus.OK);
    }
}
