package com.weiwork.common.office;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class WrodToHtml {
	public static final int WORD_HTML = 8;
	public static final int WORD_HTML_FILTER = 10;
	public static final int WORD_TXT = 7;
	public static final int EXCEL_HTML = 44;
	public static final int EXCEL_XML = 46;
	public static final int EXCEL_43 = 43; // Excel 2003 测试可用
	
	/**
	 * WORD转HTML
	 * 
	 * @param docfile
	 *            WORD文件全路径
	 * @param htmlfile
	 *            转换后HTML存放路径
	 */
	public static void wordToHtml(String docfile, String htmlfile) {
		// 初始化
		ComThread.InitSTA();
		ActiveXComponent app = new ActiveXComponent("Word.Application"); // 启动word
		try {
			app.setProperty("Visible", new Variant(false));
			Dispatch docs = app.getProperty("Documents").toDispatch();
			Dispatch doc = Dispatch.invoke(
					docs,
					"Open",
					Dispatch.Method,
					new Object[] { docfile, new Variant(false),
							new Variant(true) }, new int[1]).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
					htmlfile, new Variant(WORD_HTML_FILTER) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(doc, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			app.invoke("Quit", new Variant[] {});
			ComThread.Release();
		}
	}

	/**
	 * WORD转HTML
	 * 
	 * @param docfile
	 *            WORD文件全路径
	 * @param htmlfile
	 *            转换后HTML存放路径
	 */
	public static void wordToHtml(String docfile, String htmlfile, int type) {
		// 初始化
		ComThread.InitSTA();
		ActiveXComponent app = new ActiveXComponent("Word.Application"); // 启动word
		try {
			app.setProperty("Visible", new Variant(false));
			Dispatch docs = app.getProperty("Documents").toDispatch();
			Dispatch doc = Dispatch.invoke( docs, "Open", Dispatch.Method, new Object[] { docfile, new Variant(false), new Variant(true) }, new int[1]).toDispatch();
			Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] {
					htmlfile, new Variant(type) }, new int[1]);
			Variant f = new Variant(false);
			Dispatch.call(doc, "Close", f);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			app.invoke("Quit", new Variant[] {});
			ComThread.Release();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 10; i < 11; i++) {
			String from = "E:\\tmp\\11" + i + ".docx";
			String to = "E:\\tmp\\11" + i + ".html";
			System.out.println("from:".concat(from.concat(";to:").concat(to)));
			wordToHtml(from , to , i);
		}
		// System.out.println(System.getProperty("java.library.path"));
	}

}
