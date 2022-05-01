package fr.unice.polytech.si3.qgl.smartware;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import fr.unice.polytech.si3.qgl.regatta.cockpit.ICockpit;
import fr.unice.polytech.si3.qgl.smartware.actions.Actions;
import fr.unice.polytech.si3.qgl.smartware.crew.Sailor;
import fr.unice.polytech.si3.qgl.smartware.game.Game;
import fr.unice.polytech.si3.qgl.smartware.game.goal.RegattaGoal;

import fr.unice.polytech.si3.qgl.smartware.crew.Captain;
import fr.unice.polytech.si3.qgl.smartware.sea.SeaEntity;
import fr.unice.polytech.si3.qgl.smartware.sea.Wind;
import fr.unice.polytech.si3.qgl.smartware.ship.Ship;
import fr.unice.polytech.si3.qgl.smartware.game.NextRound;


public class Cockpit implements ICockpit {

	Game newGame;
	ObjectMapper objectMapper;
	Captain captain;

	public void initGame(String game) {

		if (game.equals("")) return;

		objectMapper = new ObjectMapper();
		objectMapper.registerSubtypes(new NamedType(Sailor.class, "sailors"));
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);


		try {
			newGame = objectMapper.readValue(game,Game.class);
			newGame.instanciateDeck();
			newGame.fillDeck();

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		captain = new Captain(newGame);

	}

	public String nextRound(String round) {
		if (round.equals(""))
			return "";

		try {
			JsonNode jsonNode = objectMapper.readTree(round);
			NextRound nextRound = objectMapper.readValue(round, NextRound.class);
			updateShip(nextRound.getShip());
			updateWind(nextRound.getWind());
			updateSeaEntities(nextRound.getVisibleEntities());

		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		Actions actions = new Actions();

		if(newGame.getGoal() instanceof RegattaGoal){
			try {
				actions = captain.regattaHandler(actions);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
//		 newGame.print();
		return actions.toJsonString();
	}

	private void updateWind(Wind wind) {
		if(wind != null ){
			newGame.getSea().setWind(wind.getOrientation(),wind.getStrength());
		}
	}

	void updateShip(Ship ship) {
		newGame.getShip().setLife(ship.getLife());
		newGame.getShip().getPosition().setX(ship.getPosition().getX());
		newGame.getShip().getPosition().setY(ship.getPosition().getY());
		newGame.getShip().getPosition().setOrientation(ship.getPosition().getOrientation());
	}

	void updateSeaEntities(SeaEntity[] visibleEntities) throws JsonProcessingException {
		// JsonNode visibleEntities = jsonNode.get("visibleEntities");
		// Arrays.stream(visibleEntities).forEach(s -> s.getPosition().setOrientation(0));
		if (visibleEntities != null) {
			newGame.getSea().setSeaEntities(visibleEntities.clone());
			System.out.println("N SEA ENT = "+visibleEntities.length);
			//System.out.println(newGame.getSea().getSeaEntities());
		}
	}


	@Override
	public List<String> getLogs() {
		return new ArrayList<>();
	}

	public Game getNewGame() {
		return newGame;
	}

	public Captain getCaptain() {
		return captain;
	}

	public void setCaptain(Captain captain) {
		this.captain = captain;
	}
}
