package com.example.black.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.black.R;
import com.example.black.act.PublishComment;
import com.example.black.lib.AppManager;
import com.example.black.lib.HttpUtil;
import com.example.black.lib.ImageUtils;
import com.example.black.lib.JsonUtil;
import com.example.black.lib.KeyBoardUtil;
import com.example.black.lib.NetworkUtil;
import com.example.black.lib.Util;
import com.example.black.lib.model.Comment_Company;
import com.example.black.lib.model.Comment_Company2;
import com.example.black.lib.model.ExosureImage;
import com.example.black.lib.model.FaceAdapter;
import com.example.black.view.custom.ChatEmoji;
import com.example.black.view.custom.DialogUtil;
import com.example.black.view.custom.XListView;
import com.example.black.view.custom.XListView.IXListViewListener;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * ��������
 * @author admin
 *
 */
public class Comment_fragment extends Fragment implements OnItemClickListener{
	private View view;
	private XListView lv_onattestation;
	private FrameLayout fl_noattestation_fl1;
	private EditText et_noattestation_comment;
	private View footview;
	private RelativeLayout rel_noattestation_rl3;
	private Comment_Company2 company_top;
	private int page_count=1;
	private int page_size=10;
	private int image_count = 0;//���ͼƬ��ʾ������
	private List<String> image_path = new ArrayList<String>();// �洢��ͼƬ·������
	private boolean Delete_type=true;//�Ƿ�ɾ��ͼƬ��״̬
	private Comment_Company comment_company;
	//����
	private int mqtt_push_type;
	private int companys_type;
	private String comment_id;
	private String exposal_id;//�ع�id
	
	//�ϴ�ͼƬ���صĽ��
	private Handler images_handler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Map<String, String> maps = (Map<String, String>) msg.obj;
				if (maps != null) {
					 Log.i("map---->", maps + "");
					 
					//setExosure(maps);
					PublishComment.publishComment2(extra,0,getActivity(),
						comment_company, maps,handler2,
						0);
					 maps.clear();
					image_count = 0;
					setNullBitmap(iv_pic1);
					setNullBitmap(iv_pic2);
					setNullBitmap(iv_pic3);
					setNullBitmap(iv_pic4);
					setNullBitmap(iv_pic5);
					//iv_add.setVisibility(View.VISIBLE);
					//pb_exosure.setVisibility(View.INVISIBLE);
					//up_value.setText("δѡ���ļ�");
					System.gc();
				} else {
					Toast.makeText(getActivity(), "�ϴ�ͼƬʧ��", 0).show();
				}
				break;

