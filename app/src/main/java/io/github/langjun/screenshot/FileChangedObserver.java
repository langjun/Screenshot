package io.github.langjun.screenshot;

import android.net.Uri;
import android.os.FileObserver;
import android.util.Log;

import java.io.File;

/**
 * @author langjun
 * @date 16/5/9 17:18
 */
public class FileChangedObserver extends FileObserver {
	private final String TAG = "FileChangedObserver";

	private IOnFileChangedListener mListener;

	public FileChangedObserver(String path) {
		super(path);
	}

	public FileChangedObserver(String path, int mask) {
		super(path, mask);
	}

	@Override
	public void onEvent(int event, String path) {

		if (path == null) {
			return;
		}

		File file = new File(path);
		Uri uri = null;

		try {
			uri = Uri.fromFile(file);
		} catch (NullPointerException e) {
			Log.e(TAG, e.toString());
		}

		if (mListener != null) {
			mListener.onFileChanged(uri);
		}
	}

	public void setFileChangedListener(IOnFileChangedListener listener) {
		this.mListener = listener;
	}

	public interface IOnFileChangedListener {
		void onFileChanged(Uri uri);
	}
}
