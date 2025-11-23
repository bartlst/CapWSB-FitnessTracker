package pl.wsb.fitnesstracker.event;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class EventRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    public List<Event> findByName(String name) {
        String jpql = "SELECT e FROM Event e WHERE LOWER(e.name) = LOWER(:name)";
        TypedQuery<Event> query = em.createQuery(jpql, Event.class);
        query.setParameter("name", name);
        return query.getResultList();
    }
}
