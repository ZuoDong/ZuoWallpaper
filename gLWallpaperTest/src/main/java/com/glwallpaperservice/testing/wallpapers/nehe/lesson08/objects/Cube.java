package com.glwallpaperservice.testing.wallpapers.nehe.lesson08.objects;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import net.markguerra.android.glwallpapertest.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import com.glwallpaperservice.testing.wallpapers.nehe.BufferUtils;
import com.glwallpaperservice.testing.wallpapers.nehe.lesson08.GLImage;

/**
 * This class is an object representation of 
 * a Cube containing the vertex information,
 * texture coordinates, the vertex indices
 * and drawing functionality, which is called 
 * by the renderer.
 * 
 * @author Savas Ziplies (nea/INsanityDesign)
 */
public class Cube {

	private float one = 1.0f;
	/** The buffer holding the vertices */
	private FloatBuffer vertexBuffer;
	/** The buffer holding the texture coordinates */
	private FloatBuffer textureBuffer;
	/** The buffer holding the indices */
	private ByteBuffer indexBuffer;
	/** The buffer holding the normals */
	private FloatBuffer normalBuffer;

	/** Our texture pointer */
//	private int[] textures = new int[3];
	private int[] textures = new int[6];

	/** The initial vertex definition */
	// 正方体顶点
	private float[] vertices = {
			-one,-one,one,
			one,-one,one,
			one,one,one,
			-one,one,one,

			-one,-one,-one,
			-one,one,-one,
			one,one,-one,
			one,-one,-one,

			-one,one,-one,
			-one,one,one,
			one,one,one,
			one,one,-one,

			-one,-one,-one,
			one,-one,-one,
			one,-one,one,
			-one,-one,one,

			one,-one,-one,
			one,one,-one,
			one,one,one,
			one,-one,one,

			-one,-one,-one,
			-one,-one,one,
			-one,one,one,
			-one,one,-one,
	};

	/** The initial normals for the lighting calculations */	
	private float normals[] = {
						//Normals
						0.0f, 0.0f, 1.0f, 						
						0.0f, 0.0f, -1.0f, 
						0.0f, 1.0f, 0.0f, 
						0.0f, -1.0f, 0.0f, 
						
						0.0f, 0.0f, 1.0f, 
						0.0f, 0.0f, -1.0f, 
						0.0f, 1.0f, 0.0f, 
						0.0f, -1.0f, 0.0f,
						
						0.0f, 0.0f, 1.0f, 
						0.0f, 0.0f, -1.0f, 
						0.0f, 1.0f, 0.0f, 
						0.0f, -1.0f, 0.0f,
						
						0.0f, 0.0f, 1.0f, 
						0.0f, 0.0f, -1.0f, 
						0.0f, 1.0f, 0.0f, 
						0.0f, -1.0f, 0.0f,
						
						0.0f, 0.0f, 1.0f, 
						0.0f, 0.0f, -1.0f, 
						0.0f, 1.0f, 0.0f, 
						0.0f, -1.0f, 0.0f,
						
						0.0f, 0.0f, 1.0f, 
						0.0f, 0.0f, -1.0f, 
						0.0f, 1.0f, 0.0f, 
						0.0f, -1.0f, 0.0f,
											};

	/** The initial texture coordinates (u, v) */
	//纹理点
	private float[] texture = {
			1,0,0,0,0,1,1,1,
			0,0,0,1,1,1,1,0,
			1,1,1,0,0,0,0,1,
			0,1,1,1,1,0,0,0,
			0,0,0,1,1,1,1,0,
			1,0,0,0,0,1,1,1,
	};

	/** The initial indices definition */
//	private byte indices[] = {
//						// Faces definition
//						0, 1, 3, 0, 3, 2, 		// Face front
//						4, 5, 7, 4, 7, 6, 		// Face right
//						8, 9, 11, 8, 11, 10, 	// ...
//						12, 13, 15, 12, 15, 14,
//						16, 17, 19, 16, 19, 18,
//						20, 21, 23, 20, 23, 22,
//												};

	ByteBuffer indices1 = ByteBuffer.wrap(new byte[] {
			0,1,3,2,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
	});
	ByteBuffer indices2 = ByteBuffer.wrap(new byte[] {
			0,0,0,0,
			4,5,7,6,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0, });
	ByteBuffer indices3 = ByteBuffer.wrap(new byte[] {
			0,0,0,0,
			0,0,0,0,
			8,9,11,10,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,  });
	ByteBuffer indices4 = ByteBuffer.wrap(new byte[] {
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			12,13,15,14,
			0,0,0,0,
			0,0,0,0,  });
	ByteBuffer indices5 = ByteBuffer.wrap(new byte[] {
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			16,17,19,18,
			0,0,0,0, });
	ByteBuffer indices6 = ByteBuffer.wrap(new byte[] {
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			0,0,0,0,
			20,21,23,22, });

	/**
	 * The Cube constructor.
	 * 
	 * Initiate the buffers.
	 */
	public Cube() {
		//
//		ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
//		byteBuf.order(ByteOrder.nativeOrder());
//		vertexBuffer = byteBuf.asFloatBuffer();
//		vertexBuffer.put(vertices);
//		vertexBuffer.position(0);
//
//		//
//		byteBuf = ByteBuffer.allocateDirect(texture.length * 4);
//		byteBuf.order(ByteOrder.nativeOrder());
//		textureBuffer = byteBuf.asFloatBuffer();
//		textureBuffer.put(texture);
//		textureBuffer.position(0);
//
//		//
//		byteBuf = ByteBuffer.allocateDirect(normals.length * 4);
//		byteBuf.order(ByteOrder.nativeOrder());
//		normalBuffer = byteBuf.asFloatBuffer();
//		normalBuffer.put(normals);
//		normalBuffer.position(0);
//
//		//
//		indexBuffer = ByteBuffer.allocateDirect(indices.length);
//		indexBuffer.put(indices);
//		indexBuffer.position(0);

		vertexBuffer = BufferUtils.getFloatBuffer(vertices);
		textureBuffer = BufferUtils.getFloatBuffer(texture);
		normalBuffer = BufferUtils.getFloatBuffer(normals);
	}

