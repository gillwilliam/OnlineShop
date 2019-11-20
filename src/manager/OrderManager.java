package manager;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.Category;
import entities.Order;
import entities.ProductList;

public class OrderManager {

	private EntityManagerFactory emf;

	public OrderManager(String unidadDePersistencia) {
		emf = Persistence.createEntityManagerFactory(unidadDePersistencia);
	}

	public OrderManager(EntityManagerFactory factory) {
		emf = factory;
	}

	public OrderManager() {
		emf = Persistence.createEntityManagerFactory("OnlineShop");
	}

	public void create(Order order) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			if (order.getProductList() != null) {
				ProductList pl = em.merge(order.getProductList());
				order.setProductList(pl);
				pl.setOrder(order);
			}
			em.persist(order);
			em.getTransaction().commit();
		} catch (Exception ex) {
			try {
				if (em.getTransaction().isActive()) {
					em.getTransaction().rollback();
				}
			} catch (Exception e) {
				ex.printStackTrace();
				throw e;
			}
			throw ex;
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Order> findAll() {
		List<Order> resultado;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createNamedQuery("Order.findAll", Order.class);
			resultado = query.getResultList();
		} finally {
			em.close();
		}
		return resultado;

	}

	public Order findById(int id) {
		Order resultado;
		EntityManager em = emf.createEntityManager();
		try {
			resultado = em.find(Order.class, id);
		} finally {
			em.close();
		}
		return resultado;
	}

	@SuppressWarnings("unchecked")
	public List<Order> findByName(String search) {
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("SELECT o " + " FROM Order o " + " WHERE o.name LIKE '%" + search + "%'");
		return (List<Order>) query.getResultList();
	}

}
