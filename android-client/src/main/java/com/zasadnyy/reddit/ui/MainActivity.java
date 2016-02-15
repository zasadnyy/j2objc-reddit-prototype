package com.zasadnyy.reddit.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zasadnyy.reddit.presenter.MainPresenter;
import com.zasadnyy.reddit.R;
import com.zasadnyy.reddit.model.entity.Submission;

import java.util.List;

@UiThread
public class MainActivity extends AppCompatActivity implements MainPresenter.Ui {

	private MainPresenter _presenter;

	private ProgressDialog _progressDialog;
	private EditText _subredditEditText;
	private Button _showRedditsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		_subredditEditText = (EditText) findViewById(R.id.subreddit_editText);
		_showRedditsButton = (Button) findViewById(R.id.showReddits_button);

		_showRedditsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onLoadSubmissionsClicked();
			}
		});

		_presenter = new MainPresenter(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		_presenter.onStart();
	}

	private void onLoadSubmissionsClicked() {
		Editable subreddit = _subredditEditText.getText();
		_presenter.loadSubmissions(subreddit.toString());
	}

	@Override
	public void showProgress(String message) {
		_progressDialog = ProgressDialog.show(this, null, message, true, false);
	}

	@Override
	public void finishProgress() {
		if (_progressDialog != null) {
			_progressDialog.dismiss();
		}
	}

	@Override
	public void listSubmissions(final List<Submission> submissions) {
		new AlertDialog.Builder(this)
				.setTitle(_subredditEditText.getText() + " submissions")
				.setCancelable(true)
				.setAdapter(
						new ArrayAdapter<Submission>(this, android.R.layout.select_dialog_item, submissions),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Toast.makeText(MainActivity.this, submissions.get(which).getTitle(), Toast.LENGTH_SHORT).show();
							}
						})
				.create()
				.show();
	}

	@Override
	public void showError(String errorMessage) {
		Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
	}
}
