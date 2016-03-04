package ic.vela.ibridge.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedSet;


/*==================================================================*/
/**
 * SortedSetTestMain 클래스
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    SortedSetTestMain
 * @author  강석
 * @version 1.0, 2013/06/17
 * @since   jdk1.6.0_45
 * 
 * 
 * - Arrays ArrayList
 * - Set Map
 * - HashSet HashMap Hashtable
 * - SortedSet SortedMap
 * - LinkedHashSet LinkedHashMap LinkedList
 * - Collection Collections
 * 
 */
/*==================================================================*/
public class SortedSetTestMain
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
	
	/*==================================================================*/
	/**
	 * constructor
	 * @param strFepId
	 */
	/*------------------------------------------------------------------*/
	public SortedSetTestMain()
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {

		}
	}
	
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////
	
	/*==================================================================*/
	/**
	 * test01 static method
	 */
	/*------------------------------------------------------------------*/
	private void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {    // DATE.2013.06.17
			try {
				/*
				 *  SortedSet Test 01-1
				 */
				
				HashMap<String,String> hashMap = new HashMap<String,String>();
				
				hashMap.put("key181", "val543");
				hashMap.put("key856", "val543");
				hashMap.put("key552", "val543");
				hashMap.put("key063", "val543");
				hashMap.put("key508", "val543");
				hashMap.put("key614", "val543");
				hashMap.put("key935", "val543");
				hashMap.put("key261", "val543");
				hashMap.put("key320", "val543");
				hashMap.put("key460", "val543");
				hashMap.put("key624", "val543");
				
				SortedSet<String> setKeys = (SortedSet<String>) hashMap.keySet();
				Iterator<String> iterKeys = setKeys.iterator();
				
				while (iterKeys.hasNext()) {
					String key = iterKeys.next();
					
					System.out.println(">" + key);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
		
		switch (1) {
		case 1:
			if (flag) {
				if (flag) {
					new SortedSetTestMain().test01();
					System.out.println("-----------------------------------------------------------");
				}
			}
			break;
		default:
			break;
		}
	}
}
/*==================================================================*/
/*------------------------------------------------------------------*/
