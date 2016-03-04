package ic.vela.ibridge.test.selector.sessiontest;


/*==================================================================*/
/**
 * 테스트 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SocketChannelAttachObject
 * @author  강석
 * @version 1.0, 2013/06/13
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class AttachObject
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
	private String  strName          = null;
	private int     iCount           = 0;
	
	/*==================================================================*/
	/**
	 * constructor
	 * @param strName
	 */
	/*------------------------------------------------------------------*/
	public AttachObject(String strName)
	{
		boolean flag = true;
		
		if (flag) {
			this.strName = strName;
			this.iCount = 0;
		}
	}
	
	public AttachObject()
	{
		this("IMSI");
	}

	/*==================================================================*/
	/*------------------------------------------------------------------*/
	public void setName(String strName)
	{
		boolean flag = true;
		
		if (flag) {
			this.strName = strName;
		}
	}
	
	public String getName()
	{
		boolean flag = true;
		
		if (flag) {
		}
		
		return this.strName;
	}
	
	public void setCount(int iCount)
	{
		boolean flag = true;
		
		if (flag) {
			this.iCount = iCount;
		}
	}
	
	public int getCount()
	{
		boolean flag = true;
		
		if (flag) {
		}
		
		return this.iCount;
	}
	
	public int increaseCount()
	{
		boolean flag = true;
		
		if (flag) {
			this.iCount ++;
		}
		
		return this.iCount;
	}
	
	public String getMessage()
	{
		boolean flag = true;
		
		String strMsg = null;
		
		if (flag) {
			strMsg = String.format("%010d", iCount);
		}
		
		return strMsg;
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
		
		if (flag) {
			AttachObject obj = new AttachObject();
			obj.setName("KANG_SEOK");
			
			for (int i=0; i < 10; i++) {
				obj.increaseCount();
				
				System.out.println("[" + obj.getMessage() + "]");
			}
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
