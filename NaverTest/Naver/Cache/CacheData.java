package cn.tedu.naver.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheData {
	
	//�������
	Map<String, Object> cacheData = new HashMap<String, Object>(); 
	//��д�� 
	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(); 
	 
	public static void main(String[] args) {  
	                    
	}  
	  
	public Object getData(String key){  
		Object value = null;
		//�Ӷ��� 
		rwl.readLock().lock(); 
		value = cacheData.get(key);//��ȡֵ  
		if(value == null){//�ж��Ƿ����ֵ  
			cacheData.put(key, value);
			
		}

		//��д��
		rwl.writeLock().unlock();
		//�ض��� 
		rwl.readLock().lock(); 
			 
		return value;
	}
}
