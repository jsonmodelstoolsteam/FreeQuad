package application.stages;

import application.Helper;

import java.io.File;

public interface InitComponentV2 extends SceneSource {
    public boolean onShow(Helper helper, Object... params);

    public void reloadSession(Helper helper, boolean hide);

    public default File getCatalog() {
        return null;
    }

}