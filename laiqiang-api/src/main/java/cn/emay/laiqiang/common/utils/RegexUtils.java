/*
 *    Copyright 2012-2013 The Haohui Network Corporation
 */
package cn.emay.laiqiang.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @project baidamei
 * @author cevencheng <cevencheng@gmail.com> www.zuidaima.com
 * @create 2012-11-15 ����4:54:42
 */
public class RegexUtils {

	/**
	 * ƥ��ͼ��
	 * 
	 * 
	 * ��ʽ: /���·��/�ļ���.��׺ (��׺Ϊgif,dmp,png)
	 * 
	 * ƥ�� : /forum/head_icon/admini2005111_ff.gif �� admini2005111.dmp
	 * 
	 * 
	 * ��ƥ��: c:/admins4512.gif
	 * 
	 */
	public static final String ICON_REGEXP = "^(/{0,1}//w){1,}//.(gif|dmp|png|jpg)$|^//w{1,}//.(gif|dmp|png|jpg)$";

	/**
	 * ƥ��email��ַ
	 * 
	 * 
	 * ��ʽ: XXX@XXX.XXX.XX
	 * 
	 * ƥ�� : foo@bar.com �� foobar@foobar.com.au
	 * 
	 * ��ƥ��: foo@bar �� $$$@bar.com
	 * 
	 */
	public static final String EMAIL_REGEXP = "(?://w[-._//w]*//w@//w[-._//w]*//w//.//w{2,3}$)";

	/**
	 * ƥ��ƥ�䲢��ȡurl
	 * 
	 * 
	 * ��ʽ: XXXX://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX
	 * 
	 * ƥ�� : http://www.suncer.com ��news://www
	 * 
	 * ��ƥ��: c:/window
	 * 
	 */
	public static final String URL_REGEXP = "(//w+)://([^/:]+)(://d*)?([^#//s]*)";

	/**
	 * ƥ�䲢��ȡhttp
	 * 
	 * ��ʽ: http://XXX.XXX.XXX.XX/XXX.XXX?XXX=XXX �� ftp://XXX.XXX.XXX ��
	 * https://XXX
	 * 
	 * ƥ�� : http://www.suncer.com:8080/index.html?login=true
	 * 
	 * ��ƥ��: news://www
	 * 
	 */
	public static final String HTTP_REGEXP = "(http|https|ftp)://([^/:]+)(://d*)?([^#//s]*)";

	/**
	 * ƥ������
	 * 
	 * 
	 * ��ʽ(��λ��Ϊ0): XXXX-XX-XX�� XXXX-X-X
	 * 
	 * 
	 * ��Χ:1900--2099
	 * 
	 * 
	 * ƥ�� : 2005-04-04
	 * 
	 * 
	 * ��ƥ��: 01-01-01
	 * 
	 */
	public static final String DATE_BARS_REGEXP = "^((((19){1}|(20){1})\\d{2})|\\d{2})-[0,1]?\\d{1}-[0-3]?\\d{1}$";

	/**
	 * ƥ������
	 * 
	 * 
	 * ��ʽ: XXXX/XX/XX
	 * 
	 * 
	 * ��Χ:
	 * 
	 * 
	 * ƥ�� : 2005/04/04
	 * 
	 * 
	 * ��ƥ��: 01/01/01
	 * 
	 */
	public static final String DATE_SLASH_REGEXP = "^[0-9]{4}/(((0[13578]|(10|12))/(0[1-9]|[1-2][0-9]|3[0-1]))|(02-(0[1-9]|[1-2][0-9]))|((0[469]|11)/(0[1-9]|[1-2][0-9]|30)))$";

