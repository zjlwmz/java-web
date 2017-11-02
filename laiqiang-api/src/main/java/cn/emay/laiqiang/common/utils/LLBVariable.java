package cn.emay.laiqiang.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LLBVariable {
	
	//100��
	public static String APPID = "c5a0a7e9e120e755e4fbe1baf4da79cb";
	public static String APPSECRET = "67af824103283cd798495e44755d5f5b";
	public static String RESPONSE_TYPE = "code";
	public static String GRANT_TYPE = "authorization_code";
	
	public static String GETCODEURL = "http://www.liuliangsc.com/interfaces/getcode.php";
	public static String CODECALLBACK = "http://hbres.m.cn/rest/server/emaycallback/codecallback";
	
	public static String GETTOKENURL = "http://www.liuliangsc.com/interfaces/gettoken.php";
	public static String TOKENCALLBACK = "http://hbres.m.cn/rest/server/emaycallback/tokencallback";
	
	public static String CZURL = "http://www.liuliangsc.com/interfaces/plat_oper.php";
	public static String CZCALLBACK = "http://hbres.m.cn/rest/server/emaycallback/rechargecallback";
	public static String QUERYSTATUS = "http://www.liuliangsc.com/interfaces/plat_query.php";
	
	
	
	
	
//	public static String GETCODEURL = "http://test.liuliangsc.com/interfaces/getcode.php";
//	public static String CODECALLBACK = "http://www1.m.cn/rest/server/emaycallback/codecallback";
//	
//	public static String GETTOKENURL = "http://test.liuliangsc.com/interfaces/gettoken.php";
//	public static String TOKENCALLBACK = "http://www1.m.cn/rest/server/emaycallback/tokencallback";
//	
//	public static String CZURL = "http://test.liuliangsc.com/interfaces/plat_oper.php";
//	public static String CZCALLBACK = "http://www1.m.cn/rest/server/emaycallback/rechargecallback";
//	public static String QUERYSTATUS = "http://test.liuliangsc.com/interfaces/plat_query.php";
	
	
	//��ʽ��ַ
	//String url = "http://flow.b2m.cn:80/outerservice/request";
	//���Ե�ַ ����    
	//String url = "http://124.127.89.45:9999/outerservice/request";
	//���Ե�ַ ����
//	String url = "http://172.16.1.39:9999/outerservice/request";
	public static String CZURL_EMAY = "http://flow.b2m.cn:80/outerservice/request";
	public static String QUERYURL_EMAY = "http://flow.b2m.cn:80/outerservice/query";
	public static String APPID_EMAY = "2c05e573-96cb-4313-9213-3bda302956d1";
//	public static String APPID_EMAY = "7ca50ce4-6b1f-47f0-9b75-d4dc0faf283b";
	public static String TOKEN_EMAY = "6fa427e9a901476a";
//	public static String TOKEN_EMAY = "4190a0daf26244ef";
	
	public static String BUY_CZURL_EMAY_OLD= "http://101.200.174.41:80/outerservice/request";
	public static String BUY_QUERYURL_EMAY_OLD = "http://101.200.174.41:80/outerservice/query";
	public static String BUY_APPID_EMAY_OLD = "e2cd559e-f334-487a-809e-1f3dfe69084d";
//	public static String APPID_EMAY = "7ca50ce4-6b1f-47f0-9b75-d4dc0faf283b";
	public static String BUY_TOKEN_EMAY_OLD = "88cd26b8a5da47fc";
//	public static String TOKEN_EMAY = "4190a0daf26244ef";
	
	public static String BUY_CZURL_EMAY = "http://flow.b2m.cn:80/outerservice/request";
	public static String BUY_QUERYURL_EMAY = "http://flow.b2m.cn:80/outerservice/query";
	public static String BUY_APPID_EMAY = "11f9eb0f-25ee-477f-8347-3e7d02df9aa8";
//	public static String APPID_EMAY = "7ca50ce4-6b1f-47f0-9b75-d4dc0faf283b";
	public static String BUY_TOKEN_EMAY = "732ac2badcec472b";
	
	
	
	public static Integer NORMAL = 1;//����
	public static Integer SUCCESS = 2;//�ɹ�
	public static Integer FAILURE = 3;//ʧ��
	public static Integer AGAIN = 4;//��һ��
	
	
	public static Map<String, Integer> LLBTYPE = new HashMap<String, Integer>();
	public static Map<String, String> ERRORCODE = new HashMap<String, String>();
	public static Map<String, String> OPERATORLIST = new HashMap<String, String>();
	public static Map<Integer, String> LTFLOWTYPE = new HashMap<Integer, String>();
	public static Map<Integer, String> YDFLOWTYPE = new HashMap<Integer, String>();
	public static Map<Integer, String> DXFLOWTYPE = new HashMap<Integer, String>();
	public static Map<String, List<String[]>> FLOWPACKAGE = new HashMap<String, List<String[]>>();
	public static Map<String, List<String[]>> BUYFLOWPACKAGE = new HashMap<String, List<String[]>>();
	public static Map<String, Integer> areamap = new HashMap<String, Integer>();
	
	//�ƶ���CMCC��1����ͨ��CUCC��2�����ţ�CTCC��3
//	YD01	�ƶ�10M3Ԫ��
//	YD02	�ƶ�30M5Ԫ��
//	YD03	�ƶ�70M10Ԫ��
//	YD04	�ƶ�150M20Ԫ��
//	YD05	�ƶ�500M30Ԫ��
//	YD06	�ƶ�1024M50Ԫ��
//	YD07	�ƶ�2048M70Ԫ��
//	YD08	�ƶ�3072M100Ԫ��
//	YD09	�ƶ�4096M130Ԫ��
//	YD10	�ƶ�6144M180Ԫ��
//	YD11	�ƶ�11264M280Ԫ��
	
	
//	LT01	��ͨ50M6Ԫ��
//	LT02	��ͨ200M15Ԫ��
//	DX01	����5M1Ԫ��
//	DX02	����10M2Ԫ��
//	DX03	����30M5Ԫ��
//	DX04	����50M7Ԫ��
//	DX05	����100M10Ԫ��
//	DX06	����200M15Ԫ��
//	DX07	����500M30Ԫ��
//	DX08	����1024M50Ԫ��

//	���ţ�
//	00000	 ��ֵ�ɹ�
//	10001	���벻����
//	10002	�Ƿ�����
//	10003	�Ƿ�����
//	10005	ǩ����֤ʧ��
//	10006	�Ƿ�������
//	10007	�Ƿ�����Ʒ
//		10008	�Ƿ�������ˮ��
//		10009	�Ƿ�����
//		10010	Ƿ��ͣ��
//		10012	�����Ѷ����ע��
//		10013	�������ͻ�
//		10015	ϵͳ�쳣
//		10016	�����ظ�����
//		10019	�ײͻ���
//		10020	������Ƿ��ͣ��
//		10022	ҵ�񻥳�
//		10024	ҵ�񻥳�
//		10026	������������
//		10028	���δ��ʼ
//		10029	������ѽ���
//		10030	�Ƿ���ͬ���
//		10040	��Ȩ�޵��ø÷���
//		10044	����ÿСʱ�ܷ��ʸýӿڵĴ���
//		10033	�û�����;�������޷�����
//		10046	��������ֹͣ
//		10031	��Ʒδ����
//		10032	����Ʒ������
//		10054	�����쳣
//		10058	�ͻ�ҵ������
//		10062	ʡ��CRM δ�ҵ��ñ���������
//		10063	�û�״̬�쳣
//		10065	ϵͳ�쳣
//	   10067	��Կ����������ȷ
//		10068	��ֵ��������ʡ�����ֵ����ʡ�ݲ�һ��
//		10073	ϵͳæ
//		10074	�û���Ϣ������
//		10109	�����ڶ�Ӧ��ҵ��չʡ��
//		10110	ʣ������˻�����
//		10111	�û�״̬�쳣
//		10115	�Ҳ�����Ӧ��Ӫ��
//		10117	���üƷѽӿ�ʧ��
//		10118	CRM ����Ʒ�۸���δ����
//		10119	��ͬ���㷽ʽ������
//		10120	IMSI ���鲻����Ӧ�ֻ�����
//		10122	��ͬҵ��Χ��δ����
//		10225	�����ײ�
//		90002	CRM �ڲ�����
//		99999	��������
//	��ͨ
//		0000	��ֵ�ɹ�
//		0001	���������ѽ���
//		1000	ϵͳ�쳣���뾡����ϵ�����Ӫ��Ա
//		1001	��������
//		1002	����ǩ����֤ʧ��
//		1003	������񲻴���
//		1004	�˵�����Ʒδ��ͨ
//		1005	IP
//		1006	����ʱ�����
//		1007	���������ظ��ύ
//		2001	�û�������
//		2002	�û���ͣ��
//		2003	�û�״̬�쳣��Ƿ�ѣ�
//		2004	�û��Ǻ�����
//		2005	�ñʶ�������ɶ���
//		2006	������ϵ������
//		2007	���Ѷ�����������Ʒ��ͻ
//		2008	���������Ĳ�Ʒ
//		2009	��������
//		2010	�û��ײͲ��ܶ�����ҵ��
//		2011	����ԭ��
//		2012	����ʧ��
//		2013	�õ����ݲ�֧��2G�û�����
//		2014	�޸���Ĳ�Ʒ�������û����¶����������ѵ���������ޣ�
//		2015	��ʱ�޲�Ʒ���������Ԥ���û�
//		2016	2G/3G�ں��û���������
//		2017	�û�״̬�쳣�����ϲ�ȫ��
//		2018	�û�״̬�쳣��������Ч�ڣ�
//		2019	�û����ײͱ�������²��ܶ���
//		2020	�û���������Ϊ��ʼ����
//		2021	�û������ڴ�����
//		2022	�õ����ݲ�֧��4G�û�����
//		2023	�û�״̬�쳣��δ�鵽�û����ͣ�
//		2024	�õ����ݲ�֧��3G�û�����
//		2025	�û�����������������
//		2026	��Ʒ�������ظ�����
//		2027	ʡ��ϵͳ����
//		2028	ʡ����ĩ�³�������
//		2029	ʡ��ϵͳ��ʱ�����Ժ�����
//		2030	�û����ö�Ȳ���
//		3001	appkey��֤��Ч
//		3002	�˻����㣬�뾡����ϵ�����Ӫ��Ա
//		3003	�˻����㣬�뾡����ϵ�����Ӫ��Ա
//		3004	��Ʒ�����Ч
//		3005	ʣ��Ķ�����ϵ��Ŀ����
//		3006	�˵�����Ʒδ��ͨ
//		3007	���Ϣ������
//		3008	��ѹ���
//		3009	��������˲�Ʒ
//		3010	û��pkgNo�Ķ���Ȩ��
//		3011	������Ϣ�Ѵ��ڣ���δ�ύ����
//		4001	�ͻ�����ˮ�������ظ�����
//		9001	�ȴ���Ӧ��Ϣ��ʱ
//		9002	�����쳣
//		9003	���ݿ������쳣
//		9101	ϵͳ����
//		9102	�����쳣
//		9999	����ԭ��
//	�ƶ�
//	   000000  ��ֵ�ɹ�
//		01	�û���ָ����������Ч��Ա
//		02	�û��ײ�ö��ֵ����
//		03	ʡ��˾��ͨ���Ӱ�ʧ��
//		99	��������

	
	static{
		//������
//		LLBTYPE.put("LT01", 50); //LT01	��ͨ50M6Ԫ��
//		LLBTYPE.put("LT02", 200); //LT02	��ͨ200M15Ԫ��
//		LLBTYPE.put("YD01", 10); //YD01	�ƶ�10M3Ԫ��
//		LLBTYPE.put("YD02", 30); //YD02	�ƶ�30M5Ԫ��
//		LLBTYPE.put("YD03", 70); //YD03	�ƶ�70M10Ԫ��
//		LLBTYPE.put("YD04", 150); //YD04	�ƶ�150M20Ԫ��
//		LLBTYPE.put("YD05", 500); //YD05	�ƶ�280M30Ԫ��
////		LLBTYPE.put("YD05", 280); //YD05	�ƶ�280M30Ԫ��
////		LLBTYPE.put("YD06", 600); //YD06	�ƶ�600M50Ԫ��
////		LLBTYPE.put("YD07", 1024); //YD07	�ƶ�1024M70Ԫ��
////		LLBTYPE.put("YD08", 2048); //YD08	�ƶ�2048M100Ԫ��
//		LLBTYPE.put("DX01", 5); //DX01	����5M1Ԫ��
//		LLBTYPE.put("DX02", 10); //DX02	����10M2Ԫ��
//		LLBTYPE.put("DX03", 30); //DX03	����30M5Ԫ��
//		LLBTYPE.put("DX04", 50); //DX04	����50M7Ԫ��
//		LLBTYPE.put("DX05", 100); //DX05	����100M10Ԫ��
//		LLBTYPE.put("DX06", 200);	//DX06	����200M15Ԫ��
//		LLBTYPE.put("DX07", 500);	//DX07	����500M30Ԫ��
////		LLBTYPE.put("DX08", 1024);	//DX08	����1024M50Ԫ��
		
		//emay��
//		LLBTYPE.put("DX1", 5); 
//		LLBTYPE.put("DX2", 10); 
		LLBTYPE.put("DX3", 30); 
		LLBTYPE.put("DX4", 50); 
		LLBTYPE.put("DX5", 100); 
		LLBTYPE.put("DX6", 200); 
		LLBTYPE.put("DX7", 500); 
		LLBTYPE.put("DX8", 1024);
		
		LLBTYPE.put("LT24", 20); 
		LLBTYPE.put("LT9", 50); 
		LLBTYPE.put("LT25", 100);
		LLBTYPE.put("LT10", 200);
		LLBTYPE.put("LT26", 500);
		
		LLBTYPE.put("YD11", 10); 
		LLBTYPE.put("YD12", 30);
		LLBTYPE.put("YD13", 70); 
		LLBTYPE.put("YD14", 150);
		LLBTYPE.put("YD15", 500); 
		LLBTYPE.put("YD16", 1024);
		LLBTYPE.put("YD17", 2048); 
//		LLBTYPE.put("YD18", 3072);
//		LLBTYPE.put("YD19", 4096); 
//		LLBTYPE.put("YD20", 6144);
//		LLBTYPE.put("YD21", 11264); 
//		LLBTYPE.put("YD22", 2);
		LLBTYPE.put("YD23", 700); 
		
		DXFLOWTYPE.put(5,"DX1");   
		DXFLOWTYPE.put(10,"DX2");  
		DXFLOWTYPE.put(30,"DX3");  
		DXFLOWTYPE.put(50,"DX4");  
		DXFLOWTYPE.put(100,"DX5"); 
		DXFLOWTYPE.put(200,"DX6"); 
		DXFLOWTYPE.put(500,"DX7"); 
		DXFLOWTYPE.put(1024,"DX8");

		LTFLOWTYPE.put(20,"LT24"); 
		LTFLOWTYPE.put(50,"LT9"); 
		LTFLOWTYPE.put(100,"LT25");
		LTFLOWTYPE.put(200,"LT10");
		LTFLOWTYPE.put(500,"LT26");
		
		
		YDFLOWTYPE.put(2,"YD22");
		YDFLOWTYPE.put(10,"YD11"); 
		YDFLOWTYPE.put(30,"YD12");
		YDFLOWTYPE.put(70,"YD13"); 
		YDFLOWTYPE.put(150,"YD14");
		YDFLOWTYPE.put(500,"YD15"); 
		YDFLOWTYPE.put(700,"YD23"); 
		YDFLOWTYPE.put(1024,"YD16");
		YDFLOWTYPE.put(2*1024,"YD17"); 
		YDFLOWTYPE.put(3*1024,"YD18");
		YDFLOWTYPE.put(4*1024,"YD19"); 
		YDFLOWTYPE.put(5*1024,"YD20");
		YDFLOWTYPE.put(11*1024,"YD21" ); 
		
		
		ERRORCODE.put("00000", "��ֵ�ɹ�");
		ERRORCODE.put("10001", "���벻����");
		ERRORCODE.put("10002", "�Ƿ�����");
		ERRORCODE.put("10003", "�Ƿ�����");
		ERRORCODE.put("10005", "ǩ����֤ʧ��");
		ERRORCODE.put("10006", "�Ƿ�������");
		ERRORCODE.put("10007", "�Ƿ�����Ʒ");
		ERRORCODE.put("10008", "�Ƿ�������ˮ��");
		ERRORCODE.put("10009", "�Ƿ�����");
		ERRORCODE.put("10010", "Ƿ��ͣ��");
		ERRORCODE.put("10012", "�����Ѷ����ע��");
		ERRORCODE.put("10013", "�������ͻ�");
		ERRORCODE.put("10015", "ϵͳ�쳣");
		ERRORCODE.put("10016", "�����ظ�����");
		ERRORCODE.put("10019", "�ײͻ���");
		ERRORCODE.put("10020", "������Ƿ��ͣ��");
		ERRORCODE.put("10022", "ҵ�񻥳�");
		ERRORCODE.put("10024", "ҵ�񻥳�");
		ERRORCODE.put("10026", "������������");
		ERRORCODE.put("10028", "���δ��ʼ");
		ERRORCODE.put("10029", "������ѽ���");
		ERRORCODE.put("10030", "�Ƿ���ͬ���");
		ERRORCODE.put("10040", "��Ȩ�޵��ø÷���");
		ERRORCODE.put("10044", "����ÿСʱ�ܷ��ʸýӿڵĴ���");
		ERRORCODE.put("10033", "�û�����;�������޷�����");
		ERRORCODE.put("10046", "��������ֹͣ");
		ERRORCODE.put("10031", "��Ʒδ����");
		ERRORCODE.put("10032", "����Ʒ������");
		ERRORCODE.put("10054", "�����쳣");
		ERRORCODE.put("10058", "�ͻ�ҵ������");
		ERRORCODE.put("10062", "ʡ��CRM δ�ҵ��ñ���������");
		ERRORCODE.put("10063", "�û�״̬�쳣");
		ERRORCODE.put("10065", "ϵͳ�쳣");
		ERRORCODE.put("10067", "��Կ����������ȷ");
		ERRORCODE.put("10068", "��ֵ��������ʡ�����ֵ����ʡ�ݲ�һ��");
		ERRORCODE.put("10073", "ϵͳæ");
		ERRORCODE.put("10074", "�û���Ϣ������");
		ERRORCODE.put("10109", "�����ڶ�Ӧ��ҵ��չʡ��");
		ERRORCODE.put("10110", "ʣ������˻�����");
		ERRORCODE.put("10111", "�û�״̬�쳣");
		ERRORCODE.put("10115", "�Ҳ�����Ӧ��Ӫ��");
		ERRORCODE.put("10117", "���üƷѽӿ�ʧ��");
		ERRORCODE.put("10118", "CRM ����Ʒ�۸���δ����");
		ERRORCODE.put("10119", "��ͬ���㷽ʽ������");
		ERRORCODE.put("10120", "IMSI ���鲻����Ӧ�ֻ�����");
		ERRORCODE.put("10122", "��ͬҵ��Χ��δ����");
		ERRORCODE.put("10225", "�����ײ�");
		ERRORCODE.put("90002", "CRM �ڲ�����");
		ERRORCODE.put("99999", "��������");
		
		
		ERRORCODE.put("0000", "��ֵ�ɹ�");
		ERRORCODE.put("0001", "���������ѽ���");
		ERRORCODE.put("1000", "ϵͳ�쳣���뾡����ϵ�����Ӫ��Ա");
		ERRORCODE.put("1001", "��������");
		ERRORCODE.put("1002", "����ǩ����֤ʧ��");
		ERRORCODE.put("1003", "������񲻴���");
		ERRORCODE.put("1004", "�˵�����Ʒδ��ͨ");
		ERRORCODE.put("1005", "IP");
		ERRORCODE.put("1006", "����ʱ�����");
		ERRORCODE.put("1007", "���������ظ��ύ");
		ERRORCODE.put("2001", "�û�������");
		ERRORCODE.put("2002", "�û���ͣ��");
		ERRORCODE.put("2003", "�û�״̬�쳣��Ƿ�ѣ�");
		ERRORCODE.put("2004", "�û��Ǻ�����");
		ERRORCODE.put("2005", "�ñʶ�������ɶ���");
		ERRORCODE.put("2006", "������ϵ������");
		ERRORCODE.put("2007", "���Ѷ�����������Ʒ��ͻ");
		ERRORCODE.put("2008", "���������Ĳ�Ʒ");
		ERRORCODE.put("2009", "��������");
		ERRORCODE.put("2010", "�û��ײͲ��ܶ�����ҵ��");
		ERRORCODE.put("2011", "����ԭ��");
		ERRORCODE.put("2012", "����ʧ��");
		ERRORCODE.put("2013", "�õ����ݲ�֧��2G�û�����");
		ERRORCODE.put("2014", "�޸���Ĳ�Ʒ�������û����¶����������ѵ���������ޣ�");
		ERRORCODE.put("2015", "��ʱ�޲�Ʒ���������Ԥ���û�");
		ERRORCODE.put("2016", "2G/3G�ں��û���������");
		ERRORCODE.put("2017", "�û�״̬�쳣�����ϲ�ȫ��");
		ERRORCODE.put("2018", "�û�״̬�쳣��������Ч�ڣ�");
		ERRORCODE.put("2019", "�û����ײͱ�������²��ܶ���");
		ERRORCODE.put("2020", "�û���������Ϊ��ʼ����");
		ERRORCODE.put("2021", "�û������ڴ�����");
		ERRORCODE.put("2022", "�õ����ݲ�֧��4G�û�����");
		ERRORCODE.put("2023", "�û�״̬�쳣��δ�鵽�û����ͣ�");
		ERRORCODE.put("2024", "�õ����ݲ�֧��3G�û�����");
		ERRORCODE.put("2025", "�û�����������������");
		ERRORCODE.put("2026", "��Ʒ�������ظ�����");
		ERRORCODE.put("2027", "ʡ��ϵͳ����");
		ERRORCODE.put("2028", "ʡ����ĩ�³�������");
		ERRORCODE.put("2029", "ʡ��ϵͳ��ʱ�����Ժ�����");
		ERRORCODE.put("2030", "�û����ö�Ȳ���");
		ERRORCODE.put("3001", "appkey��֤��Ч");
		ERRORCODE.put("3002", "�˻����㣬�뾡����ϵ�����Ӫ��Ա");
		ERRORCODE.put("3003", "�˻����㣬�뾡����ϵ�����Ӫ��Ա");
		ERRORCODE.put("3004", "��Ʒ�����Ч");
		ERRORCODE.put("3005", "ʣ��Ķ�����ϵ��Ŀ����");
		ERRORCODE.put("3006", "�˵�����Ʒδ��ͨ");
		ERRORCODE.put("3007", "���Ϣ������");
		ERRORCODE.put("3008", "��ѹ���");
		ERRORCODE.put("3009", "��������˲�Ʒ");
		ERRORCODE.put("3010", "û��pkgNo�Ķ���Ȩ��");
		ERRORCODE.put("3011", "������Ϣ�Ѵ��ڣ���δ�ύ����");
		ERRORCODE.put("4001", "�ͻ�����ˮ�������ظ�����");
		ERRORCODE.put("9001", "�ȴ���Ӧ��Ϣ��ʱ");
		ERRORCODE.put("9002", "�����쳣");
		ERRORCODE.put("9003", "���ݿ������쳣");
		ERRORCODE.put("9101", "ϵͳ����");
		ERRORCODE.put("9102", "�����쳣");
		ERRORCODE.put("9999", "����ԭ��");
		
		ERRORCODE.put("000000", "��ֵ�ɹ�");
		ERRORCODE.put("01", "�û���ָ����������Ч��Ա");
		ERRORCODE.put("02", "�û��ײ�ö��ֵ����");
		ERRORCODE.put("03", "ʡ��˾��ͨ���Ӱ�ʧ��");
		ERRORCODE.put("99", "��������");
		
		OPERATORLIST.put("158","CMCC");
		OPERATORLIST.put("159","CMCC");
		OPERATORLIST.put("147","CMCC");
		OPERATORLIST.put("178","CMCC");
		OPERATORLIST.put("182","CMCC");
		OPERATORLIST.put("183","CMCC");
		OPERATORLIST.put("184","CMCC");
		OPERATORLIST.put("134","CMCC");
		OPERATORLIST.put("187","CMCC");
		OPERATORLIST.put("135","CMCC");
		OPERATORLIST.put("188","CMCC");
		OPERATORLIST.put("136","CMCC");
		OPERATORLIST.put("137","CMCC");
		OPERATORLIST.put("138","CMCC");
		OPERATORLIST.put("139","CMCC");
		OPERATORLIST.put("150","CMCC");
		OPERATORLIST.put("151","CMCC");
		OPERATORLIST.put("152","CMCC");
		OPERATORLIST.put("157","CMCC");
		OPERATORLIST.put("153","CTCC");
		OPERATORLIST.put("180","CTCC");
		OPERATORLIST.put("189","CTCC");
		OPERATORLIST.put("149","CTCC");
		OPERATORLIST.put("181","CTCC");
		OPERATORLIST.put("170","CTCC");
		OPERATORLIST.put("177","CTCC");
		OPERATORLIST.put("133","CTCC");
		OPERATORLIST.put("145","CUCC");
		OPERATORLIST.put("130","CUCC");
		OPERATORLIST.put("131","CUCC");
		OPERATORLIST.put("132","CUCC");
		OPERATORLIST.put("155","CUCC");
		OPERATORLIST.put("156","CUCC");
		OPERATORLIST.put("185","CUCC");
		OPERATORLIST.put("186","CUCC");
		OPERATORLIST.put("176","CUCC");

		List<String[]> cmcclist =new ArrayList<String[]>();
		String[] strs = new String[]{"YD13","70"};
		cmcclist.add(strs);
		strs = new String[]{"YD14", "150"};
		cmcclist.add(strs);
		strs = new String[]{"YD15", "500"};
		cmcclist.add(strs);
		FLOWPACKAGE.put("CMCC", cmcclist);
		
		List<String[]> ctcclist =new ArrayList<String[]>();
		strs = new String[]{"DX4", "50"};
		ctcclist.add(strs);
		strs = new String[]{"DX5", "100"};
		ctcclist.add(strs);
		strs = new String[]{"DX6", "200"};
		ctcclist.add(strs);
		strs = new String[]{"DX7", "500"};
		ctcclist.add(strs);
		FLOWPACKAGE.put("CTCC", ctcclist);
		
		List<String[]> cucclist =new ArrayList<String[]>();
		strs = new String[]{"LT9", "50"};
		cucclist.add(strs);
		strs = new String[]{"LT25", "100"};
		cucclist.add(strs);
		strs = new String[]{"LT10", "200"};
		cucclist.add(strs);
		strs = new String[]{"LT26", "500"};
		cucclist.add(strs);
		FLOWPACKAGE.put("CUCC", cucclist);
		
		
		
		
		
		cmcclist =new ArrayList<String[]>();
		strs = new String[]{"YD22", "2MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD11", "10MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD12", "30MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD13", "70MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD14", "150MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD15", "500MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD23", "700MB,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD16", "1G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD17", "2G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD18", "3G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD19", "4G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD20", "5G,20"};
		cmcclist.add(strs);
		strs = new String[]{"YD21", "11G,20"};
		cmcclist.add(strs);
		BUYFLOWPACKAGE.put("CMCC", cmcclist);
		
		
		
		ctcclist =new ArrayList<String[]>();
		strs = new String[]{"DX1", "5MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX2", "10MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX3", "30MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX4", "50MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX5", "100MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX6", "200MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX7", "500MB,20"};
		ctcclist.add(strs);
		strs = new String[]{"DX8", "1G,20"};
		ctcclist.add(strs);
		BUYFLOWPACKAGE.put("CTCC", ctcclist);
		
		cucclist =new ArrayList<String[]>();
		strs = new String[]{"LT9", "50MB,20"};
		cucclist.add(strs);
		strs = new String[]{"LT25", "100MB,20"};
		cucclist.add(strs);
		strs = new String[]{"LT10", "200MB,20"};
		cucclist.add(strs);
		strs = new String[]{"LT26", "500MB,20"};
		cucclist.add(strs);
		BUYFLOWPACKAGE.put("CUCC", cucclist);
		
		areamap.put("����", 1);
		areamap.put("���", 2);
		areamap.put("�Ϻ�",3);
		areamap.put("����",4	);
		areamap.put("�ӱ�",5	);
		areamap.put("����",6	);
		areamap.put("����",7	);
		areamap.put("����",8	);
		areamap.put("������",9);
		areamap.put("����",10);
		areamap.put("����",11);
		areamap.put("ɽ��",12);
		areamap.put("�½�",13);
		areamap.put("����",14);
		areamap.put("�㽭",15);
		areamap.put("����",16);
		areamap.put("����",17);
		areamap.put("����",18);
		areamap.put("����",19);
		areamap.put("ɽ��",20);
		areamap.put("���ɹ�",21);
		areamap.put("����",22);
		areamap.put("����",23);
		areamap.put("����",24);
		areamap.put("����",25);
		areamap.put("�㶫",26);
		areamap.put("�ຣ",27);
		areamap.put("����",28);
		areamap.put("�Ĵ�",29);
		areamap.put("����",30);
		areamap.put("����",31);
		
	}
	
	//��̨������˷��ʽӿ�
	public static String USER_TRAFFIC = "http://www1.m.cn/appinterface/server/updatememberbalance";
	
	
	public static List<String> flowsplit(String oper,int userflow){
		Map<String,Integer> tempmap = new HashMap<String, Integer>();
		List<Integer> intlist = new ArrayList<Integer>();
		for(String key: LLBTYPE.keySet()){
			if(key.startsWith(oper)){
				tempmap.put(key, LLBTYPE.get(key));
				intlist.add(LLBTYPE.get(key));
			}
		}
		Integer[] ints = new Integer[intlist.size()];
		for(int i = 0;i< intlist.size();i++){
			ints[i] = intlist.get(i);
		}
		Arrays.sort(ints);
		List<Integer> flowlist = new ArrayList<Integer>();
		int minflow =ints[0]; 
		while (userflow!=0) {
			for(int i=ints.length-1;i>=0;i--){
				if(userflow>=ints[i]){
					userflow -= ints[i];
					flowlist.add(ints[i]);
					break;
				}else if(userflow < minflow){
					return null;
				}
			}
		}
		List<String> retlist= new ArrayList<String>();
		for(String key: tempmap.keySet()){
			for(int flow:flowlist){
				if(LLBTYPE.get(key)==flow){
					retlist.add(key);
				}
			}
		}
		return retlist;
	
	}
	
	public static void main(String[] args){
		
		//System.out.println(LLBTYPE.get("YD12"));
	}
	
	
}																															   
