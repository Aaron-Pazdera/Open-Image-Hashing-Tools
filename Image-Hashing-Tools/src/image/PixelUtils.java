package image;

import java.util.Arrays;

import image.implementations.GreyscaleImage;

public class PixelUtils {

	public static void assertNotNull(Object... objects) throws IllegalArgumentException {
		if (objects == null) { throw new IllegalArgumentException("Array of objects is null."); }

		for (int i = 0; i < objects.length; i++) {
			if (objects[i] == null) { throw new IllegalArgumentException("Argument '" + i + "' is null."); }
		}
	}

	public static void assertNotNull(String[] labels, Object... objects) throws IllegalArgumentException {
		if (labels.length != objects.length)
			throw new IllegalArgumentException("You must provide the same number of labels as objects.");
		try {
			PixelUtils.assertNotNull(objects);
		} catch (IllegalArgumentException e) {
			String message = e.getMessage();
			if (!message.contains("'")) throw e;
			int label = Integer.parseInt(message.split("'")[1]);
			throw new IllegalArgumentException(labels[label] + " is null.");
		}
	}

	public static void assertNotNull(String label, Object object) {
		if (object == null) throw new IllegalArgumentException(label + "is null.");
	}

	public static int safeSquare(int a) throws ArithmeticException {
		return safeMult(a, a);
	}

	public static int safeMult(int a, int b) throws ArithmeticException {
		if (b == 0) { return 0; }

		int product = a * b;
		if (a == product / b) {
			return product;
		} else {
			throw new ArithmeticException(
					"Integer multiplication would overflow or underflow on safeMult(" + a + "," + b + ").");
		}
	}

	public static int safeMult(int... a) {
		if (a == null) { throw new IllegalArgumentException("Null not allowed."); }
		int product = a.length == 0 ? 0 : 1;
		for (int i = 0; i < a.length; i++) {
			product = safeMult(product, a[i]);
		}

		return product;
	}

	public static int safeAdd(int a, int b) throws ArithmeticException {
		int r = a + b;
		if (((a & b & ~r) | (~a & ~b & r)) < 0)
			throw new ArithmeticException("int addition overflow or underflow on safeAdd(" + a + ", " + b + ").");
		return r;
	}

