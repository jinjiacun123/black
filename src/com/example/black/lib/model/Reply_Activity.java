package com.example.black.lib.model;

import com.example.black.R;
import java.util.HashMap;
import java.util.Map;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ��ƽ̨�ظ�����ҳ(�������ۣ������ع�)
 * 
 * @author admin
 * 
 */
public class Reply_Activity extends FragmentActivity{
	private final UMSocialService mController = UMServiceFactory
			.getUMSocialService(UMStaticConstant.DESCRIPTOR);

	private final String[] tabname = { "ȫ��", "��ͼ������", "��������", "¥����Ʒ" };
	@SuppressWarnings("rawtypes")
	private final Class[] fragment_class = { All_fragment.class,
			Images_fragment.class, Comment_fragment.class,
			Master_fragment.class };
	private FrameLayout fl_noattestation_fl1;
	//private EditText et_comment;
	private RelativeLayout rel_noattestation_rl1;
	private RelativeLayout rel_noattestation_rl3;
	private String SouheiCommentTop = "";
	private String SouheiCommentContent = null;
	private String SouheiCommentTitle = "";
	private TextView tv_ClassName;
	private Comment_Company2 company;
	private AsyncBitmapLoader async;
	public static Reply_Activity reply_page;
	public static Map<String, Object> map;//���¥����Ϣ
	