	/**
	 * ƥ��绰
	 * 
	 * 
	 * ��ʽΪ: 0XXX-XXXXXX(10-13λ��λ����Ϊ0) ��0XXX XXXXXXX(10-13λ��λ����Ϊ0) ��
	 * 
	 * (0XXX)XXXXXXXX(11-14λ��λ����Ϊ0) �� XXXXXXXX(6-8λ��λ��Ϊ0) ��
	 * XXXXXXXXXXX(11λ��λ��Ϊ0)
	 * 
	 * 
	 * ƥ�� : 0371-123456 �� (0371)1234567 �� (0371)12345678 �� 010-123456 ��
	 * 010-12345678 �� 12345678912
	 * 
	 * 
	 * ��ƥ��: 1111-134355 �� 0123456789
	 * 
	 */
	public static final String PHONE_REGEXP = "^(?:0[0-9]{2,3}[-//s]{1}|//(0[0-9]{2,4}//))[0-9]{6,8}$|^[1-9]{1}[0-9]{5,7}$|^[1-9]{1}[0-9]{10}$";

	/**
	 * ƥ�����֤
	 * 
	 * ��ʽΪ: XXXXXXXXXX(10λ) �� XXXXXXXXXXXXX(13λ) �� XXXXXXXXXXXXXXX(15λ) ��
	 * XXXXXXXXXXXXXXXXXX(18λ)
	 * 
	 * ƥ�� : 0123456789123
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String ID_CARD_REGEXP = "^//d{10}|//d{13}|//d{15}|//d{18}$";

	/**
	 * ƥ���ʱ����
	 * 
	 * ��ʽΪ: XXXXXX(6λ)
	 * 
	 * ƥ�� : 012345
	 * 
	 * ��ƥ��: 0123456
	 * 
	 */
	public static final String ZIP_REGEXP = "^[0-9]{6}$";// ƥ���ʱ����

	/**
	 * �����������ַ���ƥ�� (�ַ����в��������� ��ѧ�η���^ ������' ˫����" �ֺ�; ����, ñ��: ��ѧ����- �Ҽ�����> �������< ��б��/
	 * ���ո�,�Ʊ��,�س����� )
	 * 
	 * ��ʽΪ: x �� һ��һ�ϵ��ַ�
	 * 
	 * ƥ�� : 012345
	 * 
	 * ��ƥ��: 0123456 // ;,:-<>//s].+$";//
	 */
	public static final String NON_SPECIAL_CHAR_REGEXP = "^[^'/";
	// ƥ���ʱ����

	/**
	 * ƥ��Ǹ������������� + 0)
	 */
	public static final String NON_NEGATIVE_INTEGERS_REGEXP = "^//d+$";

	/**
	 * ƥ�䲻������ķǸ������������� > 0)
	 */
	public static final String NON_ZERO_NEGATIVE_INTEGERS_REGEXP = "^[1-9]+//d*$";

	/**
	 * 
	 * ƥ��������
	 * 
	 */
	public static final String POSITIVE_INTEGER_REGEXP = "^[0-9]*[1-9][0-9]*$";

	/**
	 * 
	 * ƥ����������������� + 0��
	 * 
	 */
	public static final String NON_POSITIVE_INTEGERS_REGEXP = "^((-//d+)|(0+))$";

	/**
	 * 
	 * ƥ�为����
	 * 
	 */
	public static final String NEGATIVE_INTEGERS_REGEXP = "^-[0-9]*[1-9][0-9]*$";

	/**
	 * 
	 * ƥ������
	 * 
	 */
	public static final String INTEGER_REGEXP = "^-?//d+$";

	/**
	 * 
	 * ƥ��Ǹ����������������� + 0��
	 * 
	 */
	public static final String NON_NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^//d+(//.//d+)?$";

	/**
	 * 
	 * ƥ����������
	 * 
	 */
	public static final String POSITIVE_RATIONAL_NUMBERS_REGEXP = "^(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*))$";

	/**
	 * 
	 * ƥ��������������������� + 0��
	 * 
	 */
	public static final String NON_POSITIVE_RATIONAL_NUMBERS_REGEXP = "^((-//d+(//.//d+)?)|(0+(//.0+)?))$";

	/**
	 * 
	 * ƥ�为������
	 * 
	 */
	public static final String NEGATIVE_RATIONAL_NUMBERS_REGEXP = "^(-(([0-9]+//.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*//.[0-9]+)|([0-9]*[1-9][0-9]*)))$";