	/**
	 * The object own drawing function.
	 * Called from the renderer to redraw this instance
	 * with possible changes in values.
	 * 
	 * @param gl - The GL Context
	 * @param filter - Which texture filter to be used
	 */
	public void draw(GL10 gl, int filter) {
		//Bind the texture according to the set texture filter
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[filter]);

		//Enable the vertex, texture and normal state
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

		//Set the face rotation
		gl.glFrontFace(GL10.GL_CCW);
		
//		//Point to our buffers
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		gl.glNormalPointer(GL10.GL_FLOAT, 0, normalBuffer);
//
//		//Draw the vertices as triangles, based on the Index Buffer information
//		gl.glDrawElements(GL10.GL_TRIANGLES, indices.length, GL10.GL_UNSIGNED_BYTE, indexBuffer);

		BindElementsWithTexture(gl);
		
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}

	/**
	 * Load the textures
	 * 
	 * @param gl - The GL Context
	 * @param context - The Activity context
	 */
	public void loadGLTexture(GL10 gl, Context context) {
		IntBuffer textureBuffer = IntBuffer.allocate(6);
		gl.glGenTextures(6, textureBuffer);
		textures = textureBuffer.array();

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap1, 0);
		Log.i("zuo", GLImage.mBitmap1 == null?"null":GLImage.mBitmap1.toString());
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap2, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[2]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap3, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[3]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap4, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap5, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[5]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLImage.mBitmap6, 0);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_S,GL10.GL_REPEAT);
//        gl.glTexParameterf(GL10.GL_TEXTURE_2D,GL10.GL_TEXTURE_WRAP_T,GL10.GL_REPEAT);

		//不能回收
//		GLImage.mBitmap1.recycle();
//		GLImage.mBitmap2.recycle();
//		GLImage.mBitmap3.recycle();
//		GLImage.mBitmap4.recycle();
//		GLImage.mBitmap5.recycle();
//		GLImage.mBitmap6.recycle();

		//Get the texture from the Android resource directory
//		InputStream is = context.getResources().openRawResource(R.drawable.meizi1);
//		Bitmap bitmap1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
//		Bitmap bitmap = null;
//		try {
//			//BitmapFactory is an Android graphics utility for images
//			bitmap = BitmapFactory.decodeStream(is);
//
//		} finally {
//			//Always clear and close
//			try {
//				is.close();
//				is = null;
//			} catch (IOException e) {
//			}
//		}
//
//		//Generate there texture pointer
//		gl.glGenTextures(3, textures, 0);
//
//		//Create Nearest Filtered Texture and bind it to texture 0
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
//		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		//Create Linear Filtered Texture and bind it to texture 1
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
//		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
//
//		//Create mipmapped textures and bind it to texture 2
//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[2]);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR_MIPMAP_NEAREST);
//		/*
//		 * This is a change to the original tutorial, as buildMipMap does not exist anymore
//		 * in the Android SDK.
//		 *
//		 * We check if the GL context is version 1.1 and generate MipMaps by flag.
//		 * Otherwise we call our own buildMipMap implementation
//		 */
//		if(gl instanceof GL11) {
//			gl.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_GENERATE_MIPMAP, GL11.GL_TRUE);
//			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
//
//		//
//		} else {
//			buildMipmap(gl, bitmap);
//		}
		
		//Clean up
//		bitmap.recycle();
	}
	
	/**
	 * Our own MipMap generation implementation.
	 * Scale the original bitmap down, always by factor two,
	 * and set it as new mipmap level.
	 * 
	 * Thanks to Mike Miller (with minor changes)!
	 * 
	 * @param gl - The GL Context
	 * @param bitmap - The bitmap to mipmap
	 */
	private void buildMipmap(GL10 gl, Bitmap bitmap) {
		//
		int level = 0;
		//
		int height = bitmap.getHeight();
		int width = bitmap.getWidth();

		//
		while(height >= 1 || width >= 1) {
			//First of all, generate the texture from our bitmap and set it to the according level
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, level, bitmap, 0);
			
			//
			if(height == 1 || width == 1) {
				break;
			}

			//Increase the mipmap level
			level++;

			//
			height /= 2;
			width /= 2;
			Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap, width, height, true);
			
			//Clean up
			bitmap.recycle();
			bitmap = bitmap2;
		}
	}

	// 绘制四边形和Texture元素绑定
	public void BindElementsWithTexture(GL10 gl) {
		//绘制第一个面，使用第一个纹理，对应面的顶点索引为indices1
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 4, GL10.GL_UNSIGNED_BYTE,
				indices1);
		//绘制第二个面，使用第二个纹理，对应面的顶点索引为indices2
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[1]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 8, GL10.GL_UNSIGNED_BYTE,
				indices2);
		//绘制第三个面，使用第三个纹理，对应面的顶点索引为indices3
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[2]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 12, GL10.GL_UNSIGNED_BYTE,
				indices3);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[3]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 16, GL10.GL_UNSIGNED_BYTE,
				indices4);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[4]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 20, GL10.GL_UNSIGNED_BYTE,
				indices5);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[5]);
		gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, 24, GL10.GL_UNSIGNED_BYTE,
				indices6);
	}

}
