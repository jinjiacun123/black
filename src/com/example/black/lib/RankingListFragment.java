package com.example.black.lib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 排行榜
 * 
 * @author admin
 * 
 */
public class RankingListFragment extends Fragment {
	private View view;
	private RankingList_DarkFragment darkfragment;
	private RankingList_CommentFragment commentfragment;
	private RankingList_ExposureFragment exposurefragment;
    private FragmentManager manager;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_rankinglist_fragment,
					null);
		}
		 //缓存的view需要判断是否已经被加过parent，
		// 如果有parent需要从parent删除，
		// 要不然会发生这个view已经有parent的错误。
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		 //TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		initTabhost();
	}

	private void initTabhost() {
		if (darkfragment == null) darkfragment = new RankingList_DarkFragment();
		if (commentfragment==null) commentfragment=new RankingList_CommentFragment();
		if (exposurefragment==null) exposurefragment=new RankingList_ExposureFragment();
		if(manager==null) manager = getFragmentManager();
	}

	// 点击切换对应fragment
	public void ChangeFragment(Fragment fragment) {			
		manager.beginTransaction().replace(R.id.fl_content, fragment)
		.commit();
//		manager.beginTransaction().addToBackStack("fragment");// 添加到Activity管理的回退栈中。
	}

	private void initView() {
		// 标题
		TextView tv_ClassName = (TextView) getActivity().findViewById(
				R.id.tv_ClassName);
		tv_ClassName.setText("排行榜");
		// 切换按钮
		RadioGroup rg_group = (RadioGroup) getActivity().findViewById(
				R.id.rg_group);	
		
		rg_group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {				
				switch (checkedId) {
				case R.id.rb_button1:
					ChangeFragment(darkfragment);
					break;
				case R.id.rb_button2:
					ChangeFragment(commentfragment);
					break;
				case R.id.rb_button3:
					ChangeFragment(exposurefragment);
					break;
				default:
					break;
				}

			}
		});
	}  
}