			default:
				break;
			}
		};
	};
	
	//���޻�Ӻ�
	Handler handler2=new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				et_noattestation_comment.setText("");
				fl_noattestation_fl1.setVisibility(View.GONE);
				KeyBoardUtil.set_isShowOfHidden_KeyBoard(getActivity(), 2, null, et_noattestation_comment);
				String result = msg.obj.toString();
				if ("null".equals(result)) {
					Toast.makeText(getActivity(), "�Բ��𣬷������쳣��",
							Toast.LENGTH_SHORT).show();					
				} else {
					int is_success=JsonUtil.getUpdate_Info(result);
					if (is_success==0) {
						Toast.makeText(getActivity(),
								"���������Ѿ��ɹ��ύ��", Toast.LENGTH_LONG).show();
						DialogUtil.showProgressDialog(getActivity(), "������...", 0);
						
						ic_faces.setVisibility(View.GONE);
						ic_images.setVisibility(View.GONE);
						LayoutParams params = rel_noattestation_rl3.getLayoutParams();
						params.width=LayoutParams.MATCH_PARENT;
						params.height=1;
						rel_noattestation_rl3.setLayoutParams(params);
						
						page_count=1;
						PublishComment.getTopReply_Comment(extra,company_top,Util.getLoginType(getActivity()),Util.getShare_User_id(getActivity()), handler, 0, 1);
					}else if(is_success==-1){
						Toast.makeText(getActivity(), "�����������ʧ�ܣ�",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}else if(is_success==-6){
						Toast.makeText(getActivity(), "����ҵ�����ڻ�����ɾ����",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}else {
						Toast.makeText(getActivity(), "�����۲����ڻ�����ɾ����",
								Toast.LENGTH_LONG).show();
						DialogUtil.dismissProgressDialog();
					}			
				}
				break;
			default:
				break;
			}
		};
	};
	
	Handler handler=new Handler(){
		private ArrayList<Comment_Company2> list;
		private ContentRreplyAdapter adapter;
		private Map<String, Object> maps;

		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				List<Comment_Company2> comments1=new ArrayList<Comment_Company2>();
				
				list = new ArrayList<Comment_Company2>();
				String result=(String)msg.obj;
				Log.i("Test", "��������result---->"+result);
				List<Comment_Company2> comment_Company2s = new JsonUtil()
						.getComment_Company2s(extra,result);
				//Log.i("Test", "��������comment_Company2s---->"+result);
				maps = new JsonUtil().getComment_Company2s2(extra,result);
				Log.i("Test", "�������۸����ظ�maps---->"+maps.toString());
				list.add(company_top);
				if (comment_Company2s!=null &&comment_Company2s.size()>0) {
					list.addAll(comment_Company2s);
				}
				if (list!=null&&list.size()>0) {
					adapter = new ContentRreplyAdapter(handler,
							getActivity(), list,
							null, "", extra);
					adapter.setOnclick(fl_noattestation_fl1);
					adapter.setMap(maps);
					lv_onattestation.setAdapter(adapter);
					if (comments1.size()<10) {
						lv_onattestation.removeFooterView(1);
						lv_onattestation.setPullLoadEnable(false);
					}
					page_count=page_count+1;
				}else {
					Toast.makeText(getActivity(), "�Բ��𣬷������쳣��",
							Toast.LENGTH_SHORT).show();
				}
				lv_onattestation.setVisibility(View.VISIBLE);
				DialogUtil.dismissProgressDialog();
				break;
			case 2:
				//���ĵ���¼�
				String result6 = msg.obj.toString();
				//Log.i("Test", "��--------->"+result6);
				if (result6!=null&&!"".equals(result6)) {
					Map<String, String> map = new JsonUtil().getOperateResult(result6);
					switch (Integer.parseInt(map.get("is_success"))) {
					case 0:						
						int postions = adapter.getPostions();
						if (postions != -1) {
							list.get(postions)
									.setTop_num(
											list.get(postions)
													.getTop_num() + 1);
							adapter
									.setComment_Company2s(list);
							adapter.notifyDataSetChanged();
							if (postions==0) {
								if (extra==1) {
									All_fragment.change_type1=true;
								}else if(extra==2){
									All_fragment.change_type2=true;
								}
							}
						}
						Toast.makeText(getActivity(), "���ɹ���",
								Toast.LENGTH_SHORT).show();
						break;
					case -1:
						Toast.makeText(getActivity(), "����ʧ�ܣ�",
								Toast.LENGTH_SHORT).show();
						break;
					case -2:
						Toast.makeText(getActivity(),
								"�Բ���24Сʱ֮��ֻ�ܶ�һ�Σ�", Toast.LENGTH_SHORT).show();
						break;
					case -3:
						Toast.makeText(getActivity(),
								"��������ɾ��", Toast.LENGTH_SHORT).show();
						break;
					case -4:
						Toast.makeText(getActivity(),
								"�����۲�����", Toast.LENGTH_SHORT).show();
						break;
					case -5:
						Toast.makeText(getActivity(),
								"�����۵���ҵ�����ڻ�����ɾ��", Toast.LENGTH_SHORT).show();
						break;
					default:
						break;
					}
				}else {
					Toast.makeText(getActivity(), "�Բ��𣬷������쳣��",
							Toast.LENGTH_SHORT).show();
				}
				break;	
			case 3:
			List<Comment_Company2> comments2=new ArrayList<Comment_Company2>();
			//���ظ���
			String result_more=(String)msg.obj;
			List<Comment_Company2> comment_Company2s_more = new JsonUtil()
					.getComment_Company2s(extra,result_more);
			if (comment_Company2s_more!=null&&comment_Company2s_more.size()>0) {
				list.addAll(comment_Company2s_more);
				adapter.notifyDataSetChanged();					
				if (comments2.size()<10) {
					lv_onattestation.removeFooterView(1);
					lv_onattestation.setPullLoadEnable(false);
				}
				page_count=page_count+1;
			}else {		
				lv_onattestation.setPullLoadEnable(false);
				lv_onattestation.removeFooterView(1);
				Toast.makeText(getActivity(), "û�и�������", 0).show();					
			}
			lv_onattestation.stopLoadMore();
			DialogUtil.dismissProgressDialog();
			break;
			case 4:
				//ˢ��
				if(list != null){
					list.clear();
					list.add(company_top);
				}
				lv_onattestation.stopRefresh();
				lv_onattestation.removeFooterView(0);
				lv_onattestation.setPullLoadEnable(true);
				
				List<Comment_Company2> refush_list=new ArrayList<Comment_Company2>();
				String result_refush=(String)msg.obj;
				List<Comment_Company2> comment_Company2s_refush = new JsonUtil()
						.getComment_Company2s(extra,result_refush);
				maps=new JsonUtil().getComment_Company2s2(extra,result_refush);
				
				if (comment_Company2s_refush!=null&&comment_Company2s_refush.size()>0) {
					list.addAll(comment_Company2s_refush);
					page_count=page_count+1;
			}
				adapter = new ContentRreplyAdapter(handler,
						getActivity(), list,
						null, "", extra);	
				adapter.setMap(maps);
				adapter.setOnclick(fl_noattestation_fl1);
				lv_onattestation.setAdapter(adapter);
				
				if (comment_Company2s_refush!=null&&comment_Company2s_refush.size()<10) {
					lv_onattestation.removeFooterView(1);
					lv_onattestation.setPullLoadEnable(false);
				}
				
				lv_onattestation.stopRefresh();
				DialogUtil.dismissProgressDialog();
				break;
			default:
				break;
			}
		};
	};
	private ConnectivityManager manager;
	private ImageView iv_noattestation_comments;
	private RadioGroup rg_noattextation_commenttype;
	private CheckBox cb_noattestatiob_anon;
	private ImageView iv_noattestation_comment_icon1;
	private RelativeLayout ic_faces;	
	private ImageView iv_pic2;
	private ImageView iv_pic1;
	private ImageView iv_pic3;
	private ImageView iv_pic4;
	private ImageView iv_pic5;
	private long id;
	private int extra;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.c_freagment, null);
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
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		onCreate();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		FaceConversionUtil.getInstace();
		emojis = FaceConversionUtil.emojiLists;
		getIntent();
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		page_count=1;
		fl_noattestation_fl1.setVisibility(View.GONE);
		pointViews.clear();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		if (isNetworkInfo()!=null) {
//			page_count=1;
//			if(company_top != null){
//				PublishComment.getTopReply_Comment(extra,company_top,Util.getLoginType(getActivity()),Util.getShare_User_id(getActivity()), handler, 4, page_count);
//			}
//		}else {
//			Toast.makeText(getActivity(),
//					"������������û��������", Toast.LENGTH_SHORT).show();
//			lv_onattestation.stopRefresh();
//		}
	}
	
	private View findViewById(int id){
		return getActivity().findViewById(id);
	}
	
	private NetworkInfo isNetworkInfo(){
		ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		 return manager.getActiveNetworkInfo();
	}
	
	private void getIntent() {
		List<Comment_Company2> list=new ArrayList<Comment_Company2>();
		Intent intent = getActivity().getIntent();
		if (intent!=null) {
			mqtt_push_type = intent.getIntExtra("mqtt_push_type", -1);
			companys_type = intent.getIntExtra("companys_type", -1);
			extra = intent.getIntExtra("title_type", -1);
			//���͵�intent_����
			if(mqtt_push_type == 1){
				comment_id = intent.getStringExtra("comment_id");
				initpushdata(comment_id);
			//���͵�intent_�ع�
			}else if(mqtt_push_type == 3){
				
				exposal_id = intent.getStringExtra("exposal_id");
				
				initpushdata_exposal(exposal_id);
				
			//�ϼ�ҳ���intent	
			}else if(mqtt_push_type == 2){
				switch (companys_type) {
				case 1:
					if (HttpUtil.isNetworkInfo(getActivity())!=null) {
						DialogUtil.showProgressDialog(getActivity(),"������...", 0 );
						//��������
						company_top = (Comment_Company2) intent.getParcelableExtra("company_top");
						//id
						id = company_top.getId();
						//¥����������
			             PublishComment.getTopReply_Comment(extra,company_top,Util.getLoginType(getActivity()),Util.getShare_User_id(getActivity()), handler, 0, 1);							
					}else {
						Toast.makeText(getActivity(), "������������û��������",
								Toast.LENGTH_SHORT).show();
					}
				break;
				default:
					break;
				}
			}
		}else {
			//lv_onattestation.setPullRefreshEnable(false);
			//lv_onattestation.setmFooterView(0);
			Toast.makeText(getActivity(), "��ȡ����ʧ��", 0).show();
		}
	}
	
	private void initpushdata_exposal(String exposal_id) {
		// TODO Auto-generated method stub
		long exposal_id2 = Long.valueOf(exposal_id);
		if(NetworkUtil.isNetworkConnected(getActivity())){
			
			Log.i("push", "111==============>" + exposal_id2);
			
			PublishComment.getInexposalInfo(exposal_id2, mpushhandler, 1);
			
			Log.i("push", "112==========>" );
			
		}else {
			
			Toast.makeText(getActivity(), "��������ʧ��", 0).show();
		}
	}

	private void initpushdata(String comment_id) {
		long comment_id2 = Long.valueOf(comment_id);
		if(NetworkUtil.isNetworkConnected(getActivity())){
			PublishComment.getCommentInfo(comment_id2, mpushhandler, 0);
		}else {
			Toast.makeText(getActivity(), "��������ʧ��", 0).show();
		}
	}
	
	Handler mpushhandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//��ȡ����������Ϣ����ҵ��
				String result = (String) msg.obj;
				company_top = new JsonUtil().getCommentInfo(result);
				//id
				id = company_top.getId();
				PublishComment.getTopReply_Comment(extra,company_top,
						Util.getLoginType(getActivity()),
						Util.getShare_User_id(getActivity()), handler, 0, 1);
				break;
			case 1:
				//��ȡ�����ع���Ϣ����ҵ��
				String result_exposal = (String) msg.obj;
				company_top = new JsonUtil().getInexposalInfo(result_exposal);
				id = company_top.getId();
				PublishComment.getTopReply_Comment(extra,company_top,
						Util.getLoginType(getActivity()),
						Util.getShare_User_id(getActivity()), handler, 0, 1);
				break;
			}
		}
	};
	
	private void initView() {
		manager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
		//xlist
				lv_onattestation = (XListView) findViewById(R.id.lv_onattestation);
				//β����
				//footview = LinearLayout.inflate(getActivity(), R.layout.xlistview_footerview, null);
				//β�����������
				//tv_count1 = (TextView) footview.findViewById(R.id.tv_count1);
				//lv_onattestation.addFooterView(footview);
				lv_onattestation.setPullLoadEnable(true);
				lv_onattestation.setFocusable(false);
				lv_onattestation.setPullRefreshEnable(true);
				//����˵����
				RelativeLayout rl_left = (RelativeLayout) findViewById(R.id.rl_left);
				iv_noattestation_comments = (ImageView) this
						.findViewById(R.id.iv_noattestation_comments);
				//��Ҫ�ع�
				RelativeLayout rl_right = (RelativeLayout) findViewById(R.id.rl_right);
				//���ۿ�
				fl_noattestation_fl1 = (FrameLayout) findViewById(R.id.fl_noattestation_fl1);
				//���ۿ����Ͽհ�����
				RelativeLayout rel_noattestation_rl1 = (RelativeLayout) findViewById(R.id.rel_noattestation_rl1);
				//�༭��
				et_noattestation_comment = (EditText) findViewById(R.id.et_noattestation_comment);
				//���鼰�ϴ�ͼƬ��䲼��
				rel_noattestation_rl3 = (RelativeLayout) findViewById(R.id.rel_noattestation_rl3);
				rg_noattextation_commenttype = (RadioGroup) this
						.findViewById(R.id.rg_noattextation_commenttype);
				cb_noattestatiob_anon = (CheckBox) this
						.findViewById(R.id.cb_noattestatiob_anon);
				// ����
				iv_noattestation_comment_icon1 = (ImageView) this
						.findViewById(R.id.iv_noattestation_comment_icon1);
				// ���鲼��
						ic_faces = (RelativeLayout) findViewById(R.id.ic_faces);
						// �ϴ�ͼƬ
						ImageView iv_noattestation_comment_icon2 = (ImageView) findViewById(R.id.iv_noattestation_comment_icon2);
						// �ϴ�ͼƬ����
						ic_images = (FrameLayout) findViewById(R.id.ic_images);
						//�ϴ�ͼƬ������ͼƬ��ť		
						iv_add = (ImageView) findViewById(R.id.iv_add);
						// ͼƬ����
								iv_pic1 = (ImageView) findViewById(R.id.iv_pic1);
								iv_pic2 = (ImageView) findViewById(R.id.iv_pic2);
								iv_pic3 = (ImageView) findViewById(R.id.iv_pic3);
								iv_pic4 = (ImageView) findViewById(R.id.iv_pic4);
						        iv_pic5 = (ImageView) findViewById(R.id.iv_pic5);
				
						     // �����¼�
								iv_noattestation_comment_icon1
										.setOnClickListener(new View.OnClickListener() {

											@Override
											public void onClick(View v) {
												if (ic_faces.getVisibility()==View.GONE) {
													// ���������
													InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
													imm.hideSoftInputFromWindow(
															et_noattestation_comment.getWindowToken(),
															0);
													ic_faces.setVisibility(View.VISIBLE);
													ic_images.setVisibility(View.GONE);
													
													// �򿪲���
													LayoutParams lp = rel_noattestation_rl3
															.getLayoutParams();
													lp.width = LayoutParams.MATCH_PARENT;
													lp.height = LayoutParams.WRAP_CONTENT;
													rel_noattestation_rl3.setLayoutParams(lp);
												} else if(ic_faces.getVisibility()==View.VISIBLE){						
														ic_faces.setVisibility(View.GONE);
														ic_images.setVisibility(View.GONE);							
													LayoutParams lp = rel_noattestation_rl3
															.getLayoutParams();
													lp.width = LayoutParams.MATCH_PARENT;
													lp.height = 1;
													rel_noattestation_rl3.setLayoutParams(lp);
												}
											}
										});
								
								// ��ʾ�ϴ�ͼƬ����
								iv_noattestation_comment_icon2
										.setOnClickListener(new OnClickListener() {

											@Override
											public void onClick(View v) {
												if (ic_images.getVisibility()==View.GONE) {
													// ���������
													InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
													imm.hideSoftInputFromWindow(
															et_noattestation_comment.getWindowToken(),
															0);

													ic_images.setVisibility(View.VISIBLE);
													ic_faces.setVisibility(View.GONE);
													
													// ���ϴ�����
													LayoutParams lp = rel_noattestation_rl3
															.getLayoutParams();
													lp.width = LayoutParams.MATCH_PARENT;
													lp.height = LayoutParams.WRAP_CONTENT;
													rel_noattestation_rl3.setLayoutParams(lp);
												} else if(ic_images.getVisibility()==View.VISIBLE){							
													ic_faces.setVisibility(View.GONE);
													ic_images.setVisibility(View.GONE);
													LayoutParams lp = rel_noattestation_rl3
															.getLayoutParams();
													lp.width = LayoutParams.MATCH_PARENT;
													lp.height = 1;
													rel_noattestation_rl3.setLayoutParams(lp);
												}
											}
										});
								
								// �ϴ�ͼƬ�¼�
								iv_add.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View v) {
										// ��ȡ����ͼƬ
										Intent intent = new Intent(getActivity(),
												SelectPicPopupWindow.class);
										startActivityForResult(intent, 100);
									}
								});
								
								//�༭�����¼�
								et_noattestation_comment.setOnTouchListener(new OnTouchListener() {

									@Override
									public boolean onTouch(View v, MotionEvent event) {
										// ǿ����ʾ�����
										// �õ�InputMethodManager��ʵ��
										InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
										imm.showSoftInput(v,
												InputMethodManager.SHOW_FORCED);

										ic_faces.setVisibility(View.GONE);
										ic_images.setVisibility(View.GONE);
										LayoutParams lp = rel_noattestation_rl3.getLayoutParams();
										lp.width = LayoutParams.MATCH_PARENT;
										lp.height = 1;
										rel_noattestation_rl3.setLayoutParams(lp);
										return false;
									}
								});
								
								//�ύ����
								iv_noattestation_comments.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										boolean loginType = Util.getLoginType(getActivity());
										if (loginType) {
											String ed_content = et_noattestation_comment.getText()
													.toString();
											if (manager.getActiveNetworkInfo() != null) {
												if (ed_content != null
														&& ed_content.trim().length() > 0) {
													comment_company = new Comment_Company();
													switch (extra) {
													case 1:
														comment_company.setCompany_id(company_top.getCompany_id()
																);
														break;
													case 2:
														comment_company.setCompany_id(company_top.getId()
																);
														break;
													}
													comment_company.setParent_id(id);
													comment_company.setContent(ed_content);
													if (cb_noattestatiob_anon.isChecked()) {
														comment_company.setIs_anonymous(1);
													} else {
														comment_company.setIs_anonymous(0);
														}
													switch (rg_noattextation_commenttype
															.getCheckedRadioButtonId()) {
													case R.id.rb_noattestation_1:
														comment_company.setType("005001");
														break;
													case R.id.rb_noattestation_2:
														comment_company.setType("005002");
														break;
													case R.id.rb_noattestation_3:
														comment_company.setType("005003");
														break;
													}
													if (image_path != null && image_path.size() > 0) {
														//pb_exosure.setVisibility(View.VISIBLE);
														new ExoThread(image_path,comment_company.getType()).start();
													}else {
														PublishComment.publishComment2(extra,0,getActivity(),
																comment_company, null,handler2,
																0);
													}
												}else {
													//fl_noattestation.setVisibility(View.GONE);
													Toast.makeText(getActivity(),
															"���������۵����ݣ�", Toast.LENGTH_SHORT).show();
												}
											}
										else {
											// Toast.makeText(getActivity(), "��½״̬2----->"+loginType, 0).show();
											getActivity().startActivity(new Intent(getActivity(),MineFragment_Login.class));
										}
										}
									}
								});
						       
								//��ʾ���ۿ�		
				rl_left.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						if (Util.getLoginType(getActivity())) {
							fl_noattestation_fl1.setVisibility(View.VISIBLE);
						}else {
							getActivity().startActivity(new Intent(getActivity(),MineFragment_Login.class));
						}					
						}
				});
				
				//��Ҫ�ع�
				rl_right.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						//DarkTerraceActivity dark_page = DarkTerraceActivity.dark_page;
						//QualifiedTerraceActivity qualified_page = QualifiedTerraceActivity.qualified_page;
						//NoAttestationActivity no_attestation_page = NoAttestationActivity.no_attestation_page;
						//if(dark_page!=null) dark_page.finish();
						//if(qualified_page!=null) qualified_page.finish();
						//if(no_attestation_page!=null) no_attestation_page.finish();
						//getActivity().finish();
						AppManager.getInstance().killAllActivity();
						getActivity().getSharedPreferences("Statuse",
								Context.MODE_PRIVATE).edit().putString("main_type", "Exosure").commit();
					}
				});
				
				//����հ�������������
				rel_noattestation_rl1.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//���������
						KeyBoardUtil.set_isShowOfHidden_KeyBoard(getActivity(), 2, null, et_noattestation_comment);
					
						ic_faces.setVisibility(View.GONE);
						ic_images.setVisibility(View.GONE);
						LayoutParams lp = rel_noattestation_rl3.getLayoutParams();
						lp.width = LayoutParams.MATCH_PARENT;
						lp.height = 1;
						rel_noattestation_rl3.setLayoutParams(lp);
						fl_noattestation_fl1.setVisibility(View.GONE);
					}
				});
				
				lv_onattestation.setXListViewListener(new IXListViewListener() {
					
					@Override
					public void onRefresh() {
						//ˢ��
						if (isNetworkInfo()!=null) {
							page_count=1;
							PublishComment.getTopReply_Comment(extra,company_top,Util.getLoginType(getActivity()),Util.getShare_User_id(getActivity()), handler, 4, page_count);
						}else {
							Toast.makeText(getActivity(),
									"������������û��������", Toast.LENGTH_SHORT).show();
							lv_onattestation.stopRefresh();
						}				
					}
					
					@Override
					public void onLoadMore() {
						//���ظ���
						if (isNetworkInfo() != null) {
							PublishComment.getTopReply_Comment(extra,company_top,Util.getLoginType(getActivity()),Util.getShare_User_id(getActivity()), handler, 3, page_count);
						} else {
							Toast.makeText(getActivity(), "������������û��������",
									Toast.LENGTH_SHORT).show();
							lv_onattestation.stopRefresh();
						}
					}
				});
	}
	
	/** ��ʾ����ҳ��viewpager */
	private ViewPager vp_face;
	/** ����� */
	private EditText et_sendmessage;
	/** �α���ʾ���� */
	private LinearLayout layout_point;
	/** �������� */
	private View view1;
	/** ����ҳ���漯�� */
	public static ArrayList<View> pageViews;
	/** ��ǰ����ҳ */
	private int current = 0;
	/** �α�㼯�� */
	public static ArrayList<ImageView> pointViews;
	/** ������������� */
	public static List<FaceAdapter> faceAdapters;
	/** ���鼯�� */
	public static List<List<ChatEmoji>> emojis;
	/** ����ҳ�ļ����¼� */
	private OnCorpusSelectedListener mListener;
	private FrameLayout ic_images;
	private ImageView iv_add;

	private RelativeLayout rl_left;

	private TextView tv_dark_number;

	private TextView tv_praise_number;

	private ImageView iv_noattestation_addpraise;

	private TextView tv_count1;

	private int position;
	private String Image_url;
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// ��ȡ���ͼƬ
		if (resultCode == 200) {
			Image_url = data.getStringExtra("image_url");
			// Toast.makeText(getActivity(), "jay_test--->"+image_url,
			// 0).show();
			if (Image_url != null && !"".equals(Image_url)) {
				if (Delete_type) {
					image_count++;
					image_path.add(Image_url);
				}				
			//Log.i("��ȡͼƬ---------->   ", ""+image_count);				
				switch (image_count) {
				case 1:
					setBitmap(iv_pic1, Image_url, image_count);
					setOnLongClickListener(iv_pic1);
					// setOnImageChangeListener(iv_pic1);
					break;
				case 2:
					setBitmap(iv_pic2, Image_url, image_count);
					setOnLongClickListener(iv_pic2);
					// setOnImageChangeListener(iv_pic2);
					break;
				case 3:
					setBitmap(iv_pic3, Image_url, image_count);
					setOnLongClickListener(iv_pic3);
					// setOnImageChangeListener(iv_pic3);
					break;
				case 4:
					setBitmap(iv_pic4, Image_url, image_count);
					setOnLongClickListener(iv_pic4);
					// setOnImageChangeListener(iv_pic4);
					break;
				case 5:
					setBitmap(iv_pic5, Image_url, image_count);
					setOnLongClickListener(iv_pic5);
					// setOnImageChangeListener(iv_pic5);
					Toast.makeText(getActivity(), "��,����ϴ�����ͼƬ", 0).show();
					//fl_add.setVisibility(View.INVISIBLE);
					ic_images.setVisibility(View.GONE);
					iv_add.setVisibility(View.GONE);
					//iv_exposure.setClickable(true);
					break;
				default:
					break;
				}
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	// ���ý�Ҫ�ϴ���չʾͼƬ
		@SuppressWarnings("deprecation")
		private void setBitmap(ImageView view, String image_paths, int image_count) {
			// Bitmap bitmap = BitmapFactory.decodeFile(image_path);
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			// ͼƬ����ѹ��
			Bitmap bitmap = Util.extractThumbNail(image_paths,
					display.getWidth() / 5, display.getHeight() / 5, true);

			// Bitmap bitmap = BitmapFactory.decodeFile(image_paths, 1);
			if (bitmap != null) {
				// �ߴ�ѹ��
				Bitmap bitmap2 = ImageUtils.resizeImageByWidth(bitmap,
						getActivity().getWindowManager().getDefaultDisplay()
								.getWidth() / 7);
				if (bitmap2 != null) {
					//up_value.setText("��ѡ���ļ�" + image_count);
					view.setImageBitmap(bitmap2);
					view.setVisibility(View.VISIBLE);
					// if (!bitmap2.isRecycled()) {
					// bitmap2.recycle();
					// bitmap2 = null;
					// }
				}else {
					image_count--;
					image_path.remove(Image_url);	
				}
				// if (!bitmap.isRecycled()) {
				// bitmap.recycle();
				// bitmap = null;
				// }
				// System.gc();
			} else {
				image_count--;
				image_path.remove(Image_url);
			}
			Delete_type=true;
			//Log.i("��ʾͼƬ---------->   ", ""+image_count);			
		}
	
	// ����ɾ��ͼƬ
		private void setOnLongClickListener(final ImageView image) {

			image.setOnLongClickListener(new OnLongClickListener() {

				@Override
				public boolean onLongClick(View v) {
					DialogUtil.ShowDialog(getActivity(), "����", "�Ƿ�ɾ��?", "ȡ��", "ȷ��",
							null, new DialogInterface.OnClickListener() {								

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									image_count--;
									image.setImageBitmap(null);
									image.setVisibility(View.GONE);
									if (image_count > 0) {
										
										//up_value.setText("��ѡ���ļ�" + (image_count));
										//iv_add.setVisibility(View.VISIBLE);
									} else {
										//up_value.setText("δѡ���ļ�");
									}
									Delete_type=false;
									//Log.i("ɾ��ͼƬ---------->   ", ""+image_count);
								}
							});

					return false;
				}
			});

		}
		
		private void setNullBitmap(ImageView view) {
			view.setImageBitmap(null);
			view.setVisibility(View.GONE);
		}

	/**
	 * ����ѡ�����
	 * 
	 * @author naibo-liao
	 * @ʱ�䣺 2013-1-15����04:32:54
	 */
	public interface OnCorpusSelectedListener {

		void onCorpusSelected(ChatEmoji emoji);

		void onCorpusDeleted();
	}

	private void onCreate() {
		Init_View();
		Init_viewPager();
		Init_Point();
		Init_Data();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ChatEmoji emoji = (ChatEmoji) faceAdapters.get(current).getItem(
				position);
		// Log.i("jay-test--->", emoji+"");
		if (emoji.getId() == R.drawable.face_del_icon) {
			int selection = et_sendmessage.getSelectionStart();
			String text = et_sendmessage.getText().toString();
			if (selection > 0) {
				String text2 = text.substring(selection - 1);
				if ("]".equals(text2)) {
					int start = text.lastIndexOf("[");
					int end = selection;
					et_sendmessage.getText().delete(start, end);
					return;
				}
				et_sendmessage.getText().delete(selection - 1, selection);
			}
		}
		if (!TextUtils.isEmpty(emoji.getCharacter())) {
			if (mListener != null)
				mListener.onCorpusSelected(emoji);
			Log.i("emoji.getId()", emoji.getId() + "");
			Log.i("emoji.getCharacter()", emoji.getCharacter() + "");
			Log.i("emoji.getFaceName()", emoji.getFaceName() + "");
			SpannableString spannableString = FaceConversionUtil.getInstace()
					.addFace(getActivity(), emoji.getId(),
							emoji.getFaceName());
			// Log.i("jay_test--->", spannableString+"");
			et_sendmessage.append("[" + spannableString + "]");
		}
	}

	/**
	 * ��ʼ���ؼ�
	 */
	private void Init_View() {
		vp_face = (ViewPager) findViewById(R.id.vp_contains);
		et_sendmessage = (EditText) findViewById(R.id.et_noattestation_comment);
		layout_point = (LinearLayout) findViewById(R.id.iv_image);
		// et_sendmessage.setOnClickListener(this);
		// findViewById(R.id.btn_face).setOnClickListener(this);
		view1 = findViewById(R.id.ll_facechoose);
	}

	/**
	 * ��ʼ����ʾ�����viewpager
	 */
	private void Init_viewPager() {
		pageViews = new ArrayList<View>();
		// �����ӿ�ҳ
		View nullView1 = new View(getActivity());
		// ����͸������
		nullView1.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullView1);

		// �м���ӱ���ҳ

		faceAdapters = new ArrayList<FaceAdapter>();
		for (int i = 0; i < emojis.size(); i++) {
			//Toast.makeText(getActivity(), "------------->"+emojis.size(), 0).show();
			GridView view = new GridView(getActivity());
			FaceAdapter adapter = new FaceAdapter(getActivity(),
					emojis.get(i));
			view.setAdapter(adapter);
			faceAdapters.add(adapter);
			view.setOnItemClickListener(this);
			view.setNumColumns(7);
			view.setBackgroundColor(Color.TRANSPARENT);
			view.setHorizontalSpacing(1);
			view.setVerticalSpacing(1);
			view.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
			view.setCacheColorHint(0);
			view.setPadding(5, 0, 5, 0);
			view.setSelector(new ColorDrawable(Color.TRANSPARENT));
			view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
					LayoutParams.WRAP_CONTENT));
			view.setGravity(Gravity.CENTER);
			pageViews.add(view);
		}
		// �Ҳ���ӿ�ҳ��
		View nullView2 = new View(getActivity());
		// ����͸������
		nullView2.setBackgroundColor(Color.TRANSPARENT);
		pageViews.add(nullView2);
	}

	/**
	 * ��ʼ���α�
	 */
	private void Init_Point() {

		pointViews = new ArrayList<ImageView>();
		ImageView imageView;
		for (int i = 0; i < pageViews.size(); i++) {
			imageView = new ImageView(getActivity());
			imageView.setBackgroundResource(R.drawable.d1);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT));
			layoutParams.leftMargin = 10;
			layoutParams.rightMargin = 10;
			layoutParams.width = 8;
			layoutParams.height = 8;
			layout_point.addView(imageView, layoutParams);
			if (i == 0 || i == pageViews.size() - 1) {
				imageView.setVisibility(View.GONE);
			}
			if (i == 1) {
				imageView.setBackgroundResource(R.drawable.d2);
			}
			pointViews.add(imageView);

		}
	}

	/**
	 * �������
	 */
	private void Init_Data() {
		vp_face.setAdapter(new ViewPagerAdapter(pageViews));

		vp_face.setCurrentItem(1);
		current = 0;
		vp_face.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				current = arg0 - 1;
				// ����ҳ��
				draw_Point(arg0);
				// ����ǵ�һ�����������һ����ֹ��������ʵ����ʵ�ֵ�������������ǵ�һ������ת���ڶ�������������һ������ת�������ڶ���.
				if (arg0 == pointViews.size() - 1 || arg0 == 0) {
					if (arg0 == 0) {
						vp_face.setCurrentItem(arg0 + 1);// �ڶ��� ���ٴ�ʵ�ָûص�����ʵ����ת.
						pointViews.get(1).setBackgroundResource(R.drawable.d2);
					} else {
						vp_face.setCurrentItem(arg0 - 1);// �����ڶ���
						pointViews.get(arg0 - 1).setBackgroundResource(
								R.drawable.d2);
					}
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	/**
	 * �����α걳��
	 */
	public void draw_Point(int index) {
		for (int i = 1; i < pointViews.size(); i++) {
			if (index == i) {
				pointViews.get(i).setBackgroundResource(R.drawable.d2);
			} else {
				pointViews.get(i).setBackgroundResource(R.drawable.d1);
			}
		}
	}
	
	//�ϴ�ͼƬ
		class ExoThread extends Thread {

			private List<String> list;
			private ExosureImage exosureImage;
			private String type;

			public ExoThread(List<String> list,String type) {
				this.list = list;
				this.type=type;
			}

			@Override
			public void run() {
				Map<String, String> map = new HashMap<String, String>();// ͼƬid;
				for (int i = 0; i < list.size(); i++) {
					String upLoad = HttpUtil.UpLoad(list.get(i), type);
					if (upLoad != null && !"".equals(upLoad)) {
						exosureImage = JsonUtil.getExosureImageId(upLoad);
						if (exosureImage != null) {
							int is_sucess = exosureImage.getIs_sucess();
							if (is_sucess == 0) {
								map.put("pic_" + (i + 1), exosureImage.getId());
							}
						}
					}
				}
				// �ϴ��ɹ����ͼƬid
				Message message = new Message();
				message.what = 0;
				message.obj = map;
				images_handler.sendMessage(message);
				image_path.clear();
			}
		}
}
