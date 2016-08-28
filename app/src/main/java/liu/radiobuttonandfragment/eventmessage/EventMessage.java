package liu.radiobuttonandfragment.eventmessage;

import android.os.Bundle;

/**
 * Created by 刘楠 on 2016/8/28 0028.13:34
 */
public class EventMessage {

    Bundle mBundle;
    int    tag;

    public Bundle getBundle() {
        return mBundle;
    }

    public void setBundle(Bundle bundle) {
        mBundle = bundle;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public static class EventMessageAction {

        public final static int TAG_GO_MAIN     = 1;//跳转主页
        public final static int TAG_GO_SHOPCART = 2;//购物车
        public final static int TAG_GO_MESSAGE  = 3;// 消息
    }
}
