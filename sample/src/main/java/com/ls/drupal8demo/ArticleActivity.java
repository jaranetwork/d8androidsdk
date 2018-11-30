/**
 * The MIT License (MIT)
 * Copyright (c) 2014 Lemberg Solutions Limited
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.ls.drupal8demo;

import com.ls.drupal8demo.article.ArticlePreview;
import com.ls.http.base.SharedGson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;

public class ArticleActivity extends AppCompatActivity {
	public static String TAG = "ArticleActivity";
	private final static String ARTICLE_PREVIEW_KEY = "ARTICLE_PREVIEW_KEY";
	private Toolbar myToolbar;

	public static Intent getExecutionIntent(Context theContext, ArticlePreview article) {
		Intent intent = new Intent(theContext, ArticleActivity.class);
		intent.putExtra(ARTICLE_PREVIEW_KEY, SharedGson.getGson().toJson(article));
		return intent;
	}

	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_article);
		myToolbar = (Toolbar) findViewById(R.id.mainToolbar);
		String previewString = getIntent().getStringExtra(ARTICLE_PREVIEW_KEY);

		ArticlePreview preview = SharedGson.getGson().fromJson(previewString, ArticlePreview.class);
		InitHeaderWithPreview(preview);

		ArticleFragment articleFragment = ArticleFragment.newInstance(preview.getNid(), preview.getImage());
		FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
		trans.add(R.id.containerView, articleFragment);
		trans.commit();
	}

	private void InitHeaderWithPreview(ArticlePreview preview) {
		String title = preview.getCategory();
		if (title != null) {
			title = Html.fromHtml(title).toString();
			Log.d(TAG, "TITLE: "+ title);
		}
		myToolbar.setTitle(title);

		String author = preview.getAuthor();

		StringBuilder subtitleText = new StringBuilder();
		if (author != null && !author.isEmpty()) {
			subtitleText.append(String.format("By %s", author));
		}
		subtitleText.append(String.format(" // %s", preview.getDate()));
		myToolbar.setSubtitle(subtitleText);
		setSupportActionBar(myToolbar);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				finish();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
