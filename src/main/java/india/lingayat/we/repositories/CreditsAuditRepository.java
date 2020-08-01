package india.lingayat.we.repositories;

import india.lingayat.we.models.CreditsAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface CreditsAuditRepository extends JpaRepository<CreditsAudit, Long> {

}
