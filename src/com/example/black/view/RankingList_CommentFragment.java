package com.example.black.view;

import java.util.ArrayList;
import java.util.List;

import com.example.black.R;
import com.example.black.lib.HttpUrl;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.model.Company;
import com.example.black.lib.model.DarkTerraceActivity;
import com.example.black.lib.model.NoAttestationActivity;
import com.example.black.lib.model.NoStorageActivity;
import com.example.black.lib.model.QualifiedTerraceActivity;
import com.example.black.view.custom.DialogUtil;
import com.example.black.view.custom.XListView;
import com.example.black.view.custom.XListView.IXListViewListener;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

/**
 * �������
 * 
 * @author admin
 * 
 */
public class RankingList_CommentFragment extends Fragment {
	private View view;
	private XListView xl_comment_content;
	private final String Dark_AuthLevel = "com_amount";// ��֤����
	private int RankingList_Type = 2;// ����
	private int page_count = 1;
	private RanklistAdapter adapter;
	private List<Company> rankingList;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				String result = (String) msg.obj;
				//Log.i("Test", "���а��õ�������------------>" + result);
				rankingList = new JsonUtil().getMyRankingList(result, 1);
				if (rankingList != null && rankingList.size() > 0) {
					//Log.i("Test", "���а��õ�������---->" + rankingList);
					adapter = new RanklistAdapter(getActivity(), rankingList,
							RankingList_Type);
					xl_comment_content.setAdapter(adapter);
					if (rankingList.size() < 10) {
						xl_comment_content.removeFooterView(1);
						xl_comment_content.setPullLoadEnable(false);
					} else {
						page_count = page_count + 1;
						// xl_comment_content.removeFooterView(0);
						// xl_comment_content.setPullLoadEnable(true);
					}
				} else {
					Toast.makeText(getActivity(), "����������ʧ��", 0).show();
				}
				DialogUtil.dismissProgressDialog();
				break;
			case 1:
				// ���ظ���
				String result_more = (String) msg.obj;
				//Log.i("Test", "���ظ���------------>" + result_more);
				List<Company> rankingList_more = new JsonUtil()
						.getMyRankingList(result_more, 1);
				//Log.i("Test", "���ظ���------------>" + rankingList_more);
				if (rankingList_more != null && rankingList_more.size() > 0) {
					rankingList.addAll(rankingList_more);
					adapter.notifyDataSetChanged();
					if (rankingList_more.size() < 10) {
						xl_comment_content.removeFooterView(1);
						//xl_comment_content.setPullLoadEnable(false);
					} else {
						// xl_comment_content.removeFooterView(0);
						// xl_comment_content.setPullLoadEnable(true);
					}
					page_count = page_count + 1;
				} else {
					//xl_comment_content.setPullLoadEnable(false);
					xl_comment_content.removeFooterView(1);
					Toast.makeText(getActivity(), "û�и�������", 0).show();
				}
				xl_comment_content.stopLoadMore();
				break;
			case 2:
				// ˢ������
				String result_refush = (String) msg.obj;
				//Log.i("Test", "pinglunzuidauo---->"+result_refush);
				List<Company> rankingList_refush = new JsonUtil()
						.getMyRankingList(result_refush, 1);
				if (rankingList_refush != null && rankingList_refush.size() > 0) {
					rankingList.clear();
					rankingList.addAll(rankingList_refush);
					adapter = null;
					adapter = new RanklistAdapter(getActivity(), rankingList,
							RankingList_Type);
					xl_comment_content.setAdapter(adapter);
					xl_comment_content.stopRefresh();
					xl_comment_content.removeFooterView(0);
					xl_comment_content.setPullLoadEnable(true);
					if (rankingList.size() < 10) {
						xl_comment_content.removeFooterView(1);
						xl_comment_content.setPullLoadEnable(false);
					}
					page_count = page_count + 1;
				}
				xl_comment_content.stopRefresh();
				break;
			default:
				break;
			}
		};
	};
	private ImageView iv_top;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (view == null) {
			view = inflater.inflate(
					R.layout.activity_rankinglist_commentfragment, null);
		}
		// �����view��Ҫ�ж��Ƿ��Ѿ����ӹ�parent��
		// �����parent��Ҫ��parentɾ����
		// Ҫ��Ȼ�ᷢ�����view�Ѿ���parent�Ĵ���
		ViewGroup parent = (ViewGroup) view.getParent();
		if (parent != null) {
			parent.removeView(view);
		}
		return view;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (isNetworkInfo() != null) {
			DialogUtil.showProgressDialog(getActivity(), "������...", 0);
			HttpUtil.initRankingList(false,Dark_AuthLevel, 1,10,
					HttpUrl.get_list_com_method, handler, 0);
		} else {
			Toast.makeText(getActivity(), "������������û��������", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	private View findViewById(int id) {
		return getView().findViewById(id);
	}

	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}

	private NetworkInfo isNetworkInfo() {
		ConnectivityManager manager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		return manager.getActiveNetworkInfo();
	}

	private void initView() {
		// �ö���ť
		iv_top = (ImageView) findViewById(R.id.iv_top);
		xl_comment_content = (XListView) findViewById(R.id.xl_comment_content);
		xl_comment_content.setPullLoadEnable(true);
		xl_comment_content.setFocusable(false);
		xl_comment_content.setPullRefreshEnable(true);

		iv_top.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				xl_comment_content.setSelection(0);
				iv_top.setVisibility(View.GONE);
			}
		});

		xl_comment_content.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				switch (scrollState) {
				// ��������ʱ
				case OnScrollListener.SCROLL_STATE_IDLE:
					// �жϹ������ײ�
					if (xl_comment_content.getLastVisiblePosition() == (xl_comment_content
							.getCount() - 1)) {
						iv_top.setVisibility(View.VISIBLE);
					}
					// �жϹ���������
					if (xl_comment_content.getFirstVisiblePosition() == 0) {
						iv_top.setVisibility(View.GONE);
					}
					break;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub

			}
		});

		xl_comment_content.setXListViewListener(new IXListViewListener() {

			@Override
			public void onRefresh() {
				if (isNetworkInfo() != null) {
					page_count = 1;
					HttpUtil.initRankingList(false,Dark_AuthLevel, page_count,10,
							HttpUrl.get_list_com_method, handler, 2);
				} else {
					xl_comment_content.stopRefresh();
					Toast.makeText(getActivity(), "������������û��������",
							Toast.LENGTH_SHORT).show();
				}
			} 

			@Override
			public void onLoadMore() {
				if (isNetworkInfo() != null) {
					HttpUtil.initRankingList(false,Dark_AuthLevel, page_count,10,
							HttpUrl.get_list_com_method, handler, 1);
				} else {
					xl_comment_content.stopLoadMore();
					Toast.makeText(getActivity(), "������������û��������",
							Toast.LENGTH_SHORT).show();

				}
			}
		});

		xl_comment_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Company company = rankingList.get(position-1);
				if (company != null) {
					List<Company> list=new ArrayList<Company>();
					list.add(company);
					Intent intent = new Intent();
					intent.putExtra("search_key", company.getCompany_name());
					intent.putExtra("position", (position-1));
					intent.putExtra("mqtt_push_type", 2);
					intent.putParcelableArrayListExtra("companys",
							(ArrayList<? extends Parcelable>) list);
					switch (company.getAuth_level()) {
					// ��ƽ̨
					case "006001":
						intent.setClass(getActivity(),
								DarkTerraceActivity.class);
						break;
					// δ��֤
					case "006002":
						intent.setClass(getActivity(),
								NoAttestationActivity.class);
						break;
					// �Ϲ�
					case "006003":
						intent.setClass(getActivity(),
								QualifiedTerraceActivity.class);
						break;
					default:
						intent.setClass(getActivity(), NoStorageActivity.class);
						break;
					}
					startActivity(intent);
				}
				;
			}
		});
	}
}
