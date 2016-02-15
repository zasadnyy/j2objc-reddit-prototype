package com.zasadnyy.reddit.presenter;

/**
 * @author Vitaliy Zasadnyy on 01/02/16.
 * @copyright &copy; 2015 GetSocial B.V. All rights reserved.
 */

import android.app.Activity;
import android.app.Fragment;
import android.os.Handler;

/**
 * Base presenter for presenters to implement.
 *
 * @param <TUi> Type of UI interface for presenter.
 * @author Mike Gouline
 */
public abstract class Presenter<TUi extends Presenter.Ui> {

	protected TUi _ui;

	private final Handler _handler = new Handler();

	protected Presenter(TUi ui) {
		_ui = ui;
	}

	protected TUi getUi() {
		return _ui;
	}

	protected boolean hasUi() {
		return _ui != null;
	}

	protected void runOnUi(UiRunnable runnable) {
		if (hasUi()) {
			runnable.setUi(_ui);
			_handler.post(runnable);
		}
	}

	/**
	 * Call-through from {@link Activity#onStart()} or {@link Fragment#onStart()}.
	 */
	public void onStart() {
		// Implement at will
	}

	/**
	 * Call-through from {@link Activity#onResume()} or {@link Fragment#onResume()}.
	 */
	public void onResume() {
		// Implement at will
	}

	/**
	 * Call-through from {@link Activity#onPause()} or {@link Fragment#onPause()}.
	 */
	public void onPause() {
		// Implement at will
	}

	/**
	 * Call-through from {@link Activity#onStop()} or {@link Fragment#onStop()}.
	 */
	public void onStop() {
		// Implement at will
	}

	/**
	 * Call-through from {@link Activity#onDestroy()} or {@link Fragment#onDestroyView()}.
	 */
	public void onDestroy() {
		_ui = null;
	}

	/**
	 * Common UI elements that apply to all presenters.
	 */
	public interface Ui {
	}

	/**
	 * Runnable for UI tasks.
	 */
	public abstract class UiRunnable implements Runnable {
		private TUi _ui;

		public void setUi(TUi ui) {
			_ui = ui;
		}

		@Override
		public final void run() {
			run(_ui);
		}

		public abstract void run(TUi ui);
	}
}