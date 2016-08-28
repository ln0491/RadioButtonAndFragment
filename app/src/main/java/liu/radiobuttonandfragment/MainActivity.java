package liu.radiobuttonandfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import liu.radiobuttonandfragment.eventmessage.EventMessage;
import liu.radiobuttonandfragment.fragment.HomeFragment;
import liu.radiobuttonandfragment.fragment.MessageFragment;
import liu.radiobuttonandfragment.fragment.MineFragment;
import liu.radiobuttonandfragment.fragment.ShopcartFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "vivi";
    @Bind(R.id.content)
    FrameLayout mContent;
    @Bind(R.id.rbHome)
    RadioButton mRbHome;
    @Bind(R.id.rbShop)
    RadioButton mRbShop;
    @Bind(R.id.rbMessage)
    RadioButton mRbMessage;
    @Bind(R.id.rbMine)
    RadioButton mRbMine;
    @Bind(R.id.rgTools)
    RadioGroup  mRgTools;
    private Fragment[] mFragments;

    private int mIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        initFragment();
        //initRadioGroup();

    }

   /* private void initRadioGroup() {

    }*/

    private void initFragment() {
        //首页
        HomeFragment homeFragment =new HomeFragment();
        //购物车
        ShopcartFragment shopcartFragment =new ShopcartFragment();

        //消息
        MessageFragment messageFragment =new MessageFragment();
        //个人中心

        MineFragment mineFragment =new MineFragment();

        //添加到数组
        mFragments = new Fragment[]{homeFragment,shopcartFragment,messageFragment,mineFragment};

        //开启事务

        FragmentTransaction ft =
                getSupportFragmentManager().beginTransaction();

        //添加首页
        ft.add(R.id.content,homeFragment).commit();

        //默认设置为第0个
        setIndexSelected(0);


    }



    private void setIndexSelected(int index) {

        if(mIndex==index){
            return;
        }
        FragmentManager    fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft              = fragmentManager.beginTransaction();


        //隐藏
        ft.hide(mFragments[mIndex]);
        //判断是否添加
        if(!mFragments[index].isAdded()){
            ft.add(R.id.content,mFragments[index]).show(mFragments[index]);
        }else {
            ft.show(mFragments[index]);
        }

        ft.commit();
        //再次赋值
        mIndex=index;

    }

    @OnClick({R.id.rbHome, R.id.rbShop, R.id.rbMessage, R.id.rbMine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbHome:
                setIndexSelected(0);
                break;
            case R.id.rbShop:
                setIndexSelected(1);
                break;
            case R.id.rbMessage:
                setIndexSelected(2);
                break;
            case R.id.rbMine:
                setIndexSelected(3);
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setGoIndex(EventMessage eventMessage){
        Log.d(TAG, "setGoIndex: "+eventMessage.getTag());
        if(eventMessage!=null){
            int tag = eventMessage.getTag();


            if(tag== EventMessage.EventMessageAction.TAG_GO_MAIN){
                mRbHome.performClick();
                setIndexSelected(0);
            }else if(tag== EventMessage.EventMessageAction.TAG_GO_SHOPCART){
                mRbShop.performClick();

                setIndexSelected(1);
            }else if(tag== EventMessage.EventMessageAction.TAG_GO_MESSAGE){
                mRbMessage.performClick();
                setIndexSelected(2);
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       // mRbHome.performClick();
    }
}
