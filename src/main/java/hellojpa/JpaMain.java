package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        //엔티티 매니저 팩토리는 하나만 생성해서 애플리케이션 전체에서 공유한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        //엔티티 매니저는 DB 세션 단위(트랜잭션 단위)로 생성해주어야 하며, 쓰레드간에 공유하면 안된다.
        EntityManager em = emf.createEntityManager();

        //JPA에서는 트랜잭션 단위가 중요함. JPA의 모든 변경은 트랜잭션 안에서 실행되어야 한다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //JPA(JPQL)를 사용하면 검색을 할 때도 테이블이 아닌 엔티티 객체를 대상으로 검색한다.
            List<Member> members = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : members) {
                System.out.println("member.getName() = " + member.getName());
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
