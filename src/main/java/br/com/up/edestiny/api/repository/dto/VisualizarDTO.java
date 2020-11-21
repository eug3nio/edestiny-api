package br.com.up.edestiny.api.repository.dto;

import java.util.List;
import java.util.Map;

public class VisualizarDTO {

	private LocationDTO origin;
	private LocationDTO destination;
	private List<Map<String, LocationDTO>> waypoints;

	public LocationDTO getOrigin() {
		return origin;
	}

	public void setOrigin(LocationDTO origin) {
		this.origin = origin;
	}

	public LocationDTO getDestination() {
		return destination;
	}

	public void setDestination(LocationDTO destination) {
		this.destination = destination;
	}

	public List<Map<String, LocationDTO>> getWaypoints() {
		return waypoints;
	}

	public void setWaypoints(List<Map<String, LocationDTO>>	 waypoints) {
		this.waypoints = waypoints;
	}
}
