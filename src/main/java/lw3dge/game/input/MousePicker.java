package lw3dge.game.input;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Vector2f;
import lw3dge.components.math.Vector3f;
import lw3dge.components.math.Vector4f;
import lw3dge.engine.Config;
import lw3dge.game.cameras.Camera;
import lw3dge.game.terrain.Terrain;

public class MousePicker {

	private static final int RECURSION_COUNT = 50;
	private static final float RAY_RANGE = 600;

	private Vector3f currentRay = new Vector3f();

	private Matrix4f projectionMatrix;
	private Matrix4f viewMatrix;
	private Camera camera;
	
	private Terrain terrain;
	public static Vector3f currentTerrainPoint = new Vector3f();

	public MousePicker(Camera cam, Matrix4f projection, Terrain terrain) {
		camera = cam;
		projectionMatrix = projection;
		viewMatrix = camera.getViewMatrix(); 
		this.terrain = terrain;
	}

	public Vector3f getCurrentRay() {
		return currentRay;
	}

	public void update() {
		viewMatrix = camera.getViewMatrix();
		currentRay = calculateMouseRay();
		if (intersectionInRange(0, RAY_RANGE, currentRay)) {
			Vector3f res = binarySearch(0, 0, RAY_RANGE, currentRay);
			currentTerrainPoint = (res == null ? currentTerrainPoint : res);
		}
	}

	private Vector3f calculateMouseRay() {
		float mouseX = Cursor.position.x;
		float mouseY = Cursor.position.y;
		Vector2f normalizedCoords = getNormalisedDeviceCoordinates(mouseX, mouseY);
		Vector4f clipCoords = new Vector4f(normalizedCoords.x, normalizedCoords.y, -1.0f, 1.0f);
		Vector4f eyeCoords = toEyeCoords(clipCoords);
		Vector3f worldRay = toWorldCoords(eyeCoords);
		return worldRay;
	}

	private Vector3f toWorldCoords(Vector4f eyeCoords) {
		Matrix4f invertedView = Matrix4f.invert(viewMatrix, null);
		Vector4f rayWorld = Matrix4f.transform(invertedView, eyeCoords, null);
		Vector3f mouseRay = new Vector3f(rayWorld.x, rayWorld.y, rayWorld.z);
		mouseRay.normalise();
		return mouseRay;
	}

	private Vector4f toEyeCoords(Vector4f clipCoords) {
		Matrix4f invertedProjection = Matrix4f.invert(projectionMatrix, null);
		Vector4f eyeCoords = Matrix4f.transform(invertedProjection, clipCoords, null);
		return new Vector4f(eyeCoords.x, eyeCoords.y, -1f, 0f);
	}

	private Vector2f getNormalisedDeviceCoordinates(float mouseX, float mouseY) {
		float x = (2.0f * mouseX) / Config.WIDTH - 1f;
		float y = (2.0f * mouseY) / Config.HEIGHT - 1f;
		return new Vector2f(x, -y);
	}
	
	//**********************************************************
	
	private Vector3f getPointOnRay(Vector3f ray, float distance) {
		Vector3f camPos = camera.transform.position;
		Vector3f start = new Vector3f(camPos.x, camPos.y, camPos.z);
		Vector3f scaledRay = new Vector3f(ray.x * distance, ray.y * distance, ray.z * distance);
		return Vector3f.add(start, scaledRay, null);
	}
	
	private Vector3f binarySearch(int count, float start, float finish, Vector3f ray) {
		float half = start + ((finish - start) / 2f);
		if (count >= RECURSION_COUNT) {
			Vector3f endPoint = getPointOnRay(ray, half);
			Terrain terrain = getTerrain(endPoint.getX(), endPoint.getZ());
			if (terrain != null) {
				return endPoint;
			} else {
				return null;
			}
		}
		if (intersectionInRange(start, half, ray)) {
			return binarySearch(count + 1, start, half, ray);
		} else {
			return binarySearch(count + 1, half, finish, ray);
		}
	}

	private boolean intersectionInRange(float start, float finish, Vector3f ray) {
		Vector3f startPoint = getPointOnRay(ray, start);
		Vector3f endPoint = getPointOnRay(ray, finish);
		if (!isUnderGround(startPoint) && isUnderGround(endPoint)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isUnderGround(Vector3f testPoint) {
		Terrain terrain = getTerrain(testPoint.getX(), testPoint.getZ());
		float height = 0;
		if (terrain != null) {
			//height = terrain.getHeightOfTerrain(testPoint.getX(), testPoint.getZ());
			height = 0;
		}
		if (testPoint.y < height) {
			return true;
		} else {
			return false;
		}
	}

	private Terrain getTerrain(float worldX, float worldZ) {
		return terrain;
	}

}
