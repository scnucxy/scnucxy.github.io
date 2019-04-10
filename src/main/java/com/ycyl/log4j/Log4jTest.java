package com.ycyl.log4j;

import org.apache.log4j.Logger;

/**log4j日志框架测试类
 * @author ycyl
 *
 */
public class Log4jTest {

	private static final Logger log = Logger.getLogger(Log4jTest.class);
	
	public static void main(String[] args) {
           log.info("ceshi");
           log.error("erroe");
           log.info("ceshi");
           log.error("erroe");
           log.info("ceshi");
           log.error("erroe");
           log.info("ceshi");
           log.error("erroe");
	}

}
