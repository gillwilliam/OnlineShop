package manager;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import entities.User;

public class UserManager {

	private EntityManagerFactory emf;

	public UserManager(String unidadDePersistencia) {
		emf = Persistence.createEntityManagerFactory(unidadDePersistencia);
	}

	public UserManager(EntityManagerFactory factory) {
		emf = factory;
	}

	public UserManager() {
		emf = Persistence.createEntityManagerFactory("OnlineShop");
	}

	public void create(User user) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
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
	}

	public User edit(User user, String name, String surname, String phone, String address, String email,
			String newPassword) {
		EntityManager em = emf.createEntityManager();
		user = em.merge(user);
		em.getTransaction().begin();
		user.setFirstName(name);
		user.setLastName(surname);
		user.setPhone(phone);
		user.setAddress(address);
		user.setEmail(email);
		if (newPassword != null && !newPassword.isEmpty())
			user.setPassword(newPassword);
		em.getTransaction().commit();
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		List<User> resultado = new ArrayList<>();
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createNamedQuery("User.findAll", User.class);
			resultado = (List<User>) query.getResultList();
		} finally {
			em.close();
		}
		return resultado;

	}

	@SuppressWarnings("unchecked")
	public List<User> findAllType(String type) {
		List<User> resultado = new ArrayList<>();
		EntityManager em = emf.createEntityManager();
		try {
			Query query = em.createQuery("SELECT u " + " FROM User u " + " WHERE u.type = '" + type + "'");
			resultado = (List<User>) query.getResultList();
		} finally {
			em.close();
		}
		return resultado;

	}

	public User findById(int id) {
		User resultado;
		EntityManager em = emf.createEntityManager();
		try {
			resultado = em.find(User.class, id);
		} finally {
			em.close();
		}
		return resultado;
	}

	public User findById(String email) {
		EntityManager em = emf.createEntityManager();

		Query query = em.createQuery("SELECT u " + " FROM User u " + " WHERE u.email = '" + email + "'");
		return query.getResultList().size() == 0 ? null : (User) query.getResultList().get(0);
	}

	public void delete(User user) {
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
}
