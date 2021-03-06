package ic.vela.ibridge.base.seq;


/*==================================================================*/
/**
 * 시스템 환경변수 처리 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    IBridgeParameter
 * @author  강석
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 */
/*==================================================================*/
public class SeqGender 
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables
	 */
	/*------------------------------------------------------------------*/
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/
	private static SeqGender seqGender = null;
	
	private int iSeqNo = 0;
	
	/*==================================================================*/
	/**
	 * constructor
	 */
	/*------------------------------------------------------------------*/
	private SeqGender()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			;
		}
	}
	
	/*==================================================================*/
	/**
	 * to get a instance of the IBridgeParameter
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public synchronized static SeqGender getInstance()
	/*------------------------------------------------------------------*/
	{
		if (seqGender == null) {
			seqGender = new SeqGender();
			seqGender.iSeqNo = 0;
		}
		
		return seqGender;
	}
	
	/*==================================================================*/
	/**
	 * to get the sequence from the object
	 * increase the sequence number
	 * @return
	 */
	/*------------------------------------------------------------------*/
	public synchronized int getInt()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		int ret = 0;
		
		if (flag) {
			iSeqNo ++;
			ret = iSeqNo;
		}
		
		return ret;
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
		
		switch (1) {
		case 1:
			if (flag) {
				;
			}
			break;
		default:
			if (flag) {
			}
			break;
		}
	}
}

/*==================================================================*/
/*------------------------------------------------------------------*/
