package se.enbohms.hhcib.facade;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import se.enbohms.hhcib.entity.Category;
import se.enbohms.hhcib.entity.Subject;
import se.enbohms.hhcib.service.api.CrudService;

/**
 * Web socket facade which is responsible for supplying random subject from the
 * database to a connection peer.
 * 
 */
@Singleton
@ServerEndpoint("/webSocketSliderFacade")
public class CategoryWebSocketFacade {

	private final static Logger LOG = Logger
			.getLogger(CategoryWebSocketFacade.class.getName());

	private static Set<Session> peers = Collections
			.synchronizedSet(new HashSet<Session>());

	@Inject
	private CrudService crudService;

	@OnOpen
	public void onOpen(Session peer) {
		peers.add(peer);
	}

	@Schedule(persistent = false, second = "*/10", minute = "*", hour = "*", info = "Time Event publisher")
	public void onTimeEvent() {
		for (Session peer : peers) {
			try {

				List<Subject> subjects = crudService.getSubjectsFor(Category
						.getRandom());
				Random random = new Random();
				peer.getBasicRemote().sendText(
						subjects.get(random.nextInt(subjects.size()))
								.getDescription());
			} catch (IOException ex) {
				LOG.warning("Failed to fetch random subject, exception is "
						+ ex);
			}
		}
	}

	@OnClose
	public void onClose(Session peer) {
		LOG.info("closing web socket connection");
		peers.remove(peer);
	}
}