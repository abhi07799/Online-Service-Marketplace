package marketplace.service;

import lombok.RequiredArgsConstructor;
import marketplace.dto.request.VendorRequestDto;
import marketplace.dto.response.VendorResponseDto;
import marketplace.exception.CustomException;
import marketplace.exception.ResourceAlreadyExistException;
import marketplace.exception.ResourceNotFoundException;
import marketplace.model.VendorModel;
import marketplace.repository.VendorRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendorService
{
    private final Logger log = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ModelMapper mapper;

    public VendorResponseDto addVendor(VendorRequestDto vendorRequestDto)
    {
        try
        {
            log.info("Adding new vendor");
            VendorModel vendorModel = mapper.map(vendorRequestDto, VendorModel.class);

            Optional<VendorModel> optionalVendorModel = vendorRepository.findByVendorMail(vendorModel.getVendorMail());
            if (optionalVendorModel.isPresent())
            {
                throw new ResourceAlreadyExistException();
            }

            VendorModel savedVendor = vendorRepository.save(vendorModel);
            log.info("Vendor added successfully");
            return mapper.map(savedVendor, VendorResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceAlreadyExistException)
            {
                log.error("Vendor already exists with this mail",ex);
                throw new ResourceAlreadyExistException("Vendor already exists with this mail",ex);
            }
            log.error("Error while adding vendor",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public VendorResponseDto getVendorById(long vendorId)
    {
        try
        {
            log.info("Getting vendor by id: {}",vendorId);
            Optional<VendorModel> optionalVendorModel = vendorRepository.findById(vendorId);
            if (optionalVendorModel.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            log.info("Vendor retrieved successfully");
            return mapper.map(optionalVendorModel.get(), VendorResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("Vendor does not exist with this id",ex);
                throw new ResourceNotFoundException("Vendor does not exist with this id: "+vendorId,ex);
            }
            log.error("Error while getting vendor by id: {}",vendorId,ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public List<VendorResponseDto> getAllVendors()
    {
        try
        {
            log.info("Getting all vendors");
            List<VendorModel> vendorModels = vendorRepository.findAll();

            if (vendorModels.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            List<VendorResponseDto> dtoList = vendorModels.stream().map(vendor -> mapper.map(vendor, VendorResponseDto.class)).toList();
            log.info("All vendors retrieved successfully");
            return dtoList;
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("No vendor found",ex);
                throw new ResourceNotFoundException("No vendor found",ex);
            }
            log.error("Error while getting all vendors",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public VendorResponseDto updateVendor(long vendorId, VendorRequestDto vendorRequestDto)
    {
        try
        {
            log.info("Updating vendor by id: {}",vendorId);
            Optional<VendorModel> optionalVendorModel = vendorRepository.findById(vendorId);

            VendorModel vendorModel = getVendorModel(optionalVendorModel, vendorRequestDto);
            VendorModel savedVendor = vendorRepository.save(vendorModel);

            log.info("Vendor updated successfully");
            return mapper.map(savedVendor, VendorResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("Vendor does not exist with this id",ex);
                throw new ResourceNotFoundException("Vendor does not exist with this id: "+vendorId,ex);
            }
            log.error("Error while updating vendor",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    private VendorModel getVendorModel(Optional<VendorModel> optionalVendorModel, VendorRequestDto vendorRequestDto)
    {
        if (optionalVendorModel.isEmpty())
        {
            throw new ResourceNotFoundException();
        }

        VendorModel vendorModel = optionalVendorModel.get();

        // Check null first, then check for emptiness
        if (vendorRequestDto.getFullName() != null && !vendorRequestDto.getFullName().isEmpty())
        {
            vendorModel.setFullName(vendorRequestDto.getFullName());
        }
        if (vendorRequestDto.getVendorMail() != null && !vendorRequestDto.getVendorMail().isEmpty())
        {
            vendorModel.setVendorMail(vendorRequestDto.getVendorMail());
        }
        if (vendorRequestDto.getPassword() != null && !vendorRequestDto.getPassword().isEmpty())
        {
            vendorModel.setPassword(vendorRequestDto.getPassword());
        }
        if (vendorRequestDto.getVendorAddress() != null && !vendorRequestDto.getVendorAddress().isEmpty())
        {
            vendorModel.setVendorAddress(vendorRequestDto.getVendorAddress());
        }
        if(!vendorRequestDto.getServices().isEmpty())
        {
            vendorModel.setServices(vendorRequestDto.getServices());
        }
        return vendorModel;
    }
}
