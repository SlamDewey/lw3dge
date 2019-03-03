package graphics;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import components.math.Vector2f;
import components.math.Vector3f;
import graphics.models.RawModel;

public class OBJLoader {
	public static RawModel loadObjModel(String filename, Loader loader) {
		InputStream is = null;
		is = Class.class.getResourceAsStream(filename);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			System.err.println("Couldn't load file: " + filename);
			e1.printStackTrace();
		}
		String line;
		List<Vector3f> vertices = new ArrayList<Vector3f>();
		List<Vector2f> textures = new ArrayList<Vector2f>();
		List<Vector3f> normals = new ArrayList<Vector3f>();
		List<Integer> indices = new ArrayList<Integer>();
		float[] verticesA = null;
		float[] normalsA = null;
		float[] texturesA = null;
		int[] indicesA = null;
		try {
			while (true) {
				line = reader.readLine();
				String[] currentLine = line.split(" ");
				if (line.startsWith("v ")) {
					Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					vertices.add(vertex);
				} else if (line.startsWith("vt ")) {
					Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]));
					textures.add(texture);
				} else if (line.startsWith("vn ")) {
					Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]), Float.parseFloat(currentLine[2]),
							Float.parseFloat(currentLine[3]));
					normals.add(normal);
				} else if (line.startsWith("f ")) {
					texturesA = new float[vertices.size() * 2];
					normalsA = new float[vertices.size() * 3];
					break;
				}
			}
			while (line != null) {
				if (!line.startsWith("f ")) {
					line = reader.readLine();
					continue;
				}
				String[] currentLine = line.split(" ");
				String[] vertex1 = currentLine[1].split("/");
				String[] vertex2 = currentLine[2].split("/");
				String[] vertex3 = currentLine[3].split("/");
				processVertex(vertex1, indices, textures, normals, texturesA, normalsA);
				processVertex(vertex2, indices, textures, normals, texturesA, normalsA);
				processVertex(vertex3, indices, textures, normals, texturesA, normalsA);
				line = reader.readLine();
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		verticesA = new float[vertices.size() * 3];
		indicesA = new int[indices.size()];
		int vertexPointer = 0;
		for (Vector3f vertex : vertices) {
			verticesA[vertexPointer++] = vertex.x;
			verticesA[vertexPointer++] = vertex.y;
			verticesA[vertexPointer++] = vertex.z;
		}
		for (int i = 0; i < indices.size(); i++) {
			indicesA[i] = indices.get(i);
		}
		return loader.loadToVAO(verticesA, texturesA, normalsA, indicesA);
	}

	private static void processVertex(String[] vertexData, List<Integer> indices, List<Vector2f> textures,
			List<Vector3f> normals, float[] textureArray, float[] normalsArray) {
		int currentVertexPointer = Integer.parseInt(vertexData[0]) - 1;
		indices.add(currentVertexPointer);
		Vector2f currentTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
		textureArray[currentVertexPointer * 2] = currentTex.x;
		textureArray[currentVertexPointer * 2 + 1] = 1 - currentTex.y;
		Vector3f currentNorm = normals.get(Integer.parseInt(vertexData[2]) - 1);
		normalsArray[currentVertexPointer * 3] = currentNorm.x;
		normalsArray[currentVertexPointer * 3 + 1] = currentNorm.y;
		normalsArray[currentVertexPointer * 3 + 2] = currentNorm.z;
	}
}
