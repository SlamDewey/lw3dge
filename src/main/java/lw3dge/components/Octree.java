package lw3dge.components;

import java.util.ArrayList;

import lw3dge.components.math.Vector3f;
import lw3dge.engine.Log;
import lw3dge.graphics.entities.GraphicalEntity;

class Vertex {
	private GraphicalEntity entity;
	private Vector3f position;

	public Vertex(Vector3f position, GraphicalEntity entity) {
		this.position = position;
		this.entity = entity;
	}

	public Vector3f getPosition() {
		return position;
	}

	public GraphicalEntity getEntity() {
		return entity;
	}
}

public class Octree {

	private final int NUM_CHILDREN = 8;
	private final int MAX_MEMBERS = 3;

	private boolean isDivided = false;
	private CubicBounds bounds;
	private Octree[] children;
	private Vertex[] members;

	public Octree(Vector3f center, float width) {
		bounds = new CubicBounds(center, width);
		members = new Vertex[MAX_MEMBERS];
	}

	private void divide() {
		Vector3f newCenter;
		float newWidth, newX, newY, newZ;
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				for (int z = 0; z < 2; z++) {
					newCenter = bounds.center;
					newWidth = bounds.width / 2f;
					newX = newWidth * (x == 0 ? -1 : 1);
					newY = newWidth * (y == 0 ? -1 : 1);
					newZ = newWidth * (z == 0 ? -1 : 1);
					Vector3f.sub(newCenter, new Vector3f(newX, newY, newZ), newCenter);
					children[x + y + z] = new Octree(newCenter, newWidth);
				}
			}
		}
		isDivided = true;
	}

	public ArrayList<Vertex> getPointsInBounds(CubicBounds bounds) {
		ArrayList<Vertex> mems = new ArrayList<Vertex>();
		if (!bounds.intersects(this.bounds))
			return mems;

		for (int i = 0; i < MAX_MEMBERS; i++) {
			if (members[i] == null)
				break; // end of list
			if (bounds.contains(members[i].getPosition()))
				mems.add(members[i]);
		}

		if (isDivided)
			for (int i = 0; i < NUM_CHILDREN; i++)
				mems.addAll(children[i].getPointsInBounds(bounds));

		return mems;
	}

	public boolean addVertex(Vertex v) {
		if (!bounds.contains(v.getPosition()))
			return false;
		for (int i = 0; i < MAX_MEMBERS; i++) {
			if (members[i] == null) {
				members[i] = v;
				return true;
			}
		}
		divide();
		// insert into children;
		for (int i = 0; i < NUM_CHILDREN; i++) {
			if (children[i].addVertex(v))
				return true;
		}
		Log.println(Log.LogLevel.FATAL, "OCTREE WAS NOT ABLE TO ADD VERTEX CONTAINED IN BOUNDS");
		return false;
	}

}