	/**
	 * 
	 * ƥ�両����
	 * 
	 */
	public static final String RATIONAL_NUMBERS_REGEXP = "^(-?//d+)(//.//d+)?$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ��ɵ��ַ���
	 * 
	 */
	public static final String LETTER_REGEXP = "^[A-Za-z]+$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ�Ĵ�д��ɵ��ַ���
	 * 
	 */
	public static final String UPWARD_LETTER_REGEXP = "^[A-Z]+$";

	/**
	 * 
	 * ƥ����26��Ӣ����ĸ��Сд��ɵ��ַ���
	 * 
	 */
	public static final String LOWER_LETTER_REGEXP = "^[a-z]+$";

	/**
	 * 
	 * ƥ�������ֺ�26��Ӣ����ĸ��ɵ��ַ���
	 * 
	 */
	public static final String LETTER_NUMBER_REGEXP = "^[A-Za-z0-9]+$";

	/**
	 * 
	 * ƥ�������֡�26��Ӣ����ĸ�����»�����ɵ��ַ���
	 * 
	 */
	public static final String LETTER_NUMBER_UNDERLINE_REGEXP = "^//w+$";

	
	
	
	/**
	 * ƥ����
	 * 1.�Ǹ��������룬��0��100��
	 * 2.��λС���ķǸ�����������
	 * 
	 */
	public static final String money = "^(([1-9]\\d{0,9})|0)(\\.\\d{1,2})?$";
	
