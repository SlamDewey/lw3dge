package game.layers;

import java.util.List;

import graphics.entities.GraphicalEntity;

public class Layer {
	
	private int id;
	private boolean isEnabled = false;
	public List<GraphicalEntity> entities;
	
	/**
	 * Generates a new Layer with the specified ID
	 * 
	 * @param id The ID to reference this layer in a LayerStack
	 */
	public Layer(int id) {
		this.id = id;
		this.isEnabled = true;
	}
	
	/**
	 * @return the ID of this layer
	 */
	public int getID() {
		return id;
	}
	/**
	 * @return the enabled state of this layer
	 */
	public boolean isEnabled() {
		return isEnabled;
	}
	/**
	 * A helper function to set the enabled state to true for this layer
	 */
	public void enable() {
		this.isEnabled = true;
	}
	/**
	 * A helper function to set the enabled state to false for this layer
	 */
	public void disable() {
		this.isEnabled = false;
	}
	
	/*
	 * Methods a able to be overwritten by subclasses
	 */
	public void onAttach() {}
	public void onDetach() {}
	public void onTick() {}
	public void onRender() {}
}
