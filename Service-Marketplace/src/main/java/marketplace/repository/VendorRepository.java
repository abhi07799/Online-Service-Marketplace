package marketplace.repository;

import marketplace.model.VendorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<VendorModel, Long>
{
    Optional<VendorModel> findByVendorMail(String userMail);
}
