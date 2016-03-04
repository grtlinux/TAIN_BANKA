package ic.vela.ibridge.serverbatch.ksi51.common;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/*==================================================================*/
/**
 * BatchCommonHeader 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    BatchCommonHeader
 * @author  강석
 * @version 1.0, 2013/07/06
 * @since   jdk1.6.0_45
 * 
 * 
 * -Dsystem.ib.param.basefolder=D:/KANG/WORK/workspace/IB_Vela.2/server/
 * 
 * 환경파일 : D:/KANG/WORK/workspace/IB_Vela.2/server/ bat/cfg/ bat.properties
 *  
 */
/*==================================================================*/
public class BatchCommonHeader
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	private static final String        CHARSET             = "EUC-KR";
	private static final int           HEADERLENGTH        = 190;
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	private ByteBuffer byteHeaderBuffer = ByteBuffer.allocate(HEADERLENGTH);
	
	private byte[] byteHeader     = null;
	
	private byte[] byteTRX_ID     = new byte[ 9];    // TRX ID
	private byte[] byteSYS_ID     = new byte[ 3];    // SYSTEM ID
	private byte[] byteDOC_LEN    = new byte[10];    // 전체전문길이
	private byte[] byteDAT_GBN    = new byte[ 1];    // 자료구분
	private byte[] byteINS_ID     = new byte[ 3];    // 보험사기관 ID
	private byte[] byteBAK_CLS    = new byte[ 2];    // 은행구분
	private byte[] byteBAK_ID     = new byte[ 3];    // 은행 ID
	private byte[] byteDOC_CODE   = new byte[ 4];    // 전문종별 코드
	private byte[] byteBIZ_CODE   = new byte[ 6];    // 거래구분 코드
	private byte[] byteTRA_FLAG   = new byte[ 1];    // 송수신 FLAG    은행 <-> 보험(->1, <- 5),보험<->은행(->2, <-6)
	private byte[] byteDOC_STATUS = new byte[ 3];    // STATUS
	private byte[] byteRET_CODE   = new byte[ 3];    // 응답코드
	private byte[] byteSND_DATE   = new byte[ 8];    // 전문전송일
	private byte[] byteSND_TIME   = new byte[ 6];    // 전문전송시간
	private byte[] byteBAK_DOCSEQ = new byte[ 8];    // 은행 전문번호
	private byte[] byteINS_DOCSEQ = new byte[ 8];    // 보험사 전문번호
	private byte[] byteTXN_DATE   = new byte[ 8];    // 거래발생일
	private byte[] byteTOT_FILE   = new byte[ 2];    // 전체 파일 수
	private byte[] byteCUR_FILE   = new byte[ 2];    // 현재 파일 순번
	private byte[] byteDOC_NAME   = new byte[30];    // 현재 파일명
	private byte[] byteCUR_TOT    = new byte[ 2];    // 현재파일전체전문수
	private byte[] byteCUR_NOW    = new byte[ 2];    // 현재파일현재순번
	private byte[] byteDOC_CASE   = new byte[ 6];    // 전문 CASE 명
	private byte[] byteBAK_EXT    = new byte[30];    // 은행추가정의
	private byte[] byteINS_EXT    = new byte[30];    // 보험사추가정의

	/*==================================================================*/
	/**
	 * constructor
	 * 
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public BatchCommonHeader() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			
			/*
			 * 날짜와 시간을 얻는다.
			 */
			String strDate = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
			String strTime = new SimpleDateFormat("HHmmss", Locale.KOREA).format(new Date());

			/*
			 * 각 필드의 default 값을 세팅한다.
			 */
			byteTRX_ID     = String.format("%-9.9s"  , ""         ).getBytes(CHARSET);
			byteSYS_ID     = String.format("%-3.3s"  , "FTP"      ).getBytes(CHARSET);
			byteDOC_LEN    = String.format("%010d"   , 190        ).getBytes(CHARSET);
			byteDAT_GBN    = String.format("%-1.1s"  , "T"        ).getBytes(CHARSET);
			byteINS_ID     = String.format("%-3.3s"  , "L01"      ).getBytes(CHARSET);
			byteBAK_CLS    = String.format("%-2.2s"  , "02"       ).getBytes(CHARSET);
			byteBAK_ID     = String.format("%-3.3s"  , "269"      ).getBytes(CHARSET);
			byteDOC_CODE   = String.format("%-4.4s"  , "0800"     ).getBytes(CHARSET);
			byteBIZ_CODE   = String.format("%-6.6s"  , "000100"   ).getBytes(CHARSET);
			byteTRA_FLAG   = String.format("%-1.1s"  , "1"        ).getBytes(CHARSET);
			byteDOC_STATUS = String.format("%-3.3s"  , ""         ).getBytes(CHARSET);
			byteRET_CODE   = String.format("%-3.3s"  , "000"      ).getBytes(CHARSET);
			byteSND_DATE   = String.format("%-8.8s"  , strDate    ).getBytes(CHARSET);
			byteSND_TIME   = String.format("%-6.6s"  , strTime    ).getBytes(CHARSET);
			byteBAK_DOCSEQ = String.format("%-8.8s"  , "00000000" ).getBytes(CHARSET);
			byteINS_DOCSEQ = String.format("%-8.8s"  , "00000000" ).getBytes(CHARSET);
			byteTXN_DATE   = String.format("%-8.8s"  , strDate    ).getBytes(CHARSET);
			byteTOT_FILE   = String.format("%-2.2s"  , ""         ).getBytes(CHARSET);
			byteCUR_FILE   = String.format("%-2.2s"  , ""         ).getBytes(CHARSET);
			byteDOC_NAME   = String.format("%-30.30s", ""         ).getBytes(CHARSET);
			byteCUR_TOT    = String.format("%-2.2s"  , ""         ).getBytes(CHARSET);
			byteCUR_NOW    = String.format("%-2.2s"  , ""         ).getBytes(CHARSET);
			byteDOC_CASE   = String.format("%-6.6s"  , ""         ).getBytes(CHARSET);
			byteBAK_EXT    = String.format("%-30.30s", ""         ).getBytes(CHARSET);
			byteINS_EXT    = String.format("%-30.30s", ""         ).getBytes(CHARSET);
		}
	}

	/*==================================================================*/
	/**
	 * getter
	 * 
	 * @return
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public byte[] get() throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			
			/*
			 * make header
			 */
			byteHeaderBuffer.rewind();

			byteHeaderBuffer.put(byteTRX_ID    );
			byteHeaderBuffer.put(byteSYS_ID    );
			byteHeaderBuffer.put(byteDOC_LEN   );
			byteHeaderBuffer.put(byteDAT_GBN   );
			byteHeaderBuffer.put(byteINS_ID    );
			byteHeaderBuffer.put(byteBAK_CLS   );
			byteHeaderBuffer.put(byteBAK_ID    );
			byteHeaderBuffer.put(byteDOC_CODE  );
			byteHeaderBuffer.put(byteBIZ_CODE  );
			byteHeaderBuffer.put(byteTRA_FLAG  );
			byteHeaderBuffer.put(byteDOC_STATUS);
			byteHeaderBuffer.put(byteRET_CODE  );
			byteHeaderBuffer.put(byteSND_DATE  );
			byteHeaderBuffer.put(byteSND_TIME  );
			byteHeaderBuffer.put(byteBAK_DOCSEQ);
			byteHeaderBuffer.put(byteINS_DOCSEQ);
			byteHeaderBuffer.put(byteTXN_DATE  );
			byteHeaderBuffer.put(byteTOT_FILE  );
			byteHeaderBuffer.put(byteCUR_FILE  );
			byteHeaderBuffer.put(byteDOC_NAME  );
			byteHeaderBuffer.put(byteCUR_TOT   );
			byteHeaderBuffer.put(byteCUR_NOW   );
			byteHeaderBuffer.put(byteDOC_CASE  );
			byteHeaderBuffer.put(byteBAK_EXT   );
			byteHeaderBuffer.put(byteINS_EXT   );

			this.byteHeader = byteHeaderBuffer.array();
		}
		
		return this.byteHeader;
	}

	/*==================================================================*/
	/**
	 * setter
	 * 
	 * @param byteHeader
	 * @throws Exception
	 */
	/*------------------------------------------------------------------*/
	public void set(byte[] byteHeader) throws Exception
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			/*
			 * byteBuffer를 세팅한다.
			 */
			byteHeaderBuffer = ByteBuffer.wrap(byteHeader);
			
			/*
			 * 각 header 필드 값을 세팅한다.
			 */
			byteHeaderBuffer.rewind();

			byteHeaderBuffer.get(byteTRX_ID    );
			byteHeaderBuffer.get(byteSYS_ID    );
			byteHeaderBuffer.get(byteDOC_LEN   );
			byteHeaderBuffer.get(byteDAT_GBN   );
			byteHeaderBuffer.get(byteINS_ID    );
			byteHeaderBuffer.get(byteBAK_CLS   );
			byteHeaderBuffer.get(byteBAK_ID    );
			byteHeaderBuffer.get(byteDOC_CODE  );
			byteHeaderBuffer.get(byteBIZ_CODE  );
			byteHeaderBuffer.get(byteTRA_FLAG  );
			byteHeaderBuffer.get(byteDOC_STATUS);
			byteHeaderBuffer.get(byteRET_CODE  );
			byteHeaderBuffer.get(byteSND_DATE  );
			byteHeaderBuffer.get(byteSND_TIME  );
			byteHeaderBuffer.get(byteBAK_DOCSEQ);
			byteHeaderBuffer.get(byteINS_DOCSEQ);
			byteHeaderBuffer.get(byteTXN_DATE  );
			byteHeaderBuffer.get(byteTOT_FILE  );
			byteHeaderBuffer.get(byteCUR_FILE  );
			byteHeaderBuffer.get(byteDOC_NAME  );
			byteHeaderBuffer.get(byteCUR_TOT   );
			byteHeaderBuffer.get(byteCUR_NOW   );
			byteHeaderBuffer.get(byteDOC_CASE  );
			byteHeaderBuffer.get(byteBAK_EXT   );
			byteHeaderBuffer.get(byteINS_EXT   );
		}
	}
	
	
	/*==================================================================*/
	/**
	 * getter-1
	 */
	/*------------------------------------------------------------------*/
	public byte[] getByteTRX_ID() {
		return byteTRX_ID;
	}

	public byte[] getByteSYS_ID() {
		return byteSYS_ID;
	}

	public byte[] getByteDOC_LEN() {
		return byteDOC_LEN;
	}

	public byte[] getByteDAT_GBN() {
		return byteDAT_GBN;
	}

	public byte[] getByteINS_ID() {
		return byteINS_ID;
	}

	public byte[] getByteBAK_CLS() {
		return byteBAK_CLS;
	}

	public byte[] getByteBAK_ID() {
		return byteBAK_ID;
	}

	public byte[] getByteDOC_CODE() {
		return byteDOC_CODE;
	}

	public byte[] getByteBIZ_CODE() {
		return byteBIZ_CODE;
	}

	public byte[] getByteTRA_FLAG() {
		return byteTRA_FLAG;
	}

	public byte[] getByteDOC_STATUS() {
		return byteDOC_STATUS;
	}

	public byte[] getByteRET_CODE() {
		return byteRET_CODE;
	}

	public byte[] getByteSND_DATE() {
		return byteSND_DATE;
	}

	public byte[] getByteSND_TIME() {
		return byteSND_TIME;
	}

	public byte[] getByteBAK_DOCSEQ() {
		return byteBAK_DOCSEQ;
	}

	public byte[] getByteINS_DOCSEQ() {
		return byteINS_DOCSEQ;
	}

	public byte[] getByteTXN_DATE() {
		return byteTXN_DATE;
	}

	public byte[] getByteTOT_FILE() {
		return byteTOT_FILE;
	}

	public byte[] getByteCUR_FILE() {
		return byteCUR_FILE;
	}

	public byte[] getByteDOC_NAME() {
		return byteDOC_NAME;
	}

	public byte[] getByteCUR_TOT() {
		return byteCUR_TOT;
	}

	public byte[] getByteCUR_NOW() {
		return byteCUR_NOW;
	}

	public byte[] getByteDOC_CASE() {
		return byteDOC_CASE;
	}

	public byte[] getByteBAK_EXT() {
		return byteBAK_EXT;
	}

	public byte[] getByteINS_EXT() {
		return byteINS_EXT;
	}

	/*==================================================================*/
	/**
	 * getter-2
	 */
	/*------------------------------------------------------------------*/
	public String getStrTRX_ID() throws Exception {
		return new String(byteTRX_ID, CHARSET);
	}

	public String getStrSYS_ID() throws Exception {
		return new String(byteSYS_ID, CHARSET);
	}

	public String getStrDOC_LEN() throws Exception {
		return new String(byteDOC_LEN, CHARSET);
	}

	public String getStrDAT_GBN() throws Exception {
		return new String(byteDAT_GBN, CHARSET);
	}

	public String getStrINS_ID() throws Exception {
		return new String(byteINS_ID, CHARSET);
	}

	public String getStrBAK_CLS() throws Exception {
		return new String(byteBAK_CLS, CHARSET);
	}

	public String getStrBAK_ID() throws Exception {
		return new String(byteBAK_ID, CHARSET);
	}

	public String getStrDOC_CODE() throws Exception {
		return new String(byteDOC_CODE, CHARSET);
	}

	public String getStrBIZ_CODE() throws Exception {
		return new String(byteBIZ_CODE, CHARSET);
	}

	public String getStrTRA_FLAG() throws Exception {
		return new String(byteTRA_FLAG, CHARSET);
	}

	public String getStrDOC_STATUS() throws Exception {
		return new String(byteDOC_STATUS, CHARSET);
	}

	public String getStrRET_CODE() throws Exception {
		return new String(byteRET_CODE, CHARSET);
	}

	public String getStrSND_DATE() throws Exception {
		return new String(byteSND_DATE, CHARSET);
	}

	public String getStrSND_TIME() throws Exception {
		return new String(byteSND_TIME, CHARSET);
	}

	public String getStrBAK_DOCSEQ() throws Exception {
		return new String(byteBAK_DOCSEQ, CHARSET);
	}

	public String getStrINS_DOCSEQ() throws Exception {
		return new String(byteINS_DOCSEQ, CHARSET);
	}

	public String getStrTXN_DATE() throws Exception {
		return new String(byteTXN_DATE, CHARSET);
	}

	public String getStrTOT_FILE() throws Exception {
		return new String(byteTOT_FILE, CHARSET);
	}

	public String getStrCUR_FILE() throws Exception {
		return new String(byteCUR_FILE, CHARSET);
	}

	public String getStrDOC_NAME() throws Exception {
		return new String(byteDOC_NAME, CHARSET);
	}

	public String getStrCUR_TOT() throws Exception {
		return new String(byteCUR_TOT, CHARSET);
	}

	public String getStrCUR_NOW() throws Exception {
		return new String(byteCUR_NOW, CHARSET);
	}

	public String getStrDOC_CASE() throws Exception {
		return new String(byteDOC_CASE, CHARSET);
	}

	public String getStrBAK_EXT() throws Exception {
		return new String(byteBAK_EXT, CHARSET);
	}

	public String getStrINS_EXT() throws Exception {
		return new String(byteINS_EXT, CHARSET);
	}

	/*==================================================================*/
	/**
	 * setter-1
	 */
	/*------------------------------------------------------------------*/
	public void setByteTRX_ID(byte[] byteTRX_ID) {
		this.byteTRX_ID = byteTRX_ID;
	}

	public void setByteSYS_ID(byte[] byteSYS_ID) {
		this.byteSYS_ID = byteSYS_ID;
	}

	public void setByteDOC_LEN(byte[] byteDOC_LEN) {
		this.byteDOC_LEN = byteDOC_LEN;
	}

	public void setByteDAT_GBN(byte[] byteDAT_GBN) {
		this.byteDAT_GBN = byteDAT_GBN;
	}

	public void setByteINS_ID(byte[] byteINS_ID) {
		this.byteINS_ID = byteINS_ID;
	}

	public void setByteBAK_CLS(byte[] byteBAK_CLS) {
		this.byteBAK_CLS = byteBAK_CLS;
	}

	public void setByteBAK_ID(byte[] byteBAK_ID) {
		this.byteBAK_ID = byteBAK_ID;
	}

	public void setByteDOC_CODE(byte[] byteDOC_CODE) {
		this.byteDOC_CODE = byteDOC_CODE;
	}

	public void setByteBIZ_CODE(byte[] byteBIZ_CODE) {
		this.byteBIZ_CODE = byteBIZ_CODE;
	}

	public void setByteTRA_FLAG(byte[] byteTRA_FLAG) {
		this.byteTRA_FLAG = byteTRA_FLAG;
	}

	public void setByteDOC_STATUS(byte[] byteDOC_STATUS) {
		this.byteDOC_STATUS = byteDOC_STATUS;
	}

	public void setByteRET_CODE(byte[] byteRET_CODE) {
		this.byteRET_CODE = byteRET_CODE;
	}

	public void setByteSND_DATE(byte[] byteSND_DATE) {
		this.byteSND_DATE = byteSND_DATE;
	}

	public void setByteSND_TIME(byte[] byteSND_TIME) {
		this.byteSND_TIME = byteSND_TIME;
	}

	public void setByteBAK_DOCSEQ(byte[] byteBAK_DOCSEQ) {
		this.byteBAK_DOCSEQ = byteBAK_DOCSEQ;
	}

	public void setByteINS_DOCSEQ(byte[] byteINS_DOCSEQ) {
		this.byteINS_DOCSEQ = byteINS_DOCSEQ;
	}

	public void setByteTXN_DATE(byte[] byteTXN_DATE) {
		this.byteTXN_DATE = byteTXN_DATE;
	}

	public void setByteTOT_FILE(byte[] byteTOT_FILE) {
		this.byteTOT_FILE = byteTOT_FILE;
	}

	public void setByteCUR_FILE(byte[] byteCUR_FILE) {
		this.byteCUR_FILE = byteCUR_FILE;
	}

	public void setByteDOC_NAME(byte[] byteDOC_NAME) {
		this.byteDOC_NAME = byteDOC_NAME;
	}

	public void setByteCUR_TOT(byte[] byteCUR_TOT) {
		this.byteCUR_TOT = byteCUR_TOT;
	}

	public void setByteCUR_NOW(byte[] byteCUR_NOW) {
		this.byteCUR_NOW = byteCUR_NOW;
	}

	public void setByteDOC_CASE(byte[] byteDOC_CASE) {
		this.byteDOC_CASE = byteDOC_CASE;
	}

	public void setByteBAK_EXT(byte[] byteBAK_EXT) {
		this.byteBAK_EXT = byteBAK_EXT;
	}

	public void setByteINS_EXT(byte[] byteINS_EXT) {
		this.byteINS_EXT = byteINS_EXT;
	}

	/*==================================================================*/
	/**
	 * setter-2
	 */
	/*------------------------------------------------------------------*/
	public void setStrTRX_ID(String strTRX_ID) throws Exception {
		this.byteTRX_ID     = String.format("%-9.9s"  , strTRX_ID    ).getBytes(CHARSET);
	}

	public void setStrSYS_ID(String strSYS_ID) throws Exception {
		this.byteSYS_ID     = String.format("%-3.3s"  , strSYS_ID    ).getBytes(CHARSET);
	}

	public void setStrDOC_LEN(int intDocLen) throws Exception {
		this.byteDOC_LEN    = String.format("%010d"   , intDocLen    ).getBytes(CHARSET);
	}

	public void setStrDAT_GBN(String strDAT_GBN) throws Exception {
		this.byteDAT_GBN    = String.format("%-1.1s"  , strDAT_GBN   ).getBytes(CHARSET);
	}

	public void setStrINS_ID(String strINS_ID) throws Exception {
		this.byteINS_ID     = String.format("%-3.3s"  , strINS_ID    ).getBytes(CHARSET);
	}

	public void setStrBAK_CLS(String strBAK_CLS) throws Exception {
		this.byteBAK_CLS    = String.format("%-2.2s"  , strBAK_CLS   ).getBytes(CHARSET);
	}

	public void setStrBAK_ID(String strBAK_ID) throws Exception {
		this.byteBAK_ID     = String.format("%-3.3s"  , strBAK_ID    ).getBytes(CHARSET);
	}

	public void setStrDOC_CODE(String strDOC_CODE) throws Exception {
		this.byteDOC_CODE   = String.format("%-4.4s"  , strDOC_CODE  ).getBytes(CHARSET);
	}

	public void setStrBIZ_CODE(String strBIZ_CODE) throws Exception {
		this.byteBIZ_CODE   = String.format("%-6.6s"  , strBIZ_CODE  ).getBytes(CHARSET);
	}

	public void setStrTRA_FLAG(String strTRA_FLAG) throws Exception {
		this.byteTRA_FLAG   = String.format("%-1.1s"  , strTRA_FLAG  ).getBytes(CHARSET);
	}

	public void setStrDOC_STATUS(String strDOC_STATUS) throws Exception {
		this.byteDOC_STATUS = String.format("%-3.3s"  , strDOC_STATUS).getBytes(CHARSET);
	}

	public void setStrRET_CODE(String strRET_CODE) throws Exception {
		this.byteRET_CODE   = String.format("%-3.3s"  , strRET_CODE  ).getBytes(CHARSET);
	}

	public void setStrSND_DATE(String strSND_DATE) throws Exception {
		this.byteSND_DATE   = String.format("%-8.8s"  , strSND_DATE  ).getBytes(CHARSET);
	}

	public void setStrSND_TIME(String strSND_TIME) throws Exception {
		this.byteSND_TIME   = String.format("%-6.6s"  , strSND_TIME  ).getBytes(CHARSET);
	}

	public void setStrBAK_DOCSEQ(String strBAK_DOCSEQ) throws Exception {
		this.byteBAK_DOCSEQ = String.format("%-8.8s"  , strBAK_DOCSEQ).getBytes(CHARSET);
	}

	public void setStrINS_DOCSEQ(String strINS_DOCSEQ) throws Exception {
		this.byteINS_DOCSEQ = String.format("%-8.8s"  , strINS_DOCSEQ).getBytes(CHARSET);
	}

	public void setStrTXN_DATE(String strTXN_DATE) throws Exception {
		this.byteTXN_DATE   = String.format("%-8.8s"  , strTXN_DATE  ).getBytes(CHARSET);
	}

	public void setStrTOT_FILE(String strTOT_FILE) throws Exception {
		this.byteTOT_FILE   = String.format("%-2.2s"  , strTOT_FILE  ).getBytes(CHARSET);
	}

	public void setStrCUR_FILE(String strCUR_FILE) throws Exception {
		this.byteCUR_FILE   = String.format("%-2.2s"  , strCUR_FILE  ).getBytes(CHARSET);
	}

	public void setStrDOC_NAME(String strDOC_NAME) throws Exception {
		this.byteDOC_NAME   = String.format("%-30.30s", strDOC_NAME  ).getBytes(CHARSET);
	}

	public void setStrCUR_TOT(String strCUR_TOT) throws Exception {
		this.byteCUR_TOT    = String.format("%-2.2s"  , strCUR_TOT   ).getBytes(CHARSET);
	}

	public void setStrCUR_NOW(String strCUR_NOW) throws Exception {
		this.byteCUR_NOW    = String.format("%-2.2s"  , strCUR_NOW   ).getBytes(CHARSET);
	}

	public void setStrDOC_CASE(String strDOC_CASE) throws Exception {
		this.byteDOC_CASE   = String.format("%-6.6s"  , strDOC_CASE  ).getBytes(CHARSET);
	}

	public void setStrBAK_EXT(String strBAK_EXT) throws Exception {
		this.byteBAK_EXT    = String.format("%-30.30s", strBAK_EXT   ).getBytes(CHARSET);
	}

	public void setStrINS_EXT(String strINS_EXT) throws Exception {
		this.byteINS_EXT    = String.format("%-30.30s", strINS_EXT   ).getBytes(CHARSET);
	}

	/*==================================================================*/
	/**
	 * 
	 */
	/*------------------------------------------------------------------*/
	public String toString()
	{
		boolean flag = true;
		
		String strHeader = null;
		
		if (flag) {
			
			/*
			 * make header
			 */
			byteHeaderBuffer.rewind();
			
			byteHeaderBuffer.put(byteTRX_ID    );
			byteHeaderBuffer.put(byteSYS_ID    );
			byteHeaderBuffer.put(byteDOC_LEN   );
			byteHeaderBuffer.put(byteDAT_GBN   );
			byteHeaderBuffer.put(byteINS_ID    );
			byteHeaderBuffer.put(byteBAK_CLS   );
			byteHeaderBuffer.put(byteBAK_ID    );
			byteHeaderBuffer.put(byteDOC_CODE  );
			byteHeaderBuffer.put(byteBIZ_CODE  );
			byteHeaderBuffer.put(byteTRA_FLAG  );
			byteHeaderBuffer.put(byteDOC_STATUS);
			byteHeaderBuffer.put(byteRET_CODE  );
			byteHeaderBuffer.put(byteSND_DATE  );
			byteHeaderBuffer.put(byteSND_TIME  );
			byteHeaderBuffer.put(byteBAK_DOCSEQ);
			byteHeaderBuffer.put(byteINS_DOCSEQ);
			byteHeaderBuffer.put(byteTXN_DATE  );
			byteHeaderBuffer.put(byteTOT_FILE  );
			byteHeaderBuffer.put(byteCUR_FILE  );
			byteHeaderBuffer.put(byteDOC_NAME  );
			byteHeaderBuffer.put(byteCUR_TOT   );
			byteHeaderBuffer.put(byteCUR_NOW   );
			byteHeaderBuffer.put(byteDOC_CASE  );
			byteHeaderBuffer.put(byteBAK_EXT   );
			byteHeaderBuffer.put(byteINS_EXT   );

			this.byteHeader = byteHeaderBuffer.array();

			try {
				strHeader = new String(this.byteHeader, CHARSET);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return strHeader;
	}
	
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	/*==================================================================*/
	/*------------------------------------------------------------------*/
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////

	/*==================================================================*/
	/**
	 * test01
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			System.out.println("[" + String.format("%-3.3s", "12345678") + "]");
			System.out.println("[" + String.format("%-3.3s", "12") + "]");
		}
	}
	
	/*==================================================================*/
	/**
	 * test02
	 */
	/*------------------------------------------------------------------*/
	private static void test02() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			try {
				BatchCommonHeader commonHeader = new BatchCommonHeader();
				
				System.out.println("[" + commonHeader + "]");

				commonHeader.setStrDOC_CODE("0600");
				commonHeader.setStrBIZ_CODE("061000");
				
				System.out.println("[" + commonHeader + "]");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/*==================================================================*/
	/**
	 * default test : 단순한 문자 출력
	 */
	/*------------------------------------------------------------------*/
	private static void default_test00() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			System.out.println("Default function");
		}
	}
	
	/*==================================================================*/
	/**
	 * main entry point
	 * @param args
	 */
	/*------------------------------------------------------------------*/
	public static void main(String[] args) 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		switch (2) {
		case 1:
			if (flag) {
				test01();
			}
			break;
		case 2:
			if (flag) {
				test02();
			}
			break;
		default:
			if (flag) {
				default_test00();
			}
			break;
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
