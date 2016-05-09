package io.github.langjun.screenshot;

import android.net.Uri;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
	private static final String TAG = "MainActivity";
	private static final String PATH =
			Environment.getExternalStorageDirectory() + "/DCIM/Screenshots/";

	private FileChangedObserver mFileChangedObserver;
	private FileChangedObserver.IOnFileChangedListener mFileChangedListener;

	private ImageView mImageView;
	private Handler mHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		init();

		mImageView = (ImageView) findViewById(R.id.imageView);

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
		mHandler = new Handler();

		mFileChangedObserver = new FileChangedObserver(PATH, FileObserver.CLOSE_WRITE);
		mFileChangedListener = new FileChangedObserver.IOnFileChangedListener() {
			@Override
			public void onFileChanged(final Uri uri) {
				Log.i("TAG", "onFileChanged Uri = " + uri.getPath());

				mHandler.post(new Runnable() {
					@Override
					public void run() {
						mImageView.setImageURI(uri);
					}
				});
			}
		};
		mFileChangedObserver.setFileChangedListener(mFileChangedListener);
	}
}
