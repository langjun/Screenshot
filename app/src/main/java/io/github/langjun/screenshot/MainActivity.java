package io.github.langjun.screenshot;

import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	private FileChangedObserver mFileChangedObserver;
	private FileChangedObserver.IOnFileChangedListener mFileChangedListener;

	private static final String PATH =
			Environment.getExternalStorageDirectory() + "/DCIM/Screenshots/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();
		mFileChangedObserver.startWatching();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mFileChangedObserver.stopWatching();
	}

	private void init() {
		mFileChangedObserver = new FileChangedObserver(PATH, FileObserver.CLOSE_WRITE);
		mFileChangedListener = new FileChangedObserver.IOnFileChangedListener() {
			@Override
			public void onFileChanged(Uri uri) {
				Log.i("TAG", "onFileChanged Uri = " + uri.getPath());
			}
		};
		mFileChangedObserver.setFileChangedListener(mFileChangedListener);
	}
}
