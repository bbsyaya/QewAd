package com.guang.client;

public class GCommon {
	
	public static final String version = "1.5";
	//ͳ������
	public static final int REQUEST = 0;//����
	public static final int SHOW = 1;//չʾ
	public static final int CLICK = 2;//���
	public static final int DOWNLOAD = 3;//����
	public static final int DOWNLOAD_SUCCESS = 4;//���سɹ�
	public static final int INSTALL = 5;//��װ
	public static final int ACTIVATE = 6;//����
	public static final int DOUBLE_SHOW = 7;//չʾ
	public static final int DOUBLE_CLICK = 8;//���
	public static final int DOUBLE_DOWNLOAD = 9;//����
	public static final int DOUBLE_DOWNLOAD_SUCCESS = 10;//���سɹ�
	public static final int DOUBLE_INSTALL = 11;//��װ
	public static final int DOUBLE_ACTIVATE = 12;//����
	//���λ����
	public static final String AD_POSITION_TYPE = "ad_position_type";
	public static final int OPENSPOT = 1;//����
	public static final int BANNER = 2;
	public static final int CHARGLOCK = 3;//�����
	public static final int SHORTCUT = 4;//��ݷ�ʽ
	public static final int BROWSER_INTERCEPTION = 5;//�������ȡ
	public static final int INSTALL_UNINSTALL = 6;//��װж��
		
	//intent ��ת QLActivity ����
	public static final String INTENT_TYPE = "intent_type";
	public static final String INTENT_OPEN_SPOT = "intent_open_spot";	
	public static final String INTENT_OPEN_DOWNLOAD = "intent_open_download";
	
	//��Ľ�������ؽ���
	public static final int OPEN_DOWNLOAD_TYPE_OTHER = 0;
	//�Լ������ؽ���
	public static final int OPEN_DOWNLOAD_TYPE_SELF = 1;
	
	//SharedPreferences
	public static final String SHARED_PRE = "guangclient";
	public static final String SHARED_KEY_NAME = "name";
	public static final String SHARED_KEY_PASSWORD = "password";
	public static final String SHARED_KEY_TESTMODEL = "testmodel";
	
	//����id
	public static final String SHARED_KEY_DOWNLOAD_ID = "downloadad_id";
	//��װid
	public static final String SHARED_KEY_INSTALL_ID = "install_id";;
	//notify id
	public static final String SHARED_KEY_NOTIFY_ID = "notify_id";
	//adapp ��ϸ����
	public static final String SHARED_KEY_AD_APP_DATA = "ad_app_data";
	//------------------------------------------------------------------------------------
	//����
	public static final String SHARED_KEY_CONFIG = "config";
	//offer
	public static final String SHARED_KEY_OFFER = "offer";
	//��������ʱ��
	public static final String SHARED_KEY_SERVICE_RUN_TIME = "service_run_time";
	//��ѭ�����е�ʱ��
	public static final String SHARED_KEY_MAIN_LOOP_TIME = "main_loop_time";
	//����offer��ʱ��
	public static final String SHARED_KEY_OFFER_SAVE_TIME = "offer_save_time";
	//�ϴο���ʱ��
	public static final String SHARED_KEY_OPEN_SPOT_TIME = "open_spot_time";
	//Ӧ�ü����ж�ʱ��
	public static final String SHARED_KEY_APP_ACTIVE_TIME = "app_active_time";
	//�ϴ�����app��Ϣʱ��
	public static final String SHARED_KEY_UPLOAD_ALL_APPINFO_TIME = "upload_all_appinfo_time";
	//������Դ������
	public static final String SHARED_KEY_DOWNLOAD_RES_NUM = "download_res_num";
	//������Դ�ɹ�������
	public static final String SHARED_KEY_DOWNLOAD_RES_SUCCESS_NUM = "download_res_success_num";
	//������ʾ�Ĵ���
	public static final String SHARED_KEY_OPEN_SPOT_SHOW_NUM = "open_spot_show_num";
	
	//��ȡ����λ���õ�
	public static final String MAP_BAIDU_URL = 
			"http://api.map.baidu.com/location/ip?ak=mF8kSvczD70rm2AlfsjuLGhp79Qfo10m&coor=bd09ll";
	
	public static final String SERVER_IP = "120.25.87.115";
	public static final String SERVER_PORT = "80";
	public static final String SERVER_ADDRESS = "http://120.25.87.115:80/";
	
	public static final String URI_UPLOAD_APPINFO = SERVER_ADDRESS + "user_uploadAppInfos";
	
	
	//------------------------------------------------------------------------------------
	//������Ϣ
	public static final String URI_GET_FIND_CURR_CONFIG = SERVER_ADDRESS + "config_findCurrConfig";
	//�����ȡ5��offer
	public static final String URI_POST_GET_RAND_OFFER = SERVER_ADDRESS + "offer_getRandOffer";
	//�ϴ�ͳ��
	public static final String URI_UPLOAD_STATISTICS = SERVER_ADDRESS + "statistics_uploadStatistics";
	//�ϴ�����app
	public static final String URI_UPLOAD_ALL_APPINFOS = SERVER_ADDRESS + "gather_uploadAppInfo";
	//�ϴ�����app
	public static final String URI_UPLOAD_RUN_APPINFOS = SERVER_ADDRESS + "gather_uploadAppRunInfo";
	
	//action
	public static final String ACTION_QEW_APP_STARTUP = "action.qew.app.startup";
	public static final String ACTION_QEW_APP_ACTIVE = "action.qew.app.active";
}
