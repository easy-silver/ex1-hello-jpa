package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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

            Member member = em.find(Member.class, 150L);
            member.setName("ZZZ");

            System.out.println("=================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
