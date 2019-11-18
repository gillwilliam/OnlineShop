package request_handlers;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Image;

public class DisplayImage implements RequestHandler {

	@Override
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Got request");
		Image image;
		int id = Integer.parseInt(request.getParameter("id"));
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnlineShop");
		EntityManager em = emf.createEntityManager();
		try {
			image = em.find(Image.class, id);
		} finally {
			em.close();
		}
		System.out.println("FOUND IMAGE " + image.getId());
		response.setContentType("image/png");
		response.getOutputStream().write(image.getImage());
	}

}
