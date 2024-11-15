package marketplace.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import marketplace.dto.request.BookingRequestDto;
import marketplace.dto.response.BookingResponseDto;
import marketplace.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Booking", description = "Booking management APIs")
@RestController
@RequestMapping("api/v1/booking/")
public class BookingController
{
    private final Logger log = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    /*
        To add a new booking
     */
    @Operation(summary = "Create a new booking", description = "This endpoint accepts booking request dto and returns a booking response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Booking created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponseDto.class))
            ),
                    @ApiResponse(responseCode = "400",description = "Invalid input data",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PostMapping("addBooking")
    public ResponseEntity<?> createBooking(@Valid @RequestBody BookingRequestDto bookingRequestDto)
    {
        log.info("Request to create a booking");
        return new ResponseEntity<>(bookingService.addBooking(bookingRequestDto), HttpStatus.CREATED);
    }


    /*
        To fetch all bookings
     */
    @Operation(summary = "Fetch all bookings", description = "This endpoint returns a list of booking response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "All Bookings fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Booking Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getAllBookings")
    public ResponseEntity<?> getAllBookings()
    {
        log.info("Request to get all bookings");
        return new ResponseEntity<>(bookingService.getAllBookings(), HttpStatus.OK);
    }

    /*
        to fetch a booking by id
     */
    @Operation(summary = "Fetch a booking by booking id", description = "This endpoint accepts booking id and returns a booking response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Booking fetched successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Booking Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @GetMapping("getBookingById/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable Long bookingId)
    {
        log.info("Request to get booking by id: {}",bookingId);
        return new ResponseEntity<>(bookingService.getBookingById(bookingId), HttpStatus.OK);
    }
    /*
        approve booking - for admin
    */
    @Operation(summary = "approve booking", description = "This endpoint accepts booking id and returns an updated booking response dto.")
    @ApiResponses(
            { @ApiResponse(responseCode = "200",description = "Booking approved successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BookingResponseDto.class))
            ),
                    @ApiResponse(responseCode = "404",description = "Booking Not Found",
                            content = @Content(mediaType = "application/json")
                    ),
                    @ApiResponse(responseCode = "500",description = "Internal Server Error",
                            content = @Content(mediaType = "application/json")
                    )
            })
    @PutMapping("updateBookingStatus/{bookingId}")
    public ResponseEntity<?> approveBooking(@PathVariable Long bookingId, @RequestParam String bookingStatus)
    {
        log.info("Request to update booking status by id: {}",bookingId);
        return new ResponseEntity<>(bookingService.updateBookingStatus(bookingId, bookingStatus), HttpStatus.OK);
    }

}

