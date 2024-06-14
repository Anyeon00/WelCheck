package K2LJ.WelCheck_Backend.facilitypackage.repository;

import K2LJ.WelCheck_Backend.facilitypackage.domain.Facility;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FacilityRepositoryImp implements FacilityRepository {

    private final EntityManager em;
    @Override
    public void save(Facility facility) {
        em.persist(facility);
    }

    @Override
    public Facility findOne(Long id) {
        return em.find(Facility.class,id);
    }
    @Override
    public List<Facility> findAll() {
        return em.createQuery("select f from Facility f", Facility.class).getResultList();
    }
    @Override
    public List<Facility> findbyFcltCd(String fcltcd){
        return em.createQuery("select f from Facility f where f.fcltCd = : fcltCd", Facility.class)
                .setParameter("fcltCd", fcltcd)
                .getResultList();
    }
}
