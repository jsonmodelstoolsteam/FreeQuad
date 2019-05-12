package application.stages;

import java.io.File;

import com.sun.istack.internal.Nullable;

import application.Helper;

public interface InitComponentV2 {
	public boolean onShow(Helper helper, Object... params);
	public void reloadSession(Helper helper, boolean hide);
	@Nullable public default File getCatalog() {
		return null;
	};
}