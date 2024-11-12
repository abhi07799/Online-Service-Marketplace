package marketplace.service;

import lombok.RequiredArgsConstructor;
import marketplace.dto.request.ServiceRequestDto;
import marketplace.dto.response.ServiceResponseDto;
import marketplace.exception.CustomException;
import marketplace.exception.ResourceNotFoundException;
import marketplace.model.ServiceModel;
import marketplace.repository.ServiceRepository;
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
    private ModelMapper mapper;

    private final Logger log= LoggerFactory.getLogger(ServicesService.class);

    public ServiceResponseDto addService(ServiceRequestDto serviceRequestDto)
    {
        try
        {
            log.info("Adding new service");
            ServiceModel serviceModel = mapper.map(serviceRequestDto, ServiceModel.class);

            ServiceModel savedService = serviceRepository.save(serviceModel);
            log.info("Service added successfully");
            return mapper.map(savedService, ServiceResponseDto.class);
        }
        catch (Exception ex)
        {
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
            return dtoList;
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
}