	//����
	private String comment_id;//����id
	private String exposal_id;//�ع�id
	private int mqtt_push_type;
	private int title_type;//1.���� ��2.�ع�

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);
		reply_page = this;
		initActionBar();
		getintent();
		initTabhostFragment();
		async = new AsyncBitmapLoader(this);
		AppManager.getInstance().addActivity(Reply_Activity.this);
	}
	
	private void setdata() {
		
		switch (title_type) {
		case 1:
			tv_ClassName.setText("��������");
			SouheiCommentContent = "��ȫ��Ͷ��ƽ̨���ۣ�С����Ƕ���Χ��";
			SouheiCommentTop = "http://souhei.com.cn/posts_pl?qy=";
			SouheiCommentTitle = "���Ѿ��ʸ�¥���Ѻ�";
			break;
		case 2:
			tv_ClassName.setText("�����ع�");
			SouheiCommentContent = company.getCompany_name()
					+ "��˾�ֱ��ع⣬�û��޿�����";
			SouheiCommentTop = "http://souhei.com.cn/posts_bg?qy=";
			SouheiCommentTitle = "�ع�" + company.getCompany_name() + "��˾���Ѻ�";
			break;
		default:
			break;
		}
		
		map=new HashMap<String, Object>();
		map.put("SouheiCommentTitle", SouheiCommentTitle);
		map.put("SouheiCommentContent", SouheiCommentContent);
		map.put("SouheiCommentPic_1_url", company.getPic_1_url());
		map.put("SouheiCommentTop", SouheiCommentTop+ company.getCompany_id() 
				+ "&pl="+ company.getId());
	}

	//����
	private void initpushdata(String comment_id) {
		long comment_id2 = Long.valueOf(comment_id);
		if(NetworkUtil.isNetworkConnected(getApplicationContext())){
			PublishComment.getCommentInfo(comment_id2, pushhandler, 0);
		}else {
			Toast.makeText(getApplicationContext(), "��������ʧ��", 0).show();
		}
	}
	
	//�ع�
	private void initpushdata_exposal(String exposal_id) {
		long exposal_id2 = Long.valueOf(exposal_id);
		if(NetworkUtil.isNetworkConnected(getApplicationContext())){
			PublishComment.getInexposalInfo(exposal_id2, pushhandler, 1);
		}else {
			Toast.makeText(getApplicationContext(), "��������ʧ��", 0).show();
		}
	}
	
	Handler pushhandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				//��ȡ����������Ϣ����ҵ��
				String result = (String) msg.obj;
				company = new JsonUitl().getCommentInfo(result);
				setdata();
				break;
			case 1:
				//��ȡ�����ع���Ϣ����ҵ��
				String result_exposal = (String) msg.obj;
				company = new JsonUitl().getInexposalInfo(result_exposal);
				setdata();
				break;
			}
		}
	};

	private void getintent() {
		Intent intent = this.getIntent();
		if (intent != null) {
			mqtt_push_type = intent.getIntExtra("mqtt_push_type", -1);
			title_type = intent.getIntExtra("title_type", -1);
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
				company = intent.getParcelableExtra("company_top");
				setdata();
			}
		}
		// if(intent.getIntExtra("companys_type", 1)==1) {
		// Comment_Company2 company_top =
		// intent.getParcelableExtra("company_top");
		// if (company_top.getPic_1()>0) {
		// Bitmap bitmap =
		// MainActivity.imageloader.loadImageSync(company_top.getPic_1_url());
		// Toast.makeText(getApplicationContext(),
		// "pic_1_url---------->"+bitmap, 0).show();
		// }
		// }
	}

	private void initActionBar() {
		// ����
		RelativeLayout iv_ActionBar = (RelativeLayout) findViewById(R.id.iv_ActionBar);
		iv_ActionBar.setVisibility(View.VISIBLE);
		// ����
		tv_ClassName = (TextView) findViewById(R.id.tv_ClassName);
		// tv_ClassName.setText("�����ع�");
		// ����
		TextView iv_home = (TextView) findViewById(R.id.iv_home);
		iv_home.setText("����");
		iv_home.setVisibility(View.VISIBLE);

		iv_ActionBar.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		iv_home.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// http://souhei.com.cn/posts_pl?qy=11&pl=7
				// ����˵����qy =>��ҵid
				// pl =>�������ۻ��ع�id

				final StringBuffer sb = new StringBuffer();
				// String title = "�Ѻ�ƽ̨��ѯ_ȫ�����Ĳ�ѯ�ع�ƽ̨_�Ѻ�";
				// final String title="���Ѿ��ʸ�¥���Ѻ�";
				sb.append(SouheiCommentContent);
				String pic_1_url = company.getPic_1_url();
				UMImage image=null;
				if (!TextUtils.isEmpty(pic_1_url)) {
					image=new UMImage(Reply_Activity.this,pic_1_url);
					UMShareManager.UMSendShare(Reply_Activity.this,
							mController, SouheiCommentTitle, SouheiCommentTop
									+ company.getCompany_id() + "&pl="
									+ company.getId(), image, sb
									.toString());
				} else {
                image=new UMImage(Reply_Activity.this, WeiXinShareController.myShot(Reply_Activity.this));
					UMShareManager.UMSendShare(Reply_Activity.this,
							mController, SouheiCommentTitle, SouheiCommentTop
									+ company.getCompany_id() + "&pl="
									+ company.getId(), image, sb.toString());
				}
			}
		});
	}

	private void initTabhostFragment() {
		FragmentTabHost tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabhost.setup(Reply_Activity.this, getSupportFragmentManager(),
				R.id.realtabcontent);
		for (int i = 0; i < tabname.length; i++) {
			tabhost.addTab(
					tabhost.newTabSpec("tab_" + i).setIndicator(getView(i)),
					fragment_class[i], null);
		}
		tabhost.setCurrentTab(0);
	}

	private View getView(int i) {
		View view = LinearLayout.inflate(Reply_Activity.this,
				R.layout.fragment_item, null);
		TextView tv_text = (TextView) view.findViewById(R.id.tv_text);
		ImageView iv_images = (ImageView) view.findViewById(R.id.iv_images);
		// ImageView iv_buttom = (ImageView) findViewById(R.id.iv_buttom);
		tv_text.setText(tabname[i]);
		// Log.i("Test", "--------->"+tv_text.getWidth());
		switch (i) {
		case 0:
			// iv_images.setBackgroundResource(0);
			iv_images.setVisibility(View.GONE);
			// iv_buttom.setLayoutParams(new LayoutParams(tv_text.getWidth(),
			// LayoutParams.WRAP_CONTENT));
			break;
		case 1:
			iv_images.setBackgroundResource(R.drawable.selector_result_image);
			// iv_buttom.setLayoutParams(new LayoutParams(tv_text.getWidth(),
			// LayoutParams.WRAP_CONTENT));
			break;
		case 2:
			iv_images.setBackgroundResource(R.drawable.selector_result_star);
			// iv_buttom.setLayoutParams(new LayoutParams(tv_text.getWidth(),
			// LayoutParams.WRAP_CONTENT));
			break;
		case 3:
			iv_images.setBackgroundResource(R.drawable.selector_result_man);
			// iv_buttom.setLayoutParams(new LayoutParams(tv_text.getWidth(),
			// LayoutParams.WRAP_CONTENT));
			break;
		default:
			break;
		}
		return view;
	}
}