	public static int[] byteArrayToInt(byte[] arr) {
		int[] intArr = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			intArr[i] = arr[i] & 0xff;
		}
		return intArr;
	}

	public static byte[] intArrayToByte(int[] arr) {
		byte[] byteArr = new byte[arr.length];
		for (int i = 0; i < arr.length; i++) {
			byteArr[i] = (byte) arr[i];
		}
		return byteArr;
	}

	public static int[] floatArrayToInt(float[] arr) {
		int[] intArr = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			intArr[i] = (int) arr[i];
		}
		return intArr;
	}

	public static int[][] array1dToArray2d(int[] arr, int x, int y) {
		int[][] array2d = new int[y][x];
		int index = 0;
		for (int i = 0; i < array2d.length; i++) {
			for (int j = 0; j < array2d[i].length; j++) {
				array2d[i][j] = arr[index];
				index++;
			}
		}
		return array2d;
	}

	public static byte[][] array1dToArray2d(byte[] arr, int x, int y) {
		byte[][] array2d = new byte[y][x];
		int index = 0;
		for (int i = 0; i < array2d.length; i++) {
			for (int j = 0; j < array2d[i].length; j++) {
				array2d[i][j] = arr[index];
				index++;
			}
		}
		return array2d;
	}

	public static float[][] array1dToArray2d(float[] arr, int x, int y) {
		float[][] array2d = new float[y][x];
		int index = 0;
		for (int i = 0; i < array2d.length; i++) {
			for (int j = 0; j < array2d[i].length; j++) {
				array2d[i][j] = arr[index];
				index++;
			}
		}
		return array2d;
	}

	public static int[] array2dToArray1d(int[][] arr, int x, int y) {
		// arr[x][y]
		int[] array1d = new int[x * y];
		for (int i = 0; i < x; i++) {
			int[] currentArray = arr[i];
			if (currentArray.length != y) {
				throw new IllegalArgumentException("All subarrays of pixels must be of length equal to the width.");
			}

			for (int j = 0; j < y; j++) {
				array1d[i * y + j] = currentArray[j];
			}
		}
		return array1d;
	}

	public static byte[] array2dToArray1d(byte[][] arr, int x, int y) {
		// arr[x][y]
		byte[] array1d = new byte[x * y];
		for (int i = 0; i < x; i++) {
			byte[] currentArray = arr[i];
			if (currentArray.length != y) {
				throw new IllegalArgumentException("All subarrays of pixels must be of length equal to the width.");
			}

			for (int j = 0; j < y; j++) {
				array1d[i * y + j] = currentArray[j];
			}
		}
		return array1d;
	}

	public static float[] array2dToArray1d(float[][] arr, int x, int y) {
		float[] array1d = new float[x * y];
		for (int i = 0; i < x; i++) {
			float[] currentArray = arr[i];
			if (currentArray.length != y) {
				throw new IllegalArgumentException("All subarrays of pixels must be of length equal to the width.");
			}

			for (int j = 0; j < y; j++) {
				array1d[i * y + j] = currentArray[j];
			}
		}
		return array1d;
	}

	public static int[][] transpose(int[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;

		int[][] transposedMatrix = new int[n][m];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				transposedMatrix[x][y] = matrix[y][x];
			}
		}
		return transposedMatrix;
	}

	public static byte[][] transpose(byte[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;

		byte[][] transposedMatrix = new byte[n][m];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < m; y++) {
				transposedMatrix[x][y] = matrix[y][x];
			}
		}
		return transposedMatrix;
	}

	public static int[] transpose1dAs2d(int[] arr, int oldWidth, int oldHeight) {
		if (arr.length != oldWidth * oldHeight) { throw new IllegalArgumentException(); }

		int[] transposed = new int[arr.length];
		for (int offset = 0; offset < arr.length; offset++) {
			int x = offset % oldWidth;
			int y = offset / oldWidth;
			// Use x as y, and vice-versa
			transposed[x * oldWidth + y] = arr[offset];
		}

		return transposed;
	}

	public static byte[] transpose1dAs2d(byte[] arr, int oldWidth, int oldHeight) {
		if (arr.length != oldWidth * oldHeight) { throw new IllegalArgumentException(); }

		byte[] transposed = new byte[arr.length];
		for (int offset = 0; offset < arr.length; offset++) {
			int x = offset % oldWidth;
			int y = offset / oldWidth;
			// Use x as y, and vice-versa
			transposed[x * oldWidth + y] = arr[offset];
		}

		return transposed;
	}

	public static byte[] flipHorizontal(byte[] imagePixels, int width, int height) {
		byte[] pixels = new byte[imagePixels.length];
		int row, column;
		for (int i = 0; i < imagePixels.length; i++) {
			row = (i / width);
			column = (i % width);
			pixels[row * width + column] = imagePixels[(row + 1) * width - column - 1];
		}

		return pixels;
	}

	public static byte[] flipVertical(byte[] imagePixels, int width, int height) {
		byte[] pixels = new byte[imagePixels.length];
		int row, column;
		for (int i = 0; i < imagePixels.length; i++) {
			row = (i / width);
			column = (i % width);
			pixels[row * width + column] = imagePixels[(height - row - 1) * width + column];
		}

		return pixels;
	}

	public static byte[] rotate90CW(byte[] imagePixels, int width, int height) {
		byte[] pixels = new byte[imagePixels.length];
		int row, column;
		for (int i = 0; i < imagePixels.length; i++) {
			row = (i / width);
			column = (i % width);
			pixels[row * width + column] = imagePixels[(height - 1) * width
					- (((row * width + column) % height) * width) + (row * width + column) / height];
		}

		return pixels;
	}

	public static byte[] rotate90CCW(byte[] imagePixels, int width, int height) {
		byte[] pixels = new byte[imagePixels.length];
		int row, column;
		for (int i = 0; i < imagePixels.length; i++) {
			row = (i / width);
			column = (i % width);
			pixels[row * width + column] = imagePixels[(width - 1) + (((row * width + column) % height) * width)
					- (row * width + column) / height];
		}

		return pixels;
	}

	public static byte[] rotate180(byte[] imagePixels) {
		byte[] pixels = new byte[imagePixels.length];
		for (int i = 0; i < imagePixels.length; i++) {
			pixels[i] = imagePixels[pixels.length - i - 1];
		}
		return pixels;
	}

	public static GreyscaleImage extractSubimage(byte[] subimage, int width, int height, int x1, int y1, int x2,
			int y2) {

		// This block of code is duplicated below.

		// Rearrange so that (x1, y1) represents the top-left corner and (x2, y2)
		// represents the bottom-right. That is to say, swap if the first point is
		// greater.
		int tempInt;
		if (x1 > x2) {
			tempInt = x1;
			x1 = x2;
			x2 = tempInt;
		}
		if (y1 > y2) {
			tempInt = y1;
			y1 = y2;
			y2 = tempInt;
		}

		// Make sure that the coordinates don't spill over the sides of the original.
		if (x1 < 0 || x2 > width - 1 || y1 < 0 || y2 > height - 1) {
			throw new IllegalArgumentException("Points must be inside the image. Note that indexes start at zero, "
					+ "so pixel (" + width + "," + height + ") of a " + width + "x" + height
					+ " image will actually fall just outside of the image.");
		}

		int subWidth = (x2 - x1) + 1, subHeight = (y2 - y1) + 1;
		int expectedBufSize = subWidth * subHeight;

		// Now check to make sure that the buffers are the right size.
		if (expectedBufSize != subimage.length) {
			throw new IllegalArgumentException("Subimage buffer is not the right size. Got size: " + subimage.length
					+ ". Expected:" + subWidth + " x " + subHeight + ".");
		}

		// End of duplicated block

		// Now extract the actual subimage.
		tempInt = 0;
		byte[] subImage = new byte[subWidth * subHeight];
		for (int y = y1; y <= y2; y++) {
			for (int x = x1; x <= x2; x++) {
				subImage[tempInt++] = subimage[y * width + x];
			}
		}

		return new GreyscaleImage(subImage, subWidth, subHeight);
	}

	public static byte[] emplaceSubimage(byte[] imagePixels, int width, int height, byte[] subimage, int x1, int y1,
			int x2, int y2) {

		// Rearrange so that (x1, y1) represents the top-left corner and (x2, y2)
		// represents the bottom-right. That is to say, swap if the first point is
		// greater.
		int tempInt;
		if (x1 > x2) {
			tempInt = x1;
			x1 = x2;
			x2 = tempInt;
		}
		if (y1 > y2) {
			tempInt = y1;
			y1 = y2;
			y2 = tempInt;
		}

		// Make sure that the coordinates don't spill over the sides of the original.
		if (x1 < 0 || x2 > width - 1 || y1 < 0 || y2 > height - 1) {
			throw new IllegalArgumentException("Points must be inside the image. Note that indexes start at zero, "
					+ "so pixel (" + width + "," + height + ") of a " + width + "x" + height
					+ " image will actually fall just outside of the image.");
		}

		int subWidth = (x2 - x1) + 1, subHeight = (y2 - y1) + 1;
		int expectedBufSize = subWidth * subHeight;

		// Now check to make sure that the buffers are the right size.
		if (expectedBufSize != subimage.length) {
			throw new IllegalArgumentException("Subimage buffer is not the right size. Got size: " + subimage.length
					+ ". Expected:" + subWidth + " x " + subHeight + ".");
		}

		byte[] newPixels = Arrays.copyOf(imagePixels, imagePixels.length);
		tempInt = 0;

		// Now emplace the actual subimage.
		for (int y = y1; y <= y2; y++) {
			for (int x = x1; x <= x2; x++) {
				newPixels[y * width + x] = subimage[tempInt++];
			}
		}

		return newPixels;
	}

	public static byte[] emplaceSubimageAlphaMask(byte[] imagePixels, int width, int height, byte[] subimage, int x1,
			int y1, int x2, int y2, byte[] subAlphaMask) {

		// Rearrange so that (x1, y1) represents the top-left corner and (x2, y2)
		// represents the bottom-right. That is to say, swap if the first point is
		// greater.
		int tempInt;
		if (x1 > x2) {
			tempInt = x1;
			x1 = x2;
			x2 = tempInt;
		}
		if (y1 > y2) {
			tempInt = y1;
			y1 = y2;
			y2 = tempInt;
		}

		// Make sure that the coordinates don't spill over the sides of the original.
		if (x1 < 0 || x2 > width - 1 || y1 < 0 || y2 > height - 1) {
			throw new IllegalArgumentException("Points must be inside the image. Note that indexes start at zero, "
					+ "so pixel (" + width + "," + height + ") of a " + width + "x" + height
					+ " image will actually fall just outside of the image.");
		}

		int subWidth = (x2 - x1) + 1, subHeight = (y2 - y1) + 1;
		int expectedBufSize = subWidth * subHeight;

		// Now check to make sure that the buffers are the right size.
		if (expectedBufSize != subimage.length) {
			throw new IllegalArgumentException("Subimage buffer is not the right size. Got size: " + subimage.length
					+ ". Expected:" + subWidth + " x " + subHeight + ".");
		}
		if (expectedBufSize != subAlphaMask.length) {
			throw new IllegalArgumentException("Alpha mask buffer is not the right size. Got size: "
					+ subAlphaMask.length + ". Expected:" + subWidth + " x " + subHeight + ".");
		}

		byte[] newPixels = Arrays.copyOf(imagePixels, imagePixels.length);

		// Now emplace the actual subimage.
		for (int y = 0; y < subHeight; y++) {
			for (int x = 0; x < subWidth; x++) {
				int idx = (y + y1) * width + (x + x2);
				int alphaidx = y * subWidth + x;
				if (subAlphaMask[alphaidx] != 0) {
					newPixels[idx] = subimage[alphaidx];
				}
			}
		}

		return newPixels;
	}

}