	/**
	 * ��֤Email
	 * 
	 * @param email
	 *            email��ַ����ʽ��zhangsan@zuidaima.com��zhangsan@xxx.com.cn��
	 *            xxx�����ʼ�������
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+@\\w+\\.[a-z]+(\\.[a-z]+)?";
		return Pattern.matches(regex, email);
	}

	/**
	 * ��֤���֤����
	 * 
	 * @param idCard
	 *            �������֤����15λ��18λ�����һλ���������ֻ���ĸ
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkIdCard(String idCard) {
		String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
		return Pattern.matches(regex, idCard);
	}

	/**
	 * ��֤�ֻ����루֧�ֹ��ʸ�ʽ��+86135xxxx...���й��ڵأ���+00852137xxxx...���й���ۣ���
	 * 
	 * @param mobile
	 *            �ƶ�����ͨ��������Ӫ�̵ĺ����
	 *            <p>
	 *            �ƶ��ĺŶΣ�134(0-8)��135��136��137��138��139��147��Ԥ������TD��������
	 *            ��150��151��152��157��TDר�ã���158��159��187��δ���ã���188��TDר�ã�
	 *            </p>
	 *            <p>
	 *            ��ͨ�ĺŶΣ�130��131��132��155��156�������ר�ã���185��δ���ã���186��3g��
	 *            </p>
	 *            <p>
	 *            ���ŵĺŶΣ�133��153��180��δ���ã���189
	 *            </p>
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkMobile(String mobile) {
		String regex = "(\\+\\d+)?1[3458]\\d{9}$";
		return Pattern.matches(regex, mobile);
	}

	/**
	 * ��֤�̶��绰����
	 * 
	 * @param phone
	 *            �绰���룬��ʽ�����ң��������绰���� + ���ţ����д��룩 + �绰���룬�磺+8602085588447
	 *            <p>
	 *            <b>���ң������� ���� ��</b>��ʶ�绰����Ĺ��ң��������ı�׼���ң����������롣�������� 0 �� 9
	 *            ��һλ���λ���֣� ����֮���ǿո�ָ��Ĺ��ң����������롣
	 *            </p>
	 *            <p>
	 *            <b>���ţ����д��룩��</b>����ܰ���һ�������� 0 �� 9 �����֣���������д������Բ���š���
	 *            �Բ�ʹ�õ�������д���Ĺ��ң�����������ʡ�Ը������
	 *            </p>
	 *            <p>
	 *            <b>�绰���룺</b>������� 0 �� 9 ��һ����������
	 *            </p>
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkPhone(String phone) {
		String regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$";
		return Pattern.matches(regex, phone);
	}

	/**
	 * ��֤�������������͸�������
	 * 
	 * @param digit
	 *            һλ���λ0-9֮�������
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkDigit(String digit) {
		String regex = "\\-?[1-9]\\d+";
		return Pattern.matches(regex, digit);
	}

	/**
	 * ��֤�����͸�����������������������������
	 * 
	 * @param decimals
	 *            һλ���λ0-9֮��ĸ��������磺1.23��233.30
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkDecimals(String decimals) {
		String regex = "\\-?[1-9]\\d+(\\.\\d+)?";
		return Pattern.matches(regex, decimals);
	}

	/**
	 * ��֤�հ��ַ�
	 * 
	 * @param blankSpace
	 *            �հ��ַ����������ո�\t��\n��\r��\f��\x0B
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkBlankSpace(String blankSpace) {
		String regex = "\\s+";
		return Pattern.matches(regex, blankSpace);
	}

	/**
	 * ��֤����
	 * 
	 * @param chinese
	 *            �����ַ�
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkChinese(String chinese) {
		String regex = "^[\u4E00-\u9FA5]+$";
		return Pattern.matches(regex, chinese);
	}

	/**
	 * ��֤���ڣ������գ�
	 * 
	 * @param birthday
	 *            ���ڣ���ʽ��1992-09-03����1992.09.03
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkBirthday(String birthday) {
		String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
		return Pattern.matches(regex, birthday);
	}

	/**
	 * ��֤URL��ַ
	 * 
	 * @param url
	 *            ��ʽ��http://blog.csdn.net:80/xyang81/article/details/7705960? ��
	 *            http://www.csdn.net:80
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkURL(String url) {
		String regex = "(https?://(w{3}\\.)?)?\\w+\\.\\w+(\\.[a-zA-Z]+)*(:\\d{1,5})?(/\\w*)*(\\??(.+=.*)?(&.+=.*)?)?";
		return Pattern.matches(regex, url);
	}

	/**
	 * <pre>
	 * ��ȡ��ַ URL ��һ������
	 * http://www.zuidaima.com/share/1550463379442688.htm ->> zuidaima.com
	 * </pre>
	 * 
	 * @param url
	 * @return
	 */
	public static String getDomain(String url) {
		Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)", Pattern.CASE_INSENSITIVE);
		// ��ȡ����������
		// Pattern
		// p=Pattern.compile("[^//]*?\\.(com|cn|net|org|biz|info|cc|tv)",
		// Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		return matcher.group();
	}

	/**
	 * ƥ���й���������
	 * 
	 * @param postcode
	 *            ��������
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkPostcode(String postcode) {
		String regex = "[1-9]\\d{5}";
		return Pattern.matches(regex, postcode);
	}

	/**
	 * ƥ��IP��ַ(��ƥ�䣬��ʽ���磺192.168.1.1��127.0.0.1��û��ƥ��IP�εĴ�С)
	 * 
	 * @param ipAddress
	 *            IPv4��׼��ַ
	 * @return ��֤�ɹ�����true����֤ʧ�ܷ���false
	 */
	public static boolean checkIpAddress(String ipAddress) {
		String regex = "[1-9](\\d{1,2})?\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))\\.(0|([1-9](\\d{1,2})?))";
		return Pattern.matches(regex, ipAddress);
	}

	/**
	 * ��Сд���е�������ʽ����
	 * 
	 * @param source
	 *            �����Դ�ַ���
	 * @param regexp
	 *            �����������ʽ
	 * @return ���Դ�ַ�������Ҫ�󷵻���,���򷵻ؼ�
	 */
	public static boolean isHardRegexpValidate(String source, String regexp) {

		return Pattern.matches(regexp, source);
	}
	
	public static void main(String[] args) {
		boolean result=isHardRegexpValidate("100", money);
		System.out.println(result);
	}

}
