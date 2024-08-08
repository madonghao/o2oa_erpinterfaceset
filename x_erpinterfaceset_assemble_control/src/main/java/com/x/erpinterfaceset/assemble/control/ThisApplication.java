package com.x.erpinterfaceset.assemble.control;

import com.x.base.core.project.Context;

/**
 * 应用初始化及销毁业务处理
 * @author sword
 */
public class ThisApplication {

	protected static Context context;

	public static Context context() {
		return context;
	}

	public static void init() throws Exception {
	}

	public static void destroy() {
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
