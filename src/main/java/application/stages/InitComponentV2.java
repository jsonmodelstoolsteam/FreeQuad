package application.stages;

import application.Helper;
import com.sun.istack.internal.Nullable;

import java.io.File;

public interface InitComponentV2 extends SceneSource {
    public boolean onShow(Helper helper, Object... params);

    public void reloadSession(Helper helper, boolean hide);

    @Nullable
    public default File getCatalog() {
        return null;
    }

}