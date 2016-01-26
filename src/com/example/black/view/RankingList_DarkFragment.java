package com.example.black.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * �Ӻ����
 * 
 * @author admin
 * 
 */
public class RankingList_DarkFragment extends Fragment {
	private View view;
	private XListView xl_dark_content;
	private final String Dark_AuthLevel = "add_blk_amount";// ��֤����
	private final String Data_Order = "DESC";
	private List<Company> rankingList;
	private RankDarklistAdapter adapter;
	private int page_count=1;
	private int RankingList_Type=1;//�Ӻ�
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//��һ�μ�������
				String result = (String) msg.obj;
				Log.e("Test", result);
				rankingList = new JsonUtil().getMyRankingList(result,0);
				Log.e("Test", rankingList.toString());
				if (rankingList != null && rankingList.size() > 0) {
					adapter = new RankDarklistAdapter(
							getActivity(), rankingList);
					adapter.setHandler(handler);
					xl_dark_content.setAdapter(adapter);
					if (rankingList.size()<10) {
						xl_dark_content.removeFooterView(1);
						xl_dark_content.setPullLoadEnable(false);
					}else {
						page_count=page_count+1;
						//xl_dark_content.removeFooterView(0);
						//xl_dark_content.setPullLoadEnable(true);
					}
				} else {
					Toast.makeText(getActivity(), "����������ʧ��", 0).show();
				}
				DialogUtil.dismissProgressDialog();
				break;
			case 1:
				//���ظ���
				String result_more = (String) msg.obj;
				List<Company> rankingList_more = new JsonUtil().getMyRankingList(result_more,0);
				//Log.i("jay_test", "���а��õ�������------------>"+rankingList_more);
				if (rankingList_more!=null && rankingList_more.size()>0) {
					rankingList.addAll(rankingList_more);
					adapter.notifyDataSetChanged();
					if (rankingList_more.size()<10) {
						xl_dark_content.removeFooterView(1);
						//xl_dark_content.setPullLoadEnable(false);
					}else{
						//xl_dark_content.removeFooterView(0);
						//xl_dark_content.setPullLoadEnable(true);
					}
					page_count=page_count+1;
				}else {
					//xl_dark_content.setPullLoadEnable(false);
					xl_dark_content.removeFooterView(1);
					Toast.makeText(getActivity(), "û�и�������", 0).show();
				}
				xl_dark_content.stopLoadMore();
				break;
			case 2:
				//ˢ������
				String result_refush=(String)msg.obj;
				List<Company> rankingList_refush = new JsonUtil().getMyRankingList(result_refush,0);
				if (rankingList_refush!=null&&rankingList_refush.size()>0) {
					//if (rankingList.get(0).getCompany_id()==rankingList_refush.get(0).getCompany_id()) {
						//xl_dark_content.stopRefresh();
					//}else {
						rankingList.clear();
						rankingList.addAll(rankingList_refush);
						adapter=null;
						adapter=new RankDarklistAdapter(
								getActivity(), rankingList);
						adapter.setHandler(handler);
						xl_dark_content.setAdapter(adapter);
						xl_dark_content.stopRefresh();
						xl_dark_content.removeFooterView(0);
						xl_dark_content.setPullLoadEnable(true);
						if (rankingList.size()<10) {
							xl_dark_content.removeFooterView(1);
							xl_dark_content.setPullLoadEnable(false);
						}
						else {
							
							//xl_dark_content.removeFooterView(0);
							//xl_dark_content.setPullLoadEnable(true);
						}
					//}
						page_count=page_count+1;	
				}
				xl_dark_content.stopRefresh();
				break;
			case 3:
				//�Ӻ�
				String add_result = msg.obj.toString();
				Map<String, String> add_map = new JsonUtil().getOperateResult(add_result);
				if (add_map!=null) {
					switch (Integer.parseInt(add_map.get("is_success"))) {
					case 0:
						int Position = adapter.setPosition();
						if (Position>=0) {
							rankingList.get(Position).setAdd_blk_amount(rankingList.get(Position).getAdd_blk_amount()+1);
							adapter.setCompanys(rankingList);
							adapter.notifyDataSetChanged();
							Toast.makeText(getActivity(), "���Ѿ��ɹ��Ӻڣ�",
									Toast.LENGTH_LONG).show();
						}
						break;
					case -1:
						Toast.makeText(getActivity(), "���Ӻ�ʧ���ˣ�",
								Toast.LENGTH_LONG).show();
						break;
					case -2:
						Toast.makeText(getActivity(),
								"�Բ���24Сʱ֮��ֻ��һ�μӺڣ�", Toast.LENGTH_LONG).show();
						break;
					}
				}else {
					Toast.makeText(getActivity(), "�Բ��𣬷������쳣��",
							Toast.LENGTH_SHORT).show();
				}
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
			view = inflater.inflate(R.layout.activity_rankinglist_darkfragment,
					null);
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
		if (HttpUtil.isNetworkInfo(getActivity())!=null) {
			DialogUtil.showProgressDialog(getActivity(),"������...", 0 );
			HttpUtil.initRankingList(true, Dark_AuthLevel,1,10,HttpUrl.get_list_method, handler, 0);
		}else {
			Toast.makeText(getActivity(),
					"������������û��������", Toast.LENGTH_SHORT).show();
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
	
	private void initView() {
		//�ö���ť		
		iv_top = (ImageView) findViewById(R.id.iv_top);
		xl_dark_content = (XListView)findViewById(R.id.xl_dark_content);
		xl_dark_content.setPullLoadEnable(true);
		xl_dark_content.setFocusable(false);
		xl_dark_content.setPullRefreshEnable(true);		
		
		iv_top.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				xl_dark_content.setSelection(0);
				iv_top.setVisibility(View.GONE);
			}
		});
		
		xl_dark_content.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				 switch (scrollState) {
				// ��������ʱ
				case OnScrollListener.SCROLL_STATE_IDLE:
					// �жϹ������ײ�
					if (xl_dark_content.getLastVisiblePosition() == (xl_dark_content
							.getCount() - 1)) {
						iv_top.setVisibility(View.VISIBLE);
					}
					// �жϹ���������
					if (xl_dark_content.getFirstVisiblePosition() == 0) {
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
		
		xl_dark_content.setXListViewListener(new IXListViewListener() {
			
			@Override
			public void onRefresh() {
				//ˢ��
				if (HttpUtil.isNetworkInfo(getActivity())!=null) {
					page_count=1;
					//Toast.makeText(getActivity(), "�õ�������--->"+page_count, 0).show();
					HttpUtil.initRankingList( true,Dark_AuthLevel,page_count,10,HttpUrl.get_list_method, handler, 2);
				}else {
					xl_dark_content.stopRefresh();
					Toast.makeText(getActivity(),
							"������������û��������", Toast.LENGTH_SHORT).show();
					
				}
			}
			
			@Override
			public void onLoadMore() {
				//���ظ���
				if (HttpUtil.isNetworkInfo(getActivity())!=null) {	
					//Toast.makeText(getActivity(), "�õ�������--->"+page_count, 0).show();
					HttpUtil.initRankingList(true, Dark_AuthLevel,page_count,10,HttpUrl.get_list_method, handler, 1);
				}else {
					xl_dark_content.stopLoadMore();
					Toast.makeText(getActivity(),
							"������������û��������", Toast.LENGTH_SHORT).show();
					xl_dark_content.stopRefresh();
				}
			}
		});
		
		xl_dark_content.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Company company = rankingList.get((position-1));
				if (company!=null) {
					List<Company> list=new ArrayList<Company>();
					list.add(company);
					Intent intent = new Intent();
					intent.putExtra("search_key", company.getCompany_name());
					intent.putExtra("position", 0);
					intent.putExtra("mqtt_push_type", 2);
					intent.putParcelableArrayListExtra("companys", (ArrayList<? extends Parcelable>) list);
					switch (company.getAuth_level()) {
					//��ƽ̨
					case "006001":
						intent.setClass(getActivity(),DarkTerraceActivity.class);
						startActivity(intent);
						break;
					//δ��֤
					case "006002":
						intent.setClass(getActivity(),NoAttestationActivity.class);
						startActivity(intent);
						break;
					//�Ϲ�
					case "006003":
					intent.setClass(getActivity(),QualifiedTerraceActivity.class);
						startActivity(intent);
						break;
					default:
						intent.setClass(getActivity(), NoStorageActivity.class);
						startActivity(intent);
						break;
					}};
			}
		});
	}
}
