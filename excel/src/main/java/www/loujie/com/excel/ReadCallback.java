package www.loujie.com.excel;

public interface ReadCallback<T> {

	/**
	 * 将excel中的行信息存入一个对象中
	 * 
	 * @param rowNO
	 *            行号,行信息在Excel所楚的位置
	 * @param cells
	 *            行信息,将行信息转换成了String[]
	 * @return
	 */
	T setRowValue(int rowNO, String[] cells);

	// 例子
/*	ReadCallback<HashMap<String, Object>> callback = new ReadCallback<HashMap<String, Object>>() {
		@Override
		public HashMap<String, Object> setRowValue(int rowNO, String[] cells) {
			HashMap<String, Object> returnMap = new HashMap<>();
			returnMap.put("hanghao", rowNO);
			returnMap.put("name", cells[0]);
			returnMap.put("phone", cells[1]);
			returnMap.put("remark", cells[2]);
			return returnMap;
		}
	};*/

}
