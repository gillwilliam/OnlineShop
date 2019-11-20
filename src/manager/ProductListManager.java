package manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.Product;
import entities.ProductList;

public class ProductListManager {

	private EntityManagerFactory emf;

	public ProductListManager() {
		emf = Persistence.createEntityManagerFactory("OnlineShop");
	}

	public ProductListManager(String unidadDePersistencia) {
		emf = Persistence.createEntityManagerFactory(unidadDePersistencia);
	}

	public ProductListManager(EntityManagerFactory factory) {
		emf = factory;
	}

	public String create(ProductList user) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			ArrayList<Product> products = new ArrayList<Product>();
			for (Product p : user.getProducts()) {
				p = em.merge(p);
				products.add(p);
				List<ProductList> l = p.getProductLists();
				l.add(user);
				p.setProductLists(l);
			}
			user.setProducts(products);
			em.persist(user);
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
		return "";
	}

	@SuppressWarnings("unchecked")
	public List<ProductList> findAll() {
		List<ProductList> resultado;
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createNamedQuery("ProductList.findAll", ProductList.class);
			resultado = query.getResultList();
		} finally {
			em.close();
		}
		return resultado;

	}

	public ProductList findById(int id) {
		ProductList resultado;
		EntityManager em = emf.createEntityManager();
		try {
			resultado = em.find(ProductList.class, id);
		} finally {
			em.close();
		}
		return resultado;
	}

	public void delete(ProductList user) {
		EntityManager em = emf.createEntityManager();
		try {
			user = em.merge(user);
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

//	public void addProduct(ProductList list, Product p) {
//		EntityManager em = emf.createEntityManager();
//		try {
//			em.getTransaction().begin();
//			list = em.merge(list);
//			list.setProducts(products);
//			em.getTransaction().commit();
//		} finally {
//			em.close();
//		}
//	}

}
