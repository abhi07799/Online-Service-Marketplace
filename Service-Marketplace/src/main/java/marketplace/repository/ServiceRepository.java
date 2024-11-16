package marketplace.repository;

import marketplace.model.ServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<ServiceModel, Long>
{
    List<ServiceModel> findByServiceTitleLike(String serviceTitle);

    List<ServiceModel> findByVendor_Id(Long id);
}
