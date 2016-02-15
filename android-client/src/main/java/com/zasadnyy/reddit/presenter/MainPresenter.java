package com.zasadnyy.reddit.presenter;

import com.zasadnyy.reddit.model.ComponentsFactoryImpl;
import com.zasadnyy.reddit.model.IOperationCallback;
import com.zasadnyy.reddit.model.communication.OkHttpCommunication;
import com.zasadnyy.reddit.model.entity.Submission;

import java.util.List;

/**
 * @author Vitaliy Zasadnyy on 01/02/16.
 * @copyright &copy; 2015 GetSocial B.V. All rights reserved.
 */
public class MainPresenter extends Presenter<MainPresenter.Ui> {

	private static final String REDDIT_API_KEY = "MbIIzk6XocJVPkIF5cFfxPAkiEg";
	private static final String REDDIT_APP_ID = "lQ_oH0YDiu4lwA";

	private com.zasadnyy.reddit.model.RedditDataManager _dataManager;

	public MainPresenter(MainPresenter.Ui ui) {
		super(ui);
		initDataManager();
	}

	//region Public methods
	public void loadSubmissions(String subreddit) {
		_ui.showProgress("Loading submissions...");

		_dataManager.loadSubmissions(subreddit, new IOperationCallback<List<Submission>>() {
			@Override
			public void onSuccess(final List<Submission> result) {
				runOnUi(new UiRunnable() {
					@Override
					public void run(Ui ui) {
						_ui.finishProgress();
						_ui.listSubmissions(result);
					}
				});
			}

			@Override
			public void onError(final String reason) {
				runOnUi(new UiRunnable() {
					@Override
					public void run(Ui ui) {
						_ui.finishProgress();
						_ui.showError(reason);
					}
				});
			}
		});
	}
	//endregion

	//region Overrides
	@Override
	public void onStart() {
		super.onStart();

		_ui.showProgress("Initialization...");

		_dataManager.init(REDDIT_APP_ID, REDDIT_API_KEY, new com.zasadnyy.reddit.model.IOperationCallback<String>() {
			@Override
			public void onSuccess(String result) {
				runOnUi(new UiRunnable() {
					@Override
					public void run(Ui ui) {
						_ui.finishProgress();
					}
				});
			}

			@Override
			public void onError(final String reason) {
				runOnUi(new UiRunnable() {
					@Override
					public void run(Ui ui) {
						_ui.finishProgress();
						_ui.showError(reason);
					}
				});
			}
		});
	}
	//endregion

	//region Private methods
	private void initDataManager() {
		_dataManager = new com.zasadnyy.reddit.model.RedditDataManager(new ComponentsFactoryImpl());
	}
	//endregion

	//region Inner classes
	public interface Ui extends Presenter.Ui {
		void showProgress(String message);

		void finishProgress();

		void listSubmissions(List<Submission> submissions);

		void showError(String errorMessage);
	}
	//endregion
}
