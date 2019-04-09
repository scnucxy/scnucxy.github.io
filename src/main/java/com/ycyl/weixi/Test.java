package com.ycyl.weixi;

class Other{
	
	{
		System.out.println("对象初始化块");
		
	}
	
	Other(){
		System.out.println("other()构造函数");
	}
	
	Other(int i){
		this();
		System.out.println("other(int i)构造函数");
	}
	
	static {
		System.out.println("静态代码块");
	}
}



public class Test {

	public static void main(String[] args) {
          new Other(1);
	}

}
