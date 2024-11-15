package marketplace.service;

import marketplace.dto.request.BookingRequestDto;
import marketplace.dto.response.BookingResponseDto;
import marketplace.exception.CustomException;
import marketplace.exception.ResourceNotFoundException;
import marketplace.model.BookingModel;
import marketplace.model.BookingStatusEnum;
import marketplace.model.ServiceModel;
import marketplace.model.UserModel;
import marketplace.repository.BookingRepository;
import marketplace.repository.ServiceRepository;
import marketplace.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService
{
    private final Logger log = LoggerFactory.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    public BookingResponseDto addBooking(BookingRequestDto bookingRequestDto)
    {
        try
        {
            log.info("Adding new booking");

            Optional<ServiceModel> optionalServiceModel = serviceRepository.findById(bookingRequestDto.getService().getId());
            if(optionalServiceModel.isEmpty())
            {
                throw new ResourceNotFoundException("Service not found for id " + bookingRequestDto.getService().getId());
            }

            Optional<UserModel> optionalUserModel = userRepository.findById(bookingRequestDto.getUser().getId());
            if(optionalUserModel.isEmpty())
            {
                throw new ResourceNotFoundException("User not found for id " + bookingRequestDto.getUser().getId());
            }

            BookingModel bookingModel = mapper.map(bookingRequestDto, BookingModel.class);
            bookingModel.setBookingStatus(BookingStatusEnum.PENDING);

            BookingModel savedBooking = bookingRepository.save(bookingModel);
            log.info("Booking added successfully");
            return mapper.map(savedBooking, BookingResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                throw new ResourceNotFoundException(ex.getMessage());
            }
            log.error("Error while adding booking",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public BookingResponseDto getBookingById(long bookingId)
    {
        try
        {
            log.info("Getting booking by id: {}",bookingId);
            Optional<BookingModel> optionalBookingModel = bookingRepository.findById(bookingId);
            if (optionalBookingModel.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            log.info("Booking retrieved successfully");
            return mapper.map(optionalBookingModel.get(), BookingResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("Booking does not exist with this id",ex);
                throw new ResourceNotFoundException("Booking does not exist with this id: "+bookingId,ex);
            }
            log.error("Error while getting booking by id: {}",bookingId,ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public List<BookingResponseDto> getAllBookings()
    {
        try
        {
            log.info("Getting all bookings");
            List<BookingModel> bookingModels = bookingRepository.findAll();

            if (bookingModels.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            List<BookingResponseDto> dtoList = bookingModels.stream().map(booking -> mapper.map(booking, BookingResponseDto.class)).toList();
            log.info("All bookings retrieved successfully");
            return dtoList;
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("No booking found",ex);
                throw new ResourceNotFoundException("No booking found",ex);
            }
            log.error("Error while getting all bookings",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public BookingResponseDto updateBookingStatus(long bookingId, String bookingStatus)
    {
        try
        {
            log.info("Updating booking status by id: {}",bookingId);
            Optional<BookingModel> optionalBookingModel = bookingRepository.findById(bookingId);

            if (optionalBookingModel.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            BookingModel bookingModel = getBookingModel(bookingStatus, optionalBookingModel);
            BookingModel savedBooking = bookingRepository.save(bookingModel);

            log.info("Booking status updated successfully");
            return mapper.map(savedBooking, BookingResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("Booking does not exist with this id",ex);
                throw new ResourceNotFoundException("Booking does not exist with this id: "+bookingId,ex);
            }
            log.error("Error while updating booking status",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    private static BookingModel getBookingModel(String bookingStatus, Optional<BookingModel> optionalBookingModel)
    {
        BookingModel bookingModel = optionalBookingModel.get();

        if(bookingStatus.equalsIgnoreCase("confirm"))
        {
            bookingModel.setBookingStatus(BookingStatusEnum.CONFIRMED);
        }
        if(bookingStatus.equalsIgnoreCase("cancel"))
        {
            bookingModel.setBookingStatus(BookingStatusEnum.CANCELLED);
        }
        if(bookingStatus.equalsIgnoreCase("complete"))
        {
            bookingModel.setBookingStatus(BookingStatusEnum.COMPLETED);
        }
        return bookingModel;
    }
}
