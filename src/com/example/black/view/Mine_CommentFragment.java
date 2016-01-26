package com.example.black.view;

import java.util.List;

import com.example.black.R;
import com.example.black.act.CommentReplyActivity;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.Util;
import com.example.black.lib.model.Comment_Company2;
import com.example.black.view.custom.XListView;
import com.example.black.view.custom.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 我的评论-ui
 * 
 * @author admin
 * 
 */
public class Mine_CommentFragment  extends Fragment {
	private View view;
	private int page_count=1;
	private List<Comment_Company2> companys; 
	private Boolean refush_type=false;
	private int count=0;
	
	Handler handler=new Handler(){

		private Mine_CommentAdapter adapter;

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//首次加载数据
				String result=(String)msg.obj;
				companys = new JsonUtil().getMine_Comment(result, getActivity(), getActivity().getIntent().getLongExtra("user_id", -1));
				if (companys!=null &&companys.size()>0) {
					adapter = new Mine_CommentAdapter(getActivity(),companys);
					adapter.sethandler(handler);
					lv_onattestation.setAdapter(adapter);
					lv_onattestation.setVisibility(View.VISIBLE);
					if (companys.size()<10) {
						lv_onattestation.removeFooterView(1);
						lv_onattestation.setPullLoadEnable(false);
					}else{
						page_count=page_count+1;
						//lv_onattestation.removeFooterView(0);
						//lv_onattestation.setPullLoadEnable(true);
					}
				}else {
					if (getActivity().getIntent().getLongExtra("user_id", -1)!=Util.getShare_User_id(getActivity())) {
						tv_title.setText("他还没有评论过任何公司");
					}
					rl_background.setVisibility(View.VISIBLE);
				}
				break;
			case 1:
				//加载更多
				String result_more = (String) msg.obj;
				List<Comment_Company2> companys_more = new JsonUtil().getMine_Comment(result_more, getActivity(), getActivity().getIntent().getLongExtra("user_id", -1));
				if (companys_more!=null && companys_more.size()>0) {
					companys.addAll(companys_more);
					adapter.notifyDataSetChanged();
					if (companys_more.size()<10) {
						lv_onattestation.removeFooterView(1);
						lv_onattestation.setPullLoadEnable(false);
					}
					//else {
						
						//count = page_count;
						//lv_onattestation.removeFooterView(0);
						//lv_onattestation.setPullLoadEnable(true);
					//}
					page_count=page_count+1;
				}else {
					lv_onattestation.setPullLoadEnable(false);
					lv_onattestation.removeFooterView(1);
					Toast.makeText(getActivity(), "没有更多数据", 0).show();
				}
				lv_onattestation.stopLoadMore();
				break;
			case 2:
				//刷新数据
				String result_refush=(String)msg.obj;
				List<Comment_Company2> companys_refush = new JsonUtil().getMine_Comment(result_refush, getActivity(), getActivity().getIntent().getLongExtra("user_id", -1));
				if (companys_refush!=null&&companys_refush.size()>0) {
						companys.clear();
						companys.addAll(companys_refush);
						adapter=null;
						adapter=new Mine_CommentAdapter(getActivity(),companys);
						adapter.sethandler(handler);
						lv_onattestation.setAdapter(adapter);
						lv_onattestation.stopRefresh();
						lv_onattestation.removeFooterView(0);
						lv_onattestation.setPullLoadEnable(true);
						if (companys_refush.size()<10) {
							lv_onattestation.removeFooterView(1);
							lv_onattestation.setPullLoadEnable(false);
						}
						page_count=page_count+1;
				}
				lv_onattestation.stopRefresh();
			break;
			case 3:
				getActivity().finish();
				break;
			default:
				break;
			}
		};
	};

	private XListView lv_onattestation;
	private RelativeLayout rl_background;
	private TextView tv_title;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(R.layout.activity_mine_comment_fragment,
					null);
		}
		// 缓存的view需要判断是否已经被加过parent，
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
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initData();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (All_fragment.change_type1) {
			//网友评论点赞成功
			initData();
			All_fragment.change_type1=false;
		}else if(CommentReplyActivity.change_type){
			//网友评论用户回复页面点赞成功，刷新界面
			initData();
			CommentReplyActivity.change_type=false;
		}
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	
	private View findViewById(int id){
		return getActivity().findViewById(id);
	}
	
	private void initData() {
		if (HttpUtil.isNetworkInfo(getActivity())!=null) {
			HttpUtil.initMine_CommentFragment(getActivity(),getActivity().getIntent().getLongExtra("user_id", 0),handler, 1,0);
		}else {
			Toast.makeText(getActivity(), "网络连接失败", 0).show();
		}
	}
	
	private void initView() {
		rl_background = (RelativeLayout) findViewById(R.id.rl_background);
		tv_title = (TextView) findViewById(R.id.tv_title);
		//xlist
		lv_onattestation = (XListView) findViewById(R.id.lv_onattestation);
		lv_onattestation.setPullLoadEnable(true);
		lv_onattestation.setFocusable(false);
		lv_onattestation.setPullRefreshEnable(true);
		
		lv_onattestation.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				if (HttpUtil.isNetworkInfo(getActivity())!=null) {
					page_count=1;
					//if (!refush_type) {
						//page_count = count;
					//}
					//Toast.makeText(getActivity(), "page--->"+page_count, 0).show();
					HttpUtil.initMine_CommentFragment(getActivity(),getActivity().getIntent().getLongExtra("user_id", 0),handler, page_count,2);
				}else {
					lv_onattestation.stopRefresh();
					Toast.makeText(getActivity(), "网络连接失败", 0).show();
				}
			}
			
			@Override
			public void onLoadMore() {
				if (HttpUtil.isNetworkInfo(getActivity()) != null) {
					//Toast.makeText(getActivity(), "page--->"+page_count, 0).show();
					HttpUtil.initMine_CommentFragment(getActivity(),getActivity().getIntent().getLongExtra("user_id", 0),handler, page_count,1);
				} else {
					lv_onattestation.stopLoadMore();
					Toast.makeText(getActivity(), "网络连接失败", 0).show();
				}
			}
		});
	}
}
