package com.github.captain_miao.citypicker.library.widget;


public abstract class XCallbackListener {

	protected abstract void callback(Object... obj);

	public void call(Object... obj) {
			callback(obj);
	}

}
