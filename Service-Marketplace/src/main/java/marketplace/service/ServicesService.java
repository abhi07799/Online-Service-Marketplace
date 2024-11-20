package marketplace.service;

import lombok.RequiredArgsConstructor;
import marketplace.dto.request.ServiceRequestDto;
import marketplace.dto.response.ServiceResponseDto;
import marketplace.exception.CustomException;
import marketplace.exception.ResourceNotFoundException;
import marketplace.model.ServiceModel;
import marketplace.model.VendorModel;
import marketplace.repository.ServiceRepository;
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
public class ServicesService
{
    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private ModelMapper mapper;

    private final Logger log= LoggerFactory.getLogger(ServicesService.class);

    public ServiceResponseDto addService(ServiceRequestDto serviceRequestDto)
    {
        try
        {
            log.info("Adding new service");

            Optional<VendorModel> optionalVendorModel = vendorRepository.findById(serviceRequestDto.getVendor().getId());
            if(optionalVendorModel.isEmpty())
            {
                throw new ResourceNotFoundException("Vendor not found with id: " + serviceRequestDto.getVendor().getId());
            }

            ServiceModel serviceModel = mapper.map(serviceRequestDto, ServiceModel.class);
            serviceModel.setServiceStatus("InActive");
            ServiceModel savedService = serviceRepository.save(serviceModel);
            log.info("Service added successfully");
            return mapper.map(savedService, ServiceResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("Vendor does not exist",ex);
                throw new ResourceNotFoundException(ex.getMessage());
            }
            log.error("Error while adding service",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public ServiceResponseDto getServiceById(long serviceId)
    {
        try
        {
            log.info("Getting service by id: {}",serviceId);
            Optional<ServiceModel> optionalServiceModel = serviceRepository.findById(serviceId);
            if (optionalServiceModel.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            log.info("Service retrieved successfully");
            return mapper.map(optionalServiceModel.get(), ServiceResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("Service does not exist with this id",ex);
                throw new ResourceNotFoundException("Service does not exist with this id: "+serviceId,ex);
            }
            log.error("Error while getting service by id: {}",serviceId,ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public List<ServiceResponseDto> getAllServices()
    {
        try
        {
            log.info("Getting all services");
            List<ServiceModel> serviceModels = serviceRepository.findAll();

            if (serviceModels.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            List<ServiceResponseDto> dtoList = serviceModels.stream().map(service -> mapper.map(service, ServiceResponseDto.class)).toList();
            log.info("All services retrieved successfully");
            return dtoList.stream().map(dto->{
                dto.getVendor().setServices(null);
                return dto;
            }).toList();
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("No service found",ex);
                throw new ResourceNotFoundException("No service found",ex);
            }
            log.error("Error while getting all services",ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public List<ServiceResponseDto> getServiceByTitle(String serviceTitle)
    {
        try
        {
            log.info("Getting service by title: {}",serviceTitle);
            List<ServiceModel> serviceModels = serviceRepository.findByServiceTitleLike(serviceTitle);
            if (serviceModels.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            List<ServiceResponseDto> dtoList = serviceModels.stream().map(service -> mapper.map(service, ServiceResponseDto.class)).toList();
            log.info("All services with the title retrieved successfully");
            return dtoList;
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("Service does not exist with this title",ex);
                throw new ResourceNotFoundException("Service does not exist with this title: "+serviceTitle,ex);
            }
            log.error("Error while getting service by title: {}",serviceTitle,ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public ServiceResponseDto approveService(long serviceId)
    {
        try
        {
            log.info("Approving service by id: {}",serviceId);
            Optional<ServiceModel> optionalServiceModel = serviceRepository.findById(serviceId);
            if (optionalServiceModel.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            log.info("Service approved successfully");
            ServiceModel serviceModel = optionalServiceModel.get();
            serviceModel.setServiceStatus("Active");
            return mapper.map(serviceRepository.save(serviceModel), ServiceResponseDto.class);
        }
        catch (Exception ex)
        {
            if(ex instanceof ResourceNotFoundException)
            {
                log.error("Service does not exist with this id",ex);
                throw new ResourceNotFoundException("Service does not exist with this id: "+serviceId,ex);
            }
            log.error("Error while approving service by id: {}",serviceId,ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }

    public List<ServiceResponseDto> getVendorAllServices(long vendorId)
    {
        try
        {
            log.info("Getting all services of a vendor by id: {}",vendorId);
            List<ServiceModel> serviceModels = serviceRepository.findByVendor_Id(vendorId);

            if (serviceModels.isEmpty())
            {
                throw new ResourceNotFoundException();
            }
            List<ServiceResponseDto> dtoList = serviceModels.stream().map(service -> mapper.map(service, ServiceResponseDto.class)).toList();
            log.info("All services of the vendor retrieved successfully");
            return dtoList;
        }
        catch (Exception ex)
        {
            if (ex instanceof ResourceNotFoundException)
            {
                log.error("No services found of vendor id : {}",vendorId, ex);
                throw new ResourceNotFoundException("No services found for vendor id : "+vendorId, ex);
            }
            log.error("Error while getting all services of vendor", ex);
            throw new CustomException("Something went wrong!!", ex);
        }
    }
}
