package com.code.generactor.util;

/**
 * 基本工具类
 * @author xiaoHai
 *
 */
public class BaseUtil {
	
	/**
	 * 给定字符串是否为null
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str==null || "".equals(str) || str.length()==0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 数据库表名转java类名
	 * @param tableName
	 * @return
	 */
	public static String tableNameToJava(String tableName) {
		if(isEmpty(tableName)) {
			return "";
		}
		return getTableName(tableName, "_");
	}

	/**
	 * java类名转首字符小写
	 * @param beanName
	 * @return
	 */
	public static String beanNameToLower(String beanName) {
		if(isEmpty(beanName)) {
			return "";
		}
		return beanName.substring(0, 1).toLowerCase()+beanName.substring(1);
	}

	/**
	 * 数据库列名转java属性名
	 * @param columnName
	 * @return
	 */
	public static String columnNameToJava(String columnName) {
		if(isEmpty(columnName)) {
			return "";
		}

		return getColumnName(columnName, "_");
	}
	
	/**
	 * getter setter属性名称
	 * @param columnName
	 * @return
	 */
	public static String columnToGetSet(String columnName) {
		if(isEmpty(columnName)) {
			return "";
		}
		return getColumnFunc(columnName,"_");
	}
	
	
	/**
	 * 数据库表名转驼峰
	 * @param tableName
	 * @param regex
	 * @return
	 */
	private static String getTableName(String tableName,String regex) {
		StringBuilder result = new StringBuilder("");
		if(tableName.contains(regex)) {
			String[] tmp = tableName.split(regex);
			if(tmp!=null && tmp.length>1) {
				for(int i=0;i<tmp.length;i++) {
					if(tmp[i].length()<1) {
						continue;
					}
					result.append(tmp[i].substring(0, 1).toUpperCase()+tmp[i].substring(1));
				}
			}
			return result.toString();
		}else {
			return tableName.substring(0, 1).toUpperCase()+tableName.substring(1);
		}
	}
	
	/**
	 * 数据库列名转驼峰
	 * @param column
	 * @param regex
	 * @return
	 */
	private static String getColumnName(String column,String regex) {
		StringBuilder result = new StringBuilder("");
		if(column.contains(regex)) {
			String[] tmp = column.split(regex);
			if(tmp!=null && tmp.length>1) {
				for(int i=0;i<tmp.length;i++) {
					if(i==0) {
						result.append(tmp[i]);
						continue;
					}
					result.append(tmp[i].substring(0, 1).toUpperCase()+tmp[i].substring(1));
				}
			}
			return result.toString();
		}else {
			return column;
		}
	}
	
	/**
	 * getter setter名称
	 * @param column
	 * @param regex
	 * @return
	 */
	private static String getColumnFunc(String column,String regex) {
		StringBuilder result = new StringBuilder("");
		if(column.contains(regex)) {
			String[] tmp = column.split(regex);
			if(tmp!=null && tmp.length>1) {
				for(int i=0;i<tmp.length;i++) {
					result.append(tmp[i].substring(0, 1).toUpperCase()+tmp[i].substring(1));
				}
			}
			return result.toString();
		}else {
			return column.substring(0,1).toUpperCase() + column.substring(1);
		}
	}
	
	/**
	 * sql类型转java类型
	 * @param type
	 * @return
	 */
	public static String sqlTypeToJava(String type) {
		String result = "";
		switch (type.toLowerCase()) {
			case "int":
			case "tinyint":
				result = "Integer";
				break;
			case "bit":
				result = "Boolean";
				break;
			case "bigint":
				result = "Long";
				break;
			case "varchar":
			case "char":
			case "text":
			case "mediumtext":
			case "longtext":
				result = "String";
				break;
			case "datetime":
			case "timestamp":
			case "date":
				result = "Date";
				break;
			case "double":
				result = "Double";
				break;
			case "float":
				result = "Float";
				break;
			case "decimal":
				result = "BigDecimal";
				break;
			default:
				result = "";
				break;
		}
		return result;
	}

	/**
	 * sql类型转xml
	 * @param type
	 * @return
	 */
	public static String sqlTypeToXml(String type) {
		String result = "";
		switch (type.toLowerCase()) {
			case "int":
				result = "INTEGER";
				break;
			case "bit":
				result = "BOOLEAN";
				break;
			case "tinyint":
				result = "TINYINT";
				break;
			case "bigint":
				result = "BIGINT";
				break;
			case "varchar":
			case "text":
			case "mediumtext":
			case "longtext":
				result = "VARCHAR";
				break;
			case "datetime":
			case "timestamp":
				result = "TIMESTAMP";
				break;
			case "date":
				result = "DATE";
				break;
			case "double":
				result = "DOUBLE";
				break;
			case "float":
				result = "Float";
				break;
			case "char":
				result = "CHAR";
				break;
			case "decimal":
				result = "DECIMAL";
				break;
			default:
				result = "Object";
				break;
		}
		return result;
	}
	


	/**
	 * 判断数组中是否包含某个元素
	 * @param ignoreList
	 * @param name
	 * @return
	 */
	public static boolean hasItem(String[] ignoreList, String name) {
		if(ignoreList==null || ignoreList.length==0) {
			return false;
		}else {
			for(String s : ignoreList) {
				if(s.contentEquals(name)) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		//System.out.println(getColumnName("is_delete_hello_", "_"));
		//System.out.println(getTableName("order_info","_"));
		//System.out.println(beanNameToLower("OrderInfo"));
		System.out.println(tableNameToJava("music"));
	}

}
