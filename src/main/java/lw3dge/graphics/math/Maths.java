package lw3dge.graphics.math;

import lw3dge.components.math.Matrix4f;
import lw3dge.components.math.Vector3f;
import lw3dge.components.physics.Transform;

/**
 * A series of public static mathematics functions for use as a quick static
 * math library throughout the engine. Not to be used by Users unless you have a
 * great understanding of their usage. Otherwise just let them be.
 * 
 * @author Jared Massa
 */
public final class Maths { // its final because it doesn't matter and who
							// doesn't like writing final everywhere
	/*
	 * 
	 * NOTE: NONE OF THESE FUNCTIONS SHOULD BE USED OUTSIDE THE ENGINE UNLESS
	 * YOU KNOW EXACTLY WHAT YOU'RE USING THEM FOR. I WILL NOT EXPLAIN THESE
	 * FUNCTIONS TO DISCOURAGE OUTSIDE USAGE: THEY ARE MADE FOR VERY SPECIFIC
	 * PURPOSES.
	 * 
	 * That being said they aren't that hard to understand so feel free to read
	 * them
	 * 
	 */
	public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}

	public static Matrix4f createTransformationMatrix(Vector3f translation, Vector3f rotation, Vector3f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate(rotation.x, new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate(rotation.y, new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate(rotation.z, new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, scale.z), matrix, matrix);
		return matrix;
	}

	public static Matrix4f createViewMatrix(Transform camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate(camera.rotation.x, new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate(camera.rotation.y, new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate(camera.rotation.z, new Vector3f(0, 0, 1), viewMatrix, viewMatrix);
		Vector3f negativeCameraPos = new Vector3f();
		Vector3f.sub(new Vector3f(), camera.position, negativeCameraPos);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}

	public static int randInt(int min, int max) {
		return (min + (int) (Math.random() * ((max - min) + 1)));
	}
}