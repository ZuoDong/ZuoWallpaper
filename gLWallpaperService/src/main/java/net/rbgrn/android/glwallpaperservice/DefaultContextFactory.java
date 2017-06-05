package net.rbgrn.android.glwallpaperservice;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * 作者：zuo
 * 时间：2017/5/19:14:55
 */
class DefaultContextFactory implements GLSurfaceView.EGLContextFactory {

	public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig config) {
		return egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT, null);
	}

	public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
		egl.eglDestroyContext(display, context);
	}
}
