package utils;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Part;

public class Image {

	/**
	 * if image is set then stores it and returns path to the image and also removes
	 * old image if present
	 * 
	 * @param img
	 * @return
	 */
	public static Result storeImage(Part img, String oldImgPath) {
//		if (getFileName(img).isEmpty())
//			return new Result(true, "No image was specified");

		long imgSize = img.getSize();
		int imgSizeInt = (int) imgSize;
		byte[] imageBytes = new byte[imgSizeInt];
		InputStream in = null;

		try {
			in = img.getInputStream();

			if (in != null)
				in.read(imageBytes, 0, imgSizeInt);
			else
				return new Result(false, "Cannot read image due to null input stream");
		} catch (IOException e) {
			return new Result(false, "Cannot read image");
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				return new Result(false, "Cannot load file properly");
			}
		}

		Result saveRes = saveImageOnDrive(imageBytes);

		if (saveRes.success) {
			Result delOldImgRes = deleteOldImage(oldImgPath);

			if (delOldImgRes.success)
				return saveRes;
			else
				return delOldImgRes;
		} else
			return saveRes;
	}

	private static Result deleteOldImage(String oldImgPath) {
		// TODO Auto-generated method stub
		return null;
	}

	private static Result saveImageOnDrive(byte[] imageBytes) {
		// TODO Auto-generated method stub
		return null;
	}
}
