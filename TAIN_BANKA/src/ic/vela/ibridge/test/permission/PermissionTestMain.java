package ic.vela.ibridge.test.permission;

import java.io.File;


/*==================================================================*/
/**
 * PermissionTestMain Ŭ����
 * <p>Copyright: Copyright (c) 2013</p>
 * <p>Company: ICA Information and Communication </p>
 *
 * @name    PermissionTestMain
 * @author  ����
 * @version 1.0, 2013/06/09
 * @since   jdk1.6.0_45
 * 
 * 
 * - 
 * 
 */
/*==================================================================*/
public class PermissionTestMain
/*------------------------------------------------------------------*/
{
	/*==================================================================*/
	/*
	 * global static final variables   40                  60
	 */
	/*--------------------------------- ------------------- ------------*/
	
	/*==================================================================*/
	/*
	 * global variables
	 */
	/*------------------------------------------------------------------*/

	/*==================================================================*/
	/**
	 * 
	 */
	/*------------------------------------------------------------------*/
	private static void test01() 
	/*------------------------------------------------------------------*/
	{
		boolean flag = true;
		
		if (flag) {
			
			try {
				/*
				 * ���ϸ��� ��´�.
				 */
				String strTestFile = System.getProperty("system.ib.param.testfile");
				if (strTestFile == null || "".equals(strTestFile)) {
					System.out.println("ERROR : file not defined.....");
					return;
				}
				
				File file = new File(strTestFile);
				/*
				 * ������ ���縦 Ȯ���Ѵ�.
				 */
				if (!file.exists()) {
					System.out.println("ERROR : file not found..[" + strTestFile + "]");
					return;
				}
				
				if (flag) {
					/*
					 * ������ permission�� �����Ѵ�. setWritable(true), setWritable(true, true)
					 */
					if (!flag) {
						if (file.setWritable(true)) {
							System.out.println("SUCCESS : file permission changed...[setWritable(true)]");
						} else {
							System.out.println("FAIL : file permission not changed...[setWritable(true)]");
						}
					} else {
						if (file.setWritable(true, false)) {
							System.out.println("SUCCESS : file permission changed...[setWritable(true,false)]");
						} else {
							System.out.println("FAIL : file permission not changed...[setWritable(true,false)]");
						}
					}
				}
				
				if (!flag) {
					/*
					 * ������ permission�� �����Ѵ�. setExecutable(true), setExecutable(true, true)
					 */
					if (flag) {
						if (file.setExecutable(true)) {
							System.out.println("SUCCESS : file permission changed...[setExecutable(true)]");
						} else {
							System.out.println("FAIL : file permission not changed...[setExecutable(true)]");
						}
					} else {
						if (file.setExecutable(true, true)) {
							System.out.println("SUCCESS : file permission changed...[setExecutable(true,true)]");
						} else {
							System.out.println("FAIL : file permission not changed...[setExecutable(true,true)]");
						}
					}
				}
				
				if (!flag) {
					/*
					 * ������ permission�� �����Ѵ�. setReadable(true), setReadable(true, true)
					 */
					if (!flag) {
						if (file.setReadable(true)) {
							System.out.println("SUCCESS : file permission changed...[setReadable(true)]");
						} else {
							System.out.println("FAIL : file permission not changed...[setReadable(true)]");
						}
					} else {
						if (file.setReadable(true, true)) {
							System.out.println("SUCCESS : file permission changed...[setReadable(true,true)]");
						} else {
							System.out.println("FAIL : file permission not changed...[setReadable(true,true)]");
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/*==================================================================*/
	/**
	 * default test : �ܼ��� ���� ���
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
		
		switch (1) {
		case 1:
			if (flag) {
				test01();
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
