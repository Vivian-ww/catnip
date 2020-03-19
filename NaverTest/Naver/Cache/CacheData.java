package cn.tedu.naver.Cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class CacheData {
	
	//缓存对象
	Map<String, Object> cacheData = new HashMap<String, Object>(); 
	//读写锁 
	ReentrantReadWriteLock rwl = new ReentrantReadWriteLock(); 
	 
	public static void main(String[] args) {  
	                    
	}  
	  
	public Object getData(String key){  
		Object value = null;
		//加读锁 
		rwl.readLock().lock(); 
		value = cacheData.get(key);//获取值  
		//加写锁
		rwl.writeLock().lock();
		if(value == null){//判断是否存在值  
			cacheData.put(key, value);
			
		}

		//关写锁
		rwl.writeLock().unlock();
		//关读锁 
		rwl.readLock().lock(); 
			 
		return value;
	}
}
